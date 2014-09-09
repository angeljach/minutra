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
    
}
