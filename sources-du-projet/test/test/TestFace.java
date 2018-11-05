package test.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

import src.mecanique.LoadFile;
import src.exception.MissingFaceLineException;
import src.exception.MissingPointLineException;
import src.exception.TooMuchFaceLineException;
import src.exception.TooMuchPointLineException;
import src.exception.WrongFaceLineFormatException;
import src.exception.WrongPointLineFormatException;

/**
 * Classe de test sur une face. On teste si une face est créée à partir des bons points.
 * @author Valentin
 *
 */
class TestFace {
	
	/**
	 * Méthode de test sur une face. On teste si une face est créée à partir des bons points.
	 * @throws IOException
	 */
	@Test
	void test() throws IOException {
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
		LoadFile lf = new LoadFile();
		lf.readStream(new StringReader(model));
		try {
			lf.creerPoints();
			lf.creerFaces();
		} catch (WrongPointLineFormatException | WrongFaceLineFormatException | TooMuchPointLineException | MissingFaceLineException | TooMuchFaceLineException | MissingPointLineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(lf.getFaces()[0].getPoints()[1].equals(lf.getPoints()[1]));
		assertEquals((float)-19.6099, (float)lf.getFaces()[0].getPoints()[2].getX());
	}
}