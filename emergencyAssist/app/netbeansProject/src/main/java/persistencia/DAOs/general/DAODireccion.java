/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia.DAOs.general;

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
public class DAODireccion {

    public static String consultaDireccionPorId(int id) throws DBException {
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        String nombreDeLaVia, otros, localidad, provincia;
        int codigoPostal, numero;
        String dir = "";
        ResultSet result;
        try (
                PreparedStatement s = connection.getStatement("SELECT * FROM direcciones WHERE id = ?");) {
            s.setInt(1, id);
            result = s.executeQuery();

            if (result.next()) {
                nombreDeLaVia = result.getString("NombreDeLaVia");
                numero = result.getShort("Numero");
                otros = result.getString("Otros");
                codigoPostal = result.getInt("CodigoPostal");
                localidad = result.getString("Localidad");
                provincia = result.getString("Provincia");
                dir = obtenerJsonString(nombreDeLaVia, numero, otros, codigoPostal, localidad, provincia);
            }
            result.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Error en la consulta a la bd", ex);
            connection.closeConnection();
        }

        connection.closeConnection();
        return dir;
    }

    private static String obtenerJsonString(String nombreDeLaVia, int numero, String otros, int codigoPostal, String localidad, String provincia) {
        JsonObject json = Json.createObjectBuilder()
                .add("NombreDeLaVia", nombreDeLaVia)
                .add("Numero", numero)
                .add("Otros", otros)
                .add("CodigoPostal", codigoPostal)
                .add("Localidad", localidad)
                .add("Provincia", provincia)
                .build();

        return json.toString();
    }
}
