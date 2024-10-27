/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz.pares_vista_controlador.pares_vista_controlador_atenderLlamada;

import interfaz.GestorVistas;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import negocio.controladoresCasoUso.ControladorCUAtenderLlamada;
import servicioscomunes.excepciones.DBException;
import servicioscomunes.excepciones.NoExisteAseguradoException;
import servicioscomunes.excepciones.NoExistePolizaException;
import servicioscomunes.excepciones.OperadorNoEstaEnTurnoException;

/**
 *
 * @author victo
 */
public class ControladorVistaAtenderLlamada {

    private final VistaAtenderLlamada miVista;
    private final ControladorCUAtenderLlamada miControlador;
    private final GestorVistas gestorVistas;

    public ControladorVistaAtenderLlamada(VistaAtenderLlamada miVista) {
        this.miVista = miVista;
        miControlador = new ControladorCUAtenderLlamada();
        gestorVistas = GestorVistas.getGestorVistas();
    }

    public void procesarEventoRegistrarLlamada() {
        miVista.setErrorRegistrar("");
        LocalDateTime ahora = LocalDateTime.now();
        int hora = ahora.getHour();
        String tipo;

        if (hora >= 7 && hora < 15) {
            tipo = "DeMañana7";
        } else if (hora >= 15 && hora < 23) {
            tipo = "DeTarde15";
        } else {
            tipo = "DeNoche23";
        }

        if (hora == 23) {
            ahora.plusDays(1);
        }

        try {
            miControlador.consultarTurnoActual(ahora.toLocalDate().toString(), tipo);
            miVista.mostrarIntroducirDatos();
        } catch (OperadorNoEstaEnTurnoException | DBException e) {
            miVista.setErrorRegistrar(e.getMessage());
        }
    }

    public void procesarEventoConfirmarCampos() {
        miVista.setErrorRegistrar("");

        String dia = miVista.getDia();
        String mes = miVista.getMes();
        String ano = miVista.getAno();

        if (miVista.getTelefono().length() > 12) {
            miVista.setErrorRegistrar("Número de telefono demasiado largo");
            return;
        }

        if (miVista.getComunicante().length() > 100) {
            miVista.setErrorRegistrar("Nombre del comunicante demasiado largo");
            return;
        }

        if (miVista.getTelefono().equals("") || miVista.getComunicante().equals("") || miVista.getPoliza().equals("") || miVista.getNombre().equals("") || miVista.getApellidos().equals("")) {
            miVista.setErrorRegistrar("Se deben rellenar todos los campos");
            return;
        }

        String conc = ano + "-" + mes + "-" + dia;

        try {
            LocalDate fecha = LocalDate.parse(conc);
            miControlador.consultarAsegurado(miVista.getNombre(), miVista.getApellidos(), miVista.getSexo(), conc, miVista.getTelefono(), miVista.getComunicante());
            miControlador.consultarPoliza(miVista.getPoliza());
            miVista.mostrarIntroducirEmergencia();
            miVista.desactivarCampos();
        } catch (NoExistePolizaException | NoExisteAseguradoException e) {
            try {
                miControlador.crearLlamadaInfundada();
                miVista.setErrorRegistrar(e.getMessage());
                miVista.nuevaLlamada();
            } catch (DBException e1) {
                miVista.setErrorRegistrar(e1.getMessage());
                miVista.nuevaLlamada();
            }

        } catch (DateTimeParseException e) {
            miVista.setErrorRegistrar("Fecha no válida");
        } catch (DBException e2) {
            miVista.setErrorRegistrar(e2.getMessage());
            miVista.nuevaLlamada();
        }
    }

    public void procesarEventoDescripcion() {
        miVista.setErrorRegistrar("");
        String descripcion = miVista.getDescripcion();

        if (descripcion.equals("")) {
            miVista.setErrorRegistrar("Se debe indicar una descripción");
            return;
        }

        if (miVista.getIsCritica()) {
            try {
                miControlador.crearLlamadaCritica(miVista.getDescripcion());
                miVista.setErrorRegistrar("Llamada crítica creada");
                miVista.nuevaLlamada();
            } catch (DBException e) {
                miVista.setErrorRegistrar(e.getMessage());
                miVista.nuevaLlamada();
            }
        } else { //Seguimos con llamada no crítica 
            miVista.mostrarIntroducirConsejo();
            miVista.desactivarDescripcion();
        }
    }

    public void procesarEventoConsejo() {
        miVista.setErrorRegistrar("");
        String descripcion = miVista.getDescripcion();
        String resultado = miVista.getResultado();
        boolean isResuelta = miVista.getIsResulta();
        boolean isIntervencion = miVista.getIsIntervencion();

        if (descripcion.equals("") || resultado.equals("")) {
            miVista.setErrorRegistrar("Se debe indicar el consejo con su resultado");
            return;
        }

        if (isResuelta && isIntervencion) {
            miVista.setErrorRegistrar("No se puede activar un operativo al mismo tiempo que se resuelve la emergencia");
            return;
        }

        if (isIntervencion) {
            //resgistrar llamada no crítica como esLeve = false
            try {
                miControlador.guardarConsejo(descripcion, resultado, isResuelta);
                miControlador.crearLlamadaNoCritica(descripcion, false);
                miVista.setErrorRegistrar("Se ejecutaría el caso de uso “Activar operativo de emergencia”. Llamada no crítica creada");
                miVista.nuevaLlamada();
            } catch (DBException e) {
                miVista.setErrorRegistrar(e.getMessage());
                miVista.nuevaLlamada();
            }
        } else if (isResuelta) {
            //resgistrar llamada no crítica como esLeve = true
            try {
                miControlador.guardarConsejo(descripcion, resultado, isResuelta);
                miControlador.crearLlamadaNoCritica(descripcion, true);
                miVista.setErrorRegistrar("Llamada no crítica creada");
                miVista.nuevaLlamada();
            } catch (DBException e) {
                miVista.setErrorRegistrar(e.getMessage());
                miVista.nuevaLlamada();
            }
        } else { //Guardar el consejo en el controladorCU y seguir pidiendo consejos
            miControlador.guardarConsejo(descripcion, resultado, isResuelta);
            miVista.setErrorRegistrar("Consejo guardado");
        }
    }
}
