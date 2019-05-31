package com.talentPool.common.db.Exception;

/**
 * @author shivprasad
 *
 */
public class NoResultFoundException extends java.lang.Exception {
    
    /**
     * Creates a new instance of <code>NoResultFoundException</code> without detail message.
     */
    public NoResultFoundException() {
    }
    
    
    /**
     * Constructs an instance of <code>NoResultFoundException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public NoResultFoundException(String msg) {
        super(msg);
    }
}

