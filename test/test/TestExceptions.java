package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

import chargement.Initialisation;
import chargement.LoadFile;
import exception.WrongFaceLineFormatException;
import exception.WrongHeaderException;
import exception.WrongPointLineFormatException;

class TestExceptions {

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