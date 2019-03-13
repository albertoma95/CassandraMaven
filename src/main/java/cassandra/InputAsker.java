/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cassandra;

import controller.IncidenciasController;
import java.util.Date;
import model.Empleado;
import model.Incidencia;

/**
 *
 * @author Alberto
 */
public class InputAsker {
    private static IncidenciasController incidenciasController = IncidenciasController.getInstance();
    
    public static Empleado askNewEmpleado() {
        String nusuario = "";
        incidenciasController.checkNUsuario(nusuario);
        String nombre = "";
        String apellido = "";
        int edad = 0;
        String password = "";
        return new Empleado(nusuario, nombre, apellido, edad, password);
    }
    
    public static Incidencia askNewIncidencia(Empleado origen) {
        String descripcion = "";
        int estado = 0;
        boolean urgente = false;
        return new Incidencia(origen, null, descripcion, estado, new Date(), urgente);
    }
}
