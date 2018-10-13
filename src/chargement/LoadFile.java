package chargement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import exception.WrongFaceLineFormatException;
import exception.WrongHeaderException;
import exception.WrongPointLineFormatException;

/**
 * Charge le fichier 3D en créant tous les points et les faces.
 * @author Valentin
 *
 */
public class LoadFile {

	/**
	 * BufferedReader pour lire le fichier ligne par ligne.
	 */
	private BufferedReader br;
	/**
	 * Tableau stockant tous les Point de la figure.
	 */
	private Point[] points;
	/**
	 * Tableau stockant toutes les Face de la figure.
	 */
	private Face[] faces;
	/**
	 * Constructeur appelant la méthode lireStream(Reader in).
	 * @throws IOException
	 */
	public LoadFile(File f) throws IOException{
		lireStream(new FileReader(new File(f.getPath())));
	}

	/**
	 * Méthode qui charge le fichier et créé les tableaux de Point et de Face de longueurs adéquates, sans les remplir.
	 * @throws IOException
	 */
	public void lireStream(Reader in) throws IOException {
		try {
			br = new BufferedReader(in);
			br.readLine();
			br.readLine();
			points = new Point[RecupNb(br.readLine())];
			for(int i=0;i<3;i++)
				br.readLine();
			faces = new Face[RecupNb(br.readLine())];
		} catch (FileNotFoundException e) {
			System.out.println("Le fichier n'a pas été trouvé...");
			e.printStackTrace();
		} catch (WrongHeaderException e) {
			// TODO Auto-generated catch block
			System.exit(1);
		}
	}
	
	/**
	 * Création de tous les points de la figure et stockage dans un tableau de Point.
	 * @throws IOException
	 * @throws WrongPointLineFormatException 
	 */
	public void CreerPoints() throws IOException, WrongPointLineFormatException {
		br.readLine();
		br.readLine();
		for(int i=0;i<points.length;i++) {
			String ligne_point = br.readLine();
			//Je remplace les espaces par des a car je n'arrive pas à gérer les espaces dans la regex.
			ligne_point=ligne_point.replace(" ", "a");
			if(!ligne_point.matches("^(-?[0-9]+\\.?[0-9]*a){3}$"))
				throw new WrongPointLineFormatException(i+10);
			//On supprime le dernier a (espace) inutile.
			ligne_point=ligne_point.substring(0,ligne_point.length()-1);
			//Pour chaque ligne, on récupère les 3 coordonnées en repérant les espaces dans la ligne.
			float x = Float.parseFloat(ligne_point.substring(0, ligne_point.indexOf("a")));
			float y = Float.parseFloat(ligne_point.substring(ligne_point.indexOf("a")+1, ligne_point.indexOf("a", ligne_point.indexOf("a")+1)));
			float z = Float.parseFloat(ligne_point.substring(ligne_point.indexOf("a", ligne_point.indexOf("a")+1)+1));
			points[i] = new Point(x, y, z);
		}
	}
	
	/**
	 * Création des faces.
	 * @throws IOException
	 * @throws WrongFaceLineFormatException 
	 */
	public void CreerFaces() throws IOException, WrongFaceLineFormatException {
		for(int j=0;j<faces.length;j++) {
			br.mark(faces.length);
			String ligne_face = br.readLine();
			//Je remplace les espaces par des a car je n'arrive pas à gérer les espaces dans la regex.
			ligne_face=ligne_face.replace(" ", "a");
			if(!ligne_face.matches("^3a([0-9]+a){3}$"))
				throw new WrongFaceLineFormatException(j+10+points.length);
			ligne_face = ligne_face.substring(2,ligne_face.length()-1);//Supprime le 3 en début de chaque ligne.
			//Pour chaque ligne, on récupère les 3 Point en repérant les espaces dans la ligne.
			int pt1 = Integer.parseInt(ligne_face.substring(0, ligne_face.indexOf("a")));
			int pt2 = Integer.parseInt(ligne_face.substring(ligne_face.indexOf("a")+1, ligne_face.indexOf("a", ligne_face.indexOf("a")+1)));
			int pt3 = Integer.parseInt(ligne_face.substring(ligne_face.indexOf("a", ligne_face.indexOf("a")+1)+1));
			faces[j] = new Face(points[pt1],points[pt2],points[pt3]);
			faces[j].setPosition(new int[] {pt1,pt2,pt3});
		}
	}

	public void setPoints(Point[] points) {
		this.points = points;
	}

	/**
	 * Récupère le nombre de points ou le nombre de faces de la figure, en fonction de la ligne passée en paramètre.
	 * @param ligne
	 * @return
	 */
	public int RecupNb(String ligne) throws WrongHeaderException{
		if(!ligne.substring(ligne.indexOf(" ", ligne.indexOf(" ")+1)+1, ligne.length()).matches("[0-9]+")) 
			throw new WrongHeaderException();
		return Integer.parseInt(ligne.substring(ligne.indexOf(" ", ligne.indexOf(" ")+1)+1, ligne.length()));
	}

	/**
	 * Retourne le tableau contenant l'ensemble des Point de la figure.
	 * @return
	 */
	public Point[] getPoints() {
		return points;
	}
	
	/**
	 * Retourne le tableau contenant l'ensemble des Face de la figure.
	 * @return
	 */
	public Face[] getFaces() {
		return faces;
	}
	
//	/**
//	 * Récupère la plus petite des coordonnées de la dimension passée en paramètre de la figure pour adapter la taille du canvas.
//	 * @param dimension
//	 * @return
//	 */
//	public float getCoordMin(int dimension) {
//		float min = points[0].getCoordonnees()[dimension];
//		for(int i=1;i<points.length;++i) {
//			if(points[i].getCoordonnees()[dimension]<min)
//				min=points[i].getCoordonnees()[dimension];
//		}
//		return min;
//	}
//	
//	/**
//	 * Récupère la plus grande des coordonnées de la dimension passée en paramètre de la figure pour adapter la taille du canvas.
//	 * @param dimension
//	 * @return
//	 */
//	public float getCoordMax(int dimension) {
//		float max = points[0].getCoordonnees()[dimension];
//		for(int i=1;i<points.length;++i) {
//			if(points[i].getCoordonnees()[dimension]>max)
//				max=points[i].getCoordonnees()[dimension];
//		}
//		return max;
//	}
}
