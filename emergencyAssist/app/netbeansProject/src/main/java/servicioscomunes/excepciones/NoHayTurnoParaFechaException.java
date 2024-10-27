/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicioscomunes.excepciones;

/**
 *
 * @author izajime
 */
public class NoHayTurnoParaFechaException extends Exception{
    
    public NoHayTurnoParaFechaException(String msg){
        super (msg);
    }
    
    public NoHayTurnoParaFechaException(String msg, Throwable cause){
        super (msg, cause);
    }
}
