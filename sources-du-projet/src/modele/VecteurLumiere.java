package src.modele;

public class VecteurLumiere {
	
	public final Point VECTEUR_LUMIERE = new Point (0,0,-5);

	public Point getVECTEUR_LUMIERE() {
		return VECTEUR_LUMIERE;
	}
	
	public float getVECTEUR_LUMIEREX() {
		return VECTEUR_LUMIERE.getX();
	}
	
	public float getVECTEUR_LUMIEREY() {
		return VECTEUR_LUMIERE.getY();
	}
	
	public float getVECTEUR_LUMIEREZ() {
		return VECTEUR_LUMIERE.getZ();
	}
}
