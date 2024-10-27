/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.modelos.operativo;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.json.JsonObject;
import negocio.modelos.general.Direccion;
import negocio.modelos.general.Utils;

/**
 *
 * @author izajime
 */
public class Traslado {
    private LocalDate fechaInicio;
    private LocalTime horaInicio;
    private String nombreHospital;
    private Direccion direccionHospital;
    
    public Traslado(String jsonStringTraslado){
        JsonObject jsonTraslado = Utils.getJsonObject(jsonStringTraslado);
        this.fechaInicio = LocalDate.parse(jsonTraslado.getString("FechaInicio"));
        this.horaInicio = LocalTime.parse(jsonTraslado.getString("HoraInicio"));
        this.nombreHospital = jsonTraslado.getString("NombreHospital");
        this.direccionHospital = new Direccion(jsonTraslado.getString("Direccion"));
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

    public String getNombreHospital() {
        return nombreHospital;
    }

    public void setNombreHospital(String nombreHospital) {
        this.nombreHospital = nombreHospital;
    }

    public Direccion getDireccionHospital() {
        return direccionHospital;
    }

    public void setDireccionHospital(Direccion direccionHospital) {
        this.direccionHospital = direccionHospital;
    }
    
}
