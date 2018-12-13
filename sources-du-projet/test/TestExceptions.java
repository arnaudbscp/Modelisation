package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

import src.exception.MissingFaceLineException;
import src.exception.MissingPointLineException;
import src.exception.TooMuchFaceLineException;
import src.exception.TooMuchPointLineException;
import src.exception.WrongFaceLineFormatException;
import src.exception.WrongHeaderException;
import src.exception.WrongPointLineFormatException;
import src.modele.LoadFile;

/**
 * Classe de test sur les différentes exceptions pouvant survenir durant l'execution du programme.
 * @author genartv
 *
 */
class TestExceptions {

	/**
	 * Méthode testant si l'exception gérant le format de l'en-tête du fichier est bien soulevée.
	 * @throws IOException
	 */
	@Test
	void testWrongHeaderFormat() throws IOException {
		String model = "ply\n" + 
				"format ascii 1.0\n" + 
				"ligne en trop" + //ligne en trop
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
		LoadFile lf = LoadFile.getInstance();
		assertThrows(WrongHeaderException.class, () -> {
			lf.lireStream(new StringReader(model));
		});
	}
	
	/**
	 * Méthode testant si l'exception gérant le format des lignes de point du fichier est bien soulevée.
	 * @throws IOException
	 * @throws WrongHeaderException
	 */
	@Test
	void testWrongPointLineFormatException() throws IOException, WrongHeaderException {
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
				"-24.9399 8 0 513.564 \n" + //ligne avec une erreur
				"-19.6099 -8.13 512.804 \n" +
				"3 0 1 2 ";
		LoadFile lf = LoadFile.getInstance();
		lf.lireStream(new StringReader(model));
		assertThrows(WrongPointLineFormatException.class, () -> {
			lf.creerPoints();
		});
	}
	
	/**
	 * Méthode testant si l'exception gérant le nombre de ligne de points dans le cas où il y en a pas assez est bien soulevée.
	 * @throws IOException
	 * @throws WrongHeaderException
	 */
	@Test
	void testMissingPointLineException() throws IOException, WrongHeaderException {
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
				"-24.9399 0 513.564 \n" + //il manque une ligne de point
				"3 0 1 2 ";
		LoadFile lf = LoadFile.getInstance();
		lf.lireStream(new StringReader(model));
		assertThrows(MissingPointLineException.class, () -> {
			lf.creerPoints();
		});
	}

	/**
	 * Méthode testant si l'exception gérant le format des lignes de face du fichier est bien soulevée.
	 * @throws IOException
	 * @throws WrongHeaderException
	 * @throws WrongPointLineFormatException
	 * @throws MissingPointLineException
	 */
	@Test
	void testWrongFaceLineFormatException() throws IOException, WrongHeaderException, WrongPointLineFormatException, MissingPointLineException {
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
				"3 0 1 2 5 "; //ligne avec une erreur
		LoadFile lf = LoadFile.getInstance();
		lf.lireStream(new StringReader(model));
		lf.creerPoints();
		assertThrows(WrongFaceLineFormatException.class, () -> {
			lf.creerFaces();
		});
	}
	
	/**
	 * Méthode testant si l'exception gérant le nombre de ligne de points dans le cas où il y en a trop est bien soulevée.
	 * @throws IOException
	 * @throws WrongHeaderException
	 * @throws WrongPointLineFormatException
	 * @throws MissingPointLineException
	 */
	@Test
	void testTooMuchPointLineException() throws IOException, WrongHeaderException, WrongPointLineFormatException, MissingPointLineException {
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
				"-19.6099 -8.13 512.804 \n" + // ligne de point en trop
				"3 0 1 2 ";
		LoadFile lf = LoadFile.getInstance();
		lf.lireStream(new StringReader(model));
		lf.creerPoints();
		assertThrows(TooMuchPointLineException.class, () -> {
			lf.creerFaces();
		});
	}
	
	/**
	 * Méthode testant si l'exception gérant le nombre de ligne de faces dans le cas où il y en a pas assez est bien soulevée.
	 * @throws IOException
	 * @throws WrongHeaderException
	 * @throws WrongPointLineFormatException
	 * @throws MissingPointLineException
	 */
	@Test
	void testMissingFaceLineException() throws IOException, WrongHeaderException, WrongPointLineFormatException, MissingPointLineException {
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
				"-19.6099 -8.13 512.804 "; // il manque une ligne de face
		LoadFile lf = LoadFile.getInstance();
		lf.lireStream(new StringReader(model));
		lf.creerPoints();
		assertThrows(MissingFaceLineException.class, () -> {
			lf.creerFaces();
		});
	}
	
	/**
	 * Méthode testant si l'exception gérant le nombre de ligne de faces dans le cas où il y en a trop est bien soulevée.
	 * @throws IOException
	 * @throws WrongHeaderException
	 * @throws WrongPointLineFormatException
	 * @throws MissingPointLineException
	 */
	@Test
	void testTooMuchFaceLineException() throws IOException, WrongHeaderException, WrongPointLineFormatException, MissingPointLineException {
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
				"3 0 1 2 \n" +
				"3 0 1 2 "; //ligne de face en trop
		LoadFile lf = LoadFile.getInstance();
		lf.lireStream(new StringReader(model));
		lf.creerPoints();
		assertThrows(TooMuchFaceLineException.class, () -> {
			lf.creerFaces();
		});
	}
}
