package com.example.demo.api.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j

public class IpUtils {

  private static final String X_Forwarded_For = "X-FORWARDED-FOR";
  private static final String X_Real_IP = "X-Real-IP";
  private static final String USER_Real_IP = "User-Real-IP";

  public static String getUserIp(HttpServletRequest request) {
    log.debug(
        "X-Real-IP : {} ; X-Forwarded-FOR: {} ; User-Real-IP: {}",
        request.getHeader(X_Real_IP),
        request.getHeader(X_Forwarded_For),
        request.getHeader(USER_Real_IP)
    );

    String ip = request.getHeader(USER_Real_IP);
    if (ip != null && !ip.isBlank()) {
      return ip;
    }

    ip = request.getHeader(X_Real_IP);

    if (ip != null && !ip.isBlank()) {
      return ip;
    }

    ip = request.getHeader(X_Forwarded_For);

    if (ip != null && !ip.isBlank()) {
      return ip.split(",")[0];
    }

    return request.getRemoteAddr();
  }

}
