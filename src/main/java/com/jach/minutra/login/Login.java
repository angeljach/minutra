package com.jach.minutra.login;

import com.jach.minutra.model.Users;
import java.io.Serializable;
import javax.faces.application.ProjectStage;
import javax.faces.context.FacesContext;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.access.DataContext;
import org.apache.log4j.Logger;

/**
 *
 * @author jach
 */
public class Login implements Serializable{
    
    private final ProjectStage projectStage;
    
    private static final Logger LOGGER = Logger.getLogger(Login.class);

    public Login() {
        projectStage = FacesContext.getCurrentInstance().getApplication().getProjectStage();
    }
    
    /**
     * Validate the application credentials.
     * @param userName User name.
     * @param pwd Password.
     * @return Value Object with user session information.
     */
    public UsrInfoVO getInitialUserInformation(String userName, String pwd) {
        UsrInfoVO usrInfo;
        
        //---|| Valido si es auditor (y está habilitado).
        usrInfo = this.getInfoInitialFromAuditors(userName);
        if (usrInfo == null) {
            //---|| Valido si es usuario (y está habilitado)
            usrInfo = this.getInfoInitialFromUsers(userName);
        }

        if (usrInfo == null) {
            LOGGER.info(String.format("Acceso denegado al usuario '%s'", userName));
            return usrInfo;
        } else {
            //---|| Valido las credenciales del usuario.
            boolean bCredentials;
            
            if (this.projectStage == ProjectStage.Development) {
                LOGGER.info(String.format("Ambiente DESARROLLO. La contraseña de '%s' no será validada.", userName));
                bCredentials = true; 
            } else {
                if (usrInfo.isLocal()) {
                    LOGGER.info(String.format("Validando contraseña en la BD del usuario '%s'.", userName));
                    bCredentials = validCredentialsFromDataBase(userName, pwd);
                } else {
                    LOGGER.info(String.format("Validando contraseña en LDAP del usuario '%s'.", userName));
                    bCredentials = validCredentialsFromLDAP(userName, pwd);
                }
            }
            
            if (bCredentials == true) {
                LOGGER.info(String.format("Acceso concedido. Contraseña válida del usuario '%s'.", userName));
                return usrInfo;
            } else {
                LOGGER.warn(String.format("Acceso denegado. Contraseña inválida del usuario '%s'.", userName));
                return null;
            }
        }
    }
    
    private boolean validCredentialsFromDataBase(String userName, String pwd) {
        ObjectContext context = DataContext.getThreadObjectContext();
        Users user = AiDbObjectFromString.getUsersObjectFromString(context, userName);
        return (user != null) ? user.getPassword().equals(pwd) : false;
    }
    
    private boolean validCredentialsFromLDAP(String userName, String pwd) {
        DbUtilAppProp dbProp = new DbUtilAppProp();
        LdapTools ldapT = new LdapTools(
                userName, pwd,
                dbProp.getValue("ldap.domain"),
                dbProp.getValue("ldap.hostport"),
                dbProp.getValue("ldap.dn"));
        return ldapT.isValidUser();
    }
        
    private UsrInfoVO getInfoInitialFromUsers(String userName) {
        ObjectContext context = DataContext.getThreadObjectContext();
        UsrInfoVO usrInfo = null;
        Users user = AiDbObjectFromString.getUsersObjectFromString(context, userName);        
        if (user != null) {
            if (user.getIsEnable()) {
                usrInfo = new UsrInfoVO( userName,
                    user.getToUserRoles().getName(), user.getCompleteName(),
                    false, user.getIsLocal(),
                    user.getTitle(), user.getMail());
            } else {
                LOGGER.warn(String.format("El usuario '%s' está deshabilitado.", userName));
            }
        } else {
            LOGGER.warn(String.format("El usuario '%s' no fue encontrado en la BD.", userName));
        }
        return usrInfo;
    }
    
    public static void setLoginCookies(UsrInfoVO usrInfo, Integer cookieAge) {
        CookieUtil cookieUtil = new CookieUtil();
        cookieUtil.setCookie(EnumCookies.USER.getName(), usrInfo.getUserName(), cookieAge);
        cookieUtil.setCookie(EnumCookies.ROLE.getName(), usrInfo.getRole(), cookieAge);
        cookieUtil.setCookie(EnumCookies.DISPLAY_NAME.getName(), usrInfo.getDisplayName(), cookieAge);
        cookieUtil.setCookie(EnumCookies.AUDITOR.getName(), usrInfo.isAuditor() ? "T" : "F", cookieAge);
        cookieUtil.setCookie(EnumCookies.LOCAL.getName(), usrInfo.isLocal() ? "T" : "F", cookieAge);
        cookieUtil.setCookie(EnumCookies.TITLE.getName(), usrInfo.getTitle(), cookieAge);
        cookieUtil.setCookie(EnumCookies.EMAIL.getName(), usrInfo.getMail(), cookieAge);
    }
    
    public static void clearLoginCookies(UsrInfoVO usrInfo) {
        Login.setLoginCookies(usrInfo, 0);
    }
    
    public static UsrInfoVO getLoginCookies() {
        CookieUtil cookieUtil = new CookieUtil();
        String cUser = cookieUtil.getCookie(EnumCookies.USER.getName());
        String cRole = cookieUtil.getCookie(EnumCookies.ROLE.getName());
        String cDisplayName = cookieUtil.getCookie(EnumCookies.DISPLAY_NAME.getName()); 
        String cStrBAuditor = cookieUtil.getCookie(EnumCookies.AUDITOR.getName());
        String cStrBLocal = cookieUtil.getCookie(EnumCookies.LOCAL.getName());
        String cTitle = cookieUtil.getCookie(EnumCookies.TITLE.getName());
        String cEmail = cookieUtil.getCookie(EnumCookies.EMAIL.getName());
        if (cUser == null || cRole == null || cDisplayName == null || cStrBAuditor == null || 
                cStrBLocal == null || cTitle == null || cEmail == null) {
            LOGGER.warn("La información del usuario no está completa en las cookies. Se devuelve nulo.");
            return null;
        }
        //Los boleanos son asignados hasta aca para evitar la excepción de un objeto null con un método equals.
        boolean cBAuditor, cBLocal;
        switch (cStrBAuditor) {
            case "T": case "t": cBAuditor = true; break;
            case "F": case "f": cBAuditor = false; break;
            default: 
                LOGGER.warn("La información del usuario no está completa en las cookies. Se devuelve nulo.");
                return null;    //Valor no permitido
        }        

        switch (cStrBLocal) {
            case "T": case "t": cBLocal = true; break;
            case "F": case "f": cBLocal = false; break;
            default: 
                LOGGER.warn("La información del usuario no está completa en las cookies. Se devuelve nulo.");
                return null;    //Valor no permitido
        }
        return new UsrInfoVO(cUser, cRole, cDisplayName, cBAuditor, cBLocal, cTitle, cEmail);
    }
}
