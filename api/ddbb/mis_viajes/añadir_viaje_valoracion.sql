INSERT INTO 
	valoraciones (id, valorado, valorador, viaje, nota) 
VALUES (7, 2, 4, 5, 5); -- Valorado.id = 2, Valorador.id = 4, viaje.id = 5, nota = 5;

UPDATE 
	usuarios 
SET 
	valoracion = (SELECT SUM(nota)/COUNT(nota) AS valoracion FROM valoraciones va WHERE va.valorado = 2) -- Valorado.id = 3;
WHERE 
	usuarios.id = 2; -- Valorado.id = 2;
