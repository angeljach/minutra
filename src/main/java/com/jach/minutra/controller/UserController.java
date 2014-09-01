package com.jach.minutra.controller;

import com.jach.minutra.model.Users;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.cayenne.CayenneRuntimeException;
import org.apache.cayenne.DeleteDenyException;

/**
 *
 * @author jach
 */
public class UserController extends CrudController {
    private final Users user;
    private Map<Key, Object> parameters;
    
    private enum Key {
        MODIFICATION_DATE
    }
    
    public UserController(Users user) {
        super(user.getClass().getSimpleName(), user.getEmail());
        this.user = user;
    }
    
    public void create(String userRole) throws CayenneRuntimeException {
        parameters = new HashMap<>();
        //parameters.put(Key.USER_ROLE, AiDbObjectFromString.getUserRolesObjectFromString(getContext(), userRole));
        parameters.put(Key.MODIFICATION_DATE, (new Date()));

        createObject(user, false);
    }
    
    public void update(String userRole) throws IllegalArgumentException, CayenneRuntimeException {
        parameters = new HashMap<>();
        //parameters.put(Key.USER_ROLE, AiDbObjectFromString.getUserRolesObjectFromString(getContext(), userRole));
        parameters.put(Key.MODIFICATION_DATE, (new Date()));
        
        updateObject(user);
    }
    
    public void delete() throws IllegalArgumentException, DeleteDenyException {
        //TODO Implementar funcionalidad dadas las implicaciones a considerar.
        //deleteObject(user);
    }
    
    @Override
    protected void createEntry() {
        //user.setToUserRoles((UserRoles) parameters.get(Key.USER_ROLE));
        user.setModificationDate((Date) parameters.get(Key.MODIFICATION_DATE));
    }
}
