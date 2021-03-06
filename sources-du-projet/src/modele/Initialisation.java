package src.modele;

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
 * Classe Singleton permettant la création des tableaux de points et de faces grâce à LoadFile, 
 * qui calcule le centre de gravité des faces et qui trie les faces.
 * @author Valentin
 *
 */
public class Initialisation {

	/**
	 * Le tableau de faces de la figure.
	 */
	private Face[] faces;

	/**
	 * Le LoadFile qui va lire le fichier pour connaître le nombre de points et de face de la figure.
	 */
	private LoadFile lf;
	
	/**
	 * Booléen qui nous donne l'état de l'Initialisation, contient true si aucune erreur n'a été rencontrée à l'instanciation, sinon renvoie false.
	 */
	private boolean isGood;
	
	/**
	 * La seule et unique instance de l'Initialisation pour le programme.
	 */
	private final static Initialisation INSTANCE = new Initialisation(); 
	
	/**
	 * Constructeur privé typique du design-pattern Singleton.
	 */
	private Initialisation() {}

	/**
	 * Retourne l'instance de l'Initialisation. On a besoin que d'une seule instance de l'Initialisation car on ne peut ouvrir
	 * qu'un fichier à la fois. Eneffet, l'ouverture d'un autre fichier écrasera le premier et il est donc inutile de conserver 
	 * l'Initialisation de celui-ci.
	 * @return
	 */
	public final static Initialisation getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Appelle la méthode doInit(Reader) permettant la lecture du stream du fichier et son interprétation.
	 * @param f
	 * @throws IOException
	 */
	public void doInit(File f) throws IOException{
		doInit(new FileReader(f));
	}
	
	/**
	 * Lit le stream du fichier grâce à un LoadFile et créer les points et les faces en calculant également leur centre de gravité.
	 * @param f : le fichier à interpréter.
	 * @throws IOException
	 */
	public void doInit(Reader f) throws IOException{
		isGood = false;
		try {
			lf = LoadFile.getInstance();
			lf.lireStream(f);
			try {
				lf.creerPoints();
				try {
					lf.creerFaces();
					faces = lf.getFaces();
					for(int i = 0; i < faces.length; ++i)
						faces[i].calculCentreGravite();
					isGood = true;
				} catch (WrongFaceLineFormatException e) {
					e.showMessage();
				} catch (TooMuchPointLineException e) {
					e.showMessage();
				} catch (MissingFaceLineException e) {
					e.showMessage();
				} catch (TooMuchFaceLineException e) {
					e.showMessage();
				}
			} catch (WrongPointLineFormatException e) {
				e.showMessage();
			} catch (MissingPointLineException e) {
				e.showMessage();
			}
		} catch (WrongHeaderException e) {
			e.showMessage();
		}
	}

	/**
	 * Retourne le LoadFile de la figure.
	 * @return
	 */
	public LoadFile getLoadFile() {
		return lf;
	}
	
	/**
	 * Retourne l'état de l'Initialisation, renvoie true si aucune erreur n'a été rencontrée à l'instanciation, sinon renvoie false.
	 * @return
	 */
	public boolean isGood() {
		return isGood;
	}
}
