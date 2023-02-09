/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mvc.principal;

import com.mvc.controlador.CCategoria;
import com.mvc.modelo.MCategoria;
import com.mvc.vista.VCategoria;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author jorhak
 */
public class Categoria {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

        MCategoria modelo = new MCategoria();
        VCategoria vista = new VCategoria();
        CCategoria controlador = new CCategoria(modelo, vista);
        controlador.Iniciar();

    }

}
