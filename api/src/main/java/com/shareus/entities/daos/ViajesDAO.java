package com.shareus.entities.daos;

import javax.sql.DataSource;

import org.postgresql.ds.PGSimpleDataSource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shareus.entities.Pasajero;
import com.shareus.entities.Viaje;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


public class ViajesDAO implements ViajesDAOInterface {
	
	private DataSource ds = null;
	
	public ViajesDAO(DataSource ds) {
		super();
		this.ds = ds;
	}
	
	@Override
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
	
	@Override
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
	
	@Override
	public Viaje obtenerViajeId(int idViaje) {
		Connection conn;
		Viaje viaje = null;
		try {
			conn = ds.getConnection();
			String sql = "SELECT\n"
					+ "	vi.id AS id_viaje, us.nombre AS conductor, us.valoracion AS nota_conductor, us2.id AS pasajero_id, us2.nombre AS pasajero,"
					+ "	ub.nombre AS origen, ub1.nombre AS destino, vi.fecha, vi.num_pasajeros, vi.max_plazas"
					+ " FROM "
					+ "	viajes vi INNER JOIN"
					+ "	usuarios us ON vi.conductor = us.id INNER JOIN"
					+ "	ubicaciones ub ON vi.origen = ub.id INNER JOIN"
					+ "	ubicaciones ub1 ON vi.destino = ub1.id INNER JOIN"
					+ "	pasajeros pa ON vi.id = pa.viaje INNER JOIN"
					+ "	usuarios us2 ON pa.pasajero = us2.id"
					+ " WHERE"
					+ "	vi.id =?";
			PreparedStatement st = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			st.setInt(1, idViaje);			
			ResultSet rs = st.executeQuery();			
			if (rs.next()) {
				viaje = new Viaje(rs.getInt("id_viaje"), rs.getString("conductor"), rs.getString("origen"),
											rs.getString("destino"), rs.getTimestamp("fecha"),
											rs.getInt("num_pasajeros"), rs.getInt("max_plazas"), null, rs.getFloat("nota_conductor"));
				rs.previous();
				List<Pasajero> pasajeros = new ArrayList<>();
				while(rs.next()) {
					pasajeros.add(new Pasajero(rs.getInt("pasajero_id"), rs.getString("pasajero")));
				}
				viaje.setPasajeros(pasajeros);
			}
			rs.close();
			st.close();
			conn.close();	
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error en ViajesDAO (obtenerViajeId): " + e.getMessage());
		}
		return viaje;
	}	
	
	@Override
	public List<Viaje> obtenerViajes(boolean disponibles) {
		Connection conn;
		List<Viaje> viajes = new ArrayList<>();
		try {
			conn = ds.getConnection();
				
			String sql = "SELECT"
					+ "	vi.id AS id_viaje, us.nombre AS conductor, us.valoracion AS nota_conductor, "
					+ "	ub.nombre AS origen, ub1.nombre AS destino, vi.fecha, vi.num_pasajeros, vi.max_plazas"
					+ " FROM "
					+ "	viajes vi INNER JOIN"
					+ "	usuarios us ON vi.conductor = us.id INNER JOIN"
					+ "	ubicaciones ub ON vi.origen = ub.id INNER JOIN"
					+ "	ubicaciones ub1 ON vi.destino = ub1.id"
					+ " WHERE"
					+ "	vi.num_pasajeros < vi.max_plazas";
			PreparedStatement st = conn.prepareStatement(sql);	
			ResultSet rs = st.executeQuery();	
			if (disponibles == false) {
				while (rs.next()) {
					Viaje viaje = new Viaje(rs.getInt("id_viaje"), rs.getString("conductor"), rs.getString("origen"),
												rs.getString("destino"), rs.getTimestamp("fecha"),
												rs.getInt("num_pasajeros"), rs.getInt("max_plazas"), null, rs.getFloat("nota_conductor"));
					viajes.add(viaje);
				}
			} else {
				while (rs.next()) {
					if (rs.getTimestamp("fecha").after(Timestamp.from(Instant.now()))){
						Viaje viaje = new Viaje(rs.getInt("id_viaje"), rs.getString("conductor"), rs.getString("origen"),
													rs.getString("destino"), rs.getTimestamp("fecha"),
													rs.getInt("num_pasajeros"), rs.getInt("max_plazas"), null, rs.getFloat("nota_conductor"));
						viajes.add(viaje);
					}
				}
			
				rs.close();
				st.close();	
				conn.close();
			}
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error en ViajesDAO (obtenerViajes): " + e.getMessage());
		}
		return viajes;
	}

	public static void main(String[] args) throws SQLException, JsonProcessingException {
	    PGSimpleDataSource ds = new PGSimpleDataSource();
	    ObjectMapper mapper = new ObjectMapper();
	    ds.setUrl("jdbc:postgresql://api.share-us.tech:5434/shareus");
	    ds.setUser("dam");
	    ds.setPassword("damshareus");
	    ViajesDAO viajesDAO = new ViajesDAO(ds);
	    //System.out.println("OBTENER VIAJES CONDUCTOR\n");
	    //System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(viajesDAO.obtenerViajesConductor(2)));
	    //System.out.println("\nOBTENER VIAJES PASAJERO\n");	    
	    //System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(viajesDAO.obtenerViajesPasajero(2)));
	    //System.out.println("\nOBTENER VIAJE ID\n");	    
	    //System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(viajesDAO.obtenerViajeId(1)));
	    System.out.println("\nOBTENER VIAJES\n");	    
	    System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(viajesDAO.obtenerViajes(false)));
	    System.out.println("\nOBTENER VIAJES DISPONIBLES\n");	    
	    System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(viajesDAO.obtenerViajes(true)));
	}

}