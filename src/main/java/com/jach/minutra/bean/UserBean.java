package com.jach.minutra.bean;

import com.jach.minutra.controller.UserController;
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
@ManagedBean(name = "user")
@ViewScoped
public class UserBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private List<Users> items = null;
    private Users current;
    private int page = 1;

    private final UserModel modelUser = new UserModel();
    
    private boolean edit = false;
    
    private static final Logger LOGGER = Logger.getLogger(UserBean.class);

    
    @PostConstruct
    private void init() {
        LOGGER.trace("Getting users from PostConstruct");
        this.items = modelUser.getUserList(true);
    }

    public void prepareCreate() {
        current = new Users();
    }
    
    public void create() {
        (new UserController(current)).create();
    }
    
    public void cancelCreate() {
        current = null;
    }

    public void remove() {
        (new UserController(current)).delete();
    }

    public void update() {
        (new UserController(current)).update();
        this.items = modelUser.getUserList(true);
    }
    
    public List<Users> getItems() {
//        synchronized (this) {
//            this.items = modelUser.getUserList();
//        }
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
    
}
