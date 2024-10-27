/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.modelos.persona;

import javax.json.JsonObject;
import negocio.modelos.general.Utils;

/**
 *
 * @author fagon
 */
public class Asegurado extends Persona {
    private Sexo sexo;
    private String numeroDeCuenta;

    public Asegurado(String jsonStringAsegurado) {
        super(Utils.getJsonObject(jsonStringAsegurado).getString("Persona"));
        JsonObject jsonAsegurado = Utils.getJsonObject(jsonStringAsegurado);
        
        if (jsonAsegurado.getString("Sexo").charAt(0) == 'H'){
            this.sexo = Sexo.hombre;
        } else this.sexo = Sexo.mujer;
        
        this.numeroDeCuenta = jsonAsegurado.getString("NumeroDeCuenta");
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public String getNumeroDeCuenta() {
        return numeroDeCuenta;
    }

    public void setNumeroDeCuenta(String numeroDeCuenta) {
        this.numeroDeCuenta = numeroDeCuenta;
    }
    
}
