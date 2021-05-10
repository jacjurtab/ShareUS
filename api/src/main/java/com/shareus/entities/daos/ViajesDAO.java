package com.shareus.entities.daos;

import javax.sql.DataSource;

import com.shareus.entities.Valoracion;
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
	
	private final DataSource ds;
	
	public ViajesDAO(DataSource ds) {
		super();
		this.ds = ds;
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
			while(rs.next()) {
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
  
  @Override
	public boolean eliminarViaje(int viaje) {
		Connection conn;
		boolean resultado = false;
		try {
			conn = ds.getConnection();
			String sql = "DELETE FROM viajes WHERE viajes.id = ?";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, viaje);
			int col_afectadas = st.executeUpdate();

			if(col_afectadas == 1) resultado = true;
			st.close();
			conn.close();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		return resultado;
	}
  
  @Override
	public boolean insertarPasajeroViaje(int viaje, int pasajero) {
		Connection conn;
		boolean resultado = false;
		try {
			conn = ds.getConnection();
			String[] sql = {
					"INSERT INTO pasajeros (viaje, pasajero) VALUES (?, ?)",
					"UPDATE viajes SET num_pasajeros = num_pasajeros + 1 WHERE viajes.id = ?"
			};
			PreparedStatement[] st = {conn.prepareStatement(sql[0]), conn.prepareStatement(sql[1])};

			st[0].setInt(1, viaje);
			st[0].setInt(2, pasajero);
			st[1].setInt(1, viaje);

			if(st[0].executeUpdate() == 1) {
				st[1].executeUpdate();
				resultado = true;
			}

			st[0].close();
			st[1].close();
			conn.close();
		} catch (SQLException throwables) {
			try {
				conn = ds.getConnection();
				PreparedStatement st_undo = conn.prepareStatement("DELETE FROM pasajeros WHERE pasajeros.viaje = ? AND pasajeros.pasajero = ?");
				st_undo.setInt(1, viaje);
				st_undo.setInt(2, pasajero);
				st_undo.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("Error en ViajesDAO: " + throwables.getMessage());
		}

		return resultado;
	}
  
  @Override
	public boolean eliminarPasajeroViaje(int viaje, int pasajero) {
		Connection conn;
		boolean resultado = false;
		try {
			conn = ds.getConnection();
			String[] sql = {
					"DELETE FROM pasajeros WHERE pasajeros.viaje = ? AND pasajeros.pasajero = ?",
					"UPDATE viajes SET num_pasajeros = num_pasajeros - 1 WHERE viajes.id = ?"
			};
			PreparedStatement[] st = {conn.prepareStatement(sql[0]), conn.prepareStatement(sql[1])};

			st[0].setInt(1, viaje);
			st[0].setInt(2, pasajero);
			st[1].setInt(1, viaje);

			if(st[0].executeUpdate() == 1) {
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


			if(st[0].executeUpdate() == 1) {
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
	public boolean insertarViajeConductor(int conductor, int origen, int destino, Timestamp fecha, int max_plazas) {
		Connection conn;
		boolean resultado = false;
		try {
			conn = ds.getConnection();
			String sql = "INSERT INTO viajes (conductor, origen, destino, fecha, max_plazas) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, conductor);
			st.setInt(2, origen);
			st.setInt(3, destino);
			st.setTimestamp(4, fecha);
			st.setInt(5, max_plazas);

			int col_afectadas = st.executeUpdate();

			if(col_afectadas == 1) resultado = true;

			st.close();
			conn.close();
		} catch (SQLException throwables) {
			System.out.println("Error en ViajesDAO: " + throwables.getMessage());
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
//		System.out.println(mapper.writerWithDefaultPrettyPrinter()
//				.writeValueAsString(viajesDAO.insertarViajeConductor(3, 3, 12, new Timestamp(1621670400000L), 2)));
	    System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(viajesDAO.obtenerValoraciones(1, 3)));
	}
}