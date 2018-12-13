package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

import src.donnees.Point;
import src.modele.Initialisation;

/**
 * Classe testant si un fichier est correctement interprété avec des commentaires dedans.
 * @author Valentin
 *
 */
class TestCommentaires {

	/**
	 * Méthode testant si un fichier est correctement interprété malgré les commentaires dedans.
	 * @throws IOException
	 */
	@Test
	void testCommentaires() throws IOException {
		String model = "//Commentaire au début\n" + 
				"ply\n" + 
				"format ascii 1.0\n" + 
				"element vertex 6\n" + 
				"property float32 x\n" + 
				"//Commentaire au milieu\n" +
				"property float32 y\n" + 
				"property float32 z\n" + 
				"element face 3\n" + 
				"property list uint8 int32 vertex_indices\n" + 
				"//Commentaire au milieu\n" +
				"end_header\n" +
				"13.6601 0 548.364 \n" +
				"-24.9399 0 513.564 \n" + 
				"-19.6099 -8.13 512.804 \n" +
				"14.02 -0.101 2.89e-2 \n" +
				"//Commentaire au milieu\n" +
				"//Commentaire au milieu\n" +
				"-24.336 0 17.04 \n" +
				"1.12 -6.14 87.14 \n" +
				"3 0 1 2 \n" +
				"3 3 4 5 \n" +
				"3 0 1 5 \n" +
				"//Commentaire à la fin";
		Initialisation i = Initialisation.getInstance();
		i.doInit(new StringReader(model));
		assertEquals(i.getLoadFile().getPoints().length, 6);
		assertEquals(i.getLoadFile().getFaces().length, 3);
		assertEquals((float) i.getLoadFile().getPoints()[4].getZ(),(float) 17.04);
		assertEquals((float) i.getLoadFile().getFaces()[2].getPoints()[1].getX(), (float) -24.9399);
	}
}
