/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package interfaz;

/**
 *
 * @author victo
 */
public class Main {
    private static GestorVistas miGestor;
    /**
     * Main del programa de gestor de libros
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        miGestor = GestorVistas.getGestorVistas();
        miGestor.mostrarVistaIdentificarse();
    }
   
}
