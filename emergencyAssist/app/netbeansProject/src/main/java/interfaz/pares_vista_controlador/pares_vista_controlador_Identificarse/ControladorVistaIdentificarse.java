/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz.pares_vista_controlador.pares_vista_controlador_Identificarse;

import interfaz.GestorVistas;
import negocio.controladoresCasoUso.ControladorCUIdentificarse;
import servicioscomunes.excepciones.DBException;
import servicioscomunes.excepciones.EmpleadoNoActivoException;
import servicioscomunes.excepciones.NoExisteEmpleadoException;

/**
 *
 * @author fagon
 */
public class ControladorVistaIdentificarse {
    private final VistaIdentificarse miVista;
    private final ControladorCUIdentificarse miControlador;
    private final GestorVistas gestorVistas;
    
    public ControladorVistaIdentificarse(VistaIdentificarse miVista){
        this.miVista = miVista;
        miControlador = new ControladorCUIdentificarse();
        gestorVistas = GestorVistas.getGestorVistas();
    }
    
    public void procesarEventoIdentificarse(){
        String d = miVista.getDni();
        String p = miVista.getPassword();
        boolean ok;
        miVista.setTextError("");
        ok = compruebaCharArrayNoVacio(d);
        if (!ok){
            miVista.setTextError("Nif campo vacio");
            return;
        }
        ok = compruebaCharArrayNoVacio(p);
        if (!ok){
            miVista.setTextError("Password campo vacio");
            return;
        }
        
        try{
        String resultado = miControlador.identificarEmpleado(d, p);
        gestorVistas.mostrarVistaRol(resultado);
        } catch (NoExisteEmpleadoException | EmpleadoNoActivoException| DBException e) {
            miVista.setTextError(e.getMessage());
        }
    }
    
    private boolean compruebaCharArrayNoVacio(String c){
        return !c.isEmpty();
    }
    
}