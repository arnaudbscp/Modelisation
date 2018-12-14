package src.modele;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import src.exception.MissingFaceLineException;
import src.exception.MissingPointLineException;
import src.exception.TooMuchFaceLineException;
import src.exception.TooMuchPointLineException;
import src.exception.WrongFaceLineFormatException;
import src.exception.WrongHeaderException;
import src.exception.WrongPointLineFormatException;

/**
 * Lit le fichier 3D, récupère le nombre de points et de faces de la figure pour dimensionner le tableau de points et de faces, sans les
 * remplir. Ces 2 tableaux seront remplit dans l'Initialisation en appelant les méthodes CreerPoints et CreerFaces.
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
	 * La seule et unique instance du LoadFile pour le programme.
	 */
	private static final LoadFile INSTANCE = new LoadFile();

	/**
	 * Constructeur privé typique du design-pattern Singleton.
	 */
	private LoadFile() {}

	/**
	 * Retourne la seule et unique instance du LoadFile. En effet, on a besoin que d'un seul LoadFile car on lit un fichier à la fois.
	 * La lecture d'un nouveau fichier va écraser l'ancienne qui deviendrait inutile.
	 * @return
	 */
	public final static LoadFile getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Méthode permettant de lire un fichier passé en paramètre, appelant la méthode lireStream(Reader in) 
	 * qui va lire le stream du fichier.
	 * @throws IOException
	 * @throws WrongHeaderException 
	 */
	public void lireFichier(File f) throws IOException, WrongHeaderException{
		lireStream(new FileReader(new File(f.getPath())));
	}

	/**
	 * Méthode permettant de lire un stream sans spécifier de fichier.
	 * Création des tableaux de Point et de Face de longueurs adéquates, sans les remplir.
	 * @throws IOException
	 * @throws WrongHeaderException 
	 */
	public void lireStream(Reader in) throws IOException, WrongHeaderException {
		String ligne = "";
		br = new BufferedReader(in);
		for(byte i = 0; i < 3; i++) {
			ligne = br.readLine();
			//Si la ligne est un commentaire, on ne la prend pas en compte.
			if(isCommentaire(ligne))
				i--;
		}
		points = new Point[recupNb(ligne)];
		for(byte i = 0; i < 3; i++) {
			ligne = br.readLine();
			//Si la ligne est un commentaire, on ne la prend pas en compte.
			if(isCommentaire(ligne))
				i--;
		}
		faces = new Face[recupNb(br.readLine())];
	}

	/**
	 * Création de tous les points de la figure et stockage dans un tableau de Point.
	 * @throws IOException
	 * @throws WrongPointLineFormatException 
	 * @throws MissingPointLineException 
	 */
	public void creerPoints() throws IOException, WrongPointLineFormatException, MissingPointLineException {
		String lignePoint = "";
		for(byte i = 0; i < 2; i++) {
			lignePoint = br.readLine();
			if(isCommentaire(lignePoint))
				i--;
		}
		for(int i = 0; i < points.length; i++) {
			lignePoint = br.readLine() + "a";
			//Si la ligne est un commentaire, on ne la prend pas en compte.
			if(isCommentaire(lignePoint))
				i--;
			else {
				//Je remplace les espaces par des a car je n'arrive pas à gérer les espaces dans la regex.
				lignePoint = lignePoint.replace(" ", "a");
				if(!lignePoint.matches("^(-?[0-9]+\\.?[0-9]*(e-?[0-9]+)?a){3}a*$")) { //Si la ligne n'est pas dans le bon format d'une ligne représentant un point.
					if(lignePoint.matches("^3a([0-9]+a+){3}a*$")) //Si c'est une ligne représentant une face, c'est qu'il n'y a pas assez de points.
						throw new MissingPointLineException(points.length, i);
					else
						throw new WrongPointLineFormatException(i + 10); //Sinon c'est juste une erreur de format de ligne de point.
				}
				else if(lignePoint.contains("e")) 
					creerPointsExposant(lignePoint, i);
				else {
					String[] xyz = new String[3];
					//Pour chaque ligne, on récupère les 3 coordonnées en repérant les espaces dans la ligne.
					xyz = lignePoint.split("a");
					points[i] = new Point(Float.parseFloat(xyz[0]), Float.parseFloat(xyz[1]), Float.parseFloat(xyz[2]));
				}
			}
		}
	}

	/**
	 * Création d'un Point dans le cas où ses coordonnées contiennent une puissance de 10.
	 * @param ligne_point
	 */
	private void creerPointsExposant(String lignePoint, int idx) {
		// TODO Auto-generated method stub
		String[] xyz = new String[3];
		xyz = lignePoint.split("a");
		String[] StringCoords = new String[] {xyz[0], xyz[1], xyz[2]};
		float[] floatCoords = new float[StringCoords.length];
		for(byte i = 0; i < StringCoords.length; i++) {
			if(StringCoords[i].contains("e")) {
				String nb = StringCoords[i].substring(0, StringCoords[i].indexOf("e"));
				String expo = StringCoords[i].substring(StringCoords[i].indexOf("e")+1);
				floatCoords[i] = Float.parseFloat(nb) * (float)Math.pow(10.0, Integer.parseInt(expo));
			}else
				floatCoords[i] = Float.parseFloat(StringCoords[i]);
		}
		points[idx] = new Point(floatCoords[0], floatCoords[1], floatCoords[2]);
	}

	/**
	 * Création des faces de la figure et stockage dans un tableau de faces.
	 * @throws IOException
	 * @throws WrongFaceLineFormatException 
	 * @throws TooMuchPointLineException 
	 * @throws MissingFaceLineException 
	 * @throws TooMuchFaceLineException 
	 */
	public void creerFaces() throws IOException, WrongFaceLineFormatException, TooMuchPointLineException, MissingFaceLineException, TooMuchFaceLineException {
		for(int j = 0; j < faces.length; j++) {
			String ligneFace = br.readLine() + "a";
			//Si la ligne est un commentaire, on ne la prend pas en compte.
			if(isCommentaire(ligneFace))
				j--;
			else {
				//Je remplace les espaces par des a car je n'arrive pas à gérer les espaces dans la regex.
				ligneFace = ligneFace.replace(" ", "a");
				if(!ligneFace.matches("^3a([0-9]+a){3}a*$")) { //Si la ligne n'est pas dans le bon format d'une ligne représentant une face.
					if(ligneFace.matches("^(-?[0-9]+\\.?[0-9]*(e-?[0-9]+)?a){3}a*$")) //Si c'est une ligne représentant un point, c'est qu'il y a trop de points.
						throw new TooMuchPointLineException();
					else if(ligneFace.equals("nulla"))
						throw new MissingFaceLineException(faces.length, j); //Si la ligne est vide c'est qu'il manque des faces. 
					else
						throw new WrongFaceLineFormatException(j + 10 + points.length); //Sinon c'est juste une erreur de format de ligne de face.
				}
				ligneFace = ligneFace.substring(2,ligneFace.length()-1);//Supprime le 3 en début de chaque ligne.
				String[] tabFaces = new String[3];
				tabFaces = ligneFace.split("a");
				int pt1 = Integer.parseInt(tabFaces[0]);
				int pt2 = Integer.parseInt(tabFaces[1]);
				int pt3 = Integer.parseInt(tabFaces[2]);
				faces[j] = new Face(points[pt1], points[pt2], points[pt3]);
				faces[j].setPosition(new int[] {pt1, pt2, pt3});
			}
		}
		String ligneEnTrop;
		do
			ligneEnTrop = br.readLine() + "a";
		while(isCommentaire(ligneEnTrop));
		ligneEnTrop = ligneEnTrop.replace(" ", "a");
		if(!ligneEnTrop.equals("nulla")){ //Si il y a encore des lignes c'est qu'il y a trop de faces.
			if(ligneEnTrop.matches("^3a([0-9]+a){3}a*$"))
				throw new TooMuchFaceLineException();
		}
		br.close();
	}

	/**
	 * Définit directement le tableau de points.
	 * @param points
	 */
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
	 * @return tableau des points de la figure.
	 */
	public Point[] getPoints() {
		return points;
	}

	/**
	 * Retourne le tableau contenant l'ensemble des Face de la figure.
	 * @return tableau des faces de la figure.
	 */
	public Face[] getFaces() {
		return faces;
	}

	/**
	 * Récupère la plus petite des coordonnées de la dimension passée en paramètre de la figure pour adapter la taille du canvas.
	 * @param dimension 0 pour X, 1 pour Y et 2 pour Z.
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
	 * @param dimension 0 pour X, 1 pour Y et 2 pour Z.
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

	/**
	 * Retourne true si une ligne est un commentaire.
	 * @param ligne
	 * @return
	 */
	public boolean isCommentaire(String ligne) {
		return ligne.startsWith("//");
	}
}