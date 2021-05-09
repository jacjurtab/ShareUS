package com.shareus.entities.daos;

import javax.sql.DataSource;

import org.postgresql.ds.PGSimpleDataSource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shareus.entities.Viaje;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ViajesDAO implements ViajesDAOInterface {
	
	private final DataSource ds;
	
	public ViajesDAO(DataSource ds) {
		super();
		this.ds = ds;
	}

	public List<Viaje> obtenerViajesConductor(int conductor) {
		Connection conn;
		List<Viaje> viajes = new ArrayList<>();
		try {
			conn = ds.getConnection();
			String sql = "SELECT"
					+ "	vi.id AS id_viaje, us.nombre AS conductor, ub.nombre AS origen, ub1.nombre AS destino,"
					+ "	vi.fecha, vi.num_pasajeros, vi.max_plazas"
					+ " FROM"
					+ "	viajes vi INNER JOIN"
					+ "	usuarios us ON vi.conductor = us.id INNER JOIN"
					+ "	ubicaciones ub ON vi.origen = ub.id INNER JOIN"
					+ "	ubicaciones ub1 ON vi.destino = ub1.id"
					+ " WHERE"
					+ "	vi.conductor = ?";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, conductor);			
			ResultSet rs = st.executeQuery();			
			while (rs.next()) {
				Viaje viaje = new Viaje(rs.getInt("id_viaje"), rs.getString("conductor"), rs.getString("origen"),
											rs.getString("destino"), rs.getTimestamp("fecha"),
											rs.getInt("num_pasajeros"), rs.getInt("max_plazas"), null, -1);
				viajes.add(viaje);
			}
			rs.close();
			st.close();
			conn.close();	
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error en ViajesDAO (obtenerViajesConductor): " + e.getMessage());
		}
		return viajes;		
	}

	public List<Viaje> obtenerViajesPasajero(int pasajero) {
		Connection conn;
		List<Viaje> viajes = new ArrayList<>();
		try {
			conn = ds.getConnection();
			String sql = "SELECT"
					+ "	vi.id AS id_viaje, us.nombre AS conductor, us2.nombre AS pasajero, ub.nombre AS origen, ub1.nombre AS destino, "
					+ "	vi.fecha, vi.num_pasajeros, vi.max_plazas"
					+ " FROM"
					+ "	viajes vi INNER JOIN"
					+ "	usuarios us ON vi.conductor = us.id INNER JOIN"
					+ "	ubicaciones ub ON vi.origen = ub.id INNER JOIN"
					+ "	ubicaciones ub1 ON vi.destino = ub1.id INNER JOIN"
					+ "	pasajeros pa ON vi.id = pa.viaje INNER JOIN"
					+ "	usuarios us2 ON pa.pasajero = us2.id"
					+ " WHERE"
					+ "	pa.pasajero = ?";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, pasajero);			
			ResultSet rs = st.executeQuery();			
			while (rs.next()) {
				Viaje viaje = new Viaje(rs.getInt("id_viaje"), rs.getString("conductor"), rs.getString("origen"),
											rs.getString("destino"), rs.getTimestamp("fecha"),
											rs.getInt("num_pasajeros"), rs.getInt("max_plazas"), null, -1);
				viajes.add(viaje);
			}
			rs.close();
			st.close();
			conn.close();	
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error en ViajesDAO (obtenerViajesPasajero): " + e.getMessage());
		}
		return viajes;		
	}
	
	public void obtenerViajeId() {
		
		
	}
	
	public void obteneViajesDisponibles(){
		
	}

	public boolean eliminarViaje(int id) {
		Connection conn;
		boolean resultado = false;
		try {
			conn = ds.getConnection();
			String sql = "DELETE FROM viajes WHERE viajes.id = ?";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);
			int col_afectadas = st.executeUpdate();

			if(col_afectadas == 1) resultado = true;
			st.close();
			conn.close();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		return resultado;
	}

	public boolean eliminarPasajeroViaje(int id, int pasajero) {
		Connection conn;
		boolean resultado = false;
		try {
			conn = ds.getConnection();
			String[] sql = {
					"UPDATE viajes SET num_pasajeros = num_pasajeros - 1 WHERE viajes.id = ?",
					"DELETE FROM pasajeros WHERE pasajeros.viaje = ? AND pasajeros.pasajero = ?"
			};
			PreparedStatement[] st = {conn.prepareStatement(sql[0]), conn.prepareStatement(sql[1])};

			st[0].setInt(1, id);
			st.addBatch("UPDATE viajes SET num_pasajeros = num_pasajeros - 1 WHERE viajes.id = " + id);
			st.addBatch("");
			st.set
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);
			st.setInt(2, id);


		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		return resultado;
	}


	
	public static void main(String[] args) throws SQLException, JsonProcessingException {
	    PGSimpleDataSource ds = new PGSimpleDataSource();
	    ObjectMapper mapper = new ObjectMapper();
	    ds.setUrl("jdbc:postgresql://api.share-us.tech:5434/shareus");
	    ds.setUser("dam");
	    ds.setPassword("damshareus");
	    ViajesDAO viajesDAO = new ViajesDAO(ds);
	    System.out.println("OBTENER VIAJES CONDUCTOR\n");
	    System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(viajesDAO.obtenerViajesConductor(2)));
	    System.out.println("\nOBTENER VIAJES PASAJERO\n");	    
	    System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(viajesDAO.obtenerViajesPasajero(2)));
	}
}