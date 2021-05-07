UPDATE viajes SET num_pasajeros = num_pasajeros + 1 WHERE viajes.id = 1; -- Viaje id = 1, Pasajero id = 2;
INSERT INTO pasajeros (viaje, pasajero) VALUES (1, 2);