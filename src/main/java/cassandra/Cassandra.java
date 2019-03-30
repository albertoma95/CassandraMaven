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
                        if (empleadoSesion == null) {
                            throw new Exceptions(Exceptions.NUSUARIO_PASSWORD_INCORRECTO);
                        }
                        System.out.println("Bienvenido " + empleadoSesion.getNombre());
                    }
                } else {
                    //resto de opciones
                    int max = empleadoSesion.getNusuario().equals("amanzano") ? 10 : 6;
                    if ((indice < 0) || (indice > max)) {
                        throw new Exceptions(Exceptions.OPCION_INCORRECTA);
                    }
                    switch (indice) {
                        case 2:
                            //editar perfil
                            metodosVista.editarEmpleado(empleadoSesion);
                            break;
                        case 3:
                            //crear incidencia
                            Incidencia nuevaIncidencia = inputAsker.askNewIncidencia(empleadoSesion);
                            if (nuevaIncidencia != null) {
                                //creamos la incidencia
                                incidenciasController.insertIncidencia(nuevaIncidencia);
                            }
                            break;
                        case 4:
                            //mostrar incidencia las que eres origen y tambien las que eres destino
                            metodosVista.mostrarIncidencia(empleadoSesion);
                            break;
                        case 5:
                            //borrar incidencia, solo las que tu eres origen
                            Incidencia incidencia = metodosVista.selectIncidencia(empleadoSesion);
                            if(incidencia != null){
                                //borramos esta incidencia
                                incidenciasController.deleteIncidencia(incidencia);
                            }
                            break;
                        case 6:
                            //editar incidencia
                            Incidencia incidenciaEditar = metodosVista.selectIncidencia(empleadoSesion);
                            if(incidenciaEditar!= null){
                                metodosVista.editarIncidencia(incidenciaEditar, empleadoSesion);
                            }
                            break;
                        case 7:
                            //crear nuevo empleado
                            Empleado nuevoEmpleado = inputAsker.askNewEmpleado();
                            incidenciasController.insertEmpleado(nuevoEmpleado);
                            break;
                        case 8:
                            //borrar empleado
                            Empleado empleadoEliminar = metodosVista.selectEmpleado(empleadoSesion);
                            if (empleadoEliminar != null) {
                                incidenciasController.deleteEmpleado(empleadoEliminar);
                            }
                            break;
                        case 9:
                            break;
                        case 10:
                            break;
                    }
                }
            } catch (Exceptions ex) {
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
