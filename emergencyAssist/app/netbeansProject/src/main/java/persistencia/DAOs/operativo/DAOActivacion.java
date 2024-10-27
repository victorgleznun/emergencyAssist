/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia.DAOs.operativo;

import persistencia.DAOs.llamada.DAOLlamadaNoCritica;
import persistencia.DAOs.general.DAODireccion;
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
public class DAOActivacion {

    public static String consultaActivacionesPorOperativo(int idOperativo) throws DBException {
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        String direccionDondeAcudir, fechaActivacion, horaActivacion, fechaCargo, horaCargo, fechaCierre, horaCierre, traslado, llamada;
        String activaciones = "";
        ResultSet result;

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        try (
                PreparedStatement s = connection.getStatement("SELECT A.* FROM activacionesdeoperativos A LEFT OUTER JOIN decisionesdetrasladoahospital D ON A.decisiontrasladoahospital = D.id LEFT OUTER JOIN hospitales H ON D.destinodetraslado = H.id LEFT OUTER JOIN  direcciones DI ON H.direccion = DI.id WHERE A.operativoActivado = ?");) {
            s.setInt(1, idOperativo);
            result = s.executeQuery();

            while (result.next()) {
                direccionDondeAcudir = DAODireccion.consultaDireccionPorId(result.getShort("DireccionDondeAcudir"));
                fechaActivacion = result.getString("FechaActivación");
                horaActivacion = result.getString("HoraActivación");
                fechaCargo = result.getString("FechaSeHaceCargoMedico");
                horaCargo = result.getString("HoraSeHaceCargoMedico");
                fechaCierre = result.getString("FechaCierre");
                horaCierre = result.getString("HoraCierre");
                traslado = DAOTraslado.consultaTrasladoPorId(result.getString("DecisionTrasladoAHospital"));
                llamada = DAOLlamadaNoCritica.consultaLlamadaNoCriticaPorActivacion(result.getInt("Id"));

                activaciones = obtenerJsonString(direccionDondeAcudir, fechaActivacion, horaActivacion, fechaCargo, horaCargo, fechaCierre, horaCierre, traslado, llamada);
                arrayBuilder.add(Json.createReader(new StringReader(activaciones)).readObject());
            }

            JsonArray lista = arrayBuilder.build();
            activaciones = lista.toString();

            result.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Error en la consulta a la bd", ex);
            connection.closeConnection();
        }
        connection.closeConnection();
        return activaciones;
    }

    private static String obtenerJsonString(String DireccionDondeAcudir, String fechaActivacion, String horaActivacion, String fechaCargo, String horaCargo, String fechaCierre, String horaCierre, String traslado, String llamada) {
        JsonObject json = Json.createObjectBuilder()
                .add("DireccionDondeAcudir", DireccionDondeAcudir)
                .add("FechaActivacion", fechaActivacion)
                .add("HoraActivacion", horaActivacion)
                .add("FechaCargo", fechaCargo != null ? fechaCargo : "")
                .add("HoraCargo", horaCargo != null ? horaCargo : "")
                .add("FechaCierre", fechaCierre != null ? fechaCierre : "")
                .add("HoraCierre", horaCierre != null ? horaCierre : "")
                .add("Traslado", traslado != null ? traslado : "")
                .add("Llamada", llamada)
                .build();

        return json.toString();
    }
}
