package chargement;

import java.io.IOException;

import exception.NotAnAxisException;

/**
 * Classe permettant la création des tableaux de points et de faces grâce à LoadFile, qui calcule le centre de gravité des faces et qui trie les faces.
 * @author Valentin
 *
 */
public class Initialisation {
	
	protected Face[] faces;

	/**
	 * Constructeur, créé les 2 tableaux de la longueur adéquate et les remplit, calcule le centre de gravité de chaque face et les trie.
	 * @param args
	 * @throws IOException
	 * @throws NotAnAxeException 
	 */
	public Initialisation() throws IOException{
		LoadFile file = new LoadFile();
		file.CreerPoints();
		file.CreerFaces();
		faces = file.getFaces();
		for(int i=0;i<faces.length;++i) {
			faces[i].setCentre_gravite(faces[i].calculCentreGravite());
		}
		try {
			trierFaces(faces,2);
		} catch (NotAnAxisException e) {
			// TODO Auto-generated catch block
			System.exit(1);
		}
		for(int i=0;i<faces.length;++i) {
			System.out.println(faces[i]);
		}
	}

	/**
	 * Trie les faces de la plus éloignée à la plus éloignée en fonction de l'axe depuis lequel on visualise la figure.
	 * @param axe: 0 pour l'axe x, 1 pour l'axe y et 2 pour l'axe z.
	 */
	public void trierFaces(Face[] faces,int axe) throws NotAnAxisException{ //TRI A BULLE PEU EFFICACE, IMPLEMENTER UN ALGORITHME DE TRI PLUS PERFORMANT.
		if(axe<0 || axe >2)
			throw new NotAnAxisException();
		else {
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
	}

	/**
	 * Retourne un tableau contenant toutes les faces.
	 * @return
	 */
	public Face[] getFaces() {
		return faces;
	}
}
