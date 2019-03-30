/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cassandra;

import controller.IncidenciasController;
import exceptions.Exceptions;
import java.util.ArrayList;
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

    public MetodosVista() {
        incidenciasController = IncidenciasController.getInstance();
        myUtilities = MyUtilities.getInstance();
    }

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
        int indice = EntradaDatos.pedirEntero("");
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
    
    public int mostrarOpcionesIncidencia(){
        
        System.out.println("1.Destino");
        System.out.println("2.Descripción");
        System.out.println("3.Estado");
        System.out.println("4.Fecha");
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
        int indice = EntradaDatos.pedirEntero("");
        return indice;
    }

    public void editarEmpleado(Empleado empleado) {
        int indice;
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
                default: {
                    try {
                        throw new Exceptions(Exceptions.OPCION_INCORRECTA);
                    } catch (Exceptions ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
            incidenciasController.editarEmpleado(empleado);
        } while (indice != 0);
    }
    public void editarIncidencia(){
        int indice;
        do{
            indice = mostrarOpcionesIncidencia();
            
        }while(indice!=0);
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
            }
            if (indice != 0) {
                return empleadoEliminar = empleados.get(indice - 1);
            }

        } while (indice != 0);
        return empleadoEliminar;
    }

    public void mostrarIncidencia(Empleado empleadoSesion) {
        int indice;
        do {
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
            System.out.println("Estado: " + incidencia.getEstado());
            System.out.println("Descripción: " + incidencia.getDescripcion());
            System.out.println("Urgente: " + incidencia.isUrgente());
            System.out.println("Fecha: " + myUtilities.dateToString(incidencia.getFecha()));
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
