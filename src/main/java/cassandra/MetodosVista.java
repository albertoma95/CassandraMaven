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
import java.util.Iterator;
import java.util.List;
import model.Empleado;
import model.Incidencia;
import ocutilidades.EntradaDatos;
import utilities.MyUtilities;

/**
 *
 * @author Alberto
 */
public class MetodosVista {

    private static IncidenciasController incidenciasController;
    private static MyUtilities myUtilities;
    private static InputAsker inputAsker;

    public MetodosVista() {
        incidenciasController = IncidenciasController.getInstance();
        myUtilities = MyUtilities.getInstance();
        inputAsker = InputAsker.getInstance();
    }

    public static MetodosVista metodosVista;

    public static MetodosVista getInstance() {
        if (metodosVista == null) {
            metodosVista = new MetodosVista();
        }
        return metodosVista;
    }

    public int MostrarMenu(Empleado empleado) {
        System.out.println("\n***** MENU *****");
        if (empleado == null) {
            System.out.println("1.Iniciar Sesión");
        } else {
            //todos pueden ver
            System.out.println("2.Editar perfil");
            System.out.println("3.Crear Incidencia");
            System.out.println("4.Mostrar Incidencias");
            System.out.println("5.Borrar Incidencia");
            System.out.println("6.Editar Incidencia");
            if (empleado.getNusuario().equals("amanzano")) {
                //cosas admin
                System.out.println("7.Crear empleado");
                System.out.println("8.Borrar empleado");
                System.out.println("9.Ranking");
                System.out.println("10.Ultimo login");
            }
        }
        System.out.println("0.Salir");
        System.out.println("\n*****************");
        int indice = EntradaDatos.pedirEntero("Inserta una opcion: ");
        return indice;
    }

    public int mostrarOpcionesEmpleado() {
        System.out.println("1.Apellido");
        System.out.println("2.Edad");
        System.out.println("3.Nombre");
        System.out.println("4.Contraseña");
        System.out.println("0.Atrás");
        int indice = EntradaDatos.pedirEntero("");
        return indice;
    }

    public int mostrarOpcionesIncidencia() {

        System.out.println("1.Destino");
        System.out.println("2.Descripción");
        System.out.println("3.Estado");
        System.out.println("4.Poner fecha actual");
        System.out.println("5.Urgente");
        System.out.println("0.Salir");
        int indice = EntradaDatos.pedirEntero("");
        return indice;
    }

    public int mostrarEmpleadosIncidencias(List<Object> lista) {
        int contador = 1;
        if (lista.isEmpty()) {
            System.out.println("No hay opciones");
        } else {
            for (Object objeto : lista) {
                if (objeto instanceof Empleado) {
                    Empleado empleado = (Empleado) objeto;
                    System.out.println(contador + ": " + empleado.getNusuario());

                }
                if (objeto instanceof Incidencia) {
                    Incidencia instancia = (Incidencia) objeto;
                    System.out.println(contador + ": " + instancia.getDescripcion());
                }
                contador += 1;
            }
            System.out.println("0.Salir");
        }
        int indice = EntradaDatos.pedirEntero("Elige el numero asociado al empleado: ");
        return indice;
    }

    public void editarEmpleado(Empleado empleado) {
        int indice;
        boolean checker = false;
        do {
            indice = mostrarOpcionesEmpleado();
            switch (indice) {
                case 1:
                    String apellido = EntradaDatos.pedirCadena("Introduce el apellido");
                    empleado.setApellido(apellido);
                    break;
                case 2:
                    int edad = EntradaDatos.pedirEntero("Introduce la edad");
                    empleado.setEdad(edad);
                    break;
                case 3:
                    String nombre = EntradaDatos.pedirCadena("Introduce nombre");
                    empleado.setNombre(nombre);
                    break;
                case 4:
                    String contra = EntradaDatos.pedirCadena("Introduce contraseña");
                    empleado.setPassword(contra);
                    break;
                default: 
                    checker = true;
                    try {
                        throw new Exceptions(Exceptions.OPCION_INCORRECTA);
                    } catch (Exceptions ex) {
                        System.out.println(ex.getMessage());
                    }
            }
            if(!checker)
                incidenciasController.editarEmpleado(empleado);
        } while (indice != 0);
    }

