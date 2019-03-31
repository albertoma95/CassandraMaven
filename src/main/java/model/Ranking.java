/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Objects;

/**
 *
 * @author avalldeperas
 */
public class Ranking {
    private String nusuario;
    private long nIncidencias;

    public Ranking(String nusuario, long nIncidencias) {
        this.nusuario = nusuario;
        this.nIncidencias = nIncidencias;
    }

    public Ranking() {
    }
    
    

    public String getNusuario() {
        return nusuario;
    }

    public void setNusuario(String nusuario) {
        this.nusuario = nusuario;
    }

    public long getnIncidencias() {
        return nIncidencias;
    }

    public void setnIncidencias(long nIncidencias) {
        this.nIncidencias = nIncidencias;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ranking other = (Ranking) obj;
        if (this.nIncidencias != other.nIncidencias) {
            return false;
        }
        if (!Objects.equals(this.nusuario, other.nusuario)) {
            return false;
        }
        return true;
    }
    
    
    
}
