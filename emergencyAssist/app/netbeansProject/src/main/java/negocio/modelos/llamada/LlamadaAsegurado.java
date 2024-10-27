/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.modelos.llamada;

import negocio.modelos.persona.Asegurado;
import javax.json.JsonObject;
import negocio.modelos.general.Utils;

/**
 *
 * @author izajime
 */
public class LlamadaAsegurado extends Llamada{
    
    private String descripcion;
    private Asegurado paciente;
    
    public LlamadaAsegurado (String jsonStringLlamadaAsegurado){
        super(Utils.getJsonObject(jsonStringLlamadaAsegurado).getString("Llamada"));
        JsonObject jsonLlamadaAsegurado = Utils.getJsonObject(jsonStringLlamadaAsegurado);
        this.descripcion = jsonLlamadaAsegurado.getString("Descripcion");
        this.paciente = new Asegurado(jsonLlamadaAsegurado.getString("Paciente"));
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Asegurado getPaciente() {
        return paciente;
    }

    public void setPaciente(Asegurado paciente) {
        this.paciente = paciente;
    }
    
}
