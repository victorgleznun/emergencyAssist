/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicioscomunes.excepciones;

/**
 *
 * @author izajime
 */
public class NoExisteEmpleadoException extends Exception{
    
    public NoExisteEmpleadoException(String msg){
        super (msg);
    }
    
    public NoExisteEmpleadoException(String msg, Throwable cause){
        super (msg, cause);
    }

}
