package com.jach.minutra.model.exception;

import javax.ejb.ApplicationException;

/**
 *
 * @author jach
 */
@ApplicationException(rollback=true)
public class UserAlreadyExists extends Exception {
    
    private String message;
    
    public UserAlreadyExists () {
        this.message = "User Already Exists";
    }
    
    public UserAlreadyExists(String message) {
        this.message = message;
    }
    
    @Override
    public String getMessage() {
        return this.message; 
    }
}
