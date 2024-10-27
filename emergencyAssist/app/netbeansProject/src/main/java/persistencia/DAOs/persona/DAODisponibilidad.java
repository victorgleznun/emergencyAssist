/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia.DAOs.persona;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import persistencia.DBAccess.DBConnection;
import servicioscomunes.excepciones.DBException;

/**
 *
 * @author fagon
 */
public class DAODisponibilidad {

    public static String consultaDisponibilidadActualPorNif(String nif) throws DBException {
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        String nombreTipo, fechaInicio, fechaFin;
        String disponibilidad = "";
        ResultSet result;
        
        try (
                PreparedStatement s = connection.getStatement("SELECT * FROM disponibilidades INNER JOIN tiposdedisponibilidad ON disponibilidad = idtipo WHERE empleado = ? and fechafin is null");) {
            s.setString(1, nif);
            result = s.executeQuery();

            if (result.next()) {
                nombreTipo = result.getString("NombreTipo");
                fechaInicio = result.getString("FechaInicio");
                fechaFin = result.getString("FechaFin");
                disponibilidad = obtenerJsonString(nombreTipo, fechaInicio, fechaFin);
            }
            result.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Error en la consulta a la bd", ex);
            connection.closeConnection();
        }
        connection.closeConnection();
        return disponibilidad;
    }
    
    public static String consultaDisponibilidadPorNifYFecha(String nif, String fecha) throws DBException {
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        String nombreTipo, fechaInicio, fechaFin;
        String disponibilidad = "";
        ResultSet result;
        
        try (
                PreparedStatement s = connection.getStatement("SELECT * FROM disponibilidades INNER JOIN tiposdedisponibilidad ON disponibilidad = idtipo "
                                                              + " WHERE empleado = ? AND (? <= fechaFin OR fechaFin is null) AND ? >= fechaInicio "
                                                              + " ORDER BY fechaFin DESC");) {
            s.setString(1, nif);
            s.setString(2, fecha);
            s.setString(3, fecha);
            result = s.executeQuery();

            if (result.next()) {
                nombreTipo = result.getString("NombreTipo");
                fechaInicio = result.getString("FechaInicio");
                fechaFin = result.getString("FechaFin");
                disponibilidad = obtenerJsonString(nombreTipo, fechaInicio, fechaFin);
            }
            result.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Error en la consulta a la bd", ex);
            connection.closeConnection();
        }
        connection.closeConnection();
        return disponibilidad;
    }
    
    private static String obtenerJsonString(String nombreTipo, String fechaInicio, String fechaFin) {
        JsonObject json = Json.createObjectBuilder()
                .add("NombreTipo", nombreTipo)
                .add("FechaInicio", fechaInicio)
                .add("FechaFin", fechaFin != null ? fechaFin : "")
                .build();

        return json.toString();
    }
}
