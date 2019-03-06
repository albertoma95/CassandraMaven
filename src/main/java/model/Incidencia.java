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
public class Incidencia {

    private int id;
    private Empleado origen;
    private Empleado destino;
    private String descripcion;
    private int estado;
    private Date fecha;
    private boolean urgente;

    public Incidencia(int id, Empleado origen, Empleado destino, String descripcion, int estado, Date fecha, boolean urgente) {
        this.id = id;
        this.descripcion = descripcion;
        this.destino = destino;
        this.estado = estado;
        this.fecha = fecha;
        this.origen = origen;
        this.urgente = urgente;
    }

    /**
     * Get the value of urgente
     *
     * @return the value of urgente
     */
    public boolean isUrgente() {
        return urgente;
    }

    /**
     * Set the value of urgente
     *
     * @param urgente new value of urgente
     */
    public void setUrgente(boolean urgente) {
        this.urgente = urgente;
    }

    /**
     * Get the value of origen
     *
     * @return the value of origen
     */
    public Empleado getOrigen() {
        return origen;
    }

    /**
     * Set the value of origen
     *
     * @param origen new value of origen
     */
    public void setOrigen(Empleado origen) {
        this.origen = origen;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Get the value of estado
     *
     * @return the value of estado
     */
    public int getEstado() {
        return estado;
    }

    /**
     * Set the value of estado
     *
     * @param estado new value of estado
     */
    public void setEstado(int estado) {
        this.estado = estado;
    }

    /**
     * Get the value of destino
     *
     * @return the value of destino
     */
    public Empleado getDestino() {
        return destino;
    }

    /**
     * Set the value of destino
     *
     * @param destino new value of destino
     */
    public void setDestino(Empleado destino) {
        this.destino = destino;
    }

    /**
     * Get the value of descripcion
     *
     * @return the value of descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Set the value of descripcion
     *
     * @param descripcion new value of descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
