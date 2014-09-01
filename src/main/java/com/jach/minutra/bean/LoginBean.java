package com.jach.minutra.bean;

import com.jach.minutra.controller.MovementsController;
import com.jach.minutra.login.Login;
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

    private String userName;
    private String pwd;
    private String role;
    private String displayName;
    private boolean auditor;
    private boolean local;
    private String title;
    private String mail;
    private boolean loggedIn = false;
    
    private final String environment = FacesContext.getCurrentInstance().getApplication().getProjectStage().name();
    private final Login login = new Login();
    
    private static final Logger LOGGER = Logger.getLogger(LoginBean.class);
    
    public LoginBean() {
        auditor = false;
        local = false;
        
        //Verifico las cookies de sesión:
        UsrInfoVO usrInfo = Login.getLoginCookies();
        if (usrInfo != null) {
            userName = usrInfo.getUserName();
            role = usrInfo.getRole();
            displayName = usrInfo.getDisplayName();
            auditor = usrInfo.isAuditor();
            local = usrInfo.isLocal();
            title = usrInfo.getTitle();
            mail = usrInfo.getMail();
            
            loggedIn = true;
        }
    }

    /**
     * Confirm if an user has access or not to the application.
     * @return String success or fail.
     */
    public String checkValidUser() {
        String msg;
        
        //---|| Usuario o auditor.
        UsrInfoVO usrInfo = login.getInitialUserInformation(userName, pwd);
        if (usrInfo == null) {
            loggedIn = false;
            return Navigation.LOGIN_FAILED.getUrl();
        }
        Login.setLoginCookies(usrInfo, CookieUtil.COOKIE_DEFAULT_AGE);
        
        //---|| init
        //TODO Quitar esto (cuando haya implementado bien las cookies)
        this.role = usrInfo.getRole();
        this.displayName = usrInfo.getDisplayName();
        this.title = usrInfo.getTitle();
        this.mail = usrInfo.getMail();
        this.loggedIn = true;
        this.auditor = usrInfo.isAuditor();
        this.local = usrInfo.isLocal();
        
        MovementsController mov = new MovementsController();

        msg = String.format("Inicio de sesión validado. Otorgando acceso al usuario '%s'", userName);
        LOGGER.info(msg);
        mov.save(msg);

        return Navigation.MENU.getUrl();
    }

    /**
     * <p>When invoked, it will invalidate the user's session
     * and move them to the login view.</p>
     *
     * @return <code>login</code>
     */
    public String logOut() {
        LOGGER.info("Invalidando sesión y cookies.");
        UsrInfoVO usrInfo = Login.getLoginCookies();
        if (usrInfo != null) {
            LOGGER.info("Limpiando las cookies.");
            Login.clearLoginCookies(usrInfo);
        }
        
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return Navigation.LOGOUT.getUrl();
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
        return Navigation.INDEX.getUrl();
    }

    //GETTERS
    public String getUserName() {
        return userName;
    }

    public String getPwd() {
        return pwd;
    }

    public String getRole() {
        return role;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isAuditor() {
        return auditor;
    }

    public boolean isLocal() {
        return local;
    }

    public String getTitle() {
        return title;
    }

    public String getMail() {
        return mail;
    }

    public String getEnvironment() {
        return environment;
    }
    
    
    //SETTERS
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    
}
