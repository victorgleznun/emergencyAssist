/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia.DAOs.operativo;

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
 * @author victo
 */
public class DAOVehiculo {

    public static String consultaVehiculoPorMatricula(String matricula) throws DBException {
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        String vehiculo = "";
        double ubicacionLatitud, ubicacionLongitud;
        String fechaAlta, estado, nombreModelo, nombreMarca;
        ResultSet result;
                
        try (
                PreparedStatement s = connection.getStatement("SELECT V.*, E.*, M.nombre AS nombreModelo, A.nombre AS nombreMarca FROM vehiculos V INNER JOIN modelos M ON V.Modelo = M.Id INNER JOIN marcas A ON M.Marca = A.Id INNER JOIN estadosdevehiculo E ON V.Estado = E.IdEstado WHERE V.Matricula = ?");) {
            s.setString(1, matricula);
            result = s.executeQuery();

            if (result.next()) {
                ubicacionLatitud = result.getDouble("UbicacionLatitud");
                ubicacionLongitud = result.getDouble("UbicacionLongitud");
                fechaAlta = result.getString("FechaAlta");
                estado = result.getString("NombreEstado");
                nombreModelo = result.getString("nombreModelo");
                nombreMarca = result.getString("nombreMarca");
                vehiculo = obtenerJsonString(matricula, ubicacionLatitud, ubicacionLongitud, fechaAlta, estado, nombreModelo, nombreMarca);
            }
            result.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Error en la consulta a la bd", ex);
            connection.closeConnection();
        }
        connection.closeConnection();
        return vehiculo;
    }

    private static String obtenerJsonString(String matricula, double ubicacionLatitud, double ubicacionLongitud, String fechaAlta, String estado, String nombreModelo, String nombreMarca) {
        JsonObject json = Json.createObjectBuilder()
                .add("Matricula", matricula)
                .add("UbicacionLatitud", ubicacionLatitud)
                .add("UbicacionLongitud", ubicacionLongitud)
                .add("FechaAlta", fechaAlta)
                .add("Estado", estado)
                .add("NombreModelo", nombreModelo)
                .add("NombreMarca", nombreMarca)
                .build();

        return json.toString();
    }
}
