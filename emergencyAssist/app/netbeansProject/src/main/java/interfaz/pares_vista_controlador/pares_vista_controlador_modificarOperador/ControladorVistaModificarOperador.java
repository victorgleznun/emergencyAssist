/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz.pares_vista_controlador.pares_vista_controlador_modificarOperador;

import interfaz.GestorVistas;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import negocio.controladoresCasoUso.ControladorCUModificarOperadorEnTurno;
import servicioscomunes.excepciones.DBException;
import servicioscomunes.excepciones.NoHayOperadoresParaCambiarException;
import servicioscomunes.excepciones.NoHayTurnoParaFechaException;

/**
 *
 * @author victo
 */
public class ControladorVistaModificarOperador {

    private final VistaModificarOperador miVista;
    private final ControladorCUModificarOperadorEnTurno miControlador;
    private final GestorVistas gestorVistas;

    public ControladorVistaModificarOperador(VistaModificarOperador miVista) {
        this.miVista = miVista;
        miControlador = new ControladorCUModificarOperadorEnTurno();
        gestorVistas = GestorVistas.getGestorVistas();
    }

    public void procesarEventoConsultarOperador() {
        String dia = miVista.getDia();
        String mes = miVista.getMes();
        String ano = miVista.getAno();

        miVista.borrarElementosListaEnTurno();
        miVista.borrarElementosListaActivos();

        String conc = ano + "-" + mes + "-" + dia;
        String tipo = miVista.getTipo();
        miVista.setTextError("");
        miVista.setTextConfirmacion("");
        
        ArrayList<String> operadoresEnTurno;
        LocalDate fecha  = LocalDate.parse(conc);
        long diferenciaDias = ChronoUnit.DAYS.between(LocalDate.now(), fecha);
        if (diferenciaDias < 15) {
            miVista.setTextError("Solo se pueden modificar turnos con diferencia de 15 días");
            return;
        }

        try {
            operadoresEnTurno = miControlador.consultarOperadoresEnTurno(conc, tipo);

            for (String operador : operadoresEnTurno) {
                miVista.addOperadorEnTurno(operador);
            }

            miVista.mostrarOperadoresEnTurno();
        } catch (NoHayTurnoParaFechaException| DBException e) {
            miVista.setTextError(e.getMessage());
        } catch (DateTimeParseException e){
            miVista.setTextError("Fecha no válida");
        }

    }

    public void procesarEventoModificarOperador() {
        int seleccion = miVista.recogerSeleccionOperadorEnTurno();
        ArrayList<String> operadoresParaCambiar;
        miVista.borrarElementosListaActivos();

        if (seleccion != -1) {
            try {
                operadoresParaCambiar = miControlador.colsultarOperadoresParaCambiar(seleccion);

                for (String operador : operadoresParaCambiar) {
                    miVista.addOperadorActivo(operador);
                }

                miVista.mostrarOperadoresActivos();
            } catch (NoHayOperadoresParaCambiarException| DBException e) {
                miVista.setTextError(e.getMessage());
            }
        }
    }

    public void procesarEventoConfirmar() {
        int seleccion = miVista.recogerSeleccionOperadorActivo();

        if (seleccion != -1) {
            try{
                String respuesta = miControlador.confirmarCambioOperador(seleccion);
                miVista.setTextConfirmacion(respuesta);
            } catch (DBException e) {
                miVista.setTextError(e.getMessage());
            }
        }
    }

    public void procesaEventoCancelar() {
        gestorVistas.mostrarVistaGerente();
    }
}
