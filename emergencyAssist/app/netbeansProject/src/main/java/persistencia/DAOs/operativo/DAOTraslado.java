/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia.DAOs.operativo;

import persistencia.DAOs.general.DAODireccion;
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
 * @author izajime
 */
public class DAOTraslado {

    public static String consultaTrasladoPorId(String idTraslado) throws DBException {
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        String fechaInicio, horaInicio, nombreHospital, direccion;
        String traslado = "";
        ResultSet result;

        try (
                PreparedStatement s = connection.getStatement("SELECT * FROM decisionesdetrasladoahospital D INNER JOIN hospitales H ON D.destinodetraslado = H.id INNER JOIN direcciones DI ON H.direccion = DI.id WHERE D.id = ?");) {
            s.setString(1, idTraslado);
            result = s.executeQuery();

            if (result.next()) {
                fechaInicio = result.getString("FechaInicioTraslado");
                horaInicio = result.getString("HoraInicioTraslado");
                nombreHospital = result.getString("Nombre");
                direccion = DAODireccion.consultaDireccionPorId(result.getShort("Direccion"));
                traslado = obtenerJsonString(fechaInicio, horaInicio, nombreHospital, direccion);
            }

            result.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Error en la consulta a la bd", ex);
            connection.closeConnection();
        }
        connection.closeConnection();
        return traslado;
    }

    private static String obtenerJsonString(String fechaInicio, String horaInicio, String nombreHospital, String direccion) {
        JsonObject json = Json.createObjectBuilder()
                .add("FechaInicio", fechaInicio)
                .add("HoraInicio", horaInicio)
                .add("NombreHospital", nombreHospital)
                .add("Direccion", direccion)
                .build();

        return json.toString();
    }
}
