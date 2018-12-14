package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import src.modele.Face;
import src.modele.Point;
import src.modele.QuickSort;

/**
 * Classe de test du tri QuickSort
 * @author Valentin
 *
 */
class TestQuickSort {

	/**
	 * Test du tri QuickSort
	 */
	@Test
	void test() {
		Face[] tabFaces = new Face[] {
				new Face(new Point(5, 5, 5), new Point(5, 5, 5), new Point(5, 5, 5)),
				new Face(new Point(3, 3, 3), new Point(3, 3, 3), new Point(3, 3, 3)),
				new Face(new Point(2, 2, 2), new Point(2, 2, 2), new Point(2, 2, 2)),
				new Face(new Point(4, 4, 4), new Point(4, 4, 4), new Point(4, 4, 4)),
				new Face(new Point(1, 1, 1), new Point(1, 1, 1), new Point(1, 1, 1)),
				new Face(new Point(6, 6, 6), new Point(6, 6, 6), new Point(6, 6, 6)),
				new Face(new Point(0, 0, 0), new Point(0, 0, 0), new Point(0, 0, 0)),
				new Face(new Point(8, 8, 8), new Point(8, 8, 8), new Point(8, 8, 8)),
				new Face(new Point(7, 7, 7), new Point(7, 7, 7), new Point(7, 7, 7)),
				new Face(new Point(9, 9, 9), new Point(9, 9, 9), new Point(9, 9, 9)),
		};
		Face[] tabFacesTriees = new Face[] {
				new Face(new Point(0, 0, 0), new Point(0, 0, 0), new Point(0, 0, 0)),
				new Face(new Point(1, 1, 1), new Point(1, 1, 1), new Point(1, 1, 1)),
				new Face(new Point(2, 2, 2), new Point(2, 2, 2), new Point(2, 2, 2)),
				new Face(new Point(3, 3, 3), new Point(3, 3, 3), new Point(3, 3, 3)),
				new Face(new Point(4, 4, 4), new Point(4, 4, 4), new Point(4, 4, 4)),
				new Face(new Point(5, 5, 5), new Point(5, 5, 5), new Point(5, 5, 5)),
				new Face(new Point(6, 6, 6), new Point(6, 6, 6), new Point(6, 6, 6)),
				new Face(new Point(7, 7, 7), new Point(7, 7, 7), new Point(7, 7, 7)),
				new Face(new Point(8, 8, 8), new Point(8, 8, 8), new Point(8, 8, 8)),
				new Face(new Point(9, 9, 9), new Point(9, 9, 9), new Point(9, 9, 9))
		};
		for (int i = 0; i < tabFaces.length; i++) {
			tabFaces[i].setCentreGravite(tabFaces[i].calculCentreGravite());
			tabFacesTriees[i].setCentreGravite(tabFacesTriees[i].calculCentreGravite());
		}
		QuickSort.getInstance().setTab(tabFaces);
		QuickSort.getInstance().sort();
		for (int i = 0; i < tabFaces.length; i++)
			assertTrue(tabFacesTriees[i].equals(tabFaces[i]));
	}
}
