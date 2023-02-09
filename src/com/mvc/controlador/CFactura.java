/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mvc.controlador;

import com.mvc.modelo.MFactura;
import com.mvc.modelo.MProducto;
import com.mvc.vista.VFactura;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import utilidades.Fecha;

/**
 *
 * @author jorhak
 */
public class CFactura implements ActionListener {

    private MFactura modelo;
    private VFactura vista;
    private MProducto mProducto;

    public CFactura(MFactura m, VFactura v) {
        this.modelo = m;
        this.vista = v;
        this.mProducto = new MProducto();
        vista.btnAgregar.addActionListener(this);
        vista.btnRegistrar.addActionListener(this);
        vista.btnQuitar.addActionListener(this);
        vista.btnEliminar.addActionListener(this);
        vista.btnObtener.addActionListener(this);
    }

    public void Iniciar() {
        vista.setTitle("Registrar Factura");
        vista.pack();
        vista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
        Listar();
        ListarProductos();
    }

    public void Registrar() {
        Map<String, String> cabecera = Cabecera();
        List<Map<String, String>> cuerpo = Cuerpo();

        modelo.SetDato(cabecera);
        modelo.SetDatoItems(cuerpo);

        Map<String, String> factura = modelo.Registrar();
        if (factura != null) {
            List<Map<String, String>> items = modelo.RegistrarItems(factura.get("nro"));
        }
        Listar();
    }

    public void Eliminar() {
        int fila = vista.tablaFactura.getSelectedRow();
        if (fila >= 0) {
            String NroFactura = vista.tablaFactura.getValueAt(fila, 0).toString();
            if (modelo.EliminarItems(NroFactura)) {
                modelo.Eliminar(NroFactura);
            }

        } else {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado la fila para borrar la factura.");
        }
        Listar();
    }

    public void Listar() {
        vista.Actualizar(modelo.Listar());
    }

    public void ListarProductos() {
        vista.ListarProductos(mProducto.Listar());
    }

    public void Obtener() {
        int fila = vista.tablaFactura.getSelectedRow();
        if (fila >= 0) {
            vista.textNit.setText(vista.tablaFactura.getValueAt(fila, 1).toString());
            vista.textNombre.setText(vista.tablaFactura.getValueAt(fila, 2).toString());
            vista.textMontoTotal.setText(vista.tablaFactura.getValueAt(fila, 4).toString());

        } else {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado la fila para obtener la factura.");
        }
    }

    public void Agregar() {
        int fila = vista.tablaProducto.getSelectedRow();
        Map<String, String> detalle = new LinkedHashMap<>();
        if (fila >= 0) {
            int cantidad = Integer.parseInt(vista.textCantidad.getText());
            double precio = Double.parseDouble(vista.textPrecio.getText());
            String codigoProducto = vista.tablaProducto.getValueAt(fila, 0).toString();

            detalle.put("cantidad", String.valueOf(cantidad));
            detalle.put("precio", String.valueOf(precio));
            detalle.put("codigoProducto", codigoProducto);

            double total = Double.parseDouble(vista.labelDinero.getText());
            total += precio*cantidad;
            vista.labelDinero.setText(String.valueOf(total));

        } else {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado la fila para obtener el producto.");
        }
        
        vista.AgregarDetalle(detalle);

        double valor = Double.parseDouble(vista.labelDinero.getText());
        BigDecimal bd = new BigDecimal(valor).setScale(1, RoundingMode.HALF_UP);

        vista.textMontoTotal.setText(String.valueOf(bd));

    }

    public void Quitar() {
        int fila = vista.tablaDetalle.getSelectedRow();
        Map<String, String> detalle = new LinkedHashMap<>();
        if (fila >= 0) {
            int cantidad = Integer.parseInt(vista.tablaDetalle.getValueAt(fila, 0).toString());
            Double precio = Double.parseDouble(vista.tablaDetalle.getValueAt(fila, 1).toString());
            String codigoProducto = vista.tablaDetalle.getValueAt(fila, 2).toString();
            double valor = precio;
            detalle.put("cantidad", String.valueOf(cantidad));
            detalle.put("precio", String.valueOf(valor));
            detalle.put("codigoProducto", codigoProducto);

            double total = Double.parseDouble(vista.labelDinero.getText());
            total -= valor*cantidad;
            vista.labelDinero.setText(String.valueOf(total));

        } else {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado la fila para quitar el detalle.");
        }


        double valor = Double.parseDouble(vista.labelDinero.getText());
        BigDecimal bd = new BigDecimal(valor).setScale(1, RoundingMode.HALF_UP);

        vista.textMontoTotal.setText(String.valueOf(bd));
        vista.QuitarDetalle(fila);
    }

    private void LimpiarTablaDetalle() {
        vista.LimpiarDetalle();
        vista.labelDinero.setText("0");
    }

    private Map<String, String> Cabecera() {
        Map<String, String> dato = new LinkedHashMap();
        dato.put("nit", vista.textNit.getText());
        dato.put("nombre", vista.textNombre.getText());
        dato.put("fecha", Fecha.getFecha());
        dato.put("montoTotal", vista.textMontoTotal.getText());
        return dato;
    }

    private List<Map<String, String>> Cuerpo() {
        List<Map<String, String>> lista = new LinkedList<>();
        for (int i = 0; i < vista.tablaDetalle.getRowCount(); i++) {
            Map<String, String> dato = new LinkedHashMap<>();
            dato.put("cantidad", vista.tablaDetalle.getValueAt(i, 0).toString());
            dato.put("precio", vista.tablaDetalle.getValueAt(i, 1).toString());
            dato.put("codigoProducto", vista.tablaDetalle.getValueAt(i, 2).toString());
            lista.add(dato);
        }
        return lista;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (vista.btnAgregar == e.getSource()) {
            try {
                Agregar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No se pudo agregar el producto>>>>" + ex.getMessage());
            }
        }
        if (vista.btnRegistrar == e.getSource()) {
            try {
                Registrar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No se pudo registrar la factura>>>>" + ex.getMessage());
            }
        }
        if (vista.btnQuitar == e.getSource()) {
            try {
                Quitar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No se pudo quitar el item producto>>>>" + ex.getMessage());
            }
        }
        if (vista.btnEliminar == e.getSource()) {
            try {
                Eliminar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar la factura>>>>" + ex.getMessage());
            }
        }
        if (vista.btnObtener == e.getSource()) {
            try {
                Obtener();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No se pudo obtener el ítem de factura de la tabla>>>>" + ex.getMessage());
            }
        }
    }
}
