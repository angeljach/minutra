package com.jach.minutra.bean;

import com.jach.minutra.controller.MinuteController;
import com.jach.minutra.model.MinuteModel;
import com.jach.minutra.model.Minutes;
import com.jach.minutra.model.UserModel;
import com.jach.minutra.model.Users;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;

/**
 *
 * @author jach
 */
@ManagedBean(name = "minute")
@ViewScoped
public class MinuteBean implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Minutes current;
    private List<Minutes> items;
    
    private List<Users> userList;
    private Users selectedUser;

    private int page = 1;

    private final MinuteModel modelMinute = new MinuteModel();
    
    private boolean edit = false;
    
    private static final Logger LOGGER = Logger.getLogger(MinuteBean.class);

    
    @PostConstruct
    private void init() {
        LOGGER.trace("Getting minutes and users from PostConstruct");
        this.items = modelMinute.getMinuteList();
        this.userList = (new UserModel()).getUserList(false);
    }
    
    public void prepareCreate() {
        current = new Minutes();
    }
    
    public void create() {
        (new MinuteController(current)).create();
    }
    
    public void cancelCreate() {
        current = null;
    }

    public void remove() {
        (new MinuteController(current)).delete();
    }

    public void update() {
        (new MinuteController(current)).update();
        this.items = modelMinute.getMinuteList();
    }
    
    
    
    //---|| Getters and Setters
    public Minutes getCurrent() {
        return current;
    }

    public void setCurrent(Minutes current) {
        this.current = current;
    }
    
    public List<Minutes> getItems() {
        return items;
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

    public List<Users> getUserList() {
        return userList;
    }

    public Users getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(Users selectedUser) {
        this.selectedUser = selectedUser;
    }

}
