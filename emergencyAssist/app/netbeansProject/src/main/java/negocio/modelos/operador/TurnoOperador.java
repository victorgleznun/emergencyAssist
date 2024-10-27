/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.modelos.operador;

import java.io.StringReader;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import negocio.modelos.persona.Empleado;
import negocio.modelos.general.Utils;

/**
 *
 * @author izajime
 */
public class TurnoOperador {
    private int id;
    private LocalDate fechaTurno;
    private LocalDate fechaCreacion;
    private TipoTurnoOperador tipo;
    private ArrayList<Empleado> operadores;
    
    public TurnoOperador(String jsonStringTurnoOperador){
        JsonObject jsonTurnoOperador = Utils.getJsonObject(jsonStringTurnoOperador);
        this.id = jsonTurnoOperador.getInt("Id");
        this.fechaTurno = LocalDate.parse(jsonTurnoOperador.getString("FechaTurno"));
        this.fechaCreacion = LocalDate.parse(jsonTurnoOperador.getString("FechaCreacion"));
        this.tipo = TipoTurnoOperador.valueOf(jsonTurnoOperador.getString("TipoTurno"));
        
        this.operadores = new ArrayList<>();
        JsonArray jsonArrayOperadores = Json.createReader(new StringReader(jsonTurnoOperador.getString("Operadores"))).readArray();
        
        for (JsonValue operadorEnLista : jsonArrayOperadores) {
            JsonObject jsonOperador = operadorEnLista.asJsonObject();
            this.operadores.add(new Empleado(jsonOperador.toString()));
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFechaTurno() {
        return fechaTurno;
    }

    public void setFechaTurno(LocalDate fechaTurno) {
        this.fechaTurno = fechaTurno;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public TipoTurnoOperador getTipo() {
        return tipo;
    }

    public void setTipo(TipoTurnoOperador tipo) {
        this.tipo = tipo;
    }

    public ArrayList<Empleado> getOperadores() {
        return operadores;
    }

    public void setOperadores(ArrayList<Empleado> operadores) {
        this.operadores = operadores;
    }

}