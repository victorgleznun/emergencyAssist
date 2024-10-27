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
public class TurnoOperativo {
    private LocalDate fechaTurno;
    private LocalDate fechaCreacion;
    private TipoTurnoOperativo tipoTurno;
    
    public TurnoOperativo(String jsonStringTurno){
        JsonObject jsonTurno = Utils.getJsonObject(jsonStringTurno);
        this.fechaTurno = LocalDate.parse(jsonTurno.getString("FechaTurno"));
        this.fechaCreacion = LocalDate.parse(jsonTurno.getString("FechaCreacion"));
        this.tipoTurno = TipoTurnoOperativo.valueOf(jsonTurno.getString("TipoTurno"));
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

    public TipoTurnoOperativo getTipoTurno() {
        return tipoTurno;
    }

    public void setTipoTurno(TipoTurnoOperativo tipoTurno) {
        this.tipoTurno = tipoTurno;
    }
   
}


