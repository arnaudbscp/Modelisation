package chargement;

import java.io.File;
import java.io.IOException;

import exception.WrongFaceLineFormatException;
import exception.WrongPointLineFormatException;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * Classe permettant la création des tableaux de points et de faces grâce à LoadFile, qui calcule le centre de gravité des faces et qui trie les faces.
 * @author Valentin
 *
 */
public class Initialisation {


	/**
	 * Constructeur, créé les 2 tableaux de la longueur adéquate et les remplit, calcule le centre de gravité de chaque face et les trie.
	 * @param args
	 * @throws IOException
	 */
	public Initialisation(File f) throws IOException{
		LoadFile file = new LoadFile(f);
		try {
			file.CreerPoints();
		} catch (WrongPointLineFormatException e1) {
			// TODO Auto-generated catch block
			System.exit(1);
		}
		try {
			file.CreerFaces();
		} catch (WrongFaceLineFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Face[] faces = file.getFaces();
		for(int i=0;i<faces.length;++i) {
			faces[i].setCentre_gravite(faces[i].calculCentreGravite());
		}
		trierFaces(faces,1);
		for(int i=0;i<faces.length;++i) {
			System.out.println(faces[i]);
		}
	}

	/**
	 * Trie les faces de la plus éloignée à la plus éloignée en fonction de l'axe depuis lequel on visualise la figure.
	 * @param axe: 0 pour l'axe x, 1 pour l'axe y et 2 pour l'axe z.
	 */
	public void trierFaces(Face[] faces,int axe){ //TRI A BULLE PEU EFFICACE, IMPLEMENTER UN ALGORITHME DE TRI PLUS PERFORMANT.
		boolean trie;
		do {
			trie=true;
			for(int i=0;i<faces.length-1;++i) {
				if(faces[i].getCentre_gravite().getCoordonnees()[axe]>faces[i+1].getCentre_gravite().getCoordonnees()[axe]) {
					trie=false;
					Face tmp = faces[i];
					faces[i] = faces[i+1];
					faces[i+1] = tmp;
				}
			}
		}while(!trie);
	}

	/**
	 * Retourne un tableau contenant toutes les faces.
	 * @return
	 */


	public void CreerFigure(GraphicsContext gc, Face[] faces) {
		double[] px;
		double[] py;
		for (int i = 0; i < faces.length; i++) {
			px = new double[] {faces[i].getPoints()[0].getX()*-1,faces[i].getPoints()[1].getX()*-1,faces[i].getPoints()[2].getX()*-1};
			py = new double[] {faces[i].getPoints()[0].getZ()*-1,faces[i].getPoints()[1].getZ()*-1,faces[i].getPoints()[2].getZ()*-1};
			gc.fillPolygon(px, py, 3);
			gc.strokePolygon(px, py, 3);
			gc.setFill(Color.GRAY);

		}
	}
}
