package com.example.demo.domain.error;

import lombok.Getter;

@Getter
public enum ErrorEnum {

    general_internal_server_error(500001, 500, "error_general_internal_server_error"),
    recaptcha_unavailable(500002, 500, "error_recaptcha_unavailable"),

    general_bad_request(400001, 400, "error_general_bad_request"),
    recaptcha_invalid(400002, 400, "error_recaptcha_invalid"),
    recaptcha_not_provided(400003, 400, "error_recaptcha_not_provided"),
    mobile_or_password_not_provided(400101, 400, "error_mobile_or_password_not_provided"),
    owner_id_not_provided(400102, 400, "error_owner_id_not_provided"),
    organization_name_not_provided(400102, 400, "error_organization_name_not_provided"),

    unauthorized(401001, 401, "error_unauthorized"),

    general_not_found(404001, 404, "error_general_not_found"),
    user_not_found(404102, 404, "error_user_not_found"),
    user_role_not_found(404103, 404, "error_user_role_not_found"),


    access_denied(403001, 403, "error_general_access_denied"),
    recaptcha_blocked(403002, 403, "error_recaptcha_blocked"),
    user_locked(403003, 403, "error_user_locked"),

    general_duplication(409001, 409, "error_general_duplication"),
    user_duplication(409102, 409, "error_user_duplication"),
    organization_name_duplication(409103, 409, "error_organization_name_duplication"),
    ;

    /**
     * The code should be unique.
     * The correct format is: 123456 => 123: Http Status Code.
     *                                  4:   Service Code (General is zero).
     *                                  56:  Error Code.
     */

    private int code;
    private int status;
    private String messageKey;

    ErrorEnum(int code, int status, String messageKey) {
        this.code = code;
        this.status = status;
        this.messageKey = messageKey;
    }

}
