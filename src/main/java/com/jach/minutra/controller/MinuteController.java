package com.jach.minutra.controller;

import com.jach.minutra.model.MinuteMembers;
import com.jach.minutra.model.MinuteModel;
import com.jach.minutra.model.Minutes;
import com.jach.minutra.model.Users;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.cayenne.CayenneRuntimeException;
import org.apache.cayenne.DeleteDenyException;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.SelectQuery;
import org.apache.log4j.Logger;

/**
 *
 * @author jach
 */
public class MinuteController extends CrudController {
    private final Minutes minute;
    private Map<Key, Object> parameters;
    
    private enum Key {
        AUTHOR
    }
    
    private static final Logger LOGGER = Logger.getLogger(MinuteController.class);
    
    public MinuteController(Minutes minute) {
        super(minute.getClass().getSimpleName(), minute.getTitle());
        this.minute = minute;
    }
    
    public void create(Users author) throws CayenneRuntimeException {
        parameters = new HashMap<>();
        
        Date now = new Date();
        minute.setCreationDate(now);
        minute.setModificationDate(now);

        parameters.put(Key.AUTHOR, author);
        
        //TRUE porque es un nuevo objeto (no se deriva de otro)
        //TRUE porque es un nuevo objeto (no se deriva de otro)
        //TRUE porque es un nuevo objeto (no se deriva de otro)
        createObject(minute, false);
    }
    
    public void update(List<Users> newUsersList) throws IllegalArgumentException, CayenneRuntimeException {
        parameters = new HashMap<>();
        
        minute.setModificationDate(new Date());
        
        parameters.put(Key.AUTHOR, minute.getToUsers());
        
        updateObject(minute);
        
        List<Users> currUsersList = (new MinuteModel()).getUsedUsersFromSummary(minute);

        addUsersToMinute(newUsersList, currUsersList);
        removeUsersFromSummary(newUsersList, currUsersList);
    }
    
    public void delete() throws IllegalArgumentException, DeleteDenyException {
        //TODO Implementar funcionalidad dadas las implicaciones a considerar.
        deleteObject(minute);
    }
    
    @Override
    protected void createEntry() {
        minute.setToUsers((Users) parameters.get(Key.AUTHOR));
    }
    
    private void addUsersToMinute(List<Users> newUsersList, List<Users> currUsersList) {
        ObjectContext context = minute.getObjectContext();
        
        boolean bNewUserAdded = false;
        for (Users user : newUsersList) {
            if (! currUsersList.contains(user)) {
                bNewUserAdded = true;   //Nuevo usuario agregado.
                //---|| Registro los usuarios que no han sido asignados
                MinuteMembers newMinMem = (MinuteMembers) context.newObject(MinuteMembers.class);
            
                newMinMem.setToUsers(user);
                newMinMem.setToMinutes(minute);
                newMinMem.setCreationDate(new Date());

                LOGGER.debug(String.format("Tratando de agregar el usuario '%s' "
                        + "a la minuta '%d'", user.getUserName(), minute.getId()));
            }
        }        
        if (bNewUserAdded) {
            context.commitChanges();    //---|| Si se agregó un usuario, hago commit.
        }
    }
    
    private void removeUsersFromSummary(List<Users> newUsersList, List<Users> currUsersList) {
        ObjectContext context = minute.getObjectContext();
        
        List<Users> toDelSP = new ArrayList<>();
        //---|| Obtengo los usuarios que ya no fueron asignados.
        for (Users user : currUsersList) {
            if (! newUsersList.contains(user)) {
                LOGGER.debug(String.format("Tratando de eliminar el usuario '%s' "
                        + "de la minuta '%d'", 
                        user.getUserName(), minute.getId()));
                toDelSP.add(user);
            }
        }
        if (!toDelSP.isEmpty()) {
            //---|| Elimino los usuarios que ya no fueron asignados.
            //Expression e = ExpressionFactory.matchAllExp(SummaryParticipants.TO_USERS_PROPERTY, toDelSP);
            Expression e = ExpressionFactory.inExp(MinuteMembers.TO_USERS_PROPERTY, toDelSP);
            e = e.andExp(ExpressionFactory.matchExp(MinuteMembers.TO_MINUTES_PROPERTY, minute));
            SelectQuery sel = new SelectQuery(MinuteMembers.class, e);

            List<MinuteMembers> summPartLst = (List<MinuteMembers>) context.performQuery(sel);
            
            if (!summPartLst.isEmpty()) {
                context.deleteObjects(summPartLst);
                context.commitChanges();
            }
        }
    }
}
