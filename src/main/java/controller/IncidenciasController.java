/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import cassandra.MetodosVista;
import exceptions.Exceptions;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Empleado;
import model.Incidencia;
import ocutilidades.EntradaDatos;
import persistence.CassandraDAO;

/**
 *
 * @author Alberto
 */
public class IncidenciasController {
    
    private static IncidenciasController incidenciasController;
    private CassandraDAO cassandraDAO;
    
    public static IncidenciasController getInstance() {
        if (incidenciasController == null) {
            incidenciasController = new IncidenciasController();
        }
        return incidenciasController;
    }
    
    public IncidenciasController() {
        cassandraDAO = CassandraDAO.getInstance();
    }
    
    public void insertEmpleado(Empleado empleado) {
        cassandraDAO.saveOrUpdateEmpleado(empleado);
    }
    
    public void insertIncidencia(Incidencia incidencia) {
        cassandraDAO.insertOrUpdateIncidencia(incidencia);
        
    }
    
    public void deleteEmpleado(Empleado empleado) {
        cassandraDAO.removeEmpleado(empleado);
    }
    
    public Empleado checkNUsuario(String nusuario) {
        return cassandraDAO.getEmpleadoByNusuario(nusuario);
    }
    
    public List<Empleado> getAllEmpleados() {
        return cassandraDAO.selectAllEmpleado();
    }
    
    public void editarEmpleado(Empleado empleado) {
        cassandraDAO.saveOrUpdateEmpleado(empleado);
    }
    
    public Empleado iniciarSesion(String nusuario, String password) {
        Empleado empleado = null;
        if (cassandraDAO.loginEmpleado(nusuario, password)) {
            //existe, nos traemos el empleado
            empleado = cassandraDAO.getEmpleadoByNusuario(nusuario);
        }
        return empleado;
    }
}
