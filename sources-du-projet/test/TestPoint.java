package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

import src.exception.MissingPointLineException;
import src.exception.WrongHeaderException;
import src.exception.WrongPointLineFormatException;
import src.modele.LoadFile;

/**
 * Classe de test sur un point. On teste si un point est créé à partir des bonnes coordonnées.
 * @author Valentin
 *
 */
class TestPoint {

	/**
	 * Méthode de test sur un point. On teste si un point est créé à partir des bonnes coordonnées.
	 * @author Valentin
	 * @throws WrongHeaderException 
	 *
	 */
	@Test
	void testCreerPoint() throws IOException, WrongHeaderException {
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
		LoadFile lf = LoadFile.getInstance();
		lf.lireStream(new StringReader(model));
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
	
	/**
	 * Méthode de test sur un point. On teste si un point est créé à partir des bonnes coordonnées, avec des coordonnées contenant des exposants.
	 * @author Valentin
	 * @throws WrongHeaderException 
	 *
	 */
	@Test
	void testCreerPointExposant() throws IOException, WrongHeaderException {
		String model = "ply\n" + 
				"format ascii 1.0\n" + 
				"element vertex 1\n" + 
				"property float32 x\n" + 
				"property float32 y\n" + 
				"property float32 z\n" + 
				"element face 0\n" + 
				"property list uint8 int32 vertex_indices\n" + 
				"end_header\n" +
				"13.6601 0.12e-1 548.364 ";
		LoadFile lf = LoadFile.getInstance();
		lf.lireStream(new StringReader(model));
		try {
			lf.creerPoints();
		} catch (WrongPointLineFormatException | MissingPointLineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals((float)13.6601, (float)lf.getPoints()[0].getX());
		assertEquals((float)0.012, (float)lf.getPoints()[0].getY());
		assertEquals((float)548.364, (float)lf.getPoints()[0].getZ());
	}
}
