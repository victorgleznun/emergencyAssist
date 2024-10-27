/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia.DAOs.llamada;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import persistencia.DAOs.persona.DAOAsegurado;
import persistencia.DBAccess.DBConnection;
import servicioscomunes.excepciones.DBException;

/**
 *
 * @author izajime
 */
public class DAOLlamadaAsegurado {

    public static String consultaAseguradoPorId(int idLlamada) throws DBException {
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        String descripcion, paciente, llamada;
        String llamadaAsegurado = "";
        ResultSet result;

        try (
                PreparedStatement s = connection.getStatement("SELECT * FROM llamadasdeasegurados L WHERE L.id = ?");) {
            s.setInt(1, idLlamada);
            result = s.executeQuery();

            if (result.next()) {
                llamada = DAOLlamada.consultaLlamadaPorId(idLlamada);
                descripcion = result.getString("DescripcionDeLaEmergencia");
                paciente = DAOAsegurado.consultaAseguradosNif(result.getString("Paciente"));
                llamadaAsegurado = obtenerJsonString(llamada, descripcion, paciente);
            }

            result.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Error en la consulta a la bd", ex);
            connection.closeConnection();
        }
        connection.closeConnection();
        return llamadaAsegurado;
    }
    
    public static int insertLlamadaAsegurado(String telefono, LocalDate fechaInicio, LocalTime horaInicio, LocalDate fechaFin, LocalTime horaFin, String comunicante, String descripcion, String nifP, String nifO) throws DBException {
        int idLlamada = DAOLlamada.insertLlamada(telefono, fechaInicio, horaInicio, fechaFin, horaFin, comunicante, nifO);
        
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();

        try (
                PreparedStatement s = connection.getStatement("INSERT INTO llamadasdeasegurados (id, descripciondelaemergencia, paciente) VALUES (?, ?, ?)");) {
            s.setInt(1, idLlamada);
            s.setString(2, descripcion);
            s.setString(3, nifP);
            s.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Error en la consulta a la bd", ex);
            connection.closeConnection();
        }
        connection.closeConnection();
        return idLlamada;
    }

    private static String obtenerJsonString(String llamada, String descripcion, String paciente) {
        JsonObject json = Json.createObjectBuilder()
                .add("Llamada", llamada)
                .add("Descripcion", descripcion)
                .add("Paciente", paciente)
                .build();

        return json.toString();
    }
}
