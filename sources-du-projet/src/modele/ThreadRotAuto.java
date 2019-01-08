package src.modele;

/**
 * Thread permettant de réaliser une rotation de la rotation automatique. Un Thread sera créé par rotation de la rotation auto.
 * @author Valentin
 *
 */
public class ThreadRotAuto implements Runnable{
	
	/**
	 * Le modèle.
	 */
	private Modele modele;
	
	/**
	 * Représente le nombre de pas que l'on souhaite faire lors de la rotation auto (ex: 12 pas déplacera la figure de 360/12 = 30 degrès à chaque fois)
	 */
	private short step;

	/**
	 * Constructeur spécifiant le modèle et le pas (nombre de rotation de la rotation auto)
	 * @param modele
	 * @param step 
	 */
	public ThreadRotAuto(Modele modele, short step) {
		this.modele = modele;
		this.step = step;
	}

	/**
	 * Appelle la méthode du modèle réalisant la rotation, en synchronisant les threads de chaque rotation.
	 */
	@Override
	public void run() {
		modele.runThreadSync(step);
	}
}
