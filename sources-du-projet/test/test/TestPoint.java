package test.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

import src.mecanique.LoadFile;
import src.exception.MissingPointLineException;
import src.exception.WrongPointLineFormatException;

/**
 * Classe de test sur un point. On teste si un point est créé à partir des bonnes coordonnées.
 * @author Valentin
 *
 */
class TestPoint {

	/**
	 * Méthode de test sur un point. On teste si un point est créé à partir des bonnes coordonnées.
	 * @author Valentin
	 *
	 */
	@Test
	void test() throws IOException {
		String model = "ply\n" + 
				"format ascii 1.0\n" + 
				"element vertex 1\n" + 
				"property float32 x\n" + 
				"property float32 y\n" + 
				"property float32 z\n" + 
				"element face 0\n" + 
				"property list uint8 int32 vertex_indices\n" + 
				"end_header\n" +
				"13.6601 0 548.364 ";
		LoadFile lf = new LoadFile();
		lf.readStream(new StringReader(model));
		try {
			lf.creerPoints();
		} catch (WrongPointLineFormatException | MissingPointLineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals((float)13.6601, (float)lf.getPoints()[0].getX());
		assertEquals((float)0, (float)lf.getPoints()[0].getY());
		assertEquals((float)548.364, (float)lf.getPoints()[0].getZ());
	}
}
