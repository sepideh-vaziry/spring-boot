package com.example.demo.infrastructure.service.recaptcha;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.example.demo.domain.service.recaptcha.ReCaptchaService;
import com.example.demo.infrastructure.config.RecaptchaConfig;
import com.example.demo.infrastructure.service.recaptcha.error.InvalidReCaptchaException;
import com.example.demo.infrastructure.service.recaptcha.error.UnavailableReCaptchaException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReCaptchaServiceImpl implements ReCaptchaService {

  private static final String VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s&remoteip=%s";
  private static final String CLIENT_TYPE_WEB = "web";
  private static final String CLIENT_TYPE_ANDROID = "android";
  private static final String CLIENT_TYPE_IOS = "ios";

  private final RecaptchaConfig recaptchaConfig;
  private final ReCaptchaAttemptService recaptchaAttemptService;
  private final RestTemplate restTemplate;

  @Override
  public void processWebResponse(String recaptchaResponse, String clientIp) {
    processResponse(
        recaptchaResponse,
        clientIp,
        recaptchaConfig.getSecret(CLIENT_TYPE_WEB)
    );
  }

  @Override
  public void processAndroidResponse(String recaptchaResponse, String clientIp) {
    processResponse(
        recaptchaResponse,
        clientIp,
        recaptchaConfig.getSecret(CLIENT_TYPE_ANDROID)
    );
  }

  @Override
  public void processIOSResponse(String recaptchaResponse, String clientIp) {
    processResponse(
        recaptchaResponse,
        clientIp,
        recaptchaConfig.getSecret(CLIENT_TYPE_IOS)
    );
  }

  private void processResponse(String recaptchaResponse, String clientIp, String secret) {
    if(recaptchaAttemptService.isBlocked(clientIp)) {
      throw new InvalidReCaptchaException();
    }

    try {
      URI verifyUri = URI.create(VERIFY_URL.formatted(
          secret,
          recaptchaResponse,
          clientIp
      ));

      RecaptchaResponse response = restTemplate.getForObject(verifyUri, RecaptchaResponse.class);
      if (response == null) {
        log.error("ReCaptcha response is Null");

        throw new UnavailableReCaptchaException();
      }

      if (!response.isSuccess()) {
        if (response.hasClientError()) {
          recaptchaAttemptService.reCaptchaFailed(clientIp);
        }

        throw new InvalidReCaptchaException();
      }

    } catch (RestClientResponseException e) {
      log.error("Process of response reCaptcha has been got error", e);

      throw new UnavailableReCaptchaException();
    }

    recaptchaAttemptService.reCaptchaSucceeded(clientIp);
  }

  @Data
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonIgnoreProperties(ignoreUnknown = true)
  @JsonPropertyOrder({
      "success",
      "challenge_ts",
      "hostname",
      "error-codes"
  })
  public static class RecaptchaResponse {

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("challenge_ts")
    private String challengeTs;

    @JsonProperty("hostname")
    private String hostname;

    @JsonProperty("error-codes")
    private ErrorCode[] errorCodes;

    @JsonIgnore
    public boolean hasClientError() {
      ErrorCode[] errors = getErrorCodes();
      if(errors == null) {
        return false;
      }

      for(ErrorCode error : errors) {
        switch (error) {
          case InvalidResponse, MissingResponse, BadRequest -> {
            return true;
          }
        }
      }

      return false;
    }

    static enum ErrorCode {
      MissingSecret,
      InvalidSecret,
      MissingResponse,
      BadRequest,
      InvalidResponse;

      private static final Map<String, ErrorCode> errorsMap = new HashMap<>(4);

      static {
        errorsMap.put("missing-input-secret",   MissingSecret);
        errorsMap.put("invalid-input-secret",   InvalidSecret);
        errorsMap.put("missing-input-response", MissingResponse);
        errorsMap.put("invalid-input-response", InvalidResponse);
      }

      @JsonCreator
      public static ErrorCode forValue(String value) {
        return errorsMap.get(value.toLowerCase());
      }
    }

  }

}
