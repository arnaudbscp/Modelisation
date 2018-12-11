package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

import src.exception.WrongHeaderException;
import src.modele.LoadFile;

/**
 * Classe de test sur la récupération du nombre de points et de faces de la figure.
 * @author Valentin
 *
 */
class TestNb {

	/**
	 * Méthode testant la récupération du nombre de points de la figure dans l'en-tête du fichier.
	 * @throws IOException
	 * @throws WrongHeaderException 
	 */
	@Test 
	public void testNbPoints() throws IOException, WrongHeaderException {
		String model = "ply\n" + 
				"format ascii 1.0\n" + 
				"element vertex 855\n" + 
				"property float32 x\n" + 
				"property float32 y\n" + 
				"property float32 z\n" + 
				"element face 1689\n" + 
				"property list uint8 int32 vertex_indices\n" + 
				"end_header";
		LoadFile lf = LoadFile.getInstance();
		lf.lireStream(new StringReader(model));
		assertEquals(855, lf.getPoints().length);
	}

	/**
	 * Méthode testant la récupération du nombre de faces de la figure dans l'en-tête du fichier.
	 * @throws IOException
	 * @throws WrongHeaderException 
	 */
	@Test
	public void testNbFaces() throws IOException, WrongHeaderException {
		String model = "ply\n" + 
				"format ascii 1.0\n" + 
				"element vertex 855\n" + 
				"property float32 x\n" + 
				"property float32 y\n" + 
				"property float32 z\n" + 
				"element face 1689\n" + 
				"property list uint8 int32 vertex_indices\n" + 
				"end_header";
		LoadFile lf = LoadFile.getInstance();
		lf.lireStream(new StringReader(model));
		assertEquals(1689, lf.getFaces().length);
	}
}
