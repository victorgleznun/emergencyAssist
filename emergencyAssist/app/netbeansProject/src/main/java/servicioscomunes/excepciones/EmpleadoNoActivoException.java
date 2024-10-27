/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicioscomunes.excepciones;

/**
 *
 * @author izajime
 */
public class EmpleadoNoActivoException extends Exception{
    
    public EmpleadoNoActivoException(String msg){
        super (msg);
    }
    
    public EmpleadoNoActivoException(String msg, Throwable cause){
        super (msg, cause);
    }
}
