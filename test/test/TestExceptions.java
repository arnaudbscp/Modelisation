package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

import mecanique.Initialisation;
import mecanique.LoadFile;

import exception.WrongFaceLineFormatException;
import exception.WrongHeaderException;
import exception.WrongPointLineFormatException;

/**
 * Classe de test des différentes exceptions qui peuvent survenir.
 * @author Valentin
 *
 */
class TestExceptions {

	/**
	 * Méthode testant le bon renvoi d'une exception WrongHeaderException lorsque l'en-tête du fichier selectionné n'est pas
	 * écrit dans le bon format.
	 * @throws IOException
	 */
	@Test
	void testWrongHeader() throws IOException {
		String model = "ply\n" + 
				"format ascii 1.0\n" + 
				"comment by genartv\n" +
				"element vertex 855\n" + 
				"property float32 x\n" + 
				"property float32 y\n" + 
				"property float32 z\n" + 
				"element face 1689\n" + 
				"property list uint8 int32 vertex_indices\n" + 
				"end_header";
		LoadFile f = new LoadFile();
		assertThrows(WrongHeaderException.class, () -> f.lireStream(new StringReader(model)) );
	}

	/**
	 * Méthode testant le bon renvoi d'une exception WrongPointLineException lorsqu'une ligne désignant un point n'est pas écrite 
	 * dans le bon format.
	 * @throws IOException
	 */
	@Test
	void testWrongPointLine() throws IOException {
		File f = new File("ressources/dolphin.ply");
		LoadFile lf = new LoadFile(f);
		String model = "ply\n" + 
				"format ascii 1.0\n" + 
				"comment by genartv\n" +
				"element vertex 855\n" + 
				"property float32 x\n" + 
				"property float32 y\n" + 
				"property float32 z\n" + 
				"element face 1689\n" + 
				"property list uint8 int32 vertex_indices\n" + 
				"end_header\n" +
				"13.6601e-5 0 548.364 ";
		assertThrows(WrongPointLineFormatException.class, () -> {
			lf.lireStream(new StringReader(model));
			@SuppressWarnings("unused")
			Initialisation i = new Initialisation(f);
		});
	}

	/**
	 * Méthode testant le bon renvoi d'une exception WrongPointFaceException lorsqu'une ligne désignant une face n'est pas écrite 
	 * dans le bon format.
	 * @throws IOException
	 */
	@Test
	void testWrongFaceLine() throws IOException {
		File f = new File("ressources/dolphin.ply");
		LoadFile lf = new LoadFile(f);
		String model = "ply\n" + 
				"format ascii 1.0\n" + 
				"comment by genartv\n" +
				"element vertex 855\n" + 
				"property float32 x\n" + 
				"property float32 y\n" + 
				"property float32 z\n" + 
				"element face 1689\n" + 
				"property list uint8 int32 vertex_indices\n" + 
				"end_header\n" +
				"13.6601 0 548.364 \n" +
				"-24.9399 0 513.564 \n" + 
				"-19.6099 -8.13 512.804 \n" +
				"3 0 1a 2 ";
		assertThrows(WrongFaceLineFormatException.class, () -> {
			lf.lireStream(new StringReader(model));
			@SuppressWarnings("unused")
			Initialisation i = new Initialisation(f);
		});
	}
}