package src.launcher;

import javafx.application.Application;
import javafx.stage.Stage;
import src.controleur.Controleur;
import src.modele.Modele;
import src.vue.DessinVue;

public class Launcher extends Application {

	public void start(Stage stage) {
		Modele m1 = new Modele();
		Controleur c1 = new Controleur(m1);
		DessinVue dv = new DessinVue(c1);
		m1.addObserver(dv);
		Stage stage1 =new Stage();
		dv.start(stage1);
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}
