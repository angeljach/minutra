package com.jach.minutra.bean;

import com.jach.minutra.model.UserModel;
import com.jach.minutra.model.Users;
import com.jach.minutra.persistence.Crud;
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
public class UserBean extends CrudBean implements Crud {
    
    private Users current;
    private List<Users> items;
    
    private int page = 1;

    private static final Logger LOGGER = Logger.getLogger(UserBean.class);

    public UserBean() {
        super("User");
        //this.init();
    }
    
    @PostConstruct
    private void init() {
        LOGGER.trace("Getting users from PostConstruct");
        UserModel userM = new UserModel();
        this.items = userM.getUserList();
    }
    
    @Override
    protected void executeUpdate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void executeCreate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void executeDestroy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void executeInit() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String prepareEdit() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String cancelUpdate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String prepareCreate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String create() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String cancelCreate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void destroy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    

//---|| Getters and Setters
    public Users getCurrent() {
        return current;
    }

    public void setCurrent(Users current) {
        this.current = current;
    }
    
    public int getPage() {
        return page;
    }
 
    public void setPage(int page) {
        this.page = page;
    }

    public List<Users> getItems() {
        return items;
    }

    public void setItems(List<Users> items) {
        this.items = items;
    }

    
}
