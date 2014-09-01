package com.jach.minutra.persistence;

/**
 *
 * @author jach
 */
public interface Crud {
    public String prepareEdit();
    
    public String update();
    
    public String cancelUpdate();
    
    public String prepareCreate();
    
    public String create();
    
    public String cancelCreate();
    
    public void destroy();
}
