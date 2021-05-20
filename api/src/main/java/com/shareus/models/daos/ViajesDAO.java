package com.shareus.models.daos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.shareus.models.Pasajero;
import com.shareus.models.Viaje;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


public class ViajesDAO implements ViajesDAOInterface {

	private final DataSource ds;

	public ViajesDAO(DataSource ds) {
		super();
		this.ds = ds;
	}

	public static void main(String[] args) throws SQLException, JsonProcessingException {
		PGSimpleDataSource ds = new PGSimpleDataSource();
		ds.setUrl("jdbc:postgresql://api.share-us.tech:5434/shareus");
		ds.setUser("dam");
		ds.setPassword("damshareus");
		ViajesDAO viajes = new ViajesDAO(ds);
		ObjectMapper mapper = new ObjectMapper();
		SimpleBeanPropertyFilter theFilter = SimpleBeanPropertyFilter.serializeAllExcept("nota_conductor");
		FilterProvider filters = new SimpleFilterProvider().addFilter("myFilter", theFilter);

		Viaje viaje = viajes.obtenerViajeId(1);
		String dtoAsString = mapper.writer(filters).writeValueAsString(viaje);

		System.out.println(dtoAsString);
		/*System.out.println(mapper.writerWithDefaultPrettyPrinter()
				.writeValueAsString(viajesDAO.insertarViajeConductor(3, 3, 12, new Timestamp(1621670400000L), 2)));*/
		//System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(viajesDAO.obtenerValoraciones(1, 3)));
		//System.out.println("OBTENER VIAJES CONDUCTOR\n");
		//System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(viajesDAO.obtenerViajesConductor(2)));
		//System.out.println("\nOBTENER VIAJES PASAJERO\n");
		//System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(viajesDAO.obtenerViajesPasajero(2)));
		//System.out.println("\nOBTENER VIAJE ID\n");
		//System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(viajesDAO.obtenerViajeId(1)));
		//System.out.println("\nOBTENER VIAJES\n");
		//System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(viajesDAO.obtenerViajes(false)));
		//System.out.println("\nOBTENER VIAJES DISPONIBLES\n");
		//System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(viajesDAO.obtenerViajes(true)));
	}

