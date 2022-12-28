package com.gitee.pro.exception;

/**
 * 保存或更新 Admin 时，如果检测到登录账号重复抛出这个异常
 */
public class LoginAcctAlreadyInUseForSaveException extends RuntimeException {

    private static final long serialVersionUID = 7410017816335212384L;

    public LoginAcctAlreadyInUseForSaveException() {
        super();
    }

    public LoginAcctAlreadyInUseForSaveException(String message) {
        super(message);
    }

    public LoginAcctAlreadyInUseForSaveException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAcctAlreadyInUseForSaveException(Throwable cause) {
        super(cause);
    }

    protected LoginAcctAlreadyInUseForSaveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
