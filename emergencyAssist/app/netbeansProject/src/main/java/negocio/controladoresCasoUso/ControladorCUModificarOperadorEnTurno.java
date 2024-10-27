/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.controladoresCasoUso;

import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import negocio.modelos.operador.TurnoOperador;
import negocio.modelos.persona.DisponibilidadEmpresa;
import negocio.modelos.persona.Empleado;
import negocio.modelos.persona.TipoDeDisponibilidad;
import persistencia.DAOs.operador.DAOOperadoresEnTurno;
import persistencia.DAOs.operador.DAOTurnoOperador;
import persistencia.DAOs.persona.DAODisponibilidad;
import persistencia.DAOs.persona.DAOEmpleado;
import servicioscomunes.excepciones.DBException;
import servicioscomunes.excepciones.NoHayOperadoresParaCambiarException;
import servicioscomunes.excepciones.NoHayTurnoParaFechaException;

/**
 *
 * @author Mario Llorente
 */
public class ControladorCUModificarOperadorEnTurno {

    private TurnoOperador turno;
    private final ArrayList<Empleado> operadoresParaCambiar;
    private int seleccionOperadorACambiar;

    public ControladorCUModificarOperadorEnTurno() {
        this.turno = null;
        this.seleccionOperadorACambiar = -1;
        this.operadoresParaCambiar = new ArrayList<>();
    }

    public ArrayList<String> consultarOperadoresEnTurno(String fecha, String tipo) throws NoHayTurnoParaFechaException, DBException{
        ArrayList<String> operadoresEnTurnoString = new ArrayList<>();
        String turnoJSON = DAOTurnoOperador.consultaTurnoPorFecha(fecha, tipo);
        if (turnoJSON.equals("")){
            throw new NoHayTurnoParaFechaException("No existe ningún turno a modificar en la fecha seleccionada");
        }
        turno = new TurnoOperador(turnoJSON);

        for (Empleado operador : turno.getOperadores()) {
            operadoresEnTurnoString.add(operador.getNombre() + " | " + operador.getApellidos() + " | " + operador.getNif() + " | " + operador.getTelefono());
        }
        return operadoresEnTurnoString;
    }

    public ArrayList<String> colsultarOperadoresParaCambiar(int seleccion) throws NoHayOperadoresParaCambiarException, DBException {
        ArrayList<String> operadoresParaCambiarString = new ArrayList<>();
        ArrayList<Empleado> operadoresActuales = new ArrayList<>();
        this.seleccionOperadorACambiar = seleccion;
        this.operadoresParaCambiar.removeAll(operadoresParaCambiar);

        String operadoresActualesJSON = DAOEmpleado.consultaOperadoresActuales();
        JsonArray jsonArrayOperadoresActuales = Json.createReader(new StringReader(operadoresActualesJSON)).readArray();

        //Creamos todos los operadores que existen actualmente
        for (JsonValue operadorEnLista : jsonArrayOperadoresActuales) {
            JsonObject jsonOperador = operadorEnLista.asJsonObject();
            operadoresActuales.add(new Empleado(jsonOperador.toString()));
        }

        //Filtramos por candidatos a ser cambiamos
        String disponibilidadTurnoJsonString;
        DisponibilidadEmpresa disponibilidadTurno;
        for (Empleado operador : operadoresActuales) {
            disponibilidadTurnoJsonString = DAODisponibilidad.consultaDisponibilidadPorNifYFecha(operador.getNif(), this.turno.getFechaTurno().toString());
            //Cuidado porque puede no haber ninguna disponibilidad, aunque no sería lógico
            if (!disponibilidadTurnoJsonString.equals("")) {
                disponibilidadTurno = new DisponibilidadEmpresa(disponibilidadTurnoJsonString);
                //No está de baja a fecha de hoy
                if (operador.getDisponibilidad().getTipo() != TipoDeDisponibilidad.DeBaja) {
                    //No está de vacaciones a fecha del turno
                    if (disponibilidadTurno.getTipo() != TipoDeDisponibilidad.DeVacaciones) {
                        //No se encuentra en el turno especificado. No se comprueba que tenga turno organizado para ese día :(
                        if (!turno.getOperadores().contains(operador)) {
                            //Pasaron 12h desde su último turno
                            if (comprobarTurnoAnterior(operador.getNif())) {
                                this.operadoresParaCambiar.add(operador);
                            }
                        }
                    }
                }
            }
        }
        
        if (this.operadoresParaCambiar.isEmpty()){
            throw new NoHayOperadoresParaCambiarException("No hay operadores activos para modificar el actual");
        }

        //Lo pasamos a String para mostrar a la vista
        for (Empleado operador : this.operadoresParaCambiar) {
            operadoresParaCambiarString.add(operador.getNombre() + " | " + operador.getApellidos() + " | " + operador.getNif() + " | " + operador.getTelefono());
        }
        return operadoresParaCambiarString;
    }
    
    public String confirmarCambioOperador(int seleccion) throws DBException {
        if(DAOOperadoresEnTurno.deleteOperadorEnTurnoByNifYId(turno.getOperadores().get(this.seleccionOperadorACambiar).getNif(), turno.getId())){
            if(DAOOperadoresEnTurno.insertOperadorEnTurnoByNifYId(this.operadoresParaCambiar.get(seleccion).getNif(), turno.getId())){
                return "Cambio efectuado con éxito";
            }
        }
        return "No se pudo realizar el cambio";
    }

    private boolean comprobarTurnoAnterior(String nif) throws DBException {
        String turnoAnteriorJsonString = DAOTurnoOperador.consultaTurnoAnteriorPorFechaYNif(nif, this.turno.getFechaTurno().toString());
        
        //Puede no tener turno anterior
        if (turnoAnteriorJsonString.equals("")){
            return true;
        }
        TurnoOperador turnoAnterior = new TurnoOperador(turnoAnteriorJsonString);
        LocalDateTime ldtTurnoAnterior = crearLDT(turnoAnterior.getFechaTurno().toString(), turnoAnterior.getTipo().toString());
        LocalDateTime ldtTurno = crearLDT(this.turno.getFechaTurno().toString(), this.turno.getTipo().toString());
        return Math.abs(ChronoUnit.HOURS.between(ldtTurnoAnterior, ldtTurno)) >= 20;
    }

    private LocalDateTime crearLDT(String fecha, String tipo) {
        switch (tipo) {
            case "DeMañana7":
                return LocalDateTime.parse(fecha + "T07:00:00");
            case "DeTarde15":
                return LocalDateTime.parse(fecha + "T15:00:00");
            case "DeNoche23":
                return LocalDateTime.parse(fecha + "T23:00:00");
        }
        return null;
    }
}
