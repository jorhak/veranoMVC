/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mvc.modelo;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import utilidades.Conexion;

/**
 *
 * @author jorhak
 */
public class MDetalle {
    private int ID;
    private int NroFactura;
    private int Cantidad;
    private double Precio;
    private int CodigoProducto;
    
    public void SetDato(Map<String, String> dato) {
        ID = Integer.parseInt(dato.getOrDefault("id", "0"));
        NroFactura = Integer.parseInt(dato.getOrDefault("nroFactura", "0"));
        Cantidad = Integer.parseInt(dato.getOrDefault("cantidad", "0"));
        Precio = Double.parseDouble(dato.getOrDefault("precio", "0.0"));
        CodigoProducto = Integer.parseInt(dato.getOrDefault("codigoProducto", "0"));
    }

    public void SetFacturaNro(String NroFactura) {
        this.NroFactura = Integer.parseInt(NroFactura);
    }

    public Map<String, String> Registrar() {
        boolean proccessed = false;
        String sqlInsert = "insert into detalle (nro, cantidad, precio, codigoProducto) "
                + "values (?,?,?,?);";

        String sqlUpdate = "update detalle "
                + "set cantidad=?, precio=? "
                + "where nro=?;";

        String sql = ID != 0 ? sqlUpdate : sqlInsert;

        try {
            PreparedStatement statement = Conexion.getInstance().getConnection().prepareStatement(sql);
            statement.setInt(1, NroFactura);
            statement.setInt(2, Cantidad);
            statement.setDouble(3, Precio);
            statement.setInt(4, CodigoProducto);
;

            if (ID != 0) {
                statement.setInt(1, Cantidad);
                statement.setDouble(2, Precio);
                statement.setInt(3, NroFactura);
                
            }

            proccessed = Conexion.getInstance().executeSQL(statement);
        } catch (SQLException e) {
        }

        return proccessed ? Buscar("id", ID, "nro", NroFactura) : null;
    }

    public static boolean Eliminar(String nroFactura, String idDetalle) {
        String sql = "delete from detalle where id=? and nro=?;";
        return Conexion.getInstance().delete(sql, idDetalle, nroFactura);
    }

    public static Map<String, String> Buscar(String columnName0, Object value0, String columnName1, Object value1) {
        String sql = "select * from detalle where %s='%s' and %s='%s' limit 1;";
        sql = String.format(sql, columnName0, value0, columnName1, value1);

        List<Map<String, String>> resultado = Conexion.getInstance().executeSQLResultList(sql);
        return !resultado.isEmpty() ? resultado.get(0) : null;
    }

    public static List<Map<String, String>> Listar(String NroFactura) {
        String sql = "select * from detalle where nro='%s';";
        sql = String.format(sql, NroFactura);
        return Conexion.getInstance().executeSQLResultList(sql);
    }
}
