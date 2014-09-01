package com.jach.minutra.bean;

import com.jach.minutra.controller.MovementsController;
import com.jach.minutra.login.Login;
import com.jach.minutra.model.Users;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author acruz
 */
@ManagedBean(name = "login")
@SessionScoped
public class LoginBean implements Serializable {

    private String email;
    private String pwd;
    private Users user;
    private boolean loggedIn = false;
    
    private final String environment = FacesContext.getCurrentInstance().getApplication().getProjectStage().name();
    private final Login login = new Login();
    
    private static final String PAGE_DOOR = "/door";
    private static final String PAGE_LOGIN_FAILED = "/loginfailed.xhtml";
    private static final String PAGE_LOGOUT = "/logout.xhtml";
    
    private static final Logger LOGGER = Logger.getLogger(LoginBean.class);
    
    public LoginBean() {

    }

    /**
     * Confirm if an user has access or not to the application.
     * @return String success or fail.
     */
    public String checkValidUser() {
        String msg;

        user = login.getInitialUserInformation(email, pwd);
        if (user == null) {
            loggedIn = false;
            return PAGE_LOGIN_FAILED;
        }
        
        //---|| init
        this.loggedIn = true;
        
        MovementsController mov = new MovementsController(user);

        msg = String.format("Inicio de sesión validado. Otorgando acceso al usuario [%s][%s]", user.getEmail(), user.getName());
        LOGGER.info(msg);
        mov.save(msg);

        return PAGE_DOOR;
    }

    /**
     * <p>When invoked, it will invalidate the user's session
     * and move them to the login view.</p>
     *
     * @return <code>login</code>
     */
    public String logOut() {
        LOGGER.info("Invalidando sesión.");
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return PAGE_LOGOUT;
//        LOGGER.info("Otra invalidación.");
//        this.userName = "";
//        this.pwd = "";
//        this.role = "";
//        this.displayName = "";
//        this.auditor = false;
//        this.local = false;
//        this.title = "";
//        this.mail = "";
//        this.loggedIn = false;
//        
//        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
//        return Navigation.LOGOUT.getUrl().concat("?faces-redirect=true");
    }
    
    /**
     * Validate if a session is still active.
     * @return True if session is active
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }
    
    public String goIndex() {
        return PAGE_DOOR;
    }

    //GETTERS
    public Users getUser() {
        return user;
    }
    
    public String getEmail() {
        return email;
    }

    public String getPwd() {
        return pwd;
    }

    public String getEnvironment() {
        return environment;
    }
    
    
    //SETTERS
    public void setEmail(String email) {
        this.email = email;
    }
    

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    
}
