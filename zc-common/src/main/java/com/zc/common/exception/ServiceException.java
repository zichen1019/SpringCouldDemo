package com.zc.common.exception;


/**
 * @author zichen
 */
public class ServiceException extends BaseException {

    public ServiceException(){
        super();
    }

    public ServiceException(ErrorType errorType){
        super(errorType);
    }
}
