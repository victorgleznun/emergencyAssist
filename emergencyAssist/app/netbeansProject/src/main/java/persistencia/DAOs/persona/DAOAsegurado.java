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
 * @author victo
 */
public class DAOAsegurado {

    public static String consultaAseguradosNif(String nif) throws DBException {
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        String asegurado = "";
        String persona, sexo, numeroDeCuenta;
        ResultSet result;
        
        try (
                PreparedStatement s = connection.getStatement("SELECT * FROM asegurados WHERE Nif = ?");) {
            s.setString(1, nif);
            result = s.executeQuery();

            if (result.next()) {
                persona = DAOPersona.consultaPersonaNif(nif);
                sexo = result.getString("Sexo");
                numeroDeCuenta = result.getString("NumeroDeCuenta");
                asegurado = obtenerJsonString(persona, sexo, numeroDeCuenta);
            }
            result.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Error en la consulta a la bd", ex);
            connection.closeConnection();
        }
        connection.closeConnection();
        return asegurado;
    }
    
    public static String consultaAseguradoPorDatos(String nombre, String apellidos, String sexo, String fechaNacimiento) throws DBException {
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        String persona, numeroDeCuenta;
        String asegurado = "";
        
        ResultSet result;
        
        try (
                PreparedStatement s = connection.getStatement("SELECT * FROM asegurados A NATURAL JOIN personas P WHERE P.nombre = ? AND P.apellidos = ? AND A.sexo = ? AND P.fechadenacimiento = ?");) {
            s.setString(1, nombre);
            s.setString(2, apellidos);
            s.setString(3, sexo);
            s.setString(4, fechaNacimiento);
            result = s.executeQuery();

            if (result.next()) {
                persona = DAOPersona.consultaPersonaNif(result.getString("Nif"));
                numeroDeCuenta = result.getString("NumeroDeCuenta");
                asegurado = obtenerJsonString(persona, sexo, numeroDeCuenta);
            }
            result.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Error en la consulta a la bd", ex);
            connection.closeConnection();
        }
        connection.closeConnection();
        return asegurado;
    }

    private static String obtenerJsonString(String persona, String sexo, String NumeroDeCuenta) {
        JsonObject json = Json.createObjectBuilder()
                .add("Persona", persona)
                .add("Sexo", sexo)
                .add("NumeroDeCuenta", NumeroDeCuenta)
                .build();

        return json.toString();
    }
}