    public void editarIncidencia(Incidencia incidencia, Empleado empleadoSesion) {
        int indice;
        do {
            indice = mostrarOpcionesIncidencia();
            switch (indice) {
                case 1:
                    //destino
                    Empleado destino = selectEmpleado(empleadoSesion);
                    incidencia.setDestino(destino);
                    break;
                case 2:
                    String descripcion = EntradaDatos.pedirCadena("Introduce descripción");
                    incidencia.setDescripcion(descripcion);
                    break;
                case 3:
                    //estado
                    break;
                case 4:
                    //fecha
                    Date fecha = new Date();
                    incidencia.setFecha(fecha);
                    break;
                case 5:
                    //urgente
                    Boolean urgente = inputAsker.isUrgente();
                    incidencia.setUrgente(urgente);
                    break;
                default:
                    try {
                        throw new Exceptions(Exceptions.OPCION_INCORRECTA);
                    } catch (Exceptions ex) {
                        System.out.println(ex.getMessage());
                    }
            }
            incidenciasController.editarIncidencia(incidencia);
        } while (indice != 0);
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

    public Empleado selectEmpleado(Empleado empleado) {
        Empleado empleadoEliminar = null;
        int indice;
        do {
            List<Empleado> empleados = incidenciasController.getAllEmpleados();
            //el master de la app no debe poder borrarse a si mismo
            Iterator<Empleado> itr = empleados.iterator();
            while (itr.hasNext()) {
                if (itr.next().getNusuario().equals(empleado.getNusuario())) {
                    itr.remove();
                }
            }
            List<Object> lista = new ArrayList<>();
            for (Empleado emp : empleados) {
                lista.add(emp);
            }

            indice = mostrarEmpleadosIncidencias(lista);
            if (indice < 0 || (indice > empleados.size())) {
                try {
                    throw new Exceptions(Exceptions.OPCION_INCORRECTA);
                } catch (Exceptions ex) {
                    System.out.println(ex.getMessage());
                }
            } else if (indice != 0) {
                return empleadoEliminar = empleados.get(indice - 1);
            }

        } while (indice != 0);
        return empleadoEliminar;
    }

    public void mostrarIncidencia(Empleado empleadoSesion) {
        int indice;
        do {
            System.out.println("\nMenú Mostrar Incidencias");
            System.out.println("1.Incidencias que eres origen");
            System.out.println("2.Incidencias que eres destino");
            System.out.println("0.Salir");
            indice = EntradaDatos.pedirEntero("");
            switch (indice) {
                case 1:
                    //origen
                    List<Incidencia> incidenciasOrigen = incidenciasController.getIncidenciaOrigenDestino(empleadoSesion, true);
                    printIncidencias(incidenciasOrigen);
                    break;
                case 2:
                    //destino
                    List<Incidencia> incidenciasDestino = incidenciasController.getIncidenciaOrigenDestino(empleadoSesion, false);
                    printIncidencias(incidenciasDestino);
                    break;
                default: {
                    try {
                        throw new Exceptions(Exceptions.OPCION_INCORRECTA);
                    } catch (Exceptions ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        } while (indice != 0);
    }

    public void printIncidencias(List<Incidencia> incidencias) {
        for (Incidencia incidencia : incidencias) {
            System.out.println("Incidencia:");
            System.out.println("Destino: " + incidencia.getDestino().getNusuario());
            String estado = incidencia.getEstado() == 0 ? "Pendiente" : "Finalizada";
            System.out.println("Estado: " + estado);
            System.out.println("Descripción: " + incidencia.getDescripcion());
            String urgente = incidencia.isUrgente() ? "Si" : "No";
            System.out.println("Urgente: " + urgente);
            System.out.println("Fecha: " + myUtilities.dateToString(incidencia.getFecha()) + "\n");
        }
    }

    public Incidencia selectIncidencia(Empleado empleadoSesion) {
        Incidencia incidenciaEliminar = null;
        int indice;
        do {
            List<Incidencia> incidenciasOrigen = incidenciasController.getIncidenciaOrigenDestino(empleadoSesion, true);

            List<Object> lista = new ArrayList<>();
            for (Incidencia inc : incidenciasOrigen) {
                lista.add(inc);
            }

            indice = mostrarEmpleadosIncidencias(lista);
            if (indice < 0 || (indice > incidenciasOrigen.size())) {
                try {
                    throw new Exceptions(Exceptions.OPCION_INCORRECTA);
                } catch (Exceptions ex) {
                    System.out.println(ex.getMessage());
                }
            }
            if (indice != 0) {
                return incidenciaEliminar = incidenciasOrigen.get(indice - 1);
            }

        } while (indice != 0);

        return incidenciaEliminar;
    }
}
