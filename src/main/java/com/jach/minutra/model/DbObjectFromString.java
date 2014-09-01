package com.jach.minutra.model;

import org.apache.cayenne.ObjectContext;
import org.apache.log4j.Logger;

/**
 *
 * @author jach
 */
public final class DbObjectFromString {
    
    private static final Logger LOGGER = Logger.getLogger(DbObjectFromString.class);

    private DbObjectFromString() {
    }
    
    /**
     * Search for an "user name" in "Users" table and return the object.
     * @param c Context.
     * @param email The user E-Mail.
     * @return Users object.
     */
    public static Users getUsersObjectFromEmail(ObjectContext c, String email) {
        LOGGER.trace(String.format("Obteniendo el objeto Users de '%s'", email));
        GenericDBObjectFromString<Users> gObjFromStr = new GenericDBObjectFromString<>(Users.class);
        return gObjFromStr.getDBObjectFromString(c, Users.EMAIL_PROPERTY, email);
    }
    
}
