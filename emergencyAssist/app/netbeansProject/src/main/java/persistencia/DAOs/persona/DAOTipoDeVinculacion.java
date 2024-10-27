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
public class DAOTipoDeVinculacion {

    public static String consultaVinculacionConEmpresaActualPorNif(String nif) throws DBException {
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        String nombreTipo, fechaInicio, fechaFin;
        String vinculo = "";
        ResultSet result;
        try (
                PreparedStatement s = connection.getStatement("SELECT * FROM vinculacionesconlaempresa INNER JOIN tiposdevinculacion ON vinculo = idtipo WHERE empleado = ? and fechafin is null");) {
            s.setString(1, nif);
            result = s.executeQuery();

            if (result.next()) {
                nombreTipo = result.getString("NombreTipo");
                fechaInicio = result.getString("FechaInicio");
                fechaFin = result.getString("FechaFin");
                vinculo = obtenerJsonString(nombreTipo, fechaInicio, fechaFin);
            }
            result.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Error en la consulta a la bd", ex);
            connection.closeConnection();
        }
        connection.closeConnection();
        return vinculo;
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
