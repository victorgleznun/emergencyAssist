/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.modelos.operativo;

import java.time.LocalDate;
import javax.json.JsonObject;
import negocio.modelos.general.Utils;

/**
 *
 * @author izajime
 */
public class Vehiculo {
    private String matricula;
    private coordenadaGPS ubicacion;
    private LocalDate fechaAlta;
    private EstadoVehiculo estado;
    private String nombreModelo;
    private String nombreMarca;
    
    public Vehiculo (String jsonStringVehiculo){
        JsonObject jsonVehiculo = Utils.getJsonObject(jsonStringVehiculo);
        this.matricula = jsonVehiculo.getString("Matricula");
        ubicacion = new coordenadaGPS(jsonVehiculo.getJsonNumber("UbicacionLatitud").doubleValue(), jsonVehiculo.getJsonNumber("UbicacionLongitud").doubleValue());
        this.fechaAlta = LocalDate.parse(jsonVehiculo.getString("FechaAlta"));
        this.estado = EstadoVehiculo.valueOf(jsonVehiculo.getString("Estado"));
        this.matricula = jsonVehiculo.getString("NombreModelo");
        this.matricula = jsonVehiculo.getString("NombreMarca");
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public coordenadaGPS getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(coordenadaGPS ubicacion) {
        this.ubicacion = ubicacion;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public EstadoVehiculo getEstado() {
        return estado;
    }

    public void setEstado(EstadoVehiculo estado) {
        this.estado = estado;
    }

    public String getNombreModelo() {
        return nombreModelo;
    }

    public void setNombreModelo(String nombreModelo) {
        this.nombreModelo = nombreModelo;
    }

    public String getNombreMarca() {
        return nombreMarca;
    }

    public void setNombreMarca(String nombreMarca) {
        this.nombreMarca = nombreMarca;
    }
    
}
