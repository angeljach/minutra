package com.jach.minutra.bean;

import com.jach.minutra.model.Minutes;
import com.jach.minutra.persistence.Crud;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;

/**
 *
 * @author jach
 */
@ManagedBean(name = "minute")
@ViewScoped
public class MinuteBean extends CrudBean implements Crud {

    private Minutes current;
    private List<Minutes> items;

    private int page = 1;

    private static final Logger LOGGER = Logger.getLogger(MinuteBean.class);

    public MinuteBean() {
        super("Minuta");
        this.init();
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
    
    @PostConstruct
    private void init() {
        LOGGER.trace("Getting minutes from PostConstruct");
        MinuteModel userM = new MinuteModel();
        this.items = userM.getMinuteList();
    }
    
    //---|| Getters and Setters
    public Minutes getCurrent() {
        return current;
    }

    public void setCurrent(Minutes current) {
        this.current = current;
    }
    
    public int getPage() {
        return page;
    }
 
    public void setPage(int page) {
        this.page = page;
    }

    public List<Minutes> getItems() {
        return items;
    }

    public void setItems(List<Minutes> items) {
        this.items = items;
    }
    
}
