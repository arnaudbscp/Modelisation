package src.launcher;

import javafx.application.Application;
import javafx.stage.Stage;
import src.controleur.Controleur;
import src.modele.Modele;
import src.vue.DessinVue;

/**
 * Classe contenant la méthode principale pour lancer le programme.
 * @author Valentin
 *
 */
public class Launcher extends Application {

	/**
	 * Méthode créant les 3 classes du design-pattern MVC (Modèle, Vue, Contrôleur), attachant au modèle la liste des Vues 
	 * et lançant l'interface graphique.
	 */
	@SuppressWarnings("deprecation")
	public void start(Stage stage) {
		Modele m1 = new Modele();
		Controleur c1 = new Controleur(m1);
		DessinVue dv = new DessinVue(c1);
		m1.addObserver(dv);
		Stage stage1 = new Stage();
		dv.start(stage1);
	}
	
	/**
	 * Méthode principale lançant le programme.
	 * @param args
	 */
	public static void main(String[] args) {
		Application.launch(args);
	}
}
