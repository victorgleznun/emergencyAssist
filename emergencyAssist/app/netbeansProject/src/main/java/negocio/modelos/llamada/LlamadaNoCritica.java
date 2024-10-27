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
public class LlamadaNoCritica extends LlamadaAsegurado {
    private boolean esLeve;
    
    public LlamadaNoCritica(String jsonStringLlamadaNoCritica){
           super(Utils.getJsonObject(jsonStringLlamadaNoCritica).getString("LlamadaAsegurado"));
           JsonObject jsonLlamadaNoCritica = Utils.getJsonObject(jsonStringLlamadaNoCritica);
           this.esLeve = jsonLlamadaNoCritica.getBoolean("EsLeve");
    }

    public boolean isEsLeve() {
        return esLeve;
    }

    public void setEsLeve(boolean esLeve) {
        this.esLeve = esLeve;
    }
    
}
