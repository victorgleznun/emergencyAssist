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
 * @author izajime
 */
public class DAOPoliza {

    public static String consultaPolizaPorNumeroYNif(String nPoliza, String nif) throws DBException {
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        String poliza = "";
        String fechaInicio, fechaVencimiento;
        ResultSet result;
        
        try (
                PreparedStatement s = connection.getStatement("SELECT * FROM polizascontratadas PP INNER JOIN polizas P ON PP.poliza = P.numeropoliza WHERE P.numeropoliza = ? AND PP.asegurado = ?");) {
            s.setString(1, nPoliza);
            s.setString(2, nif);
            result = s.executeQuery();

            if (result.next()) {
                fechaInicio = result.getString("FechaInicio");
                fechaVencimiento = result.getString("FechaVencimiento");
                poliza = obtenerJsonString(nPoliza, fechaInicio, fechaVencimiento);
            }
            result.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Error en la consulta a la bd", ex);
            connection.closeConnection();
        }
        connection.closeConnection();
        return poliza;
    }

    private static String obtenerJsonString(String nPoliza, String fechaInicio, String fechaVencimiento) {
        JsonObject json = Json.createObjectBuilder()
                .add("NPoliza", nPoliza)
                .add("FechaInicio", fechaInicio)
                .add("FechaVencimiento", fechaVencimiento)
                .build();

        return json.toString();
    }
}
