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
    }
    
    public UserController(Users user) {
        super(user.getClass().getSimpleName(), user.getEmail());
        this.user = user;
    }
    
    public void create() throws CayenneRuntimeException {
        parameters = new HashMap<>();
        
        user.setIsEnabled(true);
        user.setCreationDate(new Date());
        user.setModificationDate(new Date());
        
        //parameters.put(Key.USER_ROLE, AiDbObjectFromString.getUserRolesObjectFromString(getContext(), userRole));

        //TRUE porque es un nuevo objeto (no se deriva de otro)
        //TRUE porque es un nuevo objeto (no se deriva de otro)
        //TRUE porque es un nuevo objeto (no se deriva de otro)
        createObject(user, true);
    }
    
    public void update() throws IllegalArgumentException, CayenneRuntimeException {
        parameters = new HashMap<>();
        
        user.setModificationDate(new Date());
        
        //parameters.put(Key.USER_ROLE, AiDbObjectFromString.getUserRolesObjectFromString(getContext(), userRole));        
        updateObject(user);
    }
    
    public void delete() throws IllegalArgumentException, DeleteDenyException {
        //TODO Implementar funcionalidad dadas las implicaciones a considerar.
        deleteObject(user);
    }
    
    @Override
    protected void createEntry() {
        //user.setToUserRoles((UserRoles) parameters.get(Key.USER_ROLE));
    }
}
