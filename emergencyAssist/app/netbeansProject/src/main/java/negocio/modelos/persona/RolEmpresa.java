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
public class RolEmpresa {
    private TipoDeRol tipo;
    private LocalDate fechaInicioEnPuesto;
    private LocalDate fechaPermiso;
    private String numeroColegiado;
    
    public RolEmpresa(String jsonStringRolEmpresa){
        JsonObject jsonRolEmpresa = Utils.getJsonObject(jsonStringRolEmpresa);
        tipo = TipoDeRol.valueOf(jsonRolEmpresa.getString("NombreTipo"));
        fechaInicioEnPuesto = LocalDate.parse(jsonRolEmpresa.getString("FechaInicioEnPuesto"));
        if (jsonRolEmpresa.getString("FechaPermisoConduccion").equals("")){
            fechaPermiso = null;
        } else fechaPermiso = LocalDate.parse(jsonRolEmpresa.getString("FechaPermisoConduccion"));
        
        numeroColegiado = jsonRolEmpresa.getString("NumeroColegiadoMedico");
    }

    public TipoDeRol getTipo() {
        return tipo;
    }

    public void setTipo(TipoDeRol tipo) {
        this.tipo = tipo;
    }

    public LocalDate getFechaInicioEnPuesto() {
        return fechaInicioEnPuesto;
    }

    public void setFechaInicioEnPuesto(LocalDate fechaInicioEnPuesto) {
        this.fechaInicioEnPuesto = fechaInicioEnPuesto;
    }

    public LocalDate getFechaPermiso() {
        return fechaPermiso;
    }

    public void setFechaPermiso(LocalDate fechaPermiso) {
        this.fechaPermiso = fechaPermiso;
    }

    public String getNumeroColegiado() {
        return numeroColegiado;
    }

    public void setNumeroColegiado(String numeroColegiado) {
        this.numeroColegiado = numeroColegiado;
    }
    
}
