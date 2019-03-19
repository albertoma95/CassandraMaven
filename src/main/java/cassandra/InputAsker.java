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
import ocutilidades.EntradaDatos;

/**
 *
 * @author Alberto
 */
public class InputAsker {

    private static IncidenciasController incidenciasController = IncidenciasController.getInstance();

    public static InputAsker inputAsker;

    public static InputAsker getInstance() {
        if (inputAsker == null) {
            inputAsker = new InputAsker();
        }
        return inputAsker;
    }

    public static Empleado askNewEmpleado() {
        String nusuario = EntradaDatos.pedirCadena("Introduce el nombre de usuario");
        incidenciasController.checkNUsuario(nusuario);
        String nombre = EntradaDatos.pedirCadena("Introduce el nombre");
        String apellido = EntradaDatos.pedirCadena("Introduce Apellido");
        int edad = EntradaDatos.pedirEntero("Introduce la edad");
        String password = EntradaDatos.pedirCadena("Introduce tu contraseña");
        return new Empleado(nusuario, nombre, apellido, edad, password);
    }

    public static Incidencia askNewIncidencia(Empleado origen) {
        String descripcion = "";
        int estado = 0;
        boolean urgente = false;
        return new Incidencia(origen, null, descripcion, estado, new Date(), urgente);
    }
}
