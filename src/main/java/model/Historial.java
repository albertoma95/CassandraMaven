/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author avalldeperas
 */
public class Historial {

    private int id;
    private String empleado;
    private Date fecha;
    private int tipo;

    public Historial(int id, String empleado, Date fecha, int tipo) {
        this.id = id;
        this.empleado = empleado;
        this.fecha = fecha;
        this.tipo = tipo;
    }

    public Historial() {
    }
    
    

    /**
     * Get the value of tipo
     *
     * @return the value of tipo
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * Set the value of tipo
     *
     * @param tipo new value of tipo
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Get the value of empleado
     *
     * @return the value of empleado
     */
    public String getEmpleado() {
        return empleado;
    }

    /**
     * Set the value of empleado
     *
     * @param empleado new value of empleado
     */
    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    /**
     * Get the value of id
     *
     * @return the value of id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the value of id
     *
     * @param id new value of id
     */
    public void setId(int id) {
        this.id = id;
    }

}
