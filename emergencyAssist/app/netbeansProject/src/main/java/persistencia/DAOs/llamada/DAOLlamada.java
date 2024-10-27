/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia.DAOs.llamada;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import persistencia.DAOs.persona.DAOEmpleado;
import persistencia.DBAccess.DBConnection;
import servicioscomunes.excepciones.DBException;

/**
 *
 * @author izajime
 */
public class DAOLlamada {

    public static String consultaLlamadaPorId(int idLlamada) throws DBException {
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        String telefono, fechaInicio, horaInicio, fechaFin, horaFin, comunicante, operador;
        String llamada = "";
        ResultSet result;

        try (
                PreparedStatement s = connection.getStatement("SELECT * FROM llamadas L WHERE L.id = ?");) {
            s.setInt(1, idLlamada);
            result = s.executeQuery();

            if (result.next()) {
                telefono = result.getString("NumeroTelefonoOrigen");
                fechaInicio = result.getString("FechaInicio");
                horaInicio = result.getString("HoraInicio");
                fechaFin = result.getString("FechaFin");
                horaFin = result.getString("HoraFin");
                comunicante = result.getString("NombreComunicante");
                operador = DAOEmpleado.consultaEmpleadoPorNif(result.getString("AtendidaPor"));
                llamada = obtenerJsonString(telefono, fechaInicio, horaInicio, fechaFin, horaFin, comunicante, operador);
            }

            result.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Error en la consulta a la bd", ex);
            connection.closeConnection();
        }
        connection.closeConnection();
        return llamada;
    }
    
    public static int insertLlamada(String telefono, LocalDate fechaInicio, LocalTime horaInicio, LocalDate fechaFin, LocalTime horaFin, String comunicante, String nifO) throws DBException {
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        int idLlamada = consultarNuevoIdLlamada() + 1;

        try (
                PreparedStatement s = connection.getStatement("INSERT INTO llamadas (id, numerotelefonoorigen, fechainicio, horainicio, fechafin, horafin, nombrecomunicante, atendidapor) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");) {
            s.setInt(1, idLlamada);
            s.setString(2, telefono);
            s.setDate(3, Date.valueOf(fechaInicio));
            s.setTime(4, Time.valueOf(horaInicio));
            s.setDate(5, Date.valueOf(fechaFin));
            s.setTime(6, Time.valueOf(horaFin));
            s.setString(7, comunicante);
            s.setString(8, nifO); 
            s.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Error en la consulta a la bd", ex);
            connection.closeConnection();
        }
        connection.closeConnection();
        return consultarNuevoIdLlamada();
    }
    
    private static int consultarNuevoIdLlamada() throws DBException {
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        ResultSet result;
        
        try (
                PreparedStatement s = connection.getStatement("SELECT MAX(id) as newId FROM llamadas");) {
                result = s.executeQuery();
            if (result.next()) {
                return result.getInt("newId");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Error en la consulta a la bd", ex);
            connection.closeConnection();
        }
        connection.closeConnection();
        return 1;
        
    }

    private static String obtenerJsonString(String telefono, String fechaInicio, String horaInicio, String fechaFin, String horaFin, String comunicante, String operador) {
        JsonObject json = Json.createObjectBuilder()
                .add("Telefono", telefono)
                .add("FechaInicio", fechaInicio)
                .add("HoraInicio", horaInicio)
                .add("FechaFin", fechaFin)
                .add("HoraFin", horaFin)
                .add("Comunicante", comunicante)
                .add("Operador", operador)
                .build();

        return json.toString();
    }
}
