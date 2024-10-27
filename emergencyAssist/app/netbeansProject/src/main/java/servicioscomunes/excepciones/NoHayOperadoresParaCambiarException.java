/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicioscomunes.excepciones;

/**
 *
 * @author izajime
 */
public class NoHayOperadoresParaCambiarException extends Exception{
    
    public NoHayOperadoresParaCambiarException(String msg){
        super (msg);
    }
    
    public NoHayOperadoresParaCambiarException(String msg, Throwable cause){
        super (msg, cause);
    }
}
