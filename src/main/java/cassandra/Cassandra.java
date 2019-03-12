/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cassandra;

import controller.IncidenciasController;
import model.Empleado;
import model.Incidencia;

/**
 *
 * @author Alberto
 */
public class Cassandra {

    private static IncidenciasController incidenciasController;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        incidenciasController = IncidenciasController.getInstance();
        
        // incidenciasController.deleteEmpleado("amanzano");
    }
    
    
    private void createEmpleado() {
        Empleado empleado = InputAsker.askNewEmpleado();
        incidenciasController.insertEmpleado(empleado);
    }
    
    private void createIncidencia() {
        Empleado origen = new Empleado("", "", "", 0, "");
        Incidencia incidencia = InputAsker.askNewIncidencia(origen);
        incidenciasController.insertIncidencia(incidencia);
    }
}
