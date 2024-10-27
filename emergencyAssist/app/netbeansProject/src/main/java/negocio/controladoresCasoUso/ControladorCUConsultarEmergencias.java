/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.controladoresCasoUso;

import java.io.StringReader;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import negocio.modelos.operativo.Activacion;
import negocio.modelos.operativo.filtroEstrategy.*;
import negocio.modelos.operativo.Operativo;
import negocio.modelos.general.Sesion;
import persistencia.DAOs.operativo.DAOActivacion;
import persistencia.DAOs.operativo.DAOOperativo;
import servicioscomunes.excepciones.DBException;
import servicioscomunes.excepciones.NoExisteTurnoException;

/**
 *
 * @author izajime
 */
public class ControladorCUConsultarEmergencias {

    private Operativo operativo;
    private ArrayList<Activacion> activaciones;
    
    public ControladorCUConsultarEmergencias(){
        this.activaciones = new ArrayList<>();
        operativo = null;
    }

    public ArrayList<String> consultarEmergencia(String fecha, String filtro) throws NoExisteTurnoException, DBException{
        ArrayList<String> emergencias = new ArrayList<>();
        activaciones.removeAll(activaciones);
        Sesion sesion = Sesion.getInstance();
        String nif = sesion.getEmpleado().getNif();
        String operativoJSON = DAOOperativo.consultaOperativoPorFecha(nif, fecha);
        
        if (operativoJSON.equals("")) {
            throw new NoExisteTurnoException("No existe turno en la fecha seleccionada");
        }
        
        this.operativo = new Operativo(operativoJSON);
        String activacionesJSON = DAOActivacion.consultaActivacionesPorOperativo(this.operativo.getId());

        JsonArray jsonArrayActivaciones = Json.createReader(new StringReader(activacionesJSON)).readArray();
        
        for (JsonValue activacionEnLista : jsonArrayActivaciones) {
            JsonObject jsonActivacion = activacionEnLista.asJsonObject();
            this.activaciones.add(new Activacion(jsonActivacion.toString()));
        }

        this.activaciones = getActivacionesPorFiltro(filtro);

        String conc;
        for (Activacion activacion : this.activaciones) {
            conc = activacion.getFechaActivacion().toString() + " " + activacion.getHoraActivacion().toString();
            emergencias.add(conc);
        }

        return emergencias;
    }

    private ArrayList<Activacion> getActivacionesPorFiltro(String filtro) {
        FiltroContexto context = new FiltroContexto();
        switch (filtro) {
            case "Todo":
                context.setEstrategiaFiltro(new EstrategiaSinFiltro(activaciones));
                break;
            case "Con Traslado":
                context.setEstrategiaFiltro(new EstrategiaConTraslado(activaciones));
                break;
            case "Sin Traslado":
                context.setEstrategiaFiltro(new EstrategiaSinTraslado(activaciones));
                break;
        }

        return context.getEmergenciasPorFiltro();
    }

    public String mostrarDetalles(int index) {
        return activaciones.get(index).toString();
    }
}
