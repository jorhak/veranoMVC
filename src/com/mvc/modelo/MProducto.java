/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mvc.modelo;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import utilidades.Conexion;
import utilidades.IModelo;

/**
 *
 * @author jorhak
 */
public class MProducto implements IModelo{
    private int Codigo;
    private String Nombre;
    private double Precio;
    private int IdCategoria;

    public MProducto(int codigo, String nombre, double precio, int idCategoria) {
        this.Codigo = codigo;
        this.Nombre = nombre;
        this.Precio = precio;
        this.IdCategoria = idCategoria;

    }

    public MProducto() {
        this(0, "", 0.0, 0);
    }

    @Override
    public void SetID(String codigo) {
        this.Codigo = Integer.parseInt(codigo);
    }

    @Override
    public void SetDato(Map<String, String> dato) {
        this.Codigo = Integer.parseInt(dato.getOrDefault("codigo", "0"));
        this.Nombre = dato.getOrDefault("nombre", "");
        this.Precio = Double.parseDouble(dato.getOrDefault("precio", "0.0"));
        this.IdCategoria = Integer.parseInt(dato.getOrDefault("idCategoria", "0"));
    }

    @Override
    public boolean Registrar() {
        boolean proccessed = false;
        String sql = "insert into producto (nombre, precio, idCategoria) "
                + "values (?,?,?);";

        try {
            PreparedStatement statement = Conexion.getInstance().getConnection().prepareStatement(sql);
            statement.setString(1, this.Nombre);
            statement.setDouble(2, this.Precio);
            statement.setInt(3, this.IdCategoria);

            proccessed = Conexion.getInstance().executeSQL(statement);
        } catch (SQLException e) {
        }

        return proccessed;
    }

    @Override
    public boolean Modificar() {
        boolean proccessed = false;
        String sql = "update producto "
                + "set nombre=?, precio=? "
                + "where codigo=?;";

        try {
            PreparedStatement statement = Conexion.getInstance().getConnection().prepareStatement(sql);
            statement.setString(1, this.Nombre);
            statement.setDouble(2, this.Precio);
            statement.setInt(3, this.Codigo);

            proccessed = Conexion.getInstance().executeSQL(statement);
        } catch (SQLException e) {
        }

        return proccessed;
    }

    @Override
    public boolean Eliminar() {
        String sql = "delete from producto where codigo=?;";
        return Conexion.getInstance().delete(sql, this.Codigo);
    }

    @Override
    public Map<String, String> BuscarID() {
        return BuscarColumnaValor("codigo", this.Codigo);
    }

    @Override
    public Map<String, String> BuscarColumnaValor(String columnName, Object columnValue) {
        String sql = "select * from producto where %s='%s' limit 1;";
        sql = String.format(sql, columnName, columnValue);

        List<Map<String, String>> resultado = Conexion.getInstance().executeSQLResultList(sql);
        return !resultado.isEmpty() ? resultado.get(0) : null;
    }

    @Override
    public List<Map<String, String>> Listar() {
        String sql = "select * from producto order by 1;";
        return Conexion.getInstance().executeSQLResultList(sql);
    }

    @Override
    public Map<String, String> ComboBox() {
        Map<String, String> producto = new LinkedHashMap<>();

        String sql = "select * from producto order by 1;";
        List<Map<String, String>> rows = Conexion.getInstance().executeSQLResultList(sql);

        for (int i = 0; i < rows.size(); i++) {
            Map<String, String> row = rows.get(i);

            String fullname = row.get("nombre");
            producto.put(row.get("codigo"), fullname);
        }
        return producto;
    }
    
    public Map<String,String> Seleccionado(int codigoProducto){
        Map<String, String> socio = new LinkedHashMap<>();

        String sql = "select * from socio where codigo='"+codigoProducto+"' order by 1;";
        List<Map<String, String>> rows = Conexion.getInstance().executeSQLResultList(sql);

        for (int i = 0; i < rows.size(); i++) {
            Map<String, String> row = rows.get(i);

            String fullname = row.get("nombre");
            socio.put(row.get("codigo"), fullname);
        }
        return socio;
    }
}
