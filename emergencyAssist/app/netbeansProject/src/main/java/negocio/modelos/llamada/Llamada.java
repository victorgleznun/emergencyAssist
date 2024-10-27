/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.modelos.llamada;

import negocio.modelos.persona.Empleado;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.json.JsonObject;
import negocio.modelos.general.Utils;

/**
 *
 * @author izajime
 */
public class Llamada {
    private String telefono;
    private LocalDate fechaInicio;
    private LocalTime horaInicio;
    private LocalDate fechaFin;
    private LocalTime horaFin;
    private String comunicante;
    private Empleado operador;
    
    public Llamada (String jsonStringLlamada){
        JsonObject jsonLlamada = Utils.getJsonObject(jsonStringLlamada);
        this.telefono = jsonLlamada.getString("Telefono");
        this.fechaInicio = LocalDate.parse(jsonLlamada.getString("FechaInicio"));
        this.horaInicio = LocalTime.parse(jsonLlamada.getString("HoraInicio"));
        this.fechaFin = LocalDate.parse(jsonLlamada.getString("FechaFin"));
        this.horaFin = LocalTime.parse(jsonLlamada.getString("HoraFin"));
        this.comunicante = jsonLlamada.getString("Comunicante");
        this.operador = new Empleado(jsonLlamada.getString("Operador"));
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public String getComunicante() {
        return comunicante;
    }

    public void setComunicante(String comunicante) {
        this.comunicante = comunicante;
    }

    public Empleado getOperador() {
        return operador;
    }

    public void setOperador(Empleado operador) {
        this.operador = operador;
    }
    
}
