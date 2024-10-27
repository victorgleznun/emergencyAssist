/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia.DAOs.llamada;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistencia.DBAccess.DBConnection;
import servicioscomunes.excepciones.DBException;

/**
 *
 * @author izajime
 */
public class DAOConsejo {
    
    public static void insertConsejo(String descripcion, String resultado, boolean soluciona, int idLlamada) throws DBException {
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();

        try (
                PreparedStatement s = connection.getStatement("INSERT INTO consejos (descripcion, resultado, soluciona, llamada) VALUES (?, ?, ?, ?)");) {
            s.setString(1, descripcion);
            s.setString(2, resultado);
            s.setBoolean(3, soluciona);
            s.setInt(4, idLlamada);
            s.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Error en la consulta a la bd", ex);
            connection.closeConnection();
        }
        connection.closeConnection();
    }
}
