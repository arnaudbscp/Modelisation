package src.mecanique;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import src.donnees.Face;
import src.exception.MissingFaceLineException;
import src.exception.MissingPointLineException;
import src.exception.TooMuchFaceLineException;
import src.exception.TooMuchPointLineException;
import src.exception.WrongFaceLineFormatException;
import src.exception.WrongHeaderException;
import src.exception.WrongPointLineFormatException;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Classe permettant la création des tableaux de points et de faces grâce à LoadFile, qui calcule le centre de gravité des faces et qui trie les faces.
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
	 * Lit le fichier grâce à un LoadFile et créer les points et les faces en calculant également leur centre de gravité.
	 * @param f : le fichier à interpréter.
	 * @throws IOException
	 */
	public Initialisation(File f) throws IOException{
		isGood = false;
		try {
			lf = new LoadFile(f);
			try {
				lf.creerPoints();
				try {
					lf.creerFaces();
					faces = lf.getFaces();
					for(int i = 0; i < faces.length; ++i)
						faces[i].setCentreGravite(faces[i].calculCentreGravite());
					isGood = true;
				} catch (WrongFaceLineFormatException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), e.getTitle(), JOptionPane.ERROR_MESSAGE);
				} catch (TooMuchPointLineException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), e.getTitle(), JOptionPane.ERROR_MESSAGE);
				} catch (MissingFaceLineException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), e.getTitle(), JOptionPane.ERROR_MESSAGE);
				} catch (TooMuchFaceLineException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), e.getTitle(), JOptionPane.ERROR_MESSAGE);
				}
			} catch (WrongPointLineFormatException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), e.getTitle(), JOptionPane.ERROR_MESSAGE);
			} catch (MissingPointLineException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), e.getTitle(), JOptionPane.ERROR_MESSAGE);
			}
		} catch (WrongHeaderException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), e.getTitle(), JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Créer la figure en interprétant les différentes coordonnées de points et en les reliant entre eux, puis en colorant la figure.
	 * @param gc
	 * @param faces
	 * @param c
	 */
	public void creerFigure(GraphicsContext gc, Face[] faces, Color c, ModeDessin md) {
		double[] px;
		double[] py;
		for (int i = 0; i < faces.length; i++)
			faces[i].setCentreGravite(faces[i].calculCentreGravite());
		QuickSort.getInstance().setTab(faces);
		QuickSort.getInstance().sort();
		for (int i = 0; i < faces.length; i++) {
			px = new double[] {faces[i].getPoints()[0].getX()*-1 + (gc.getCanvas().getWidth()/2),faces[i].getPoints()[1].getX()*-1 + (gc.getCanvas().getWidth()/2),faces[i].getPoints()[2].getX()*-1+(gc.getCanvas().getWidth()/2)};
			py = new double[] {faces[i].getPoints()[0].getZ()*-1 + (gc.getCanvas().getHeight()/2),faces[i].getPoints()[1].getZ()*-1 + (gc.getCanvas().getHeight()/2),faces[i].getPoints()[2].getZ()*-1+(gc.getCanvas().getHeight()/2)};
			if(md.equals(ModeDessin.FACES_ARRETES)) {
				gc.fillPolygon(px, py, 3);
				gc.strokePolygon(px, py, 3);
				gc.setFill(c);
			}else if(md.equals(ModeDessin.FACES)) {
				gc.fillPolygon(px, py, 3);
				gc.setFill(c);
			}else
				gc.strokePolygon(px, py, 3);
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
