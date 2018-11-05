package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import src.donnees.Face;
import src.donnees.Point;

/**
 * Classe de test sur le calcul du barycentre d'une face triangulaire.
 * @author Valentin
 *
 */
class TestCalculGravite {

	/**
	 * Méthode testant si le résultat du calcul du barycentre d'une face triangulaire est correct.
	 */
	@Test
	void test() {
		Point p1 = new Point(1, 2, 3);
		Point p2 = new Point(4, 5, 6);
		Point p3 = new Point(7, 8, 9);
		Face face = new Face(p1, p2, p3);
		assertEquals(4.0, face.calculCentreGravite().getX());
		assertEquals(5.0, face.calculCentreGravite().getY());
		assertEquals(6.0, face.calculCentreGravite().getZ());
	}
}
