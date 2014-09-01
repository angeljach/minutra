package com.jach.minutra.model;

import org.apache.cayenne.Cayenne;
import org.apache.cayenne.CayenneRuntimeException;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.SelectQuery;
import org.apache.log4j.Logger;

/**
 *
 * @author jach
 * @param <T>
 */
public class GenericDBObjectFromString<T> {
        private T t;    // T = "Type"
        private final Class<? extends T> classType;
        private static final Logger LOGGER = Logger.getLogger(GenericDBObjectFromString.class);
        
        public GenericDBObjectFromString(Class<? extends T> classType) {
            this.classType = classType;
        }
        
        /**
         * Search for a value in a field in a table in a data base and return the specified object (the register).
         * @param c Object Context.
         * @param field Name of the data base field.
         * @param value Value of the data base field to search.
         * @return Data base object.
         */
        public T getDBObjectFromString(ObjectContext c, String field, String value) {
            Expression e = ExpressionFactory.matchExp(field, value);
            SelectQuery sel = new SelectQuery(classType, e);
            T obj = null;
            try {
                obj = (T) Cayenne.objectForQuery(c, sel);
            } catch(CayenneRuntimeException crex) {
                LOGGER.error(String.format("Ocurrió un error al tratar de "
                        + "obtener el objeto %s de la BD.", classType.getName()));
                LOGGER.error(crex.getMessage());
            }
            return obj;
        }
        
        public void set(T t) { this.t = t; }
        public T get() { return t; }
    }