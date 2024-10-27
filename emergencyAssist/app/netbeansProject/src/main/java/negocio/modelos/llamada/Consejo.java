/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.modelos.llamada;

import javax.json.JsonObject;
import negocio.modelos.general.Utils;

/**
 *
 * @author izajime
 */
public class Consejo {

    private String descripcion;
    private String resultado;
    private boolean soluciona;

    public Consejo(String jsonStringConsejo) {
        JsonObject jsonConsejo = Utils.getJsonObject(jsonStringConsejo);
        this.descripcion = jsonConsejo.getString("Descripcion");
        this.resultado = jsonConsejo.getString(jsonConsejo.getString("Resultado"));
        this.soluciona = jsonConsejo.getBoolean(("Soluciona"));
    }
    
    public Consejo(String descripcion, String resultado, boolean soluciona) {
        this.descripcion = descripcion;
        this.resultado = resultado;
        this.soluciona = soluciona;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public boolean isSoluciona() {
        return soluciona;
    }

    public void setSoluciona(boolean soluciona) {
        this.soluciona = soluciona;
    }
    
}
