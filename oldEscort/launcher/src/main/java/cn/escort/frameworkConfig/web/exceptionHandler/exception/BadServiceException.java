package cn.escort.frameworkConfig.web.exceptionHandler.exception;

public class BadServiceException  extends BaseException{
    public BadServiceException(String message) {
        super(message);
    }
    public BadServiceException(String message,Exception e) {
        super(message+e.getMessage());
    }
}
