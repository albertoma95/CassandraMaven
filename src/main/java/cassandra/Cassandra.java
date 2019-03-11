/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cassandra;

import controller.IncidenciasController;

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
        incidenciasController.insertEmpleado("amanzano", "Alberto", "Manzano", 21, "1234");
    }
    
}
