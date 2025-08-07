package cn.escort.utils.crypto;

import cn.escort.frameworkConfig.web.exceptionHandler.exception.BaseException;

import java.security.GeneralSecurityException;

public class CryptoException extends BaseException {

    protected CryptoException(String message) {
        super(message);
    }
}
