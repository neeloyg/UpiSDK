/*
package com.upi.sdk.errors;

*/
/**
 * Error codes need to be defined here
 * Created by SwapanP on 22-04-2016.
 *//*

public enum UPIErrorCode {

    ILLEGAL_INPUTS,
    SDK_NOT_INITIALIZED,
    SDK_ALREADY_INITIALIZED,
    INVALID_BANKACCNO,
    INVALID_IFSC,
    DUPLICATE_BANKACC,
    INVALID_BANKACC,
    INVALID_USER,
    LOGIN_ERROR,
    INVALID_USER_ID_OR_PASSWORD,
    REGISTRATION_ERROR,

    //Bank Account
    INVALID_NICKNAME,
    //VPA
    VPAID_REQUIRED,
    MAXLIMIT_PER_TRANS_REQUIRED,
    VALIDUPTO_LESS_THAN_CURRENT,
    MISSING_PAYEE_LIST,
    INVALID_PAYMENT_ACC,
    INVALID_COLLECTION_ACC,
    //Payment Request
    INVALID_PAYER,
    INVALID_PAYEELIST,
    TXN_AMOUNT_MISSMATCH,
    TXN_AUTH_FAILED,
    SYSTEM_ERROR,
    MOBILE_NUMBER_VERIFICATION_FAILED,
    //userLogin
    ACCOUNT_LOCKED_CONTACT_YOUR_ADMINSTRATOR,
    MOBILE_NOT_VERIFIED,

    //    User Registration
    USER_ALREADY_EXISTS,

    //    isVpaAvailable
    VPA_ALREADY_EXISTS,
    MOBILE_NUMBER_ALREADY_EXISTS,
    EMAIL_ADDRESS_ALREADY_EXISTS,
    MAX_PAY_LIMIT_SHOULD_BE_LESS_THAN_SPECFIED_LIMIT,
    IDENTITY_REQUIRED,
    IDENTITY__TYPE_REQUIRED,
    IDENTITY__NUMBER_REQUIRED,
    INVALID_IDENTITY_TYPE,
    DEVICE_INFO_REQUIRED,
    DEVICE_MOBILE_REQUIRED,
    DEVICE_MOBILE_NUMBER_NOT_SAME_AS_USER_MOBILE,
    DEVICE_BLOCK_MISSING,
    DEVICE_FIGERPRINT_REQUIRED,
    PASSWORD_REQUIRED,
    MAX_PAY_LIMIT_REQUIRED,
    NICK_NAME_ALREADY_EXISTS,
    MOBILE_NUMBER_REQUIRED,
    MMID_REQUIRED,
    NO_ACCOUNT_INFORMATION_PROVIDED,
    NICK_NAME_REQUIRED,
    ACCOUNT_NUMBER_REQUIRED,
    IFSC_CODE_REQUIRED,
    DUPLICATE_ACCOUNT_LINK,
    ACCOUNT_HAS_LIVE_VPA,
    LINK_ACCOUNT_ID_REQUIRED,
    ACCOUNT_DOESNOT_EXISTS,
    VALID_UPTO_SHOULD_BE_GREATER_THAN_THE_CURRENT_DATE,
    INVALID_PAY_ACCOUNT,
    INVALID_COLLECT_ACCOUNT,
    INVALID_BENEFICIARY,
    VPA_REQUIRED,
    INVALID_USER_ID,
    USER_REQUIRED,
    VPA_ID_REQUIRED,
    BENEFICIARY_NAME_REQUIRED,
    BENEFICIARY_VPA_REQUIRED,
    BENEFICIARY_NAME_EXISTS_FOR_THE_USER,
    BENEFICIARY_VPA_EXISTS_FOR_THE_USER,
    BENEFICIARY_ID_REQUIRED,
    VPA_MARKED_AS_DEFAULT,
    INVALID_PASSOWORD,
    REQUIRE_NEW_PASSOWORD,
    NEW_PASSWORD_SAME_AS_OLD,
    OTP_NOT_SEND,
    OTP_REQUIRED,
    OTP_NOT_VALID,
    RULE_NAME_REQUIRED,
    PAY_TYPE_REQUIRED,
    INVALID_PAY_TYPE,
    RULE_DETAILS_REQUIRED,
    RULE_ID_REQUIRED,
    RULE_HAS_ACTIVE_VPA,
    SCHD_NAME_REQUIED,
    SCHD_TYPE_REQUIED,
    INVALID_SCHD_AMT,
    INVALID_SCHD_FREQ,
    INVALID_SCHD_DATE,
    INVALID_SCHD_CUR_FREQ,
    INVALID_SCHD_START_DATE,
    INVALID_SCHD_START_TIME,
    VPA_NOT_EXISITS,
    INVALD_SCHEDULE_TYPE,
    SCHEDULE_ID_REQUIRED,
    NULL_VALUE,
    EMPTY_STRING,
    INVALID_FORMAT,
    MIN_LENGTH_REQUIRED,
    MAX_LENGTH_REQUIRED,
    MIN_VALUE_REQUIRED,
    MAX_VALUE_REQUIRED,
    NOT_NEUMERIC,
    OTP_FOR_SELECT_PAYEE_BUT_MISSING_PAYEE,
    OTP_FOR_ALL_PAYEE_BUT_PAYEE_PROVIDED,
    TRANSACTION_IN_PROGRESS,
    ONETIME_NOT_ALLOWED,
    QUESTION_ID_REQUIRED,
    ANSWER_REQUIRED,
    INVALID_QUESTION_ID,
    VPA_NAME_SAME_AS_DEFAULT,
    EXPIRY_DATE_NOT_ALLOWED,
    INVALID_DEFAULT_VPA_NAME,
    DEFAULT_VPA_NOT_ALLOWED,
    ACCOUNT_HAS_RULE,
    VPA_HAS_SCHEDULE,
    BENEFICARY_SCHEDULE_EXISTS,
    UPI_ERROR;

    public static UPIErrorCode valueOfServiceError(String ErrorCode) {
        UPIErrorCode code = null;
        switch (ErrorCode) {
            case "ERR00000":
                code = UPIErrorCode.SYSTEM_ERROR;
                break;

            case "ERR00001":
                code = UPIErrorCode.INVALID_USER_ID_OR_PASSWORD;
                break;
            case "ERR00002":
                code = UPIErrorCode.ACCOUNT_LOCKED_CONTACT_YOUR_ADMINSTRATOR;
                break;
            case "ERR00003":
                code = UPIErrorCode.MOBILE_NOT_VERIFIED;
                break;
            case "ERR00004":
                code = UPIErrorCode.USER_ALREADY_EXISTS;
                break;
            case "ERR00005":
                code = UPIErrorCode.VPA_ALREADY_EXISTS;
                break;

            case "ERR00006":
                code = UPIErrorCode.MOBILE_NUMBER_ALREADY_EXISTS;
                break;
            case "ERR00007":
                code = UPIErrorCode.EMAIL_ADDRESS_ALREADY_EXISTS;
                break;
            case "ERR00008":
                code = UPIErrorCode.MAX_PAY_LIMIT_SHOULD_BE_LESS_THAN_SPECFIED_LIMIT;
                break;
            case "ERR00009":
                code = UPIErrorCode.OTP_NOT_VALID;
                break;
            case "ERR00010":
                code = UPIErrorCode.NICK_NAME_ALREADY_EXISTS;
                break;
            case "ERR00011":
                code = UPIErrorCode.MOBILE_NUMBER_REQUIRED;
                break;
            case "ERR00012":
                code = UPIErrorCode.MMID_REQUIRED;
                break;
            case "ERR00013":
                code = UPIErrorCode.ACCOUNT_NUMBER_REQUIRED;
                break;
            case "ERR00014":
                code = UPIErrorCode.IFSC_CODE_REQUIRED;
                break;
            case "ERR00015":
                code = UPIErrorCode.DUPLICATE_ACCOUNT_LINK;
                break;
            case "ERR00016":
                code = UPIErrorCode.NULL_VALUE;//not found
                break;
            case "ERR00017":
                code = UPIErrorCode.VALID_UPTO_SHOULD_BE_GREATER_THAN_THE_CURRENT_DATE;
                break;
            case "ERR00018":
                code = UPIErrorCode.OTP_FOR_SELECT_PAYEE_BUT_MISSING_PAYEE;
                break;
            case "ERR00019":
                code = UPIErrorCode.OTP_FOR_ALL_PAYEE_BUT_PAYEE_PROVIDED;
                break;
            case "ERR00020":
                code = UPIErrorCode.INVALID_PAY_ACCOUNT;
                break;
            case "ERR00021":
                code = UPIErrorCode.INVALID_COLLECT_ACCOUNT;
                break;
            case "ERR00022":
                code = UPIErrorCode.INVALID_BENEFICIARY;
                break;
            case "ERR00023":
                code = UPIErrorCode.VPA_REQUIRED;
                break;
            case "ERR00024":
                code = UPIErrorCode.INVALID_USER_ID;
                break;
            case "ERR00025":
                code = UPIErrorCode.USER_REQUIRED;
                break;
            case "ERR00026":
                code = UPIErrorCode.BENEFICIARY_NAME_REQUIRED;
                break;
            case "ERR00027":
                code = UPIErrorCode.BENEFICIARY_VPA_REQUIRED;
                break;
            case "ERR00028":
                code = UPIErrorCode.BENEFICIARY_NAME_EXISTS_FOR_THE_USER;
                break;
            case "ERR00029":
                code = UPIErrorCode.BENEFICIARY_VPA_EXISTS_FOR_THE_USER;
                break;
            case "ERR00030":
                code = UPIErrorCode.NO_ACCOUNT_INFORMATION_PROVIDED;
                break;
            case "ERR00031":
                code = UPIErrorCode.NICK_NAME_REQUIRED;
                break;
            case "ERR00032":
                code = UPIErrorCode.IDENTITY_REQUIRED;
                break;
            case "ERR00033":
                code = UPIErrorCode.IDENTITY__TYPE_REQUIRED;
                break;
            case "ERR00034":
                code = UPIErrorCode.IDENTITY__NUMBER_REQUIRED;
                break;
            case "ERR00035":
                code = UPIErrorCode.ACCOUNT_HAS_LIVE_VPA;
                break;
            case "ERR00036":
                code = UPIErrorCode.INVALID_IDENTITY_TYPE;
                break;
            case "ERR00037":
                code = UPIErrorCode.DEVICE_INFO_REQUIRED;
                break;
            case "ERR00038":
                code = UPIErrorCode.DEVICE_MOBILE_REQUIRED;
                break;
            case "ERR00039":
                code = UPIErrorCode.DEVICE_MOBILE_NUMBER_NOT_SAME_AS_USER_MOBILE;
                break;
            case "ERR00040":
                code = UPIErrorCode.DEVICE_BLOCK_MISSING;
                break;
            case "ERR00041":
                code = UPIErrorCode.DEVICE_FIGERPRINT_REQUIRED;
                break;
            case "ERR00042":
                code = UPIErrorCode.PASSWORD_REQUIRED;
                break;
            case "ERR00043":
                code = UPIErrorCode.INVALID_PASSOWORD;
                break;
            case "ERR00044":
                code = UPIErrorCode.REQUIRE_NEW_PASSOWORD;
                break;
            case "ERR00045":
                code = UPIErrorCode.NEW_PASSWORD_SAME_AS_OLD;
                break;
            case "ERR00046":
                code = UPIErrorCode.BENEFICIARY_ID_REQUIRED;
                break;
            case "ERR00047":
                code = UPIErrorCode.LINK_ACCOUNT_ID_REQUIRED;
                break;
            case "ERR00048":
                code = UPIErrorCode.ACCOUNT_DOESNOT_EXISTS;
                break;
            case "ERR00049":
                code = UPIErrorCode.ACCOUNT_DOESNOT_EXISTS;
                break;
            case "ERR00050":
                code = UPIErrorCode.VPA_ID_REQUIRED;
                break;
            case "ERR00051":
                code = UPIErrorCode.OTP_REQUIRED;
                break;
            case "ERR00052":
                code = UPIErrorCode.OTP_NOT_SEND;
                break;
            case "ERR00053":
                code = UPIErrorCode.OTP_NOT_VALID;
                break;
            case "ERR00054":
                code = UPIErrorCode.RULE_NAME_REQUIRED;
                break;
            case "ERR00055":
                code = UPIErrorCode.PAY_TYPE_REQUIRED;
                break;
            case "ERR00056":
                code = UPIErrorCode.INVALID_PAY_TYPE;
                break;
            case "ERR00057":
                code = UPIErrorCode.RULE_DETAILS_REQUIRED;
                break;
            case "ERR00058":
                code = UPIErrorCode.MAX_PAY_LIMIT_REQUIRED;
                break;
            case "ERR00059":
                code = UPIErrorCode.SCHD_NAME_REQUIED;
                break;
            case "ERR00060":
                code = UPIErrorCode.SCHD_TYPE_REQUIED;
                break;
            case "ERR00061":
                code = UPIErrorCode.INVALID_SCHD_AMT;
                break;
            case "ERR00062":
                code = UPIErrorCode.INVALID_SCHD_FREQ;
                break;
            case "ERR00063":
                code = UPIErrorCode.INVALID_SCHD_DATE;
                break;
            case "ERR00064":
                code = UPIErrorCode.INVALID_SCHD_CUR_FREQ;
                break;
            case "ERR00065":
                code = UPIErrorCode.INVALID_SCHD_START_DATE;
                break;
            case "ERR00066":
                code = UPIErrorCode.INVALID_SCHD_START_TIME;
                break;
            case "ERR00067":
                code = UPIErrorCode.SCHEDULE_ID_REQUIRED;
                break;
            case "ERR00068":
                code = UPIErrorCode.VPA_NOT_EXISITS;
                break;
            case "ERR00069":
                code = UPIErrorCode.INVALD_SCHEDULE_TYPE;
                break;
            case "ERR00070":
                code = UPIErrorCode.NULL_VALUE;
                break;
            case "ERR00071":
                code = UPIErrorCode.EMPTY_STRING;
                break;
            case "ERR00072":
                code = UPIErrorCode.INVALID_FORMAT;
                break;
            case "ERR00073":
                code = UPIErrorCode.MIN_LENGTH_REQUIRED;
                break;
            case "ERR00074":
                code = UPIErrorCode.MAX_LENGTH_REQUIRED;
                break;
            case "ERR00075":
                code = UPIErrorCode.MIN_VALUE_REQUIRED;
                break;
            case "ERR00076":
                code = UPIErrorCode.MAX_VALUE_REQUIRED;
                break;
            case "ERR00077":
                code = UPIErrorCode.NOT_NEUMERIC;
                break;
            case "ERR00078":
                code = UPIErrorCode.RULE_ID_REQUIRED;
                break;
            case "ERR00079":
                code = UPIErrorCode.RULE_HAS_ACTIVE_VPA;
                break;
            case "ERR00080":
                code = UPIErrorCode.VPA_MARKED_AS_DEFAULT;
                break;
            case "ERR00081":
                code = UPIErrorCode.ONETIME_NOT_ALLOWED;
                break;
            case "ERR00082":
                code = UPIErrorCode.QUESTION_ID_REQUIRED;
                break;
            case "ERR00083":
                code = UPIErrorCode.ANSWER_REQUIRED;
                break;
            case "ERR00084":
                code = UPIErrorCode.INVALID_QUESTION_ID;
                break;
            case "ERR00085":
                code = UPIErrorCode.VPA_NAME_SAME_AS_DEFAULT;
                break;
            case "ERR00086":
                code = UPIErrorCode.EXPIRY_DATE_NOT_ALLOWED;
                break;
            case "ERR00087":
                code = UPIErrorCode.INVALID_DEFAULT_VPA_NAME;
                break;
            case "ERR00088":
                code = UPIErrorCode.DEFAULT_VPA_NOT_ALLOWED;
                break;

            case "ERR00089":
                code = UPIErrorCode.TRANSACTION_IN_PROGRESS;
                break;

            case "ERR00090":
                code = UPIErrorCode.MOBILE_NUMBER_VERIFICATION_FAILED;
                break;
            case "ERR00091":
                code = UPIErrorCode.ACCOUNT_HAS_RULE;
                break;
            case "ERR00092":
                code = UPIErrorCode.VPA_HAS_SCHEDULE;
                break;
            case "ERR00093":
                code = UPIErrorCode.BENEFICARY_SCHEDULE_EXISTS;
                break;


            default:
                code = UPIErrorCode.SYSTEM_ERROR;
                break;
        }

        return code;
    }

    public static UPIErrorCode getUPIErrors(String errorCode) {
        return UPIErrorCode.getUPIErrors(errorCode);

    }
}

*/
