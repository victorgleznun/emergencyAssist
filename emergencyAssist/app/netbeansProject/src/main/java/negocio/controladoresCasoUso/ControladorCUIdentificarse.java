/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.controladoresCasoUso;

import negocio.modelos.persona.Empleado;
import negocio.modelos.general.Sesion;
import persistencia.DAOs.persona.DAOEmpleado;
import servicioscomunes.excepciones.DBException;
import servicioscomunes.excepciones.EmpleadoNoActivoException;
import servicioscomunes.excepciones.NoExisteEmpleadoException;

/**
 *
 * @author fagon
 */
public class ControladorCUIdentificarse {
    
    public ControladorCUIdentificarse(){
    }
    
    public String identificarEmpleado(String nif, String password) throws NoExisteEmpleadoException, EmpleadoNoActivoException, DBException {
        String consulta = DAOEmpleado.consultaEmpleadoPorLoginYPassword(nif, password);
        String valor;
        if (consulta.equals("")){
            throw new NoExisteEmpleadoException("No existe empleado");
        }else{
            Empleado emp = new Empleado(consulta);
            Sesion sesion = Sesion.getInstance();
            sesion.setEmpleado(emp);
            if (!emp.estaActivo()){
                throw new EmpleadoNoActivoException("El empleado no está activo");
            }else{
                valor = sesion.getEmpleado().getRol().getTipo().toString();
            }
        }
        return valor;
    }
    
}
