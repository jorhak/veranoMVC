/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mvc.controlador;

import com.mvc.modelo.MCategoria;
import com.mvc.vista.VCategoria;
import java.awt.event.ActionEvent;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import utilidades.IControlador;

/**
 *
 * @author jorhak
 */
public class CCategoria implements IControlador {

    private MCategoria modelo;
    private VCategoria vista;

    public CCategoria(MCategoria modelo, VCategoria vista) {
        this.modelo = modelo;
        this.vista = vista;

        vista.btnNuevo.addActionListener(this);
        vista.btnRegistrar.addActionListener(this);
        vista.btnModificar.addActionListener(this);
        vista.btnEliminar.addActionListener(this);
        vista.btnBuscar.addActionListener(this);
        vista.btnRefrescar.addActionListener(this);
        vista.btnObtener.addActionListener(this);
        vista.btnCancelar.addActionListener(this);
    }

    @Override
    public void Iniciar() {
        vista.setTitle("Registrar Categoria");
        vista.pack();
        vista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
        vista.textId.setVisible(false);
        Listar();
    }

    @Override
    public void Nuevo() {
        vista.textDescripcion.setEnabled(true);
        vista.btnRegistrar.setEnabled(true);
        vista.textDescripcion.requestFocusInWindow();
    }

    @Override
    public void Registrar() {
        Map<String, String> dato = new LinkedHashMap();
        dato.put("descripcion", vista.textDescripcion.getText());

        modelo.SetDato(dato);
        modelo.Registrar();
        Listar();
    }

    @Override
    public void Modificar() {
        Map<String, String> dato = new LinkedHashMap();
        dato.put("id", vista.textId.getText());
        dato.put("descripcion", vista.textDescripcion.getText());
        modelo.SetDato(dato);
        modelo.Modificar();
        Listar();
    }

    @Override
    public void Eliminar() {
        int fila = vista.tablaCategoria.getSelectedRow();
        if (fila >= 0) {
            vista.textId.setText(vista.tablaCategoria.getValueAt(fila, 0).toString());
            modelo.SetID(vista.textId.getText());
            modelo.Eliminar();
            Listar();
        } else {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado la fila y no se ha eliminado la categoria.");
        }
    }

    @Override
    public void Listar() {
        vista.Actualizar(modelo.Listar());
    }

    @Override
    public void ComboBox() {

    }

    @Override
    public void Cancelar() {
        vista.textId.setText("");
        vista.textDescripcion.setText("");
        vista.textBuscar.setText("");
        vista.textDescripcion.setEnabled(false);
        vista.btnRegistrar.setEnabled(false);
        vista.btnModificar.setEnabled(false);
        vista.textDescripcion.requestFocusInWindow();
    }

    @Override
    public void Obtener() {
        Nuevo();
        vista.btnRegistrar.setEnabled(false);
        vista.btnModificar.setEnabled(true);
        int fila = vista.tablaCategoria.getSelectedRow();
        if (fila >= 0) {
            vista.textId.setText(vista.tablaCategoria.getValueAt(fila, 0).toString());
            vista.textDescripcion.setText(vista.tablaCategoria.getValueAt(fila, 1).toString());

        } else {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado la fila para obtener la categoria.");
        }
    }

    @Override
    public void Buscar() {
        List<Map<String, String>> dato = new LinkedList<>();
        modelo.SetID(vista.textBuscar.getText());
        dato.add(modelo.BuscarID());
        vista.Actualizar(dato);
    }

    @Override
    public void Refrescar() {
        Listar();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(vista.btnNuevo == e.getSource()){
            try {
                Nuevo();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,"No se pudieron activar los text y botton>>>>"+ex.getMessage()  );
            }
        }
        if(vista.btnCancelar == e.getSource()){
            try {
                Cancelar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,"No se pudieron vaciar los TextField>>>>"+ex.getMessage()  );
            }
        }
        if(vista.btnRegistrar == e.getSource()){
            try {
                Registrar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,"No se pudo registrar la categoria>>>>"+ex.getMessage()  );
            }
        }
        if(vista.btnModificar == e.getSource()){
            try {
                Modificar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,"No se pudo modificar la categoria>>>>"+ex.getMessage()  );
            }
        }
        if(vista.btnEliminar == e.getSource()){
            try {
                Eliminar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,"No se pudo eliminar la categoria>>>>"+ex.getMessage()  );
            }
        }
        if(vista.btnObtener == e.getSource()){
            try {
                Obtener();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,"No se pudo obtener la fila>>>>"+ex.getMessage()  );
            }
        }
        if(vista.btnBuscar == e.getSource()){
            try {
                Buscar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,"No se pudo encontrar la categoria>>>>"+ex.getMessage()  );
            }
        }
        if(vista.btnRefrescar == e.getSource()){
            try {
                Refrescar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,"No se pudo refrescar la tabla>>>>"+ex.getMessage()  );
            }
        }
    }

}
