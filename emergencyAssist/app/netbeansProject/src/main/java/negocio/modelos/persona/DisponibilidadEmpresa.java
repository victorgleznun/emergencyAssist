/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.modelos.persona;

import java.time.LocalDate;
import javax.json.JsonObject;
import negocio.modelos.general.Utils;

/**
 *
 * @author izajime
 */
public class DisponibilidadEmpresa {
    private TipoDeDisponibilidad tipo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    
    public DisponibilidadEmpresa(String jsonStringDisponibilidadEmpresa){
        JsonObject jsonDisponibilidadEmpresa = Utils.getJsonObject(jsonStringDisponibilidadEmpresa);
        tipo = TipoDeDisponibilidad.valueOf(jsonDisponibilidadEmpresa.getString("NombreTipo"));
        fechaInicio = LocalDate.parse(jsonDisponibilidadEmpresa.getString("FechaInicio"));
        if (jsonDisponibilidadEmpresa.getString("FechaFin").equals("")){
            fechaFin = null;
        }else fechaFin = LocalDate.parse(jsonDisponibilidadEmpresa.getString("FechaFin"));
        
    }

    public TipoDeDisponibilidad getTipo() {
        return tipo;
    }

    public void setTipo(TipoDeDisponibilidad tipo) {
        this.tipo = tipo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
    
}
