/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mvc.modelo;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import utilidades.Conexion;

/**
 *
 * @author jorhak
 */
public class MFactura {
    private int Nro;
    private String Nit;
    private String Nombre;
    private String Fecha;
    private double MontoTotal;
    private List<MDetalle> Detalle;

    public MFactura(int Nro, String Nit, String Nombre, String Fecha, double Monto) {
        this.Nro = Nro;
        this.Nit = Nit;
        this.Nombre = Nombre;
        this.Fecha = Fecha;
        this.MontoTotal = Monto;
        this.Detalle = new LinkedList<>();
    }

    public MFactura() {
        this(0, "", "", "",0.0);
    }

    public void SetDato(Map<String, String> dato) {
        Nro = Integer.parseInt(dato.getOrDefault("nro", "0"));
        Nit = dato.getOrDefault("nit", "");
        Nombre = dato.getOrDefault("nombre", "");
        Fecha = dato.getOrDefault("fecha", "");
        MontoTotal = Double.parseDouble(dato.getOrDefault("montoTotal", "0.0"));
    }

    public void SetDatoItem(Map<String, String> dato) {
        Detalle.clear();
        MDetalle detalle = new MDetalle();
        detalle.SetDato(dato);
        Detalle.add(detalle);
    }

    public void SetDatoItems(List<Map<String, String>> dato) {
        Detalle.clear();
        for (int i = 0; i < dato.size(); i++) {
            MDetalle detalle = new MDetalle();
            detalle.SetDato(dato.get(i));
            Detalle.add(detalle);
        }
    }

    public Map<String, String> Registrar() {
        boolean proccessed = false;
        String sqlInsert = "insert into factura (nit, nombre, fecha, montoTotal) "
                + "values (?,?,?,?);";

        String sqlUpdate = "update factura "
                + "set nit=?, nombre=? "
                + "where nro=?;";

        String sql = Nro != 0 ? sqlUpdate : sqlInsert;

        try {
            PreparedStatement statement = Conexion.getInstance().getConnection().prepareStatement(sql);
            statement.setString(1, Nit);
            statement.setString(2, Nombre);
            statement.setString(3, Fecha);
            statement.setDouble(4, MontoTotal);

            if (Nro != 0) {
                statement.setString(1, Nit);
                statement.setString(2, Nombre);
                statement.setInt(3, Nro);
            }

            proccessed = Conexion.getInstance().executeSQL(statement);
        } catch (SQLException e) {
        }
        int nroFactura = Integer.parseInt(getIDFactura());
        return proccessed ? Buscar("nro", nroFactura) : null;
    }

    public Map<String, String> RegistrarItem() {
        if (Detalle.size() == 1) {
            MDetalle detalle = Detalle.get(0);
            return detalle.Registrar();
        }

        return null;
    }

    public List<Map<String, String>> RegistrarItems(String NroFactura) {
        List<Map<String, String>> items = new LinkedList<>();

        for (int i = 0; i < Detalle.size(); i++) {
            MDetalle detalle = Detalle.get(i);
            detalle.SetFacturaNro(NroFactura);
            Map<String,String> de = detalle.Registrar();
            items.add(de);
        }

        return items;
    }

    public boolean Eliminar(String NroFactura) {
        String sql = "delete from factura where nro=?;";
        return Conexion.getInstance().delete(sql, NroFactura);
    }

    public boolean EliminarItem(String NroFactura, String IdDetalle) {
        return MDetalle.Eliminar(NroFactura, IdDetalle);
    }

    public boolean EliminarItems(String NroFactura) {
        List<Map<String, String>> detalle = MDetalle.Listar(NroFactura);

        for (int i = 0; i < detalle.size(); i++) {
            String IdDetalle = detalle.get(i).get("id");
            if (!MDetalle.Eliminar(NroFactura, IdDetalle)) {
                return false;
            }
        }
        return !detalle.isEmpty() ? true : false;
    }

    public Map<String, String> Buscar(String columnName, Object columnValue) {
        String sql = "select * from factura where %s='%s' limit 1;";
        sql = String.format(sql, columnName, columnValue);

        List<Map<String, String>> resultado = Conexion.getInstance().executeSQLResultList(sql);
        return !resultado.isEmpty() ? resultado.get(0) : null;
    }

    public Map<String, String> BuscarItem(String NroFactura, String IdDetalle) {
        return MDetalle.Buscar("nro", NroFactura, "id", IdDetalle);
    }

    public List<Map<String, String>> BuscarItems(String IdFactura) {
        return MDetalle.Listar(IdFactura);
    }

    public List<Map<String, String>> Listar() {
        String sql = "select * from factura order by 1;";
        return Conexion.getInstance().executeSQLResultList(sql);
    }

    public Map<String, String> ComboBox() {
        Map<String, String> factura = new LinkedHashMap<>();

        String sql = "select * from factura order by 1;";
        List<Map<String, String>> rows = Conexion.getInstance().executeSQLResultList(sql);

        for (int i = 0; i < rows.size(); i++) {
            Map<String, String> row = rows.get(i);

            String fullname = row.get("nit") + " " + row.get("nombre");
            factura.put(row.get("nro"), fullname);
        }
        return factura;
    }

    public String getIDFactura() {
        String sql = "select * from factura order by nro desc limit 1;";
        List<Map<String, String>> rows = Conexion.getInstance().executeSQLResultList(sql);
        Map<String, String> row = rows.get(0);
        String id = row.get("nro");

        return id;
    }
}
