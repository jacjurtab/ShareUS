SELECT
	vi.id AS id_viaje, us.nombre AS conductor, us.valoracion AS valoracion_conductor, 
	ub.nombre AS origen, ub1.nombre AS destino, vi.fecha, vi.hora, vi.num_pasajeros, vi.max_plazas
FROM 
	viajes vi INNER JOIN
	usuarios us ON vi.conductor = us.id INNER JOIN
	ubicaciones ub ON vi.origen = ub.id INNER JOIN
	ubicaciones ub1 ON vi.destino = ub1.id