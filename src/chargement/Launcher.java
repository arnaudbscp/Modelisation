package chargement;

import java.io.IOException;

import exception.NotAnAxisException;

/**
 * Classe principale lançant l'application.
 * @author Valentin
 *
 */
public class Launcher {
	
	protected Face[] faces;

	/**
	 * Constructeur.
	 * @param args
	 * @throws IOException
	 * @throws NotAnAxeException 
	 */
	public Launcher() throws IOException{
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

	public Face[] getFaces() {
		return faces;
	}
}
