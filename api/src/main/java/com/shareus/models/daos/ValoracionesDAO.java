package com.shareus.models.daos;

import com.shareus.models.Valoracion;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ValoracionesDAO implements ValoracionesDAOInterface {

    private final DataSource ds;

    public ValoracionesDAO(DataSource ds) {
        super();
        this.ds = ds;
    }

    @Override
    public boolean insertarViajeValoracion(int viaje, int valorador, int valorado, int nota) {
        Connection conn;
        boolean resultado = false;
        try {
            conn = ds.getConnection();
            String[] sql = {
                    "INSERT INTO valoraciones (valorado, valorador, viaje, nota) VALUES (?, ?, ?, ?);",
                    "UPDATE usuarios SET valoracion = (SELECT AVG(nota) AS valoracion FROM valoraciones va WHERE va.valorado = ?) " +
                            "WHERE usuarios.id = ?"
            };
            PreparedStatement[] st = {conn.prepareStatement(sql[0]), conn.prepareStatement(sql[1])};

            st[0].setInt(1, valorado);
            st[0].setInt(2, valorador);
            st[0].setInt(3, viaje);
            st[0].setInt(4, nota);

            st[1].setInt(1, valorado);
            st[1].setInt(2, valorado);


            if (st[0].executeUpdate() == 1) {
                st[1].executeUpdate();
                resultado = true;
            }

            st[0].close();
            st[1].close();
            conn.close();
        } catch (SQLException throwables) {
            System.out.println("Error en ViajesDAO: " + throwables.getMessage());
        }

        return resultado;
    }

    @Override
    public List<Valoracion> obtenerValoraciones(int viaje, int valorado) {
        Connection conn;
        List<Valoracion> valoraciones = new ArrayList<>();
        try {
            conn = ds.getConnection();
            String sql = "SELECT va.id, vi.id AS viaje_id, us.nombre AS valorador, va.comentario, va.nota AS nota " +
                    "FROM viajes vi INNER JOIN valoraciones va ON vi.id = va.viaje INNER JOIN usuarios us ON va.valorador = us.id " +
                    "INNER JOIN usuarios us2 ON va.valorado = us2.id WHERE vi.id = ? AND va.valorado = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, viaje);
            st.setInt(2, valorado);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Valoracion valoracion = new Valoracion(rs.getInt("id"), rs.getInt("viaje_id"),
                        rs.getString("valorador"), rs.getString("comentario"), rs.getInt("nota"));
                valoraciones.add(valoracion);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException throwables) {
            System.out.println("Error en ViajesDAO: " + throwables.getMessage());
        }

        return valoraciones;
    }

}
