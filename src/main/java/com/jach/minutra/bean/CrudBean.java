package com.jach.minutra.bean;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.apache.cayenne.CayenneRuntimeException;
import org.apache.cayenne.DeleteDenyException;

/**
 *
 * @author acruzh
 */
public abstract class CrudBean {

    private String objectName;

    public CrudBean(String objectName) {
        this.objectName = objectName;
    }
    
    public final boolean updateElement() {
        FacesContext notifyCntx = FacesContext.getCurrentInstance();
        try {
            executeUpdate();
            notifyCntx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Modificación exitosa", objectName + " modificado exitosamente"));
            executeInit();            
            return true;
        } catch(IllegalArgumentException ex) {
            notifyCntx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Actualización de objeto nulo", "Se ha tratado de actualizar un objeto nulo"));
        } catch(CayenneRuntimeException ex) {
            notifyCntx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Actualización fallida", ex.getMessage()));
        }
        return false;
    }
    
    public final boolean createElement() {
        FacesContext notifyCntx = FacesContext.getCurrentInstance();
        try {
            executeCreate();
            notifyCntx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Creación exitosa", objectName + " creado exitosamente"));
            executeInit();           
            return true;
        } catch(CayenneRuntimeException ex) {
            notifyCntx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Creación fallida", ex.getMessage()));
        }
        return false;
    }

    public final void destroyElement() {
        FacesContext notifyCntx = FacesContext.getCurrentInstance();
        try {
            executeDestroy();
            
            notifyCntx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Eliminación exitosa", objectName + " eliminado exitosamente"));
            executeInit();
        } catch(IllegalArgumentException ex) {
            notifyCntx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Eliminación de objeto nulo", "Se ha tratado de eliminar un objeto nulo"));
        } catch(DeleteDenyException ex) {
            notifyCntx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Eliminación fallida", ex.getMessage()));
        }
    }
    
    protected abstract void executeUpdate();
    
    protected abstract void executeCreate();
    
    protected abstract void executeDestroy();
    
    protected abstract void executeInit();
}
