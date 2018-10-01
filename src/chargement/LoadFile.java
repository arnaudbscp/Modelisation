package chargement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Charge le fichier 3D en créant tous les points.
 * @author Valentin
 *
 */
public class LoadFile {
	/**
	 * Stocke le fichier.
	 */
	private File fichier;
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
	 * Constructeur qui charge le fichier.
	 * @throws IOException
	 */
	public LoadFile() throws IOException{
		try {
			fichier = new File("ressources/dolphin.ply");
			br = new BufferedReader(new FileReader(fichier));
			br.readLine();
			br.readLine();
			points = new Point[RecupNb(br.readLine())];
			for(int i=0;i<3;i++)
				br.readLine();
			faces = new Face[RecupNb(br.readLine())];
		} catch (FileNotFoundException e) {
			System.out.println("Le fichier n'a pas été trouvé...");
			e.printStackTrace();
		}
	}
	
	/**
	 * Création de tous les points de la figure et stockage dans un tableau de Point.
	 * @throws IOException
	 */
	public void CreerPoints() throws IOException {
		br.readLine();
		br.readLine();
		for(int i=0;i<points.length;i++) {
			String ligne_point = br.readLine();
			//Pour chaque ligne, on récupère les 3 coordonnées en repérant les espaces dans la ligne.
			float x = Float.parseFloat(ligne_point.substring(0, ligne_point.indexOf(" ")));
			float y = Float.parseFloat(ligne_point.substring(ligne_point.indexOf(" ")+1, ligne_point.indexOf(" ", ligne_point.indexOf(" ")+1)));
			float z = Float.parseFloat(ligne_point.substring(ligne_point.indexOf(" ", ligne_point.indexOf(" ")+1)+1));
			points[i] = new Point(x, y, z);
		}
	}
	
	/**
	 * Création des faces.
	 * @throws IOException
	 */
	public void CreerFaces() throws IOException {
		for(int j=0;j<faces.length;j++) {
			String ligne_face = br.readLine();
			ligne_face = ligne_face.substring(2,ligne_face.length()-1);//Supprime le 3 en début de chaque ligne.
			//Pour chaque ligne, on récupère les 3 Point en repérant les espaces dans la ligne.
			int pt1 = Integer.parseInt(ligne_face.substring(0, ligne_face.indexOf(" ")));
			int pt2 = Integer.parseInt(ligne_face.substring(ligne_face.indexOf(" ")+1, ligne_face.indexOf(" ", ligne_face.indexOf(" ")+1)));
			int pt3 = Integer.parseInt(ligne_face.substring(ligne_face.indexOf(" ", ligne_face.indexOf(" ")+1)+1));
			faces[j] = new Face(points[pt1],points[pt2],points[pt3]);
		}
	}
	
	/**
	 * Récupère le nombre de points ou le nombre de faces de la figure, en fonction de la ligne passée en paramètre.
	 * @param ligne
	 * @return
	 */
	public int RecupNb(String ligne) {
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
}
