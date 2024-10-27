/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia.DAOs.persona;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import persistencia.DBAccess.DBConnection;
import servicioscomunes.excepciones.DBException;

/**
 *
 * @author fagon
 */
public class DAOEmpleado {

    public static String consultaEmpleadoPorLoginYPassword(String nif, String password) throws DBException{
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        String empleado = "";
        String rolAcutal, vinculacionActual, disponibilidadActual, persona, fechaInicioEnEmpresa;
        ResultSet result;
        try (
                PreparedStatement s = connection.getStatement("SELECT * FROM empleados WHERE Nif = ? and Password = ?");) {
            s.setString(1, nif);
            s.setString(2, password);
            result = s.executeQuery();

            if (result.next()) {
                fechaInicioEnEmpresa = result.getString("FechaInicioEnEmpresa");
                persona = DAOPersona.consultaPersonaNif(nif);
                rolAcutal = DAORolesEnEmpresa.consultaRolEnEmpresaAcutalPorNif(nif);
                vinculacionActual = DAOTipoDeVinculacion.consultaVinculacionConEmpresaActualPorNif(nif);
                disponibilidadActual = DAODisponibilidad.consultaDisponibilidadActualPorNif(nif);

                empleado = obtenerJsonString(fechaInicioEnEmpresa, persona, rolAcutal, vinculacionActual, disponibilidadActual);
            }
            result.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Error en la consulta a la bd", ex);
            connection.closeConnection();
        }
        connection.closeConnection();
        return empleado;
    }

    public static String consultaEmpleadoPorNif(String nif) throws DBException {
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        String empleado = "";
        String rolAcutal, vinculacionActual, disponibilidadActual, persona, fechaInicioEnEmpresa;
        ResultSet result;
        try (
                PreparedStatement s = connection.getStatement("SELECT * FROM empleados WHERE Nif = ?");) {
            s.setString(1, nif);
            result = s.executeQuery();

            if (result.next()) {
                fechaInicioEnEmpresa = result.getString("FechaInicioEnEmpresa");
                persona = DAOPersona.consultaPersonaNif(nif);
                rolAcutal = DAORolesEnEmpresa.consultaRolEnEmpresaAcutalPorNif(nif);
                vinculacionActual = DAOTipoDeVinculacion.consultaVinculacionConEmpresaActualPorNif(nif);
                disponibilidadActual = DAODisponibilidad.consultaDisponibilidadActualPorNif(nif);

                empleado = obtenerJsonString(fechaInicioEnEmpresa, persona, rolAcutal, vinculacionActual, disponibilidadActual);
            }
            result.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Error en la consulta a la bd", ex);
            connection.closeConnection();
        }
        connection.closeConnection();
        return empleado;
    }
    
    public static String consultaOperadoresPorFechaTurno(String fechaTurno, String tipo) throws DBException {
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        String operadores = "";
        String rolAcutal, vinculacionActual, disponibilidadActual, persona, fechaInicioEnEmpresa;
        ResultSet result;
        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        try (
                PreparedStatement s = connection.getStatement("SELECT E.* FROM empleados E INNER JOIN operadoresenturno O ON E.nif = O.operador INNER JOIN turnosdeoperador T ON O.turno = T.id INNER JOIN tiposdeturnodeoperador TT ON T.tipodeturno = TT.idTipo WHERE T.fechaTurno = ? AND TT.nombretipo = ?");) {
            s.setString(1, fechaTurno);
            s.setString(2, tipo);
            result = s.executeQuery();

            while (result.next()) {
                fechaInicioEnEmpresa = result.getString("FechaInicioEnEmpresa");
                persona = DAOPersona.consultaPersonaNif(result.getString("Nif"));
                rolAcutal = DAORolesEnEmpresa.consultaRolEnEmpresaAcutalPorNif(result.getString("Nif"));
                vinculacionActual = DAOTipoDeVinculacion.consultaVinculacionConEmpresaActualPorNif(result.getString("Nif"));
                disponibilidadActual = DAODisponibilidad.consultaDisponibilidadActualPorNif(result.getString("Nif"));

                operadores = obtenerJsonString(fechaInicioEnEmpresa, persona, rolAcutal, vinculacionActual, disponibilidadActual);
                arrayBuilder.add(Json.createReader(new StringReader(operadores)).readObject());
            }
            
            JsonArray lista = arrayBuilder.build();
            operadores = lista.toString();
            
            result.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Error en la consulta a la bd", ex);
            connection.closeConnection();
        }
        connection.closeConnection();
        return operadores;
    }
    
    public static String consultaOperadoresActuales() throws DBException {
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        String operadores = "";
        String rolAcutal, vinculacionActual, disponibilidadActual, persona, fechaInicioEnEmpresa;
        ResultSet result;
        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        try (
                PreparedStatement s = connection.getStatement("SELECT E.* FROM empleados E INNER JOIN rolesenempresa R ON E.nif = R.empleado INNER JOIN tiposderol T ON R.rol = T.idtipo" + 
                                                              " WHERE R.fechainicioenpuesto = (SELECT MAX(R1.fechainicioenpuesto)" +
                                                              " FROM empleados E1 INNER JOIN rolesenempresa R1 ON E1.nif = R1.empleado" +
                                                              " WHERE E1.nif = E.nif) AND T.nombretipo = 'Operador'");) {
            result = s.executeQuery();

            while (result.next()) {
                fechaInicioEnEmpresa = result.getString("FechaInicioEnEmpresa");
                persona = DAOPersona.consultaPersonaNif(result.getString("Nif"));
                rolAcutal = DAORolesEnEmpresa.consultaRolEnEmpresaAcutalPorNif(result.getString("Nif"));
                vinculacionActual = DAOTipoDeVinculacion.consultaVinculacionConEmpresaActualPorNif(result.getString("Nif"));
                disponibilidadActual = DAODisponibilidad.consultaDisponibilidadActualPorNif(result.getString("Nif"));

                operadores = obtenerJsonString(fechaInicioEnEmpresa, persona, rolAcutal, vinculacionActual, disponibilidadActual);
                arrayBuilder.add(Json.createReader(new StringReader(operadores)).readObject());
            }
            
            JsonArray lista = arrayBuilder.build();
            operadores = lista.toString();
            
            result.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Error en la consulta a la bd", ex);
            connection.closeConnection();
        }
        connection.closeConnection();
        return operadores;
    }

    private static String obtenerJsonString(String fechaInicioEnEmpresa, String persona, String rolActual, String vinculacionActual, String disponibilidadActual) {
        JsonObject json = Json.createObjectBuilder()
                .add("FechaInicioEnEmpresa", fechaInicioEnEmpresa)
                .add("Persona", persona)
                .add("RolEmpresa", rolActual)
                .add("VinculacionEmpresa", vinculacionActual)
                .add("DisponibilidadEmpresa", disponibilidadActual)
                .build();

        return json.toString();
    }
}
