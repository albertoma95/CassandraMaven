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
    
    
    private int codigo;

    private final List<String> mensajes = Arrays.asList(
            "El usuario no existe",
            "Opción incorrecta",
            "Nombre demasiado largo",
            "Apellido demasiado largo",
            "Dni incorrecto",
            "Codigo postal incorrecto",
            "Numero de telefono incorrecto",
            "Numero de mascotas incorrecto",
            "Matrícula incorrecta",
            "Contraseña inadecuada",
            "Tipo de usuario incorrecto",
            "Este usuario ya existe",
            "No puedes boirrarte a tí mismo"
    );

    public Exceptions(int codigo) {
        this.codigo = codigo;
    }

    @Override
    public String getMessage() {
        return mensajes.get(codigo);
    }
}
