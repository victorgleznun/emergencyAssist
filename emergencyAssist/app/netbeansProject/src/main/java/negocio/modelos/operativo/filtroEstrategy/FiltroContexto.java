/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.modelos.operativo.filtroEstrategy;

import java.util.ArrayList;
import negocio.modelos.operativo.Activacion;

/**
 *
 * @author izajime
 */
public class FiltroContexto {

    private FiltroEstrategia filtro;

    public FiltroContexto() {
    }

    public void setEstrategiaFiltro(FiltroEstrategia estrategia) {
        this.filtro = estrategia;
    }

    public ArrayList<Activacion> getEmergenciasPorFiltro() {
        return filtro.getListaFiltrada();
    }
}
