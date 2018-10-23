package chargement;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javax.swing.JOptionPane;

import exception.WrongFaceLineFormatException;
import exception.WrongPointLineFormatException;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Classe permettant la création des tableaux de points et de faces grâce à LoadFile, qui calcule le centre de gravité des faces et qui trie les faces.
 * @author Valentin
 *
 */
public class Initialisation {
	
	/**
	 * Le tableau de faces.
	 */
	private Face[] faces;
	private LoadFile file;
	
	public Initialisation(){}
	
	public Initialisation(File f) throws IOException{
		lireStream(new FileReader(new File(f.getPath())));
	}

	/**
	 * Constructeur, créé les 2 tableaux de la longueur adéquate et les remplit, calcule le centre de gravité de chaque face et les trie.
	 * @param args
	 * @throws IOException
	 */
	public void lireStream(Reader in) throws IOException{
		file = new LoadFile();
		file.lireStream(in);
		try {
			file.creerPoints();
		} catch (WrongPointLineFormatException e) {
			JOptionPane.showMessageDialog(null, e.toString(),"Erreur Format Ligne Point",JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		try {
			file.creerFaces();
		} catch (WrongFaceLineFormatException e) {
			JOptionPane.showMessageDialog(null, e.toString(),"Erreur Format Ligne Face",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		faces = file.getFaces();
		for(int i=0;i<faces.length;++i) {
			faces[i].setCentre_gravite(faces[i].calculCentreGravite());
		}
		trierFaces();
	}

	/**
	 * Trie les faces de la plus éloignée à la plus éloignée en fonction de l'axe depuis lequel on visualise la figure.
	 * 
	 */
	public void trierFaces(){ //TRI A BULLE PEU EFFICACE, IMPLEMENTER UN ALGORITHME DE TRI PLUS PERFORMANT.
		boolean trie;
		do {
			trie=true;
			for(int i=0;i<faces.length-1;++i) {
				if(faces[i].getCentre_gravite().getCoordonnees()[2]>faces[i+1].getCentre_gravite().getCoordonnees()[2]) {
					trie=false;
					Face tmp = faces[i];
					faces[i] = faces[i+1];
					faces[i+1] = tmp;
				}
			}
		}while(!trie);
	}

	/**
	 * Créer la figure en interprétant les différentes coordonnées de points et en les reliant entre eux, puis en colorant la figure.
	 * @param gc
	 * @param faces
	 * @param c
	 */
	public void creerFigure(GraphicsContext gc, Face[] faces,Color c) {
		double[] px;
		double[] py;
	
		for (int i = 0; i < faces.length; i++) {
			px = new double[] {faces[i].getPoints()[0].getX()*-1+(gc.getCanvas().getWidth()/2),faces[i].getPoints()[1].getX()*-1+(gc.getCanvas().getWidth()/2),faces[i].getPoints()[2].getX()*-1+(gc.getCanvas().getWidth()/2)};
			py = new double[] {faces[i].getPoints()[0].getZ()*-1+(gc.getCanvas().getHeight()/2),faces[i].getPoints()[1].getZ()*-1+(gc.getCanvas().getHeight()/2),faces[i].getPoints()[2].getZ()*-1+(gc.getCanvas().getHeight()/2)};
			gc.fillPolygon(px, py, 3);
			gc.strokePolygon(px, py, 3);
			gc.setFill(c);
		}
	}

	public LoadFile getLoadFile() {
		return file;
	}
}
