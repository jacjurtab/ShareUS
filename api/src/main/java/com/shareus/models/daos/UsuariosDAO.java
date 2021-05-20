package com.shareus.models.daos;

import com.shareus.models.Usuario;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuariosDAO {

    private final DataSource ds;

    public UsuariosDAO(DataSource ds) {
        super();
        this.ds = ds;
    }

    public Usuario obtenerUsuario(int id) {
        Connection conn;
        Usuario usuario = null;
        try {
            conn = ds.getConnection();
            String sql = "SELECT " +
                    "us.id AS userId, us.uvus AS userName, us.nombre AS nombre, us.primer_apellido AS primerApellido," +
                    "us.segundo_apellido AS segundoApellido, us.email AS email, us.telefono AS telefono," +
                    "us.valoracion AS valoracion " +
                    "FROM usuarios us WHERE us.id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                usuario = new Usuario(rs.getInt("userId"), rs.getString("userName"),
                        rs.getString("nombre"), rs.getString("primerapellido"),
                        rs.getString("segundoapellido"), rs.getString("email"),
                        rs.getString("telefono"), rs.getFloat("valoracion"));
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error en UsuariosDAO (obtenerUsuario): " + e.getMessage());
        }
        return usuario;
    }

    public Usuario obtenerUsuario(String email) {
        Connection conn;
        Usuario usuario = null;
        try {
            conn = ds.getConnection();
            String sql = "SELECT " +
                    "us.id AS userId, us.uvus AS userName, us.email AS email " +
                    "FROM usuarios us WHERE us.email = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                usuario = new Usuario(rs.getInt("userId"), rs.getString("userName"), rs.getString("email"));
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error en UsuariosDAO (obtenerUsuario): " + e.getMessage());
        }
        return usuario;
    }

    public boolean inicializarUsuario(String email) {
        Connection conn;
        boolean resultado = false;
        try {
            conn = ds.getConnection();
            String sql = "INSERT INTO usuarios (uvus, email) VALUES (?, ?)";
            PreparedStatement st = conn.prepareStatement(sql);

            st.setString(1, email.split("@")[0]);
            st.setString(2, email);

            if (st.executeUpdate() == 1) {
                resultado = true;
            }

            st.close();
            conn.close();
        } catch (SQLException throwables) {
            System.out.println("Error en UsuariosDAO (inicializarUsuario): " + throwables.getMessage());
        }

        return resultado;
    }

    public boolean completarUsuario(int id, String nombre, String primer_apellido, String segundo_apellido, String telefono) {
        Connection conn;
        boolean resultado = false;
        try {
            conn = ds.getConnection();
            String sql = "UPDATE usuarios SET nombre = ?, primer_apellido = ?, segundo_apellido = ?, telefono = ? " +
                    "WHERE usuarios.id = ?";
            PreparedStatement st = conn.prepareStatement(sql);

            st.setString(1, nombre);
            st.setString(2, primer_apellido);
            st.setString(3, segundo_apellido);
            st.setString(4, telefono);
            st.setInt(5, id);

            if (st.executeUpdate() == 1) {
                resultado = true;
            }

            st.close();
            conn.close();
        } catch (SQLException throwables) {
            System.out.println("Error en UsuariosDAO (completarUsuario): " + throwables.getMessage());
        }

        return resultado;
    }




}
