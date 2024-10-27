/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz.pares_vista_controlador.pares_vista_controlador_Identificarse;

import interfaz.GestorVistas;

/**
 *
 * @author Mario Llorente
 */
public class ControladorVistaOperador {
     private final VistaOperador miVista;
    private final GestorVistas gestorVistas;
    
    public ControladorVistaOperador(VistaOperador miVista){
        this.miVista = miVista;
        gestorVistas = GestorVistas.getGestorVistas();
    }
    
    public void procesaEventoAtenderLlamada(){
            gestorVistas.mostrarVistaAtenderLlamada();
    }
    
}