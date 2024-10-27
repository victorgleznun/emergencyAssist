/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz.pares_vista_controlador.pares_vista_controlador_consultarEmergencias;

import interfaz.GestorVistas;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import negocio.controladoresCasoUso.ControladorCUConsultarEmergencias;
import servicioscomunes.excepciones.DBException;
import servicioscomunes.excepciones.NoExisteTurnoException;

/**
 *
 * @author victo
 */
public class ControladorVistaConsultarEmergencias {
    private final VistaConsultarEmergencias miVista;
    private final ControladorCUConsultarEmergencias miControlador;
    private final GestorVistas gestorVistas;
    
    public ControladorVistaConsultarEmergencias(VistaConsultarEmergencias miVista){
        this.miVista = miVista;
        miControlador = new ControladorCUConsultarEmergencias();
        gestorVistas = GestorVistas.getGestorVistas();
    }
    
    public void procesarEventoConsultarEmergencia(){
        String dia = miVista.getDia();
        String mes = miVista.getMes();
        String ano = miVista.getAno();
        
        miVista.borrarElementosLista();
        miVista.setTextError("");
        
        String conc =  ano + "-" + mes + "-" + dia;
        String filtro = miVista.getFiltro();
        
        try{
           ArrayList<String> emergencias;
           LocalDate fecha = LocalDate.parse(conc);
           emergencias = miControlador.consultarEmergencia(conc, filtro);
           
           for (String emergencia : emergencias){
               miVista.addEmergencia(emergencia);
           }
           
           miVista.mostrarEmergencias();
        } catch(DateTimeParseException e){
            miVista.setTextError("Fecha no válida");
            miVista.borrarElementosLista();
            miVista.borrarDetalles();
        } catch(NoExisteTurnoException| DBException e){
            miVista.setTextError(e.getMessage());
            miVista.borrarElementosLista();
            miVista.borrarDetalles();
        }
        
    }
    
    public void procesaEventoDetalleEmergencia (){
        int seleccion = miVista.recogerSeleccion();
        if (seleccion != -1){
            miVista.mostrarDetalles(miControlador.mostrarDetalles(seleccion)); 
        }
        
    }
    
    public void procesaEventoCancelar(){
       gestorVistas.mostrarVistaOperativo();
    }
}
