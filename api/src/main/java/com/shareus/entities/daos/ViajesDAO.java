package com.shareus.entities.daos;

import javax.sql.DataSource;

import org.postgresql.ds.PGSimpleDataSource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shareus.entities.Viaje;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ViajesDAO {
	
	private DataSource ds = null;
	
	public ViajesDAO(DataSource ds) {
		super();
		this.ds = ds;
	}
	
	/**
	 * Obtiene todos los viajes realizados por un usuario como conductor 
	 * @param id del usuario que actúa como conductor del viaje
	 * @return lista de todos los viajes realizados por un usuario
	 */
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
	
	/**
	 * Obtiene todos los viajes de un usuario como pasajero
	 * @param id del usuario 
	 * @return lista de todos los viajes en los que está inscrito el usuario
	 */
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