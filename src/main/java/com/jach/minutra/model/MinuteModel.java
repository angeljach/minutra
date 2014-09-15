package com.jach.minutra.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;
import org.apache.log4j.Logger;

/**
 *
 * @author jach
 */
public class MinuteModel implements Serializable {
    
    private final ObjectContext context;
    
    private static final Logger LOGGER = Logger.getLogger(MinuteModel.class);

    public MinuteModel() {
        this.context = DataContext.getThreadObjectContext();
    }

    public List<Minutes> getMinuteList() {
        LOGGER.trace("Retriving minute list order by from_date descending.");
        SelectQuery sel = new SelectQuery(Minutes.class);
        sel.addOrdering(new Ordering(Minutes.FROM_DATE_PROPERTY, SortOrder.DESCENDING));
        return (List<Minutes>) context.performQuery(sel);
    }
    
    public List<Users> getUsedUsersFromSummary(Minutes minute) {
        Expression e = ExpressionFactory.matchDbExp(MinuteMembers.TO_MINUTES_PROPERTY, minute);
        SelectQuery sel = new SelectQuery(MinuteMembers.class, e);

        List<MinuteMembers> lstResSummPartic = (List<MinuteMembers>) minute.getObjectContext().performQuery(sel);
        List<Users> lstUsers = new ArrayList<>();
        lstResSummPartic.stream().forEach((summPart) -> {
            lstUsers.add(summPart.getToUsers());
        });        
        return lstUsers;
    }
    
    // Obtiene los usuarios disponibles para ser usados como miembros de una minuta.
    public List<Users> getAvailableUsersFromSummary(Minutes minute) {

        //---|| Obtengo todos los usuarios (no locales y activos).
        List<Users> lstAvailUsers = (new UserModel()).getUserList(false);
        //---|| Obtengo los documentos usados en la auditoría.
        List<Users> lstUsedUsers = this.getUsedUsersFromSummary(minute);
        
        //Quito de la lista TOTAL los usuarios usados.
        lstAvailUsers.removeAll(lstUsedUsers);
        
        return (lstAvailUsers);
    }
    
}
