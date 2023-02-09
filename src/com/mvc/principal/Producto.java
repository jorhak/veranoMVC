/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mvc.principal;

import com.mvc.controlador.CProducto;
import com.mvc.modelo.MProducto;
import com.mvc.vista.VProducto;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author jorhak
 */
public class Producto {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

        MProducto modelo = new MProducto();
        VProducto vista = new VProducto();
        CProducto controlador = new CProducto(modelo, vista);
        controlador.Iniciar();
    }
    
}
