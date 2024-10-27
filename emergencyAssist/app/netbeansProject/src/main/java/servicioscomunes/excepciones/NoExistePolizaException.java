/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicioscomunes.excepciones;

/**
 *
 * @author izajime
 */
public class NoExistePolizaException extends Exception{
    
    public NoExistePolizaException(String msg){
        super (msg);
    }
    
    public NoExistePolizaException(String msg, Throwable cause){
        super (msg, cause);
    }
}
