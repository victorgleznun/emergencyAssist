/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.modelos.general;

import negocio.modelos.persona.Empleado;

/**
 *
 * @author fagon
 */
public class Sesion {
    private static Sesion instance;
    private Empleado empleado;

    private Sesion() {
    }

    public static Sesion getInstance() {
        if (instance == null) {
            instance = new Sesion();
        }
        return instance;
    }

    public Empleado getEmpleado() {
        return empleado;
    }
    
    public void setEmpleado(Empleado emp){
        empleado = emp;
    }
}
