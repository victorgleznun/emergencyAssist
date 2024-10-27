/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.modelos.general;

import javax.json.JsonObject;

/**
 *
 * @author fagon
 */
public class Direccion {
    private String nombreDeLaVia;
    private int numero;
    private String otros;
    private int codigoPostal;
    private String localidad;
    private String provincia;

    public Direccion(String jsonStringDireccion) {
        JsonObject jsonDireccion = Utils.getJsonObject(jsonStringDireccion);
        this.nombreDeLaVia = jsonDireccion.getString("NombreDeLaVia");
        this.numero = jsonDireccion.getInt("Numero");
        this.otros = jsonDireccion.getString("Otros");
        this.codigoPostal = jsonDireccion.getInt("CodigoPostal");
        this.localidad = jsonDireccion.getString("Localidad");
        this.provincia = jsonDireccion.getString("Provincia");
    }

    public String getNombreDeLaVia() {
        return nombreDeLaVia;
    }

    public void setNombreDeLaVia(String nombreDeLaVia) {
        this.nombreDeLaVia = nombreDeLaVia;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getOtros() {
        return otros;
    }

    public void setOtros(String otros) {
        this.otros = otros;
    }

    public int getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(int codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
    
}
