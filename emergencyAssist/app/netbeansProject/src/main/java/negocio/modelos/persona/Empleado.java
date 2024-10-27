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
 * @author fagon
 */
public class Empleado extends Persona {
    private RolEmpresa rol;
    private VinculacionEmpresa vinculacion;
    private DisponibilidadEmpresa disponibilidad;
    private LocalDate fechaIncioEnEmpresa;

    public Empleado(String jsonStringEmpleado) {
        super(Utils.getJsonObject(jsonStringEmpleado).getString("Persona"));
        JsonObject jsonEmpleado = Utils.getJsonObject(jsonStringEmpleado);
        rol = new RolEmpresa(jsonEmpleado.getString("RolEmpresa"));
        vinculacion = new VinculacionEmpresa(jsonEmpleado.getString("VinculacionEmpresa"));
        disponibilidad = new DisponibilidadEmpresa(jsonEmpleado.getString("DisponibilidadEmpresa"));
        fechaIncioEnEmpresa = LocalDate.parse(jsonEmpleado.getString("FechaInicioEnEmpresa"));
    }
    
    public boolean estaActivo(){
        return (disponibilidad.getTipo() == TipoDeDisponibilidad.Disponible && vinculacion.getTipo() == TipoDeVinculacion.ConNormalidad);
    }

    public RolEmpresa getRol() {
        return rol;
    }

    public void setRol(RolEmpresa rol) {
        this.rol = rol;
    }

    public VinculacionEmpresa getVinculacion() {
        return vinculacion;
    }

    public void setVinculacion(VinculacionEmpresa vinculacion) {
        this.vinculacion = vinculacion;
    }

    public DisponibilidadEmpresa getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(DisponibilidadEmpresa disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public LocalDate getFechaIncioEnEmpresa() {
        return fechaIncioEnEmpresa;
    }

    public void setFechaIncioEnEmpresa(LocalDate fechaIncioEnEmpresa) {
        this.fechaIncioEnEmpresa = fechaIncioEnEmpresa;
    }
    
}
