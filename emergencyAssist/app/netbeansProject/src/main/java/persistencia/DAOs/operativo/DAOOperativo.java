/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia.DAOs.operativo;

import persistencia.DAOs.general.DAODireccion;
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
 * @author fagon
 */
public class DAOOperativo {

    public static String consultaOperativoPorFecha(String nif, String fechaTurno) throws DBException {
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        int id;
        String fechaCreacion, estado, turno, base, vehiculo, conductor, medico;
        String operativo = "";
        ResultSet result;

        try (
                PreparedStatement s = connection.getStatement("SELECT O.id AS idOperativo, E.*, T.*, O.*, C.* FROM operativos O INNER JOIN turnosdeoperativo T ON T.id = O.turno INNER JOIN estadosdeoperativo E ON O.estado = E.idEstado INNER JOIN consultorios C ON O.base = C.id WHERE (O.medico = ? or O.conductor = ?) and T.fechaturno = ?");) {
            s.setString(1, nif);
            s.setString(2, nif);
            s.setString(3, fechaTurno);
            result = s.executeQuery();

            if (result.next()) {
                id = result.getInt("idOperativo");
                fechaCreacion = result.getString("FechaCreacion");
                estado = result.getString("NombreEstado");
                turno = DAOTurnoOperativo.consultaTurnoPorFecha(result.getInt("idOperativo"), fechaTurno);
                base = DAODireccion.consultaDireccionPorId(result.getShort("Direccion"));
                vehiculo = DAOVehiculo.consultaVehiculoPorMatricula(result.getString("Vehiculo"));
                conductor = DAOEmpleado.consultaEmpleadoPorNif(result.getString("Conductor"));
                medico = DAOEmpleado.consultaEmpleadoPorNif(result.getString("Medico"));
                operativo = obtenerJsonString(id, fechaCreacion, estado, turno, base, vehiculo, conductor, medico);
            }
            result.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Error en la consulta a la bd", ex);
            connection.closeConnection();
        }
        connection.closeConnection();
        return operativo;
    }

    private static String obtenerJsonString(int id, String fechaCreacion, String estado, String turno, String base, String vehiculo, String conductor, String medico) {
        JsonObject json = Json.createObjectBuilder()
                .add("Id", id)
                .add("FechaCreacion", fechaCreacion)
                .add("Estado", estado)
                .add("Turno", turno)
                .add("Base", base)
                .add("Vehiculo", vehiculo)
                .add("Conductor", conductor)
                .add("Medico", medico)
                .build();

        return json.toString();
    }
}
