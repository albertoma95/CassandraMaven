/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Alberto
 */
public class Exceptions extends Exception{
    
    public static final int OPCION_INCORRECTA = 0;
    public static final int NUSUARIO_PASSWORD_INCORRECTO = 1;
    
    
    private int codigo;

    private final List<String> mensajes = Arrays.asList(
            "Opcion incorrecta",
            "Nombre de usuario o password incorrecto"
    );

    public Exceptions(int codigo) {
        this.codigo = codigo;
    }

    @Override
    public String getMessage() {
        return mensajes.get(codigo);
    }
}
