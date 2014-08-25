package com.jach.minutra.bean;

import com.jach.minutra.model.UserModel;
import com.jach.minutra.model.Users;
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
public class UserBean {
    
    private Users current;
    private List<Users> items;

    private static final Logger LOGGER = Logger.getLogger(UserBean.class);

    public UserBean() {
    }
    
    @PostConstruct
    private void init() {
        LOGGER.trace("Getting users from PostConstruct");
        UserModel userM = new UserModel();
        this.items = userM.getUserList();
    }

    
    //---|| Getters and Setters
    public Users getCurrent() {
        return current;
    }

    public void setCurrent(Users current) {
        this.current = current;
    }

    public List<Users> getItems() {
        return items;
    }

    public void setItems(List<Users> items) {
        this.items = items;
    }
    
    
}
