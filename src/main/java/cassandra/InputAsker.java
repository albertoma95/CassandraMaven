/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cassandra;

import controller.IncidenciasController;
import exceptions.Exceptions;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Empleado;
import model.Incidencia;
import ocutilidades.EntradaDatos;

/**
 *
 * @author Alberto
 */
public class InputAsker {
    

    private static IncidenciasController incidenciasController = IncidenciasController.getInstance();
    private static MetodosVista metodosVista = MetodosVista.getInstance();

    public static InputAsker inputAsker;

    public static InputAsker getInstance() {
        if (inputAsker == null) {
            inputAsker = new InputAsker();
        }
        return inputAsker;
    }

    public static List<String> askLogin() {
        List<String> nUsuarioPassword = new ArrayList<>();
        String nusuario = EntradaDatos.pedirCadena("Introduce el nombre de usuario");
        String password = EntradaDatos.pedirCadena("Introduce tu contraseña");
        nUsuarioPassword.add(nusuario);
        nUsuarioPassword.add(password);
        return nUsuarioPassword;
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
        Incidencia incidencia = null;
        //hay que elgir a quien es el que la recibe
        Empleado destino = metodosVista.selectEmpleado(origen);
        if (destino != null) {
            String descripcion = EntradaDatos.pedirCadena("Introduce una descripción");
            int estado = 0;
            boolean urgente = isUrgente();
            return incidencia = new Incidencia((int) (new Date().getTime() / 1000), origen, destino, descripcion, estado, new Date(), urgente);
        }
        return incidencia;
    }

    public static boolean isUrgente() {
        int indice;
        boolean urgente = false;
        do {
            System.out.println("1.No urgente");
            System.out.println("2.Urgente");
            indice = EntradaDatos.pedirEntero("");
            switch (indice) {
                case 2:
                    urgente = true;
                    break;
                default: {
                    try {
                        throw new Exceptions(Exceptions.OPCION_INCORRECTA);
                    } catch (Exceptions ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        } while ((indice != 1) || (indice != 1));
        return urgente;
    }
}
