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
public class MCategoria implements IModelo {

    private int id;
    private String descripcion;

    public MCategoria(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public MCategoria() {
        this(0, "");
    }

    @Override
    public void SetID(String id) {
        this.id = Integer.parseInt(id);
    }

    @Override
    public void SetDato(Map<String, String> dato) {
        this.id = Integer.parseInt(dato.getOrDefault("id", "0"));
        this.descripcion = dato.getOrDefault("descripcion", "");
    }

    @Override
    public boolean Registrar() {
        boolean proccessed = false;
        String sql = "insert into categoria (descripcion) "
                + "values (?);";

        try {
            PreparedStatement statement = Conexion.getInstance().getConnection().prepareStatement(sql);
            statement.setString(1, this.descripcion);

            proccessed = Conexion.getInstance().executeSQL(statement);
        } catch (SQLException e) {
        }

        return proccessed;
    }

    @Override
    public boolean Modificar() {
        boolean proccessed = false;
        String sql = "update categoria "
                + "set descripcion=? "
                + "where id=?;";

        try {
            PreparedStatement statement = Conexion.getInstance().getConnection().prepareStatement(sql);
            statement.setString(1, this.descripcion);
            statement.setInt(2, this.id);

            proccessed = Conexion.getInstance().executeSQL(statement);
        } catch (SQLException e) {
        }

        return proccessed;
    }

    @Override
    public boolean Eliminar() {
        String sql = "delete from categoria where id=?;";
        return Conexion.getInstance().delete(sql, this.id);
    }

    @Override
    public Map<String, String> BuscarID() {
        return BuscarColumnaValor("id", this.id);
    }

    @Override
    public Map<String, String> BuscarColumnaValor(String columnName, Object columnValue) {
        String sql = "select * from categoria where %s='%s' limit 1;";
        sql = String.format(sql, columnName, columnValue);

        List<Map<String, String>> resultado = Conexion.getInstance().executeSQLResultList(sql);
        return !resultado.isEmpty() ? resultado.get(0) : null;
    }

    @Override
    public List<Map<String, String>> Listar() {
        String sql = "select * from categoria order by 1;";
        return Conexion.getInstance().executeSQLResultList(sql);
    }

    @Override
    public Map<String, String> ComboBox() {
        Map<String, String> categoria = new LinkedHashMap<>();

        String sql = "select * from categoria order by 1;";
        List<Map<String, String>> rows = Conexion.getInstance().executeSQLResultList(sql);

        for (int i = 0; i < rows.size(); i++) {
            Map<String, String> row = rows.get(i);

            categoria.put(row.get("id"), row.get("descripcion"));
        }
        return categoria;
    }
    
    public Map<String,String> Seleccionado(int idCategoria){
        Map<String, String> categoria = new LinkedHashMap<>();

        String sql = "select * from categoria where id='"+idCategoria+"' order by 1;";
        List<Map<String, String>> rows = Conexion.getInstance().executeSQLResultList(sql);

        for (int i = 0; i < rows.size(); i++) {
            Map<String, String> row = rows.get(i);

            categoria.put(row.get("id"), row.get("descripcion"));
        }
        return categoria;
    }

}
