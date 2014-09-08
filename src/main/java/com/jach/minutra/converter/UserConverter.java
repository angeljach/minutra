package com.jach.minutra.converter;

import com.jach.minutra.model.UserModel;
import com.jach.minutra.model.Users;
import java.util.HashMap;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author jach
 */
@FacesConverter(value = "userConverter")
public class UserConverter implements Converter {
    
    private final Map<String, Users> userMap;
    
    public UserConverter() {
        userMap = getMapWithNameAsKey();
    }
    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submValCompleteName) {
        //TODO Validar si hacer un SELECT a todos los usuarios es mas rápido a buscar en el mapa
        return (userMap.containsKey(submValCompleteName) ? userMap.get(submValCompleteName) : null);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        return (value == null) ? null : ((((Users) value).getFullName()));
    }
 
    private Map<String, Users> getMapWithNameAsKey() {
        UserModel dbUtil = new UserModel();
        Map<String, Users> varMap = new HashMap<>();
        dbUtil.getUserList().stream().forEach((user) -> {
            varMap.put(user.getFullName(), user);
        });
        return varMap;
    }
    
}