	@Override
	public List<Viaje> obtenerViajesConductor(int conductor, Boolean vencidos) {
		Connection conn;
		List<Viaje> viajes = new ArrayList<>();
		try {
			conn = ds.getConnection();
			String sql = "SELECT"
					+ "	vi.id AS id_viaje, us.nombre AS conductor, us.valoracion AS nota_conductor, ub.nombre AS origen, ub1.nombre AS destino,"
					+ "	vi.fecha, vi.num_pasajeros, vi.max_plazas, vi.precio"
					+ " FROM"
					+ "	viajes vi INNER JOIN"
					+ "	usuarios us ON vi.conductor = us.id INNER JOIN"
					+ "	ubicaciones ub ON vi.origen = ub.id INNER JOIN"
					+ "	ubicaciones ub1 ON vi.destino = ub1.id"
					+ " WHERE"
					+ "	vi.conductor = ? ORDER BY vi.fecha";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, conductor);
			ResultSet rs = st.executeQuery();
			if (vencidos != null) {
				if (!vencidos) { //Viajes  no vencidos
					while (rs.next()) {
						if (rs.getTimestamp("fecha").after(Timestamp.from(Instant.now()))) {
							Viaje viaje = new Viaje(rs.getInt("id_viaje"), conductor, rs.getString("conductor"), rs.getString("origen"),
									rs.getString("destino"), rs.getTimestamp("fecha").getTime(),
									rs.getInt("num_pasajeros"), rs.getInt("max_plazas"), null, rs.getFloat("nota_conductor"), rs.getFloat("precio"));
							viajes.add(viaje);
						}
					}
				}else { //Viajes vencidos
					while (rs.next()) {
						if (rs.getTimestamp("fecha").before(Timestamp.from(Instant.now()))) {
							Viaje viaje = new Viaje(rs.getInt("id_viaje"), conductor, rs.getString("conductor"), rs.getString("origen"),
									rs.getString("destino"), rs.getTimestamp("fecha").getTime(),
									rs.getInt("num_pasajeros"), rs.getInt("max_plazas"), null, rs.getFloat("nota_conductor"), rs.getFloat("precio"));
							viajes.add(viaje);
						}
					}
				}
			}else { //Viajes vencidos y no vencidos
				while (rs.next()) {
					Viaje viaje = new Viaje(rs.getInt("id_viaje"), conductor, rs.getString("conductor"), rs.getString("origen"),
							rs.getString("destino"), rs.getTimestamp("fecha").getTime(),
							rs.getInt("num_pasajeros"), rs.getInt("max_plazas"), null, rs.getFloat("nota_conductor"), rs.getFloat("precio"));
					viajes.add(viaje);
				}
			}
			rs.close();
			st.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("Error en ViajesDAO (obtenerViajesConductor): " + e.getMessage());
		}
		return viajes;
	}

	@Override
	public List<Viaje> obtenerViajesPasajero(int pasajero, Boolean vencidos) {
		Connection conn;
		List<Viaje> viajes = new ArrayList<>();
		try {
			conn = ds.getConnection();
			String sql = "SELECT"
					+ "	vi.id AS id_viaje, us.nombre AS conductor, us.id AS idConductor, us.valoracion AS nota_conductor, us2.nombre AS pasajero, ub.nombre AS origen, ub1.nombre AS destino, "
					+ "	vi.fecha, vi.num_pasajeros, vi.max_plazas, vi.precio"
					+ " FROM"
					+ "	viajes vi INNER JOIN"
					+ "	usuarios us ON vi.conductor = us.id INNER JOIN"
					+ "	ubicaciones ub ON vi.origen = ub.id INNER JOIN"
					+ "	ubicaciones ub1 ON vi.destino = ub1.id INNER JOIN"
					+ "	pasajeros pa ON vi.id = pa.viaje INNER JOIN"
					+ "	usuarios us2 ON pa.pasajero = us2.id"
					+ " WHERE"
					+ "	pa.pasajero = ? ORDER BY vi.fecha";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, pasajero);
			ResultSet rs = st.executeQuery();
			if(vencidos != null) {
				if (!vencidos) { //Viajes no vencidos
					while (rs.next()) {
						if (rs.getTimestamp("fecha").after(Timestamp.from(Instant.now()))) {
							Viaje viaje = new Viaje(rs.getInt("id_viaje"), rs.getInt("idConductor"), rs.getString("conductor"), rs.getString("origen"),
									rs.getString("destino"), rs.getTimestamp("fecha").getTime(),
									rs.getInt("num_pasajeros"), rs.getInt("max_plazas"), null, rs.getFloat("nota_conductor"), rs.getFloat("precio"));
							viajes.add(viaje);
						}
					}
				}else { // Viajes vencidos
					while (rs.next()) {
						if (rs.getTimestamp("fecha").before(Timestamp.from(Instant.now()))) {
							Viaje viaje = new Viaje(rs.getInt("id_viaje"), rs.getInt("idConductor"), rs.getString("conductor"), rs.getString("origen"),
									rs.getString("destino"), rs.getTimestamp("fecha").getTime(),
									rs.getInt("num_pasajeros"), rs.getInt("max_plazas"), null, rs.getFloat("nota_conductor"), rs.getFloat("precio"));
							viajes.add(viaje);
						}
					}
				}
			}else { //viajes vencidos y no vencidos
				while (rs.next()) {
					Viaje viaje = new Viaje(rs.getInt("id_viaje"), rs.getInt("idConductor"), rs.getString("conductor"), rs.getString("origen"),
							rs.getString("destino"), rs.getTimestamp("fecha").getTime(),
							rs.getInt("num_pasajeros"), rs.getInt("max_plazas"), null, rs.getFloat("nota_conductor"), rs.getFloat("precio"));
					viajes.add(viaje);
				}
			}
			rs.close();
			st.close();
			conn.close();
		} catch (SQLException e) {
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
			String sql = "SELECT"
					+ "	vi.id AS id_viaje, us.nombre AS conductor, us.id AS idConductor, us.valoracion AS nota_conductor, us2.id AS pasajero_id, us2.nombre AS pasajero,"
					+ "	ub.nombre AS origen, ub1.nombre AS destino, vi.fecha, vi.num_pasajeros, vi.max_plazas, vi.precio"
					+ " FROM "
					+ "	viajes vi INNER JOIN"
					+ "	ubicaciones ub ON vi.origen = ub.id INNER JOIN"
					+ "	ubicaciones ub1 ON vi.destino = ub1.id INNER JOIN"
					+ "	usuarios us ON vi.conductor = us.id FULL OUTER JOIN"
					+ "	pasajeros pa ON vi.id = pa.viaje FULL OUTER JOIN"
					+ "	usuarios us2 ON pa.pasajero = us2.id"
					+ " WHERE"
					+ "	vi.id =?";
			PreparedStatement st = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			st.setInt(1, idViaje);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				viaje = new Viaje(rs.getInt("id_viaje"), rs.getInt("idConductor"), rs.getString("conductor"), rs.getString("origen"),
						rs.getString("destino"), rs.getTimestamp("fecha").getTime(),
						rs.getInt("num_pasajeros"), rs.getInt("max_plazas"), null, rs.getFloat("nota_conductor"), rs.getFloat("precio"));
				rs.previous();
				List<Pasajero> pasajeros = new ArrayList<>();
				while (rs.next()) {
					pasajeros.add(new Pasajero(rs.getInt("pasajero_id"), rs.getString("pasajero")));
				}
				viaje.setPasajeros(pasajeros);
			}
			rs.close();
			st.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("Error en ViajesDAO (obtenerViajeId): " + e.getMessage());
		}
		return viaje;
	}

	@Override
	public List<Viaje> obtenerViajes(Boolean disponibles) {
		Connection conn;
		List<Viaje> viajes = new ArrayList<>();
		try {
			conn = ds.getConnection();

			String sql = "SELECT"
					+ "	vi.id AS id_viaje, us.nombre AS conductor, us.id AS idConductor, us.valoracion AS nota_conductor, "
					+ "	ub.nombre AS origen, ub1.nombre AS destino, vi.fecha, vi.num_pasajeros, vi.max_plazas, vi.precio"
					+ " FROM "
					+ "	viajes vi INNER JOIN"
					+ "	usuarios us ON vi.conductor = us.id INNER JOIN"
					+ "	ubicaciones ub ON vi.origen = ub.id INNER JOIN"
					+ "	ubicaciones ub1 ON vi.destino = ub1.id"
					+ (disponibles!=null && disponibles?" WHERE vi.num_pasajeros < vi.max_plazas":"")
					+ " ORDER BY vi.fecha";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			if(disponibles != null) {
				if (disponibles) {
					while (rs.next()) {
						if (rs.getTimestamp("fecha").after(Timestamp.from(Instant.now()))) {
							Viaje viaje = new Viaje(rs.getInt("id_viaje"), rs.getInt("idConductor"), rs.getString("conductor"), rs.getString("origen"),
									rs.getString("destino"), rs.getTimestamp("fecha").getTime(),
									rs.getInt("num_pasajeros"), rs.getInt("max_plazas"), null, rs.getFloat("nota_conductor"), rs.getFloat("precio"));
							viajes.add(viaje);
						}
					}
				} else {
					while (rs.next()) {
						if (rs.getTimestamp("fecha").before(Timestamp.from(Instant.now()))) {
							Viaje viaje = new Viaje(rs.getInt("id_viaje"), rs.getInt("idConductor"), rs.getString("conductor"), rs.getString("origen"),
									rs.getString("destino"), rs.getTimestamp("fecha").getTime(),
									rs.getInt("num_pasajeros"), rs.getInt("max_plazas"), null, rs.getFloat("nota_conductor"), rs.getFloat("precio"));
							viajes.add(viaje);
						}
					}
				}
			} else {
				while (rs.next()) {
					Viaje viaje = new Viaje(rs.getInt("id_viaje"), rs.getInt("idConductor"), rs.getString("conductor"), rs.getString("origen"),
							rs.getString("destino"), rs.getTimestamp("fecha").getTime(),
							rs.getInt("num_pasajeros"), rs.getInt("max_plazas"), null, rs.getFloat("nota_conductor"), rs.getFloat("precio"));
					viajes.add(viaje);
				}

			}
			rs.close();
			st.close();
			conn.close();
		} catch (SQLException e) {
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

			if (col_afectadas == 1) resultado = true;
			st.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("Error en ViajesDAO (eliminarViaje): " + e.getMessage());
		}

		return resultado;
	}

	@Override
	public boolean insertarPasajeroViaje(int viaje, int pasajero) {
		Connection conn;
		boolean resultado = false;
		boolean deshacer = false;
		try {
			conn = ds.getConnection();
			String[] sql = {
					"INSERT INTO pasajeros (viaje, pasajero) VALUES (?, ?)",
					"UPDATE viajes SET num_pasajeros = (SELECT count(*) FROM pasajeros WHERE pasajeros.viaje = ?) WHERE viajes.id = ?"
			};
			PreparedStatement[] st = {conn.prepareStatement(sql[0]), conn.prepareStatement(sql[1])};

			st[0].setInt(1, viaje);
			st[0].setInt(2, pasajero);
			st[1].setInt(1, viaje);
			st[1].setInt(2, viaje);

			if (st[0].executeUpdate() == 1) {
				deshacer = true;
				if(st[1].executeUpdate() == 1) {
					resultado = true;
					deshacer = false;
				}
			}

			st[0].close();
			st[1].close();
			conn.close();
		} catch (SQLException throwables) {
			try {
				if(deshacer) {
					conn = ds.getConnection();
					PreparedStatement st_undo = conn.prepareStatement("DELETE FROM pasajeros WHERE pasajeros.viaje = ? AND pasajeros.pasajero = ?");
					st_undo.setInt(1, viaje);
					st_undo.setInt(2, pasajero);
					st_undo.executeUpdate();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("Error en ViajesDAO (insertarPasajeroViaje): " + throwables.getMessage());
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
					"UPDATE viajes SET num_pasajeros = (SELECT count(*) FROM pasajeros WHERE pasajeros.viaje = ?) WHERE viajes.id = ?"
			};
			PreparedStatement[] st = {conn.prepareStatement(sql[0]), conn.prepareStatement(sql[1])};

			st[0].setInt(1, viaje);
			st[0].setInt(2, pasajero);
			st[1].setInt(1, viaje);
			st[1].setInt(2, viaje);

			if (st[0].executeUpdate() == 1) {
				st[1].executeUpdate();
				resultado = true;
			}

			st[0].close();
			st[1].close();
			conn.close();
		} catch (SQLException throwables) {
			System.out.println("Error en ViajesDAO (eliminarPasajeroViaje): " + throwables.getMessage());
		}

		return resultado;
	}

	@Override
	public boolean insertarViajeConductor(Integer conductor, Integer origen, Integer destino, Timestamp fecha, Integer max_plazas, Float precio) {
		Connection conn;
		boolean resultado = false;
		try {
			conn = ds.getConnection();
			String sql = "INSERT INTO viajes (conductor, origen, destino, fecha, max_plazas, precio) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, conductor);
			st.setInt(2, origen);
			st.setInt(3, destino);
			st.setTimestamp(4, fecha);
			st.setInt(5, max_plazas);
			st.setFloat(6, precio);

			int col_afectadas = st.executeUpdate();

			if (col_afectadas == 1) resultado = true;

			st.close();
			conn.close();
		} catch (SQLException throwables) {
			System.out.println("Error en ViajesDAO (insertarViajeConductor): " + throwables.getMessage());
		}

		return resultado;
	}

	public List<Viaje> obtenerViajesUbi(String origen, String destino, Boolean disponibles){
		Connection conn;
		List<Viaje> viajes = new ArrayList<>();
		try {
			conn = ds.getConnection();

			String sql = "SELECT"
					+ "	vi.id AS id_viaje, us.nombre AS conductor, us.id AS idConductor, us.valoracion AS nota_conductor, "
					+ "	ub.nombre AS origen, ub1.nombre AS destino, vi.fecha, vi.num_pasajeros, vi.max_plazas, vi.precio"
					+ " FROM "
					+ "	viajes vi INNER JOIN"
					+ "	usuarios us ON vi.conductor = us.id INNER JOIN"
					+ "	ubicaciones ub ON vi.origen = ub.id INNER JOIN"
					+ "	ubicaciones ub1 ON vi.destino = ub1.id"
					+ " WHERE ub.nombre=? AND ub1.nombre=?"
					+ (disponibles!=null && disponibles?" AND (vi.num_pasajeros<vi.max_plazas)":"")
					+ " ORDER BY vi.fecha";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, origen);
			st.setString(2, destino);
			ResultSet rs = st.executeQuery();
			if(disponibles != null) {
				if (disponibles) {
					while (rs.next()) {
						if (rs.getTimestamp("fecha").after(Timestamp.from(Instant.now()))) {
							Viaje viaje = new Viaje(rs.getInt("id_viaje"), rs.getInt("idConductor"), rs.getString("conductor"), rs.getString("origen"),
									rs.getString("destino"), rs.getTimestamp("fecha").getTime(),
									rs.getInt("num_pasajeros"), rs.getInt("max_plazas"), null, rs.getFloat("nota_conductor"), rs.getFloat("precio"));
							viajes.add(viaje);
						}
					}
				} else {
					while (rs.next()) {
						if (rs.getTimestamp("fecha").before(Timestamp.from(Instant.now()))) {
							Viaje viaje = new Viaje(rs.getInt("id_viaje"), rs.getInt("idConductor"), rs.getString("conductor"), rs.getString("origen"),
									rs.getString("destino"), rs.getTimestamp("fecha").getTime(),
									rs.getInt("num_pasajeros"), rs.getInt("max_plazas"), null, rs.getFloat("nota_conductor"), rs.getFloat("precio"));
							viajes.add(viaje);
						}
					}
				}
			} else {
				while (rs.next()) {
					Viaje viaje = new Viaje(rs.getInt("id_viaje"), rs.getInt("idConductor"), rs.getString("conductor"), rs.getString("origen"),
							rs.getString("destino"), rs.getTimestamp("fecha").getTime(),
							rs.getInt("num_pasajeros"), rs.getInt("max_plazas"), null, rs.getFloat("nota_conductor"), rs.getFloat("precio"));
					viajes.add(viaje);
				}

			}
			rs.close();
			st.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("Error en ViajesDAO (obtenerViajes): " + e.getMessage());
		}
		return viajes;

	}
}