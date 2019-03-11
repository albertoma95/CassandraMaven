/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Empleado;
import persistence.CassandraDAO;

/**
 *
 * @author Alberto
 */
public class IncidenciasController {

    private static IncidenciasController incidenciasController;
    private CassandraDAO cassandraDAO;
    
     public static IncidenciasController getInstance() {
        if(incidenciasController == null)
            incidenciasController = new IncidenciasController();
        return incidenciasController;
    }
    
    public IncidenciasController() {
        cassandraDAO = CassandraDAO.getInstance();
    }

    public void insertEmpleado(String nusuario, String nombre, String apellido, int edad, String password) {
        Empleado empleado = new Empleado(nusuario, nombre, apellido, edad, password);
        cassandraDAO.saveOrUpdateEmpleado(empleado);
    }
    
}
