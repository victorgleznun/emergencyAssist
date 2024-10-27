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
public class EstrategiaConTraslado implements FiltroEstrategia {

    ArrayList<Activacion> activaciones;

    public EstrategiaConTraslado(ArrayList<Activacion> activaciones) {
        this.activaciones = activaciones;
    }

    @Override
    public ArrayList<Activacion> getListaFiltrada() {
        ArrayList<Activacion> listaFiltrada = new ArrayList<>();

        for (Activacion activacion : activaciones) {
            if (activacion.getTraslado() != null) {
                listaFiltrada.add(activacion);
            }
        }
        return listaFiltrada;
    }
}
