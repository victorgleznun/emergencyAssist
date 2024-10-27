/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz;

import interfaz.pares_vista_controlador.pares_vista_controlador_Identificarse.VistaGerente;
import interfaz.pares_vista_controlador.pares_vista_controlador_Identificarse.VistaIdentificarse;
import interfaz.pares_vista_controlador.pares_vista_controlador_Identificarse.VistaOperador;
import interfaz.pares_vista_controlador.pares_vista_controlador_Identificarse.VistaOperativo;
import interfaz.pares_vista_controlador.pares_vista_controlador_consultarEmergencias.VistaConsultarEmergencias;
import interfaz.pares_vista_controlador.pares_vista_controlador_modificarOperador.VistaModificarOperador;
import interfaz.pares_vista_controlador.pares_vista_controlador_atenderLlamada.VistaAtenderLlamada;
import javax.swing.JFrame;

/**
 *
 * @author fagon
 */
public class GestorVistas {

    private static GestorVistas instance;
    private JFrame vistaActual;

    private GestorVistas() {
    }

    public static GestorVistas getGestorVistas() {
        if (instance == null) {
            instance = new GestorVistas();
        }
        return instance;
    }

    public void mostrarVistaIdentificarse() {
        if (vistaActual != null) {
            vistaActual.setVisible(false);
            vistaActual.dispose();
        }
        vistaActual = new VistaIdentificarse();
        vistaActual.setVisible(true);
    }
    
    public void mostrarVistaRol(String rol){
        if (vistaActual != null) {
            vistaActual.setVisible(false);
            vistaActual.dispose();
        }
        switch (rol){
            case "Gerente": 
                vistaActual = new VistaGerente();
                break;
            case "Médico":
                vistaActual = new VistaOperativo();
                break;
            case "Operador":
                vistaActual = new VistaOperador();
                break;
            case "Conductor":
                vistaActual = new VistaOperativo();
                break;
        }
        vistaActual.setVisible(true);
    }
    
    public void mostrarVistaOperativo() {
        if (vistaActual != null) {
            vistaActual.setVisible(false);
            vistaActual.dispose();
        }
        vistaActual = new VistaOperativo();
        vistaActual.setVisible(true);
    }
    
    public void mostrarVistaGerente() {
        if (vistaActual != null) {
            vistaActual.setVisible(false);
            vistaActual.dispose();
        }
        vistaActual = new VistaGerente();
        vistaActual.setVisible(true);
    }
    
    public void mostrarVistaConsultarEmergencias() {
        if (vistaActual != null) {
            vistaActual.setVisible(false);
            vistaActual.dispose();
        }
        vistaActual = new VistaConsultarEmergencias();
        vistaActual.setVisible(true);
    }
        
    public void mostrarVistaModificarOperador() {
        if (vistaActual != null) {
            vistaActual.setVisible(false);
            vistaActual.dispose();
        }
        vistaActual = new VistaModificarOperador();
        vistaActual.setVisible(true);
    }
    
    public void mostrarVistaAtenderLlamada() {
        if (vistaActual != null) {
            vistaActual.setVisible(false);
            vistaActual.dispose();
        }
        vistaActual = new VistaAtenderLlamada();
        vistaActual.setVisible(true);
    }
    
    

}
