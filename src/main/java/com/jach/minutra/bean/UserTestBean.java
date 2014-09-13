package com.jach.minutra.bean;

import com.jach.minutra.controller.UserController;
import com.jach.minutra.model.UserModel;
import com.jach.minutra.model.Users;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author jach
 */
@ManagedBean(name = "user")
@ViewScoped
public class UserTestBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private static final int CLIENT_ROWS_IN_AJAX_MODE = 15;
    private List<Users> items = null;
    
    private Users current;
    private int page = 1;

    private int clientRows;
    
    private final UserModel modelUser = new UserModel();
    
    private boolean edit = false;

    public void switchAjaxLoading(ValueChangeEvent event) {
        this.clientRows = (Boolean) event.getNewValue() ? CLIENT_ROWS_IN_AJAX_MODE : 0;
    }
    
    public void prepareCreate() {
        current = new Users();
    }
    
    public void create() {
        (new UserController(current)).create();
    }

    public void remove() {
        (new UserController(current)).delete();
    }

    public void update() {
        (new UserController(current)).update();
        this.items = modelUser.getUserList();
    }
    
    public List<Users> getItems() {
        synchronized (this) {
            this.items = modelUser.getUserList();
        }
        return items;
    }

    public Users getCurrent() {
        return current;
    }

    public void setCurrent(Users current) {
        this.current = current;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
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
