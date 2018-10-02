package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import chargement.Face;
import chargement.Point;

class TestCalculGravité {

	@Test
	void test() {
		Point p1 = new Point(1,2,3);
		Point p2 = new Point(4,5,6);
		Point p3 = new Point(7,8,9);
		Face face = new Face(p1,p2,p3);
		assertEquals((float) 4, face.calculCentreGravite().getX());
		assertEquals((float) 5, face.calculCentreGravite().getY());
		assertEquals((float) 6, face.calculCentreGravite().getZ());
	}

}
