package cn.escort.frameworkConfig.web.exceptionHandler.exception;


public class BaseException extends RuntimeException{

    private BaseException(){
        throw new AssertionError("No cn.escort.frameworkConfig.web.exceptionHandler.exception.BaseException instances for you!");
    }

    protected BaseException(String message) {
        super(message);
    }

}
