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
	File fichier;
	/**
	 * BufferedReader pour lire le fichier ligne par ligne.
	 */
	BufferedReader br;
	/**
	 * Constructeur qui charge le fichier.
	 */
	public LoadFile(){
		try {
			fichier = new File("ressources/dolphin.ply");
			br = new BufferedReader(new FileReader(fichier));
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
		Point[] points = new Point[RecupNbPoints(br.readLine())];
		for(int i=0;i<6;i++) // 6 lignes séparent la ligne indiquant le nombre de points de la ligne représentant le premier point. On peut les passer car elles ne sont pas nécessaires à interpréter.
			br.readLine();
		for(int j=0;j<points.length;j++) {
			String ligne_points = br.readLine();
			//Pour chaque ligne, on récupère les 3 points en repérant les espaces dans la ligne.
			float x = Float.parseFloat(ligne_points.substring(0, ligne_points.indexOf(" ")));
			float y = Float.parseFloat(ligne_points.substring(ligne_points.indexOf(" ")+1, ligne_points.indexOf(" ", ligne_points.indexOf(" ")+1)));
			float z = Float.parseFloat(ligne_points.substring(ligne_points.indexOf(" ", ligne_points.indexOf(" ")+1)+1, ligne_points.length()));
			points[j] = new Point(x, y, z);
		}
	}
	
	/**
	 * Récupère le nombre de points de la figure.
	 * @param ligne
	 * @return
	 */
	public int RecupNbPoints(String ligne) {
		return Integer.parseInt(ligne.substring(15, ligne.length()));
	}
	
	public static void main(String[] args) throws IOException {
		LoadFile file = new LoadFile();
		file.CreerPoints();
	}
}
