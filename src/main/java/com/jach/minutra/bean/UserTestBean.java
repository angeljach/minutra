package com.jach.minutra.bean;

import com.jach.minutra.controller.UserController;
import com.jach.minutra.model.UserModel;
import com.jach.minutra.model.Users;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author jach
 */
@ManagedBean(name = "userTest")
@ViewScoped
public class UserTestBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private static final int CLIENT_ROWS_IN_AJAX_MODE = 15;
    private List<Users> allInventoryItems = null;
    
    private int currentCarIndex;
    private Users editedCar;
    private int page = 1;

    private int clientRows;
    
    
    
    private final UserModel modelUser = new UserModel();
    

    public void switchAjaxLoading(ValueChangeEvent event) {
        this.clientRows = (Boolean) event.getNewValue() ? CLIENT_ROWS_IN_AJAX_MODE : 0;
    }

    public void remove() {
        (new UserController(editedCar)).delete();
    }

    public void store() {
        (new UserController(editedCar)).update();
        this.allInventoryItems = modelUser.getUserList();
    }
    
    public List<Users> getAllInventoryItems() {
        synchronized (this) {
            this.allInventoryItems = modelUser.getUserList();
        }
        return allInventoryItems;
    }


    public int getCurrentCarIndex() {
        return currentCarIndex;
    }

    public void setCurrentCarIndex(int currentCarIndex) {
        this.currentCarIndex = currentCarIndex;
    }

    public Users getEditedCar() {
        return editedCar;
    }

    public void setEditedCar(Users editedCar) {
        this.editedCar = editedCar;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getClientRows() {
        return clientRows;
    }

    public void setClientRows(int clientRows) {
        this.clientRows = clientRows;
    }
    
}
