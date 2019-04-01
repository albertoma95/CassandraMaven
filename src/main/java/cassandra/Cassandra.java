/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cassandra;

import controller.IncidenciasController;
import exceptions.Exceptions;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import model.Empleado;
import model.Historial;
import model.Incidencia;
import model.Ranking;
import utilities.MyUtilities;

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
        MyUtilities utilities = MyUtilities.getInstance();
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
                        Historial hislogin = new Historial(incidenciasController.getMaxID(false) + 1, empleadoSesion.getNusuario(), new Date(), 1);
                        incidenciasController.insertarEvento(hislogin);
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
                                if (nuevaIncidencia.isUrgente()) {
                                    Historial hislogin = new Historial(incidenciasController.getMaxID(false) + 1, empleadoSesion.getNusuario(), new Date(), 2);
                                    incidenciasController.insertarEvento(hislogin);
                                }
                            }
                            break;
                        case 4:
                            //mostrar incidencia las que eres origen y tambien las que eres destino
                            metodosVista.mostrarIncidencia(empleadoSesion);
                            break;
                        case 5:
                            //borrar incidencia, solo las que tu eres origen
                            Incidencia incidencia = metodosVista.selectIncidencia(empleadoSesion);
                            if (incidencia != null) {
                                //borramos esta incidencia
                                incidenciasController.deleteIncidencia(incidencia);
                            }
                            break;
                        case 6:
                            //editar incidencia
                            Incidencia incidenciaEditar = metodosVista.selectIncidencia(empleadoSesion);
                            if (incidenciaEditar != null) {
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
                            //Ranking
                            System.out.println("\n-- Ránking de incidencias urgentes creadas --");
                            List<Ranking> listaRanking = incidenciasController.ranking();
                            System.out.println("Total de incidencias urgentes: " + listaRanking.size());
                            HashMap<String, Long> ranking = new HashMap<>();
                            for (Ranking ran : listaRanking) {
                                if (ranking.containsKey(ran.getNusuario())) {
                                    ranking.put(ran.getNusuario(), ranking.get(ran.getNusuario()) + 1);
                                } else {
                                    ranking.put(ran.getNusuario(), ran.getnIncidencias());
                                }
                            }
                            Map<String, Long> sorted = ranking
                                    .entrySet()
                                    .stream()
                                    .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                                    .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));
                            sorted.forEach((k, v) -> System.out.println("Empleado: " + k + ": Incidencias: " + v));
                            break;
                        case 10:
                            //ultimo login
                            Empleado empleadoLogin = metodosVista.selectEmpleado(empleadoSesion);
                            if (empleadoLogin != null) {

                                Historial ultimoLogin = incidenciasController.getUltimoInicioSesion(empleadoLogin);
                                if (ultimoLogin != null) {
                                    System.out.println("Last Login: " + utilities.dateToString(ultimoLogin.getFecha()));
                                }
                            }
                            break;
                    }
                }
            } catch (Exceptions ex) {
                System.out.println(ex.getMessage());
            }
        } while (indice != 0);
        System.out.println("Adiós");
    }
}
