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
public class DAORolesEnEmpresa {

    public static String consultaRolEnEmpresaAcutalPorNif(String nif) throws DBException {
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        String nombreTipo, fechaPermiso, numeroColegiado, fechaInicioPuesto;
        String rol = "";
        ResultSet result;
        
        try (
                PreparedStatement s = connection.getStatement("SELECT * FROM rolesenempresa INNER JOIN tiposderol ON rol = idtipo WHERE empleado = ? ORDER BY FechaInicioEnPuesto desc");) {
            s.setString(1, nif);
            result = s.executeQuery();

            if (result.next()) {
                nombreTipo = result.getString("NombreTipo");
                fechaInicioPuesto = result.getString("FechaInicioEnPuesto");
                fechaPermiso = result.getString("FechaPermisoConduccion");
                numeroColegiado = result.getString("NumeroColegiadoMedico");
                rol = obtenerJsonString(nombreTipo, fechaInicioPuesto, fechaPermiso, numeroColegiado);

            }
            result.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Error en la consulta a la bd", ex);
            connection.closeConnection();
        }
        connection.closeConnection();
        return rol;
    }

    private static String obtenerJsonString(String nombreTipo, String fechaInicioPuesto, String fechaPermiso, String numeroColegiado) {
        JsonObject json = Json.createObjectBuilder()
                .add("NombreTipo", nombreTipo)
                .add("FechaInicioEnPuesto", fechaInicioPuesto)
                .add("FechaPermisoConduccion", fechaPermiso != null ? fechaPermiso : "")
                .add("NumeroColegiadoMedico", numeroColegiado != null ? numeroColegiado : "")
                .build();

        return json.toString();
    }
}
