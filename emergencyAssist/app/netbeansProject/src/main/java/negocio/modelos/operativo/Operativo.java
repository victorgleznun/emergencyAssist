/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.modelos.operativo;

import java.time.LocalDate;
import javax.json.JsonObject;
import negocio.modelos.general.Direccion;
import negocio.modelos.persona.Empleado;
import negocio.modelos.general.Utils;

/**
 *
 * @author izajime
 */
public class Operativo {
    private int id;
    private LocalDate fechaCreacion;
    private EstadoOperativo estado;
    private TurnoOperativo turno;
    private Direccion base;
    private Vehiculo vehiculo;
    private Empleado conductor;
    private Empleado medico;
    
    public Operativo(String jsonStringOperativo){
        JsonObject jsonOperativo = Utils.getJsonObject(jsonStringOperativo);
        this.id = jsonOperativo.getInt("Id");
        this.fechaCreacion = LocalDate.parse(jsonOperativo.getString("FechaCreacion"));
        this.turno = new TurnoOperativo(jsonOperativo.getString("Turno"));
        this.base = new Direccion(jsonOperativo.getString("Base"));
        this.vehiculo = new Vehiculo(jsonOperativo.getString("Vehiculo"));
        this.conductor = new Empleado(jsonOperativo.getString("Conductor"));
        this.medico = new Empleado(jsonOperativo.getString("Medico"));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public EstadoOperativo getEstado() {
        return estado;
    }

    public void setEstado(EstadoOperativo estado) {
        this.estado = estado;
    }

    public TurnoOperativo getTurno() {
        return turno;
    }

    public void setTurno(TurnoOperativo turno) {
        this.turno = turno;
    }

    public Direccion getBase() {
        return base;
    }

    public void setBase(Direccion base) {
        this.base = base;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Empleado getConductor() {
        return conductor;
    }

    public void setConductor(Empleado conductor) {
        this.conductor = conductor;
    }

    public Empleado getMedico() {
        return medico;
    }

    public void setMedico(Empleado medico) {
        this.medico = medico;
    }
    
}
