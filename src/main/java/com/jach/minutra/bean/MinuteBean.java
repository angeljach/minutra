package com.jach.minutra.bean;

import com.jach.minutra.model.MinuteModel;
import com.jach.minutra.model.Minutes;
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
@ManagedBean(name = "minute")
@ViewScoped
public class MinuteBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final int CLIENT_ROWS_IN_AJAX_MODE = 15;
    
    private Minutes current;
    private List<Minutes> items;

    private int page = 1;

    private final MinuteModel modelMinute = new MinuteModel();
    
    private boolean edit = false;
    
    private static final Logger LOGGER = Logger.getLogger(MinuteBean.class);

    
    @PostConstruct
    private void init() {
        LOGGER.trace("Getting minutes from PostConstruct");
        this.items = modelMinute.getMinuteList();
    }
    
    public void prepareCreate() {
        current = new Minutes();
    }
    
//    public void create() {
//        (new MinutesController(current)).create();
//    }
//
//    public void remove() {
//        (new MinutesController(current)).delete();
//    }
//
//    public void update() {
//        (new MinutesController(current)).update();
//        this.items = modelMinute.getMinuteList();
//    }
    
    
    
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

}
