package com.wehealth.pdqbook.getway.error;

/**
 * @Author yangxiao on 3/20/2017.
 */

public class PDQException {
    private Exception exception;
    public PDQException(Exception e) {
        this.exception = e;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
