package com.jach.minutra.controller;

import com.jach.minutra.model.Movements;
import com.jach.minutra.model.Users;
import java.io.Serializable;
import java.util.Date;
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
public class MovementsController implements Serializable {
    /**
     * Cayenne Context Object.
     */
    private final ObjectContext context;
    /**
     * User Object. Holds the information of the current logged user.
     */
    private final Users user;
    /**
     * Maximum size of the message.
     */
    private static final int MESSAGE_MAX_SIZE = 300;
    /**
     * Logger Object.
     */
    private static final Logger LOGGER = Logger.getLogger(MovementsController.class);

    public MovementsController(Users user) {
        LOGGER.trace("Entrando a constructor de Movimientos");
        context = DataContext.getThreadObjectContext();

        LOGGER.trace("Obtención del objeto auditor.");
        this.user = user;
    }

    /**
     * Register information in movements_users table. Also, put auditor_name and date of the
     * movement.
     *
     * @param message String to store in data base.
     */
    public void save(String message) {
        LOGGER.trace("Llamado al método registra(mensaje)");
        String newMessage = message;

        if (user == null) {
            LOGGER.error("Error al tratar de crear un registro en la tabla de "
                    + "movimientos, debido a que el usuario con que se trato "
                    + "de realizar no fue encontrado.");
        } else {
            LOGGER.trace("Validando la longitud del mensaje");
            if (message.length() > MESSAGE_MAX_SIZE) {
                LOGGER.warn("El mensaje no puede ser registrado completamente debido a que su "
                        + "longitud excede los 1024 caracteres. Se almacenará truncado.");
                newMessage = message.substring(0, MESSAGE_MAX_SIZE);
            }

            try {
                Movements mov = context.newObject(Movements.class);
                mov.setCreationDate(new Date());
                mov.setDetail(newMessage);
                mov.setToUsers(user);    //

                context.commitChanges();
                LOGGER.info(String.format("Registro creado en la tabla "
                        + "movements_user: [%s][%s]",
                        user.getUserName(), message));
            } catch (Exception ex) {
                LOGGER.error(ex.getMessage());
                LOGGER.error(String.format("Error al tratar de insertar el "
                        + "mensaje '%s' en la tabla de movements_user.", message));
            }
        }
    }

    /**
     * Obtain a list of all registers from table movements_auditor.
     *
     * @return List of MovementsAuditor objects.
     */
    public List<Movements> getAllMovements() {
        LOGGER.debug("Llamado al método getAllMovements");
        return this.getMovementsFromDate(null);
    }

    /**
     * Obtain all the movements_auditor registered.
     *
     * @param dateFrom If null, return all the movements. If isn't, return from dateFrom.
     * @return List of MovementsAuditor object.
     */
    public List<Movements> getMovementsFromDate(Date dateFrom) {
        LOGGER.trace("Llamado al método getMovementsFromDate(dateFrom)");
        SelectQuery selectStatement;

        if (dateFrom != null) {
            LOGGER.debug("Devolviendo todos los movimientos registrados en el "
                    + "sistema posteriores a la fecha señalada.");
            Expression expr = ExpressionFactory.greaterExp(
                    Movements.CREATION_DATE_PROPERTY, dateFrom);
            selectStatement = new SelectQuery(Movements.class, expr);
        } else {
            //---|| Regreso todos los registros.
            LOGGER.debug("Devolviendo todos los movimientos registrados en el sistema.");
            selectStatement = new SelectQuery(Movements.class);
        }
        selectStatement.addOrdering(new Ordering("movDate", SortOrder.DESCENDING));

        return (List<Movements>) context.performQuery(selectStatement);
    }
}
