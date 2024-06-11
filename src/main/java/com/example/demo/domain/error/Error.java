package com.example.demo.domain.error;

import org.springframework.http.HttpStatus;

public abstract class Error extends RuntimeException {

  private ErrorEnum errorEnum;
  private HttpStatus httpStatus;

  public Error() {
  }

  public Error(ErrorEnum errorsEnum) {
    this.errorEnum = errorsEnum;
    this.httpStatus = HttpStatus.valueOf(errorsEnum.getStatus());
  }

  public Error(String message) {
    super(message);
  }

  public Error(ErrorEnum errorEnum, HttpStatus httpStatus) {
    this.errorEnum = errorEnum;
    this.httpStatus = httpStatus;
  }

  public Error(String message, HttpStatus httpStatus) {
    super(message);
    this.httpStatus = httpStatus;
  }

  public String getErrorMessageKey() {
    if (errorEnum != null) {
      return errorEnum.getMessageKey();
    }

    return null;
  }

  public int getDeveloperCode() {
    if (errorEnum != null) {
      return errorEnum.getCode();
    }

    else return 0;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public static class NotImplementedError extends Error {
    public NotImplementedError() {
      super("Not Implemented", HttpStatus.NOT_IMPLEMENTED);
    }

    public NotImplementedError(ErrorEnum errorEnum) {
      super(errorEnum, HttpStatus.NOT_IMPLEMENTED);
    }
  }

  public static class RequiredFieldError extends Error {

    public RequiredFieldError(ErrorEnum errorEnum) {
      super(errorEnum, HttpStatus.BAD_REQUEST);
    }

  }

  public static class BadRequestException extends Error {

    public BadRequestException() {
      super(ErrorEnum.general_bad_request, HttpStatus.BAD_REQUEST);
    }

    public BadRequestException(String message) {
      super(message, HttpStatus.BAD_REQUEST);
    }

    public BadRequestException(ErrorEnum errorEnum) {
      super(errorEnum, HttpStatus.BAD_REQUEST);
    }

  }

  public static class AccessDeniedException extends Error {

    public AccessDeniedException() {
      super(ErrorEnum.access_denied, HttpStatus.FORBIDDEN);
    }

    public AccessDeniedException(String message) {
      super(message, HttpStatus.FORBIDDEN);
    }

    public AccessDeniedException(ErrorEnum errorEnum) {
      super(errorEnum, HttpStatus.FORBIDDEN);
    }

  }

  public static class UnauthorizedException extends Error {

    public UnauthorizedException() {
      super(ErrorEnum.unauthorized, HttpStatus.UNAUTHORIZED);
    }
  }

  public static class NotFoundException extends Error {

    public NotFoundException() {
      super(ErrorEnum.general_not_found, HttpStatus.NOT_FOUND);
    }

    public NotFoundException(String message) {
      super(message, HttpStatus.NOT_FOUND);
    }

    public NotFoundException(ErrorEnum errorEnum) {
      super(errorEnum, HttpStatus.NOT_FOUND);
    }

  }

  public static class ConflictException extends Error {

    public ConflictException() {
      super(ErrorEnum.general_duplication, HttpStatus.CONFLICT);
    }

    public ConflictException(String message) {
      super(message, HttpStatus.CONFLICT);
    }

    public ConflictException(ErrorEnum errorEnum) {
      super(errorEnum, HttpStatus.CONFLICT);
    }

  }

  public static class InternalException extends Error {

    public InternalException() {
      super(ErrorEnum.general_internal_server_error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}