package com.jach.minutra.login;

import com.jach.minutra.model.DbObjectFromString;
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
     * @param email User name.
     * @param pwd Password.
     * @return Value Object with user session information.
     */
    public Users getInitialUserInformation(String email, String pwd) {
        //---|| Valido las credenciales del usuario.
        Users user = getCredentialsFromDataBase(email, pwd);
        if (user == null) {
            LOGGER.warn(String.format("Acceso denegado al usuario '%s'.", email));
        } else {
            LOGGER.info(String.format("Acceso concedido. Contraseña válida del usuario '%s'.", email));
        }
        return user;
    }
    
    private Users getCredentialsFromDataBase(String email, String pwd) {
        ObjectContext context = DataContext.getThreadObjectContext();
        Users user = DbObjectFromString.getUsersObjectFromEmail(context, email);
        
        if (this.projectStage == ProjectStage.Development) {
            LOGGER.info(String.format("Ambiente DESARROLLO. La contraseña de '%s' no será validada.", email));
        } else {
            LOGGER.info("Ambiente PRODUCTIVO");
            if (user == null) {
                LOGGER.warn(String.format("El usuario %s no fue encontrado en la BD.", email));
                return null;
            } else if(!user.getPassword().equals(pwd)) {
                LOGGER.warn(String.format("La contraseña del usuario %s es incorrecta.", email));
                return null;
            }
        }
        return user;
    }
        
}
