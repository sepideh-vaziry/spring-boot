package com.example.demo.domain.service.recaptcha;

public interface ReCaptchaService {

  void processWebResponse(String recaptchaResponse, String clientIp);

  void processAndroidResponse(String recaptchaResponse, String clientIp);

  void processIOSResponse(String recaptchaResponse, String clientIp);

}
