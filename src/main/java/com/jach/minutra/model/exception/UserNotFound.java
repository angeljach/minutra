package com.jach.minutra.model.exception;

import javax.ejb.ApplicationException;

/**
 *
 * @author jach
 */
@ApplicationException(rollback=true)
public class UserNotFound extends Exception {
    
    private String message;
    
    public UserNotFound () {
        this.message = "User not found";
    }
    
    public UserNotFound(String message) {
        this.message = message;
    }
    
    @Override
    public String getMessage() {
        return this.message; 
    }
}
