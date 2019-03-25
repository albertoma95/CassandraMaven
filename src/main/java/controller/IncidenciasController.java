/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import cassandra.MetodosVista;
import exceptions.Exceptions;
import java.util.Date;
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
    private MetodosVista metodosVista;

    public static IncidenciasController getInstance() {
        if (incidenciasController == null) {
            incidenciasController = new IncidenciasController();
        }
        return incidenciasController;
    }

    public IncidenciasController() {
        cassandraDAO = CassandraDAO.getInstance();
        metodosVista = MetodosVista.getInstance();
    }

    public void insertEmpleado(Empleado empleado) {
        cassandraDAO.saveOrUpdateEmpleado(empleado);
    }

    public void insertIncidencia(Incidencia incidencia) {
        cassandraDAO.insertOrUpdateIncidencia(incidencia);

    }

    public void deleteEmpleado(String nusuario) {
        Empleado empleado = new Empleado(nusuario, "", "", 0, "");
        cassandraDAO.removeEmpleado(empleado);
    }

    public Empleado checkNUsuario(String nusuario) {
        return cassandraDAO.getEmpleadoByNusuario(nusuario);
    }

    public void editarEmpleado(Empleado empleado) {
        int indice;
        do {
            indice = metodosVista.mostrarOpciones();
            switch (indice) {
                case 1:
                    String apellido = EntradaDatos.pedirCadena("Introduce el apellido");
                    empleado.setApellido(apellido);
                    cassandraDAO.saveOrUpdateEmpleado(empleado);
                    break;
                case 2:
                    int edad = EntradaDatos.pedirEntero("Introduce la edad");
                    empleado.setEdad(edad);
                    cassandraDAO.saveOrUpdateEmpleado(empleado);
                    break;
                case 3:
                    String nombre = EntradaDatos.pedirCadena("Introduce nombre");
                    empleado.setNombre(nombre);
                    cassandraDAO.saveOrUpdateEmpleado(empleado);
                    break;
                case 4:
                    String contra = EntradaDatos.pedirCadena("Introduce contraseña");
                    empleado.setPassword(contra);
                    cassandraDAO.saveOrUpdateEmpleado(empleado);
                    break;
                default: {
                    try {
                        throw new Exceptions(Exceptions.OPCION_INCORRECTA);
                    } catch (Exceptions ex) {
                        
                        //esto es provisional
                        System.out.println(ex.getMessage());
                    }
                }
            }
        } while (indice != 0);
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
