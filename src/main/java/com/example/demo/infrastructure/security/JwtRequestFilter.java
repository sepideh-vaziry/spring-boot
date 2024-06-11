package com.example.demo.infrastructure.security;

import com.example.demo.infrastructure.persistence.entity.user.UserEntity;
import com.example.demo.infrastructure.persistence.repository.UserRepository;
import com.example.demo.infrastructure.security.JwtTokenService.TokenInfo;
import com.example.demo.infrastructure.service.user.error.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

  private final UserRepository userRepository;
  private final JwtTokenService jwtTokenService;

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain
  ) throws ServletException, IOException {

    String jwtToken = extractToken(request);

    if (jwtToken != null) {
      TokenInfo tokenInfo = jwtTokenService.getTokenInfo(jwtToken)
          .orElse(null);

      boolean isTokenInfoComplete = tokenInfo != null && tokenInfo.getUserId() != null;

      if (isTokenInfoComplete && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserEntity user = userRepository.findById(tokenInfo.getUserId())
            .orElse(null);

        if (user != null) {

          if (tokenInfo.isValidAsAnAccess(user.getFingerprint())) {
            checkAuthenticationAndFillSecurityContext(request, user);
          }
        }
      }

    }

    filterChain.doFilter(request, response);
  }

  private String extractToken(HttpServletRequest request) {
    String jwtToken = request.getHeader("Authorization");
    if (jwtToken == null) {
      jwtToken = request.getParameter("Authorization");
    }

    if (jwtToken == null) {
      return null;
    }

    String[] splittedToken = jwtToken.split("\\s+");
    if (splittedToken.length < 2) {
      throw new UnauthorizedException();
    }

    return splittedToken[1];
  }

  private void checkAuthenticationAndFillSecurityContext(
      HttpServletRequest request,
      UserEntity user
  ) {
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
        user,
        null,
        user.getAuthorities()
    );

    usernamePasswordAuthenticationToken.setDetails(
        new WebAuthenticationDetailsSource().buildDetails(request)
    );

    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
  }

}
