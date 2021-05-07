UPDATE viajes SET num_pasajeros = num_pasajeros - 1 WHERE viajes.id = 1; -- Viaje id = 1, Pasajero id = 2;
DELETE FROM pasajeros WHERE pasajeros.viaje = 1 AND pasajeros.pasajero = 2;