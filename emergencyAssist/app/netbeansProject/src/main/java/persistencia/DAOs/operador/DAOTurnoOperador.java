/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia.DAOs.operador;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import persistencia.DAOs.persona.DAOEmpleado;
import persistencia.DBAccess.DBConnection;
import servicioscomunes.excepciones.DBException;

/**
 *
 * @author izajime
 */
public class DAOTurnoOperador {
    public static String consultaTurnoPorFecha(String fechaTurno, String tipo) throws DBException {
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        String fechaCreacion, tipoTurno, operadores;
        int id;
        String turno = "";
        ResultSet result;
        
        try (
                PreparedStatement s = connection.getStatement("SELECT * FROM turnosdeoperador T INNER JOIN tiposdeturnodeoperador TT ON T.TipoDeTurno = TT.idTipo WHERE T.FechaTurno = ? AND TT.nombretipo = ?");
        ){ 
            s.setString(1, fechaTurno);
            s.setString(2, tipo);
            result = s.executeQuery();
            
           if (result.next()) {
               id = result.getInt("Id");
               fechaCreacion = result.getString("FechaCreacion");
               tipoTurno = result.getString("NombreTipo");
               operadores = DAOEmpleado.consultaOperadoresPorFechaTurno(fechaTurno, tipo);
               
               turno = obtenerJsonString(id, fechaTurno, fechaCreacion, tipoTurno, operadores);
           }
           result.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Error en la consulta a la bd", ex);
            connection.closeConnection();
        }
        connection.closeConnection();
        return turno;
    }
    
    public static String consultaTurnoAnteriorPorFechaYNif(String nif, String fechaTurno) throws DBException {
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        String fechaCreacion, fechaTurnoN, tipoTurno, operadores;
        int id;
        String turno = "";
        ResultSet result; 
        
        try (
                PreparedStatement s = connection.getStatement("SELECT * FROM empleados E INNER JOIN operadoresenturno O ON E.nif = O.operador INNER JOIN turnosdeoperador T ON O.turno = T.id"
                                                              + " INNER JOIN tiposdeturnodeoperador TT ON T.tipodeturno = TT.idtipo"
                                                              + " WHERE E.nif = ? AND T.fechaturno <= ? ORDER BY T.fechaturno DESC");
        ){ 
            s.setString(1, nif);
            s.setString(2, fechaTurno);
            result = s.executeQuery();
            
           if (result.next()) {
               id = result.getInt("Id");
               fechaTurnoN = result.getString("FechaTurno");
               fechaCreacion = result.getString("FechaCreacion");
               tipoTurno = result.getString("NombreTipo");
               operadores = DAOEmpleado.consultaOperadoresPorFechaTurno(fechaTurno, tipoTurno);
               
               turno = obtenerJsonString(id, fechaTurnoN, fechaCreacion, tipoTurno, operadores);
           }
           
           result.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Error en la consulta a la bd", ex);
            connection.closeConnection();
        }
        connection.closeConnection();
        return turno;
    }
    
    private static String obtenerJsonString(int id, String fechaTurno, String fechaCreacion, String tipoTurno, String operadores) {
        JsonObject json = Json.createObjectBuilder()
                .add("Id", id)
                .add("FechaTurno",fechaTurno)
                .add("FechaCreacion",fechaCreacion)
                .add("TipoTurno",tipoTurno)
                .add("Operadores",operadores)
                .build();

        return json.toString();
    }
}