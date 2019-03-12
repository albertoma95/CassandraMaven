/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.Date;
import model.Empleado;
import model.Incidencia;
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

    public void insertEmpleado(Empleado empleado) {
        cassandraDAO.saveOrUpdateEmpleado(empleado);
    }
    
    public void insertIncidencia(Incidencia incidencia){
        cassandraDAO.insertOrUpdateIncidencia(incidencia);
        
    }

    public void deleteEmpleado(String nusuario) {
        Empleado empleado = new Empleado(nusuario, "", "", 0, "");
        cassandraDAO.removeEmpleado(empleado);
    }    
}
