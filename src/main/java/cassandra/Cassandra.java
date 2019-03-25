/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cassandra;

import controller.IncidenciasController;
import exceptions.Exceptions;
import java.util.List;
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
        MetodosVista metodosVista = MetodosVista.getInstance();
        InputAsker inputAsker = InputAsker.getInstance();
        int indice;
        Empleado empleadoSesion = null;
        do {
            indice = metodosVista.MostrarMenu(empleadoSesion);
            try {
                if (empleadoSesion == null) {
                    if ((indice < 0) || (indice > 2)) {
                        throw new Exceptions(Exceptions.OPCION_INCORRECTA);
                    }
                    if (indice == 1) {
                        //hacer login
                        List<String> nUsuarioPassword = inputAsker.askLogin();
                        empleadoSesion = incidenciasController.iniciarSesion(nUsuarioPassword.get(0), nUsuarioPassword.get(1));
                        if(empleadoSesion == null){
                            throw new Exceptions(Exceptions.NUSUARIO_PASSWORD_INCORRECTO);
                        }
                        System.out.println("Bienvenido "+empleadoSesion.getNombre());
                    }
                } else {
                    //resto de opciones
                    int max = empleadoSesion.equals("amanzano") ? 9 : 5;
                    if ((indice < 0) || (indice > max)) {
                        throw new Exceptions(Exceptions.OPCION_INCORRECTA);
                    }
                    switch (indice) {
                        case 2:
                            //editar perfil
                            incidenciasController.editarEmpleado(empleadoSesion);
                            break;
                        case 3:
                            break;
                        case 4:
                            break;
                        case 5:
                            break;
                        case 6:
                            //crear nuevo empleado
                            Empleado nuevoEmpleado = inputAsker.askNewEmpleado();
                            incidenciasController.insertEmpleado(nuevoEmpleado);
                            break;
                        case 7:
                            break;
                        case 8:
                            break;
                        case 9:
                            break;
                    }
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } while (indice != 0);
        System.out.println("Adiós");
    }

//    private void createEmpleado() {
//        Empleado empleado = InputAsker.askNewEmpleado();
//        incidenciasController.insertEmpleado(empleado);
//    }
//
//    private void createIncidencia() {
//        Empleado origen = new Empleado("", "", "", 0, "");
//        Incidencia incidencia = InputAsker.askNewIncidencia(origen);
//        incidenciasController.insertIncidencia(incidencia);
//    }
}
