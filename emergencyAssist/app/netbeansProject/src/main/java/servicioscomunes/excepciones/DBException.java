/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicioscomunes.excepciones;

/**
 *
 * @author izajime
 */
public class DBException extends Exception{
    
    public DBException(String msg){
        super (msg);
    }
    
    public DBException(String msg, Throwable cause){
        super (msg, cause);
    }
}
