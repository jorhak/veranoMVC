/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mvc.principal;

import com.mvc.controlador.CFactura;
import com.mvc.modelo.MFactura;
import com.mvc.vista.VFactura;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author jorhak
 */
public class Factura {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

        MFactura modelo = new MFactura();
        VFactura vista = new VFactura();
        CFactura controlador = new CFactura(modelo, vista);
        controlador.Iniciar();
    }
    
}
