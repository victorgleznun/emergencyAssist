/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia.DAOs.llamada;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistencia.DBAccess.DBConnection;
import servicioscomunes.excepciones.DBException;

/**
 *
 * @author izajime
 */
public class DAOLlamadaInfundada {

    public static int insertLlamadaInfundada(String telefono, LocalDate fechaInicio, LocalTime horaInicio, LocalDate fechaFin, LocalTime horaFin, String comunicante, String nifO) throws DBException {
        int idLlamada = DAOLlamada.insertLlamada(telefono, fechaInicio, horaInicio, fechaFin, horaFin, comunicante, nifO);
        
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();

        try (
                PreparedStatement s = connection.getStatement("INSERT INTO llamadasinfundadas (id) VALUES (?)");) {
            s.setInt(1, idLlamada);
            s.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Error en la consulta a la bd", ex);
            connection.closeConnection();
        }
        connection.closeConnection();
        return idLlamada;
    }
}
