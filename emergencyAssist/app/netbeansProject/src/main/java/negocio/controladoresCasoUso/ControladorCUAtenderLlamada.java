/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.controladoresCasoUso;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import negocio.modelos.general.Sesion;
import negocio.modelos.llamada.Consejo;
import negocio.modelos.operador.TurnoOperador;
import negocio.modelos.persona.Asegurado;
import persistencia.DAOs.llamada.DAOConsejo;
import persistencia.DAOs.llamada.DAOLlamadaCritica;
import persistencia.DAOs.llamada.DAOLlamadaInfundada;
import persistencia.DAOs.llamada.DAOLlamadaNoCritica;
import persistencia.DAOs.operador.DAOTurnoOperador;
import persistencia.DAOs.persona.DAOAsegurado;
import persistencia.DAOs.persona.DAOPoliza;
import servicioscomunes.excepciones.DBException;
import servicioscomunes.excepciones.NoExisteAseguradoException;
import servicioscomunes.excepciones.NoExistePolizaException;
import servicioscomunes.excepciones.OperadorNoEstaEnTurnoException;

/**
 *
 * @author izajime
 */
public class ControladorCUAtenderLlamada {

    private Asegurado asegurado;
    private String nifOperador;
    private LocalDate fechaInicioLlamada;
    private LocalTime horaInicioLlamada;
    private String telefono;
    private String comunicante;
    private final ArrayList<Consejo> consejos;

    public ControladorCUAtenderLlamada() {
        this.asegurado = null;
        this.fechaInicioLlamada = null;
        this.horaInicioLlamada = null;
        this.telefono = null;
        this.comunicante = null;
        this.consejos = new ArrayList<>();
    }

    public void consultarTurnoActual(String fecha, String tipo) throws OperadorNoEstaEnTurnoException, DBException {
        String turnoJSON = DAOTurnoOperador.consultaTurnoPorFecha(fecha, tipo);
        Sesion miSesion = Sesion.getInstance();
        this.nifOperador = miSesion.getEmpleado().getNif();

        if (turnoJSON.equals("")) {
            throw new OperadorNoEstaEnTurnoException("Operador no se encuentra en turno");
        }

        TurnoOperador turno = new TurnoOperador(turnoJSON);
        if (turno.getOperadores().contains(miSesion.getEmpleado())) {
            throw new OperadorNoEstaEnTurnoException("Operador no se encuentra en turno");
        }

        this.fechaInicioLlamada = LocalDate.now();
        this.horaInicioLlamada = LocalTime.now();
    }

    public void consultarPoliza(String nPoliza) throws NoExistePolizaException, DBException{
        String polizaJSON = DAOPoliza.consultaPolizaPorNumeroYNif(nPoliza, this.asegurado.getNif());
        if (polizaJSON.equals("")) {
            throw new NoExistePolizaException("Ningún asegurado coincide con los datos introducidos, se debe redirigir la llamada al 112. Llamada infundada creada");
        }
    }

    public void consultarAsegurado(String nombre, String apellidos, String sexo, String fechaNacimiento, String telefono, String comunicante) throws NoExisteAseguradoException, DBException {
        this.telefono = telefono;
        this.comunicante = comunicante;
        
        String aseguradoJSON = DAOAsegurado.consultaAseguradoPorDatos(nombre, apellidos, sexo, fechaNacimiento);
        if (aseguradoJSON.equals("")) {
            throw new NoExisteAseguradoException("Ningún asegurado coincide con los datos introducidos, se debe redirigir la llamada al 112. Llamada infundada creada");
        }
        Asegurado nuevoAsegurado = new Asegurado(aseguradoJSON);
        this.asegurado = nuevoAsegurado;
    }

    public void crearLlamadaInfundada() throws DBException {
        LocalDate fechaFinLlamada = LocalDate.now();
        LocalTime horaFinLlamada = LocalTime.now();
        DAOLlamadaInfundada.insertLlamadaInfundada(this.telefono, this.fechaInicioLlamada, this.horaInicioLlamada, fechaFinLlamada, horaFinLlamada, this.comunicante, this.nifOperador);
    }

    public void crearLlamadaCritica(String nuevaDescripcion) throws DBException {
        LocalDate fechaFinLlamada = LocalDate.now();
        LocalTime horaFinLlamada = LocalTime.now();
        DAOLlamadaCritica.insertLlamadaCritica(telefono, this.fechaInicioLlamada, this.horaInicioLlamada, fechaFinLlamada, horaFinLlamada, this.comunicante, nuevaDescripcion, asegurado.getNif(), this.nifOperador);
    }

    public void crearLlamadaNoCritica(String nuevaDescripcion, boolean leve) throws DBException {
        LocalDate fechaFinLlamada = LocalDate.now();
        LocalTime horaFinLlamada = LocalTime.now();
        int idLlamada = DAOLlamadaNoCritica.insertLlamadaNoCritica(telefono, this.fechaInicioLlamada, this.horaInicioLlamada, fechaFinLlamada, horaFinLlamada, this.comunicante, nuevaDescripcion, asegurado.getNif(), leve, this.nifOperador);

        for (Consejo consejo : consejos) {
            DAOConsejo.insertConsejo(consejo.getDescripcion(), consejo.getResultado(), consejo.isSoluciona(), idLlamada);
        }
    }

    public void guardarConsejo(String descripcion, String resultado, boolean soluciona) {
        Consejo consejo = new Consejo(descripcion, resultado, soluciona);
        consejos.add(consejo);
    }
}
