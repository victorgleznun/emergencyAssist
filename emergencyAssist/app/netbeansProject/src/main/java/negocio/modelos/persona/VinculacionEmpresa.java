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
public class VinculacionEmpresa {
    private TipoDeVinculacion tipo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    
    public VinculacionEmpresa (String jsonStringVinculacionEmpresa){
        JsonObject jsonVinculacionEmpresa = Utils.getJsonObject(jsonStringVinculacionEmpresa);
        tipo = TipoDeVinculacion.valueOf(jsonVinculacionEmpresa.getString("NombreTipo"));
        fechaInicio = LocalDate.parse(jsonVinculacionEmpresa.getString("FechaInicio"));
        if (jsonVinculacionEmpresa.getString("FechaFin").equals("")){
            fechaFin = null;
        } else fechaFin = LocalDate.parse(jsonVinculacionEmpresa.getString("FechaFin"));
        
    }

    public TipoDeVinculacion getTipo() {
        return tipo;
    }

    public void setTipo(TipoDeVinculacion tipo) {
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
