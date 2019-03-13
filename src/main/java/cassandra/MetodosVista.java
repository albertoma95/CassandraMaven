/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cassandra;

import model.Empleado;
import ocutilidades.EntradaDatos;

/**
 *
 * @author Alberto
 */
public class MetodosVista {

    public static MetodosVista metodosVista;

    public static MetodosVista getInstance() {
        if (metodosVista == null) {
            metodosVista = new MetodosVista();
        }
        return metodosVista;
    }

    public int MostrarMenu(Empleado empleado) {
        if (empleado == null) {
            System.out.println("1.Iniciar Sesión");
        } else {
            System.out.println("2.Crear usuario");
            System.out.println("3.Editar usuario");
            System.out.println("4.Borrar usuario");
            System.out.println("5.Mostrar Incidencias");
            System.out.println("6.Borrar Incidencia");
            System.out.println("4.Editar Incidencia");
        }
        System.out.println("0.Salir");
        int indice = EntradaDatos.pedirEntero("");
        return indice;
    }
}
