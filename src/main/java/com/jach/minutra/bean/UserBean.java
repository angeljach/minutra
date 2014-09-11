package com.jach.minutra.bean;

import com.jach.minutra.controller.UserController;
import com.jach.minutra.model.UserModel;
import com.jach.minutra.model.Users;
import com.jach.minutra.persistence.Crud;
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
public class UserBean extends CrudBean implements Crud, Serializable {
    
    private Users current;
    private List<Users> items;
    private int page = 1;
    
    private final UserModel modelUser = new UserModel();
    
    private boolean edit;
    
    private static final Logger LOGGER = Logger.getLogger(UserBean.class);

    public UserBean() {
        super("User");
        //this.init();
        edit = false;
    }
    
    @PostConstruct
    private void init() {
        LOGGER.trace("Getting users from PostConstruct");
        this.items = modelUser.getUserList();
    }
    
    @Override
    public String prepareEdit() {
        //Inicialización de listas
        
        
        //TODO El método debe ser void si no quiero navegar a otra página.
        //edit = true;
        return null;
    }

    @Override
    public String update() {
        //TODO El método debe ser void si no quiero navegar a otra página.
        updateElement();
        return null;
    }
    
    public void updateVoidTest() {
        (new UserController(current)).update();
    }
    
    @Override
    protected void executeUpdate() {
        (new UserController(current)).update();
    }

    @Override
    public String cancelUpdate() {
        //TODO El método debe ser void si no quiero navegar a otra página.
        return null;
    }

    @Override
    public String prepareCreate() {
        //TODO El método debe ser void si no quiero navegar a otra página.
        current = new Users();
        //edit = false;
        return null;
    }

    @Override
    public String create() {
        //TODO El método debe ser void si no quiero navegar a otra página.
        createElement();
        return null;
    }
    
    @Override
    protected void executeCreate() {
        (new UserController(current)).create();
    }

    @Override
    public String cancelCreate() {
        //TODO El método debe ser void si no quiero navegar a otra página.
        return null;
    }

    @Override
    public void destroy() {
        destroyElement();
    }
    
    @Override
    protected void executeDestroy() {
        (new UserController(current)).delete();
    }
    
    @Override
    protected void executeInit() {
        this.init();
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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
    
    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    
}
