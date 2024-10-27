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
 * @author izajime
 */
public class DAOTurnoOperativo {
    public static String consultaTurnoPorFecha(int idOperativo, String fechaTurno) throws DBException {
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        String fechaCreacion, tipoTurno;
        String turno = "";
        ResultSet result;
        
        try (
                PreparedStatement s = connection.getStatement("SELECT T.FechaCreacion, TI.NombreTipo FROM operativos O INNER JOIN turnosdeoperativo T ON O.turno = T.id INNER JOIN tiposdeturnodeoperativo TI ON T.TipoDeTurno = TI.idTipo WHERE O.id = ? and T.fechaTurno = ?");
        ){ 
            s.setInt(1, idOperativo);
            s.setString(2, fechaTurno);
            result = s.executeQuery();
            
           if (result.next()) {
               fechaCreacion = result.getString("FechaCreacion");
               tipoTurno = result.getString("NombreTipo");
               
               turno = obtenerJsonString(fechaTurno, fechaCreacion, tipoTurno);
           }
           result.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Error en la consulta a la bd", ex);
            connection.closeConnection();
        }
        connection.closeConnection();
        return turno;
    }
    
    private static String obtenerJsonString(String fechaTurno,String fechaCreacion ,String tipoTurno) {
        JsonObject json = Json.createObjectBuilder()
                .add("FechaTurno",fechaTurno)
                .add("FechaCreacion",fechaCreacion)
                .add("TipoTurno",tipoTurno)
                .build();

        return json.toString();
    }
}