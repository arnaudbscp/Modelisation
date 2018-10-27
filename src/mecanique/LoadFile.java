package mecanique;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javax.swing.JOptionPane;

import donnees.Face;
import donnees.Point;

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
	 * Constructeur vide pour les tests.
	 */
	public LoadFile() {}

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
			points = new Point[recupNb(br.readLine())];
			for(int i=0;i<3;i++)
				br.readLine();
			faces = new Face[recupNb(br.readLine())];
		} catch (WrongHeaderException e) {
			JOptionPane.showMessageDialog(null, e.toString(),"Erreur format entête",JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}

	/**
	 * Création de tous les points de la figure et stockage dans un tableau de Point.
	 * @throws IOException
	 * @throws WrongPointLineFormatException 
	 */
	public void creerPoints() throws IOException, WrongPointLineFormatException {
		br.readLine();
		br.readLine();
		for(int i=0;i<points.length;i++) {
			String ligne_point = br.readLine()+"a";
			//Je remplace les espaces par des a car je n'arrive pas à gérer les espaces dans la regex.
			ligne_point=ligne_point.replace(" ", "a");
			if(!ligne_point.matches("^(-?[0-9]+\\.?[0-9]*(e-?[0-9]+)?a){3}a*$"))
				throw new WrongPointLineFormatException(i+10);
			else if(ligne_point.contains("e")) {
				creerPointsExposant(ligne_point,i);
			}else {
				String[] xyz = new String[4];
				//Pour chaque ligne, on récupère les 3 coordonnées en repérant les espaces dans la ligne.
				xyz = ligne_point.split("a");
				points[i] = new Point(Float.parseFloat(xyz[0]), Float.parseFloat(xyz[1]), Float.parseFloat(xyz[2]));
			} 
		}
	}

	/**
	 * Création d'un Point dans le cas où ses coordonnées contiennent une puissance de 10.
	 * @param ligne_point
	 */
	private void creerPointsExposant(String ligne_point, int idx) {
		// TODO Auto-generated method stub
		String[] xyz = new String[3];
		xyz = ligne_point.split("a");
		String[] StringCoords = new String[] {xyz[0],xyz[1],xyz[2]};
		float[] floatCoords = new float[StringCoords.length];
		for(int i=0;i<StringCoords.length;i++) {
			if(StringCoords[i].contains("e")) {
				String nb = StringCoords[i].substring(0, StringCoords[i].indexOf("e"));
				String expo = StringCoords[i].substring(StringCoords[i].indexOf("e")+1);
				floatCoords[i] = Float.parseFloat(nb)*(float)Math.pow(10.0, Integer.parseInt(expo));
			}else {
				floatCoords[i] = Float.parseFloat(StringCoords[i]);
			}
		}
		points[idx] = new Point(floatCoords[0], floatCoords[1], floatCoords[2]);
	}

	/**
	 * Création des faces.
	 * @throws IOException
	 * @throws WrongFaceLineFormatException 
	 */
	public void creerFaces() throws IOException, WrongFaceLineFormatException {
		for(int j=0;j<faces.length;j++) {
			br.mark(faces.length);
			String ligne_face = br.readLine()+"a";
			//Je remplace les espaces par des a car je n'arrive pas à gérer les espaces dans la regex.
			ligne_face=ligne_face.replace(" ", "a");
			if(!ligne_face.matches("^3a([0-9]+a){3}a*$"))
				throw new WrongFaceLineFormatException(j+10+points.length);
			ligne_face = ligne_face.substring(2,ligne_face.length()-1);//Supprime le 3 en début de chaque ligne.
			String[] tabfaces = new String[3];
			tabfaces = ligne_face.split("a");
			int pt1 = Integer.parseInt(tabfaces[0]);
			int pt2 = Integer.parseInt(tabfaces[1]);
			int pt3 = Integer.parseInt(tabfaces[2]);
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
	public int recupNb(String ligne) throws WrongHeaderException{
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

	/**
	 * Récupère la plus petite des coordonnées de la dimension passée en paramètre de la figure pour adapter la taille du canvas.
	 * @param dimension
	 * @return
	 */
	public float getCoordMin(int dimension) {
		float min = points[0].getCoordonnees()[dimension];
		for(int i=1;i<points.length;++i) {
			if(points[i].getCoordonnees()[dimension]<min)
				min=points[i].getCoordonnees()[dimension];
		}
		return min;
	}

	/**
	 * Récupère la plus grande des coordonnées de la dimension passée en paramètre de la figure pour adapter la taille du canvas.
	 * @param dimension
	 * @return
	 */
	public float getCoordMax(int dimension) {
		float max = points[0].getCoordonnees()[dimension];
		for(int i=1;i<points.length;++i) {
			if(points[i].getCoordonnees()[dimension]>max)
				max=points[i].getCoordonnees()[dimension];
		}
		return max;
	}
}