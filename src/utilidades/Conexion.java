/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jorhak
 */
public class Conexion {

    private final String host = "192.168.0.21";
    private final String user = "root";
    private final String pass = "perpetuaeternidad";
    private final String database = "arquitecturaMVC";
    private final String port = "3322";
    private final String driver = "com.mysql.cj.jdbc.Driver";
    private final String path = "jdbc:mysql://" + host + ":" + port + "/" + database;

    private Connection con;

    public void connect() {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(path, user, pass);
            System.out.println("Conexion exitosa");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Conexion FAIL!!!!!:::::" + e);
        }
    }

    private static Conexion instance;

    private Conexion() {
        connect();
    }

    public static Conexion getInstance() {
        if (instance == null) {
            instance = new Conexion();
        }

        return instance;
    }

    public Connection getConnection() {
        if (con == null) {
            connect();
        }
        return con;
    }

    public void closeConnection() {
        try {
            con.close();
        } catch (SQLException ex) {
        }
        con = null;
    }

    public boolean executeSQL(String sql) {
        try ( Statement consulta = con.createStatement();) {
            consulta.executeUpdate(sql);
        } catch (SQLException ex) {
            con = null;
            connect();
            return false;
        }
        return true;
    }

    public List<Map<String, String>> executeSQLResultList(String sql) {
        List<Map<String, String>> data = new LinkedList<>();
        try ( PreparedStatement consulta = con.prepareStatement(sql);  ResultSet resultado = consulta.executeQuery();) {
            String columnNames[] = columnName(resultado);
            while (resultado.next()) {
                Map<String, String> map = new HashMap<>();
                for (int index = 0; index < columnNames.length; index++) {
                    map.put(columnNames[index], resultado.getObject(index + 1).toString());
                }
                data.add(map);
            }
        } catch (Exception e) {
            con = null;
            connect();
        }
        return data;
    }

    public boolean executeSQL(PreparedStatement preparedStatement) {
        int state = 0;
        try {
            state = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            con = null;
            connect();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
            }
        }
        return state != 0;
    }

    public boolean delete(String sql, Object... primaryKey) {
        boolean proccessed = false;
        try ( PreparedStatement statement = Conexion.getInstance().getConnection().prepareStatement(sql);) {
            for (int index = 0; index < primaryKey.length; index++) {
                statement.setString(index + 1, primaryKey[index].toString());
            }
            proccessed = Conexion.getInstance().executeSQL(statement);
        } catch (SQLException e) {
            con = null;
        }
        return proccessed;
    }

    public List<Map<String, Object>> executeSQLResultList(PreparedStatement preparedStatement) {
        List<Map<String, Object>> data = new LinkedList<>();
        try ( ResultSet resultado = preparedStatement.executeQuery();) {
            String columnNames[] = columnName(resultado);
            while (resultado.next()) {
                Map<String, Object> map = new HashMap<>();
                for (int index = 0; index < columnNames.length; index++) {
                    map.put(columnNames[index], resultado.getObject(index + 1));
                }
                data.add(map);
            }
        } catch (Exception e) {
            con = null;
            connect();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
            }
        }
        return data;
    }

    private String[] columnName(ResultSet resultSet) {
        String columns[] = null;
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            columns = new String[columnCount];
            for (int index = 0; index < columnCount; index++) {
                columns[index] = metaData.getColumnName(index + 1);
            }
        } catch (SQLException e) {
            con = null;
            connect();
        }
        return columns;
    }
}
