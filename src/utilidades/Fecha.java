/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilidades;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author jorhak
 */
public class Fecha {

    public static String getHora() {
        Date hora = new Date();
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");

        return formatoHora.format(hora);
    }

    public static String getFecha() {
        Date fecha = new Date();
        SimpleDateFormat formatoHora = new SimpleDateFormat("dd-MM-yyyy");
        return formatoHora.format(fecha);
    }
}
