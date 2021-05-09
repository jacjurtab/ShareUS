package com.shareus.entities.daos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shareus.entities.Ubicacion;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UbicacionesDAO {

    private final DataSource ds;

    public UbicacionesDAO(DataSource ds) {
        super();
        this.ds = ds;
    }

    /**
     * Borra una nota que coincida con (id,usuario)
     * @param disponible
     * @param tipo
     * @return lista de ubicaciones de tipo y con atributo disponible.
     */
    public List<Ubicacion> obtenerUbicaciones(boolean disponible, int tipo) {
        List<Ubicacion> ubicaciones = new ArrayList<>();
        Connection conn;
        try {
            conn = ds.getConnection();
            String sql = "SELECT * FROM ubicaciones ub WHERE ub.disponible = ? AND ub.tipo = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setBoolean(1, disponible);
            st.setString(2, Integer.toString(tipo));

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Ubicacion ubicacion = new Ubicacion(rs.getInt("id"), rs.getString("nombre"),
                        rs.getFloat("latitud"), rs.getFloat("longitud"),
                        Integer.parseInt(rs.getString("tipo")), rs.getBoolean("disponible"));

                ubicaciones.add(ubicacion);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error en UbicacionesDAO: " + e.getMessage());
        }

        return ubicaciones;
    }

    public static void main(String[] args) throws SQLException, JsonProcessingException {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setUrl("jdbc:postgresql://api.share-us.tech:5434/shareus");
        ds.setUser("dam");
        ds.setPassword("damshareus");
        UbicacionesDAO ubicacionesDAO = new UbicacionesDAO(ds);

        ObjectMapper mapper = new ObjectMapper();

        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ubicacionesDAO.obtenerUbicaciones(true, 0)));
    }

}
