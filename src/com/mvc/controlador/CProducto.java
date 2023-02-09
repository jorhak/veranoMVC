/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mvc.controlador;

import com.mvc.modelo.MCategoria;
import com.mvc.modelo.MProducto;
import com.mvc.vista.VProducto;
import java.awt.event.ActionEvent;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import utilidades.IControlador;

/**
 *
 * @author jorhak
 */
public class CProducto implements IControlador{
    private MProducto modelo;
    private VProducto vista;
    private MCategoria mCategoria;

    public CProducto(MProducto m, VProducto v) {
        this.modelo = m;
        this.vista = v;
        this.mCategoria = new MCategoria();

        vista.btnNuevo.addActionListener(this);
        vista.btnRegistrar.addActionListener(this);
        vista.btnModificar.addActionListener(this);
        vista.btnCancelar.addActionListener(this);
        vista.btnBuscar.addActionListener(this);
        vista.btnRefrescar.addActionListener(this);
        vista.btnObtener.addActionListener(this);
        vista.btnEliminar.addActionListener(this);
    }

    @Override
    public void Iniciar() {
        vista.setTitle("Registrar Producto");
        vista.pack();
        vista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
        vista.textCodigo.setVisible(false);
        vista.labelCodigo.setVisible(false);
        Listar();
        ComboBox();
    }

    @Override
    public void Nuevo() {
        vista.textNombre.setEnabled(true);
        vista.textPrecio.setEnabled(true);
        vista.comboCategoria.setEnabled(true);
        vista.btnRegistrar.setEnabled(true);
        vista.textNombre.requestFocusInWindow();
    }

    @Override
    public void Registrar() {
        String combo = (String) vista.comboCategoria.getSelectedItem();
        String[] categoria = combo.split(" ");

        Map<String, String> dato = new LinkedHashMap();
        dato.put("nombre", vista.textNombre.getText());
        dato.put("precio", vista.textPrecio.getText());
        dato.put("idCategoria", categoria[0]);
        modelo.SetDato(dato);
        modelo.Registrar();
        Listar();
    }

    @Override
    public void Modificar() {
        Map<String, String> dato = new LinkedHashMap();
        dato.put("codigo", vista.textCodigo.getText());
        dato.put("nombre", vista.textNombre.getText());
        dato.put("precio", vista.textPrecio.getText());
        modelo.SetDato(dato);
        modelo.Modificar();
        Listar();
    }

    @Override
    public void Eliminar() {
        int fila = vista.tablaProducto.getSelectedRow();
        if (fila >= 0) {
            vista.textCodigo.setText(vista.tablaProducto.getValueAt(fila, 0).toString());
            modelo.SetID(vista.textCodigo.getText());
            modelo.Eliminar();
            Listar();
        } else {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado la fila y no se ha eliminado el producto.");
        }
    }

    @Override
    public void Listar() {
        vista.Actualizar(modelo.Listar());
    }

    @Override
    public void ComboBox() {
        Map<String, String> combo = new LinkedHashMap<>();
        combo = mCategoria.ComboBox();
        DefaultComboBoxModel mode = new DefaultComboBoxModel();
        for(String clave: combo.keySet()){
            String valor = clave + " " + combo.get(clave);
            mode.addElement(valor);
        }
        
        vista.comboCategoria.setModel(mode);
    }

    @Override
    public void Cancelar() {
        vista.textCodigo.setText("");
        vista.textNombre.setText("");
        vista.textPrecio.setText("");
        vista.comboCategoria.setSelectedIndex(0);
        vista.btnRegistrar.setEnabled(false);
        vista.btnModificar.setEnabled(false);
    }

    @Override
    public void Obtener() {
        Nuevo();
        vista.btnRegistrar.setEnabled(false);
        vista.btnModificar.setEnabled(true);
        int fila = vista.tablaProducto.getSelectedRow();
        Map<String, String> seleccionado = new LinkedHashMap<>();
        if (fila >= 0) {
            vista.textCodigo.setText(vista.tablaProducto.getValueAt(fila, 0).toString());
            vista.textNombre.setText(vista.tablaProducto.getValueAt(fila, 1).toString());
            vista.textPrecio.setText(vista.tablaProducto.getValueAt(fila, 2).toString());
            String idCategoria = vista.tablaProducto.getValueAt(fila, 3).toString();
            seleccionado = mCategoria.Seleccionado(Integer.parseInt(idCategoria));
            for (String clave : seleccionado.keySet()) {
                String valor = clave + " " + seleccionado.get(clave);
                vista.comboCategoria.setSelectedItem(valor);
            }

        } else {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado la fila para obtener el comsumo.");
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
        if (vista.btnNuevo == e.getSource()) {
            try {
                Nuevo();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No se pudieron activar los text y botton>>>>" + ex.getMessage());
            }
        }
        if (vista.btnCancelar == e.getSource()) {
            try {
                Cancelar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No se pudieron vaciar los TextField>>>>" + ex.getMessage());
            }
        }
        if (vista.btnRegistrar == e.getSource()) {
            try {
                Registrar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No se pudo registrar el producto>>>>" + ex.getMessage());
            }
        }
        if (vista.btnModificar == e.getSource()) {
            try {
                Modificar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No se pudo modificar el producto>>>>" + ex.getMessage());
            }
        }
        if (vista.btnEliminar == e.getSource()) {
            try {
                Eliminar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar el producto>>>>" + ex.getMessage());
            }
        }
        if (vista.btnObtener == e.getSource()) {
            try {
                Obtener();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No se pudo obtener la fila>>>>" + ex.getMessage());
            }
        }
        if (vista.btnBuscar == e.getSource()) {
            try {
                Buscar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No se pudo encontrar el producto>>>>" + ex.getMessage());
            }
        }
        if (vista.btnRefrescar == e.getSource()) {
            try {
                Refrescar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No se pudo refrescar la tabla producto>>>>" + ex.getMessage());
            }
        }
    }
}
