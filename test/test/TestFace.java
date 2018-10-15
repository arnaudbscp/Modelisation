package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

import chargement.LoadFile;
import exception.WrongFaceLineFormatException;
import exception.WrongPointLineFormatException;

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
		LoadFile f = new LoadFile(new File("ressources/dolphin.ply"));
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
		f.lireStream(new StringReader(model));
		try {
			f.CreerPoints();
			f.CreerFaces();
		} catch (WrongPointLineFormatException | WrongFaceLineFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(f.getFaces()[0].getPoints()[1].equals(f.getPoints()[1]));
		assertEquals((float)-19.6099, (float)f.getFaces()[0].getPoints()[2].getX());
	}
}
