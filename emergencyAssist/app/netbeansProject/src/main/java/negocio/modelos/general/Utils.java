/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.modelos.general;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author izajime
 */
public class Utils {
    
    public static JsonObject getJsonObject (String jsonString){
        return Json.createReader(new StringReader(jsonString)).readObject();
    }
}
