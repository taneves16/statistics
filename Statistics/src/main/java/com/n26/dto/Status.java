package com.n26.dto;

/**
 * Enum containing the API's status codes.
 * 
 * 1. SUCCESS: 
 * 1. CREATED: When POST request to register a transaction was successful.
 * 2. OLD_TRANSACTION: When the transaction timestamp passed is older than 60 secs
 * 3. BAD_REQUEST: When input parameters are invalid 
 * 4. SERVER_ERROR: When some internal exception occurs during the resource call. 
 * 
 * @author Ravi
 * */
public enum Status {
    SUCCESS("200"),CREATED("201"), OLD_TRANSACTION("204"),BAD_REQUEST("400"), SERVER_ERROR("500");

    /** status code as integer */
    private String code;

    /**
     * {@link Constructor}
     * 
     * @param code
     * */
    private Status(String code) {
        this.code = code;
    }

    /**
     * @return {@link String}
     * */
    public String getCode() {
        return code;
    }

}

