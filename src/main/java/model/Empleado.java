/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author avalldeperas
 */
public class Empleado {

    private String nusuario;
    private String nombre;
    private int edad;
    private String apellido;
    private String password;

    public Empleado() {
    }
    
    public Empleado(String nusuario) {
        this.nusuario = nusuario;
    }
    
    public Empleado(String nusuario, String nombre, String apellido, int edad, String password) {
        this.nusuario = nusuario;
        this.nombre = nombre;
        this.edad = edad;
        this.apellido = apellido;
        this.password = password;
    }

    /**
     * Get the value of password
     *
     * @return the value of password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the value of password
     *
     * @param password new value of password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the value of apellido
     *
     * @return the value of apellido
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Set the value of apellido
     *
     * @param apellido new value of apellido
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Get the value of edad
     *
     * @return the value of edad
     */
    public int getEdad() {
        return edad;
    }

    /**
     * Set the value of edad
     *
     * @param edad new value of edad
     */
    public void setEdad(int edad) {
        this.edad = edad;
    }

    /**
     * Get the value of nombre
     *
     * @return the value of nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Set the value of nombre
     *
     * @param nombre new value of nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Get the value of nusuario
     *
     * @return the value of nusuario
     */
    public String getNusuario() {
        return nusuario;
    }

    /**
     * Set the value of nusuario
     *
     * @param nusuario new value of nusuario
     */
    public void setNusuario(String nusuario) {
        this.nusuario = nusuario;
    }

}
