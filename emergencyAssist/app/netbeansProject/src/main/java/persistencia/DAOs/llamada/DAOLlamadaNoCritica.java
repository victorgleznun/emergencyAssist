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
import persistencia.DBAccess.DBConnection;
import servicioscomunes.excepciones.DBException;

/**
 *
 * @author izajime
 */
public class DAOLlamadaNoCritica {

    public static String consultaLlamadaNoCriticaPorActivacion(int idActivacion) throws DBException {
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        String llamadaAsegurado;
        boolean esLeve;
        String llamadaNoCritica = "";
        ResultSet result;

        try (
                PreparedStatement s = connection.getStatement("SELECT * FROM llamadasnocriticas L WHERE L.RequiereOperativo = ?");) {
            s.setInt(1, idActivacion);
            result = s.executeQuery();

            if (result.next()) {
                llamadaAsegurado = DAOLlamadaAsegurado.consultaAseguradoPorId(result.getInt("Id"));
                esLeve = result.getBoolean("esLeve");
                llamadaNoCritica = obtenerJsonString(llamadaAsegurado, esLeve);
            }

            result.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Error en la consulta a la bd", ex);
            connection.closeConnection();
        }
        connection.closeConnection();
        return llamadaNoCritica;
    }
    
    public static int insertLlamadaNoCritica(String telefono, LocalDate fechaInicio, LocalTime horaInicio, LocalDate fechaFin, LocalTime horaFin, String comunicante, String descripcion, String nifP, boolean esLeve, String nifO) throws DBException {
        int idLlamada = DAOLlamadaAsegurado.insertLlamadaAsegurado(telefono, fechaInicio, horaInicio, fechaFin, horaFin, comunicante, descripcion, nifP, nifO);
        
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();

        try (
                PreparedStatement s = connection.getStatement("INSERT INTO llamadasnocriticas (id, esleve, requiereoperativo) VALUES (?, ?, null)");) {
            s.setInt(1, idLlamada);
            s.setBoolean(2, esLeve);
            s.executeUpdate();


        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Error en la consulta a la bd", ex);
            connection.closeConnection();
        }
        connection.closeConnection();
        return idLlamada;
    }

    private static String obtenerJsonString(String llamadaAsegurado, boolean esLeve) {
        JsonObject json = Json.createObjectBuilder()
                .add("LlamadaAsegurado", llamadaAsegurado)
                .add("EsLeve", esLeve)
                .build();

        return json.toString();
    }
}
