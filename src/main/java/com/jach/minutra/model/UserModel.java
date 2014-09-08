package com.jach.minutra.model;

import java.io.Serializable;
import java.util.List;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.access.DataContext;
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

    public List<Users> getUserList() {
        LOGGER.trace("Retriving user list.");
        SelectQuery sel = new SelectQuery(Users.class);
        sel.addOrdering(new Ordering(Users.FULL_NAME_PROPERTY, SortOrder.ASCENDING));
        return (List<Users>) context.performQuery(sel);
    }
    
}
