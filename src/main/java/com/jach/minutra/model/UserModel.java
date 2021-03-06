package com.jach.minutra.model;

import java.io.Serializable;
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
public class UserModel implements Serializable {
    
    private final ObjectContext context;
    
    private static final Logger LOGGER = Logger.getLogger(UserModel.class);

    public UserModel() {
        this.context = DataContext.getThreadObjectContext();
    }

    public List<Users> getUserList(boolean includeDisabledUsers) {
        LOGGER.trace("Retriving user list.");
        SelectQuery sel;
        if (includeDisabledUsers) {
            sel = new SelectQuery(Users.class);
        } else {
            //Exclude disabled users
            Expression e = ExpressionFactory.matchExp(Users.IS_ENABLED_PROPERTY, true);
            sel = new SelectQuery(Users.class, e);
        }
        
        sel.addOrdering(new Ordering(Users.USER_NAME_PROPERTY, SortOrder.ASCENDING));
        return (List<Users>) context.performQuery(sel);
    }
    
}
