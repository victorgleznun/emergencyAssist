package persistencia.DAOs.persona;

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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author victo
 */
public class DAOPersona {

    public static String consultaPersonaNif(String nif) throws DBException {
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        String persona = "";
        String nombre, apellidos, fechaDeNacimiento, telefono, direccionPostal;
        ResultSet result; 
        
        try (
                PreparedStatement s = connection.getStatement("SELECT * FROM personas WHERE Nif = ?");) {
            s.setString(1, nif);
            result = s.executeQuery();

            if (result.next()) {
                nombre = result.getString("Nombre");
                apellidos = result.getString("Apellidos");
                fechaDeNacimiento = result.getString("FechaDeNacimiento");
                telefono = result.getString("Telefono");
                short dir = result.getShort("DireccionPostal");
                direccionPostal = DAODireccion.consultaDireccionPorId(dir);
                persona = obtenerJsonString(nif, nombre, apellidos, fechaDeNacimiento, telefono, direccionPostal);
            }
            result.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Error en la consulta a la bd", ex);
            connection.closeConnection();
        }
        connection.closeConnection();
        return persona;
    }

    private static String obtenerJsonString(String nif, String nombre, String apellidos, String fechaDeNacimiento, String telefono, String direccionPostal) {
        JsonObject json = Json.createObjectBuilder()
                .add("Nif", nif)
                .add("Nombre", nombre)
                .add("Apellidos", apellidos)
                .add("FechaDeNacimiento", fechaDeNacimiento)
                .add("Telefono", telefono)
                .add("DireccionPostal", direccionPostal)
                .build();

        return json.toString();
    }
}
