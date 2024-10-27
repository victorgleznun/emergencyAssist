/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.modelos.operativo;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.json.JsonObject;
import negocio.modelos.general.Direccion;
import negocio.modelos.llamada.LlamadaNoCritica;
import negocio.modelos.general.Utils;

/**
 *
 * @author izajime
 */
public class Activacion {
    private Direccion direccionDondeAcudir;
    private LocalDate fechaActivacion;
    private LocalTime horaActivacion;
    private LocalDate fechaCargo;
    private LocalTime horaCargo;
    private LocalDate fechaCierre;
    private LocalTime horaCierre;
    private LlamadaNoCritica llamada;
    private Traslado traslado;
    
    public Activacion(String jsonStringActivacion){
        JsonObject jsonActivacion = Utils.getJsonObject(jsonStringActivacion);
        this.direccionDondeAcudir = new Direccion(jsonActivacion.getString("DireccionDondeAcudir"));
        this.fechaActivacion = LocalDate.parse(jsonActivacion.getString("FechaActivacion"));
        this.horaActivacion = LocalTime.parse(jsonActivacion.getString("HoraActivacion"));
        if (jsonActivacion.getString("FechaCargo").equals("") || jsonActivacion.getString("HoraCargo").equals("")){
            fechaCargo = null;
            horaCargo = null;
        } else {
            this.fechaCargo = LocalDate.parse(jsonActivacion.getString("FechaCargo"));
            this.horaCargo = LocalTime.parse(jsonActivacion.getString("HoraCargo"));
        }
        if (jsonActivacion.getString("FechaCierre").equals("") || jsonActivacion.getString("HoraCierre").equals("")){
            fechaCierre = null;
            horaCierre = null;
        } else {
            this.fechaCierre = LocalDate.parse(jsonActivacion.getString("FechaCierre"));
            this.horaCierre = LocalTime.parse(jsonActivacion.getString("HoraCierre"));
        }
        this.llamada = new LlamadaNoCritica(jsonActivacion.getString("Llamada"));
        
        if (jsonActivacion.getString("Traslado").equals("")){
            traslado = null;
        } else {
            traslado = new Traslado(jsonActivacion.getString("Traslado"));
        }
    }

    public Direccion getDireccionDondeAcudir() {
        return direccionDondeAcudir;
    }

    public void setDireccionDondeAcudir(Direccion direccionDondeAcudir) {
        this.direccionDondeAcudir = direccionDondeAcudir;
    }

    public LocalDate getFechaActivacion() {
        return fechaActivacion;
    }

    public void setFechaActivacion(LocalDate fechaActivacion) {
        this.fechaActivacion = fechaActivacion;
    }

    public LocalTime getHoraActivacion() {
        return horaActivacion;
    }

    public void setHoraActivacion(LocalTime horaActivacion) {
        this.horaActivacion = horaActivacion;
    }

    public LocalDate getFechaCargo() {
        return fechaCargo;
    }

    public void setFechaCargo(LocalDate fechaCargo) {
        this.fechaCargo = fechaCargo;
    }

    public LocalTime getHoraCargo() {
        return horaCargo;
    }

    public void setHoraCargo(LocalTime horaCargo) {
        this.horaCargo = horaCargo;
    }

    public LocalDate getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(LocalDate fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public LocalTime getHoraCierre() {
        return horaCierre;
    }

    public void setHoraCierre(LocalTime horaCierre) {
        this.horaCierre = horaCierre;
    }

    public LlamadaNoCritica getLlamada() {
        return llamada;
    }

    public void setLlamada(LlamadaNoCritica llamada) {
        this.llamada = llamada;
    }

    public Traslado getTraslado() {
        return traslado;
    }

    public void setTraslado(Traslado traslado) {
        this.traslado = traslado;
    }
    
    @Override
    public String toString(){
        String seHaceCargoString;
        String trasladoString;
        
        if (this.fechaCargo == null || this.horaCargo == null){
            seHaceCargoString = "No se hizo cargo del paciente";
        }
        else seHaceCargoString = "Se hizo cargo del paciente con fecha y hora: " + this.fechaCargo.toString() + ", " + this.horaCargo.toString();
        
        if ( this.traslado == null){
             trasladoString = "No hubo traslado";
        } else trasladoString = "Sí hubo traslado";
        
        return "Fecha y hora de la emergencia: " + this.fechaActivacion.toString() + " | " + this.horaActivacion.toString() + 
               ", Descripción: " + this.llamada.getDescripcion() + ", Se acudió a: " + this.direccionDondeAcudir.getNombreDeLaVia() +
               ", " + seHaceCargoString + ", " + trasladoString + ".";
    }
}
