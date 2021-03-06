package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

import src.exception.WrongHeaderException;
import src.modele.Initialisation;

/**
 * Classe de test sur une face. On teste si une face est créée à partir des bons points.
 * @author Valentin
 *
 */
class TestFace {
	
	/**
	 * Méthode de test sur une face. On teste si une face est créée à partir des bons points.
	 * @throws IOException
	 * @throws WrongHeaderException 
	 */
	@Test
	void test() throws IOException, WrongHeaderException {
		String model = "ply\n" + 
				"format ascii 1.0\n" + 
				"element vertex 3\n" + 
				"property float32 x\n" + 
				"property float32 y\n" + 
				"property float32 z\n" + 
				"element face 1\n" + 
				"property list uint8 int32 vertex_indices\n" + 
				"end_header\n" +
				"13.6601 0 548.364 \n" +
				"-24.9399 0 513.564 \n" + 
				"-19.6099 -8.13 512.804 \n" +
				"3 0 1 2 ";
		Initialisation i = Initialisation.getInstance();
		i.doInit(new StringReader(model));
		assertTrue(i.getLoadFile().getFaces()[0].getPoints()[1].equals(i.getLoadFile().getPoints()[1]));
		assertEquals((float)-19.6099, (float)i.getLoadFile().getFaces()[0].getPoints()[2].getX());
	}
}