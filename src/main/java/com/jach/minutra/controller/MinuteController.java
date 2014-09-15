package com.jach.minutra.controller;

import com.jach.minutra.model.Minutes;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.cayenne.CayenneRuntimeException;
import org.apache.cayenne.DeleteDenyException;

/**
 *
 * @author jach
 */
public class MinuteController extends CrudController {
    private final Minutes minute;
    private Map<Key, Object> parameters;
    
    private enum Key {
    }
    
    public MinuteController(Minutes minute) {
        super(minute.getClass().getSimpleName(), minute.getTitle());
        this.minute = minute;
    }
    
    public void create() throws CayenneRuntimeException {
        parameters = new HashMap<>();
        
        Date now = new Date();
        minute.setCreationDate(now);
        minute.setModificationDate(now);
        
        //parameters.put(Key.USER_ROLE, AiDbObjectFromString.getUserRolesObjectFromString(getContext(), userRole));

        //TRUE porque es un nuevo objeto (no se deriva de otro)
        //TRUE porque es un nuevo objeto (no se deriva de otro)
        //TRUE porque es un nuevo objeto (no se deriva de otro)
        createObject(minute, false);
    }
    
    public void update() throws IllegalArgumentException, CayenneRuntimeException {
        parameters = new HashMap<>();
        
        minute.setModificationDate(new Date());
        
        //parameters.put(Key.USER_ROLE, AiDbObjectFromString.getUserRolesObjectFromString(getContext(), userRole));        
        updateObject(minute);
    }
    
    public void delete() throws IllegalArgumentException, DeleteDenyException {
        //TODO Implementar funcionalidad dadas las implicaciones a considerar.
        deleteObject(minute);
    }
    
    @Override
    protected void createEntry() {
        //user.setToUserRoles((UserRoles) parameters.get(Key.USER_ROLE));
    }
}
