/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia.DAOs.operador;

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
public class DAOOperadoresEnTurno {
    
    public static boolean deleteOperadorEnTurnoByNifYId(String nif, int idTurno) throws DBException {
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        int rowsResult;
        
        try (
                PreparedStatement s = connection.getStatement("DELETE FROM operadoresenturno WHERE turno = ? AND operador = ?");
        ){ 
            s.setInt(1, idTurno);
            s.setString(2, nif);
            
            rowsResult = s.executeUpdate();
           return rowsResult > 0;
                   
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Error en la consulta a la bd", ex);
            connection.closeConnection();
        }
        connection.closeConnection();
        return false;
    }
    
    public static boolean insertOperadorEnTurnoByNifYId(String nif, int idTurno) throws DBException {
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        int rowsResult;
        
        try (
                PreparedStatement s = connection.getStatement("INSERT INTO operadoresenturno (turno, operador) VALUES (?, ?)");
        ){ 
            s.setInt(1, idTurno);
            s.setString(2, nif);
            
            rowsResult = s.executeUpdate();
            return rowsResult > 0;
            
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Error en la consulta a la bd", ex);
            connection.closeConnection();
        }
        connection.closeConnection();
        return false;
    }
    
}
