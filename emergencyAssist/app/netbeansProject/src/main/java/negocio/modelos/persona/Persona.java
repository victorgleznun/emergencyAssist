
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.modelos.persona;

import java.time.LocalDate;
import javax.json.JsonObject;
import negocio.modelos.general.Direccion;
import negocio.modelos.general.Utils;

/**
 *
 * @author fagon
 */

public class Persona {
    private String nif;
    private String nombre;
    private String apellidos;
    private LocalDate fechaDeNacimiento;
    private String telefono;
    private Direccion direccionPostal;

    public Persona(String jsonStringPersona) {
        JsonObject jsonPersona = Utils.getJsonObject(jsonStringPersona);
        this.nif = jsonPersona.getString("Nif");
        this.nombre = jsonPersona.getString("Nombre");
        this.apellidos = jsonPersona.getString("Apellidos");
        this.fechaDeNacimiento = LocalDate.parse(jsonPersona.getString("FechaDeNacimiento"));
        this.telefono = jsonPersona.getString("Telefono");
        this.direccionPostal = new Direccion(jsonPersona.getString("DireccionPostal"));
        
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public LocalDate getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(LocalDate fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Direccion getDireccionPostal() {
        return direccionPostal;
    }

    public void setDireccionPostal(Direccion direccionPostal) {
        this.direccionPostal = direccionPostal;
    }
}
