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
public class EstrategiaSinFiltro implements FiltroEstrategia {

    ArrayList<Activacion> activaciones;

    public EstrategiaSinFiltro(ArrayList<Activacion> activaciones) {
        this.activaciones = activaciones;
    }

    @Override
    public ArrayList<Activacion> getListaFiltrada() {
        return this.activaciones;
    }
}
