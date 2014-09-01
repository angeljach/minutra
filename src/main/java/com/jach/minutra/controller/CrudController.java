package com.jach.minutra.controller;

import org.apache.cayenne.CayenneDataObject;
import org.apache.cayenne.CayenneRuntimeException;
import org.apache.cayenne.DeleteDenyException;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.access.DataContext;
import org.apache.log4j.Logger;

/**
 *
 * @author jach
 */
public abstract class CrudController {
    //private final MovementsController movement;
    private final String objectName;
    private String objectTitle;
    
    private ObjectContext context;
    private static final Logger LOGGER = Logger.getLogger(CrudController.class);

    /**
     * Constructor. Recives the object name and object title (id, name, ...) helping to create the
     * logs and register the movements on data base.
     * @param objectName The object name of the class.
     * @param objectTitle The id, name, ... of the entry.
     */
    public CrudController(String objectName, String objectTitle) {
        context = DataContext.getThreadObjectContext();
        //movement = new MovementsController();
        this.objectName = objectName;
        this.objectTitle = objectTitle;
    }
    
    /**
     * Creates a new registry on DB.
     * @param cayenneDataObject The object to be saved.
     * @param registerNewObject In case that the object needs to be register as new object on the context.
     * @throws CayenneRuntimeException If the creation of the registry in the DB fails.
     */
    public final void createObject(CayenneDataObject cayenneDataObject, boolean registerNewObject) 
            throws CayenneRuntimeException {
        createEntry();
        try {
            if (registerNewObject) {
                registerNewObjectAndSaveEntry(cayenneDataObject);
            } else {
                saveEntry();
            }
            createSuccessLogEntry(CrudActions.CREATE, objectName, objectTitle);
        } catch(CayenneRuntimeException cex) {
            createErrorLogEntry(CrudActions.CREATE, objectName, objectTitle);
            throw cex;
        }
    }
    
    /**
     * Updates a registry on DB.
     * @param cayenneDataObject The object to be updated.
     * @throws IllegalArgumentException If CayenneDataObject is null. This object can be null only in creation.
     * @throws CayenneRuntimeException If the update of the registry in the DB fails.
     */
    public final void updateObject(CayenneDataObject cayenneDataObject) 
            throws IllegalArgumentException, CayenneRuntimeException {
        isObjectNull(cayenneDataObject);
        createEntry();  
        context = cayenneDataObject.getObjectContext();
        try {
            saveEntry();
            createSuccessLogEntry(CrudActions.UPDATE, objectName, objectTitle);
        } catch(CayenneRuntimeException cex) {
            createErrorLogEntry(CrudActions.UPDATE, objectName, objectTitle);
            throw cex;
        }
    }
    
    /**
     * Deletes a registry on DB.
     * @param cayenneDataObject The object to be deleted.
     * @throws IllegalArgumentException If CayenneDataObject is null. This object can be null only in creation.
     * @throws CayenneRuntimeException If deletion of the registry in the DB fails.
     */
    public final void deleteObject(CayenneDataObject cayenneDataObject) 
            throws IllegalArgumentException, DeleteDenyException {
        isObjectNull(cayenneDataObject);
        try {
            deleteEntry(cayenneDataObject);
            createSuccessLogEntry(CrudActions.DELETE, objectName, objectTitle);
        } catch(DeleteDenyException cex) {
            createErrorLogEntry(CrudActions.DELETE, objectName, objectTitle);
            LOGGER.error(cex.getMessage());
            throw cex;
        }
    }
    
    /**
     * An abstract method to implement the creation of the CayenneDataObject.
     */
    protected abstract void createEntry();
    
    /**
     * Verify if CayenneDataObject is null.
     * @param cayenneDataObject The CayenneDataObject object.
     * @throws IllegalArgumentException If CayenneDataObject is null.
     */
    void isObjectNull(CayenneDataObject cayenneDataObject) throws IllegalArgumentException {
        if (cayenneDataObject == null) {
            LOGGER.error("Se ha tratado de actualizar/eliminar un objeto CayenneDataObject nulo.");
            throw new IllegalArgumentException("El objeto CayenneDataObject es nulo por lo que no "
                    + "es posible actualizar/eliminar el objeto.");
        }
    }
    
    /**
     * Create a new CayenneDataObject object in the context and commit the changes to save it in DB.
     * @param cayenneDataObject The CayenneDataObject object.
     * @throws CayenneRuntimeException If the creation of the registry in the DB fails.
     */
    void registerNewObjectAndSaveEntry(CayenneDataObject cayenneDataObject) throws CayenneRuntimeException {
        try {
            context.registerNewObject(cayenneDataObject);
            context.commitChanges();
        } catch (CayenneRuntimeException cex) {
            context.rollbackChanges();
            throw cex;
        }
    }
    
    /**
     * Commit the changes to create the entry in DB.
     * @throws CayenneRuntimeException If the saved of the registry in the DB fails.
     */
    void saveEntry() throws CayenneRuntimeException {
        try {
            context.commitChanges();
        } catch (CayenneRuntimeException cex) {
            context.rollbackChanges();
            throw cex;
        }
    }
    
    /**
     * Delete an entry in DB.
     * @param cayenneDataObject The CayenneDataObject object.
     * @throws DeleteDenyException If deletion of the registry in the DB fails.
     */
    void deleteEntry(CayenneDataObject cayenneDataObject) throws DeleteDenyException {
        try {
            context = cayenneDataObject.getObjectContext();
            context.deleteObjects(cayenneDataObject);
            context.commitChanges();
        } catch (DeleteDenyException cex) {
            throw cex;
        }
    }
    
    /**
     * Create an info log entry and a new register on movement table to indicate the action success.
     * @param ca The CRUD action.
     * @param objectName The object name of the class.
     * @param objectTitle The id, name, ... of the entry.
     */
    void createSuccessLogEntry(CrudActions ca, String objectName, String objectTitle) {
        String msg = String.format("%s exitosa del objecto '%s' llamado '%s' en la BD.", 
                ca.getName(), objectName, objectTitle);
        //movement.save(msg);
        LOGGER.info(msg);
    }
    
    /**
     * Create an error log entry and a new register on movement table to indicate the action fail.
     * @param ca The CRUD action.
     * @param objectName The object name of the class.
     * @param objectTitle The id, name, ... of the entry.
     */
    void createErrorLogEntry(CrudActions ca, String objectName, String objectTitle) {
        String msg = String.format("Error durante la %s en la BD del objeto '%s' llamado '%s'.", 
                ca.getName(), objectName, objectTitle);
        LOGGER.error(msg);
    }

    
    
    /**
     * Return the context of cayenne.
     * @return ObjectContext of cayenne.
     */
    public ObjectContext getContext() { return context; }

    /**
     * Return the movement object.
     * @return Movement object.
     */
    //public MovementsController getMovement() { return movement; }

    
    public String getObjectTitle() {
        return objectTitle;
    }

    /**
     * Used mostly when registerNewObject is true, because objectTitle can't be setted before creation.
     * @param objectTitle 
     */
    public void setObjectTitle(String objectTitle) {
        this.objectTitle = objectTitle;
    }

    //public void setContext(ObjectContext context) { this.context = context; }

    
    /**
     * Enum to indicate the CRUD actions.
     */
    public enum CrudActions {
        CREATE("creación"),
        UPDATE("actualización"),
        DELETE("eliminación");
        
        private final String name;

        private CrudActions(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
