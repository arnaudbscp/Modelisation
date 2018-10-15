package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

import chargement.LoadFile;

class TestNb {

	@Test 
	public void testNbPoints() throws IOException {
		LoadFile f = new LoadFile(new File("ressources/dolphin.ply"));
		String model = "ply\n" + 
				"format ascii 1.0\n" + 
				"element vertex 855\n" + 
				"property float32 x\n" + 
				"property float32 y\n" + 
				"property float32 z\n" + 
				"element face 1689\n" + 
				"property list uint8 int32 vertex_indices\n" + 
				"end_header";
		f.lireStream(new StringReader(model));
		assertEquals(855, f.getPoints().length);
	}

	@Test
	public void testNbFaces() throws IOException {
		LoadFile f = new LoadFile(new File("ressources/dolphin.ply"));
		String model = "ply\n" + 
				"format ascii 1.0\n" + 
				"element vertex 855\n" + 
				"property float32 x\n" + 
				"property float32 y\n" + 
				"property float32 z\n" + 
				"element face 1689\n" + 
				"property list uint8 int32 vertex_indices\n" + 
				"end_header";
		f.lireStream(new StringReader(model));
		assertEquals(1689, f.getFaces().length);
	}
}
