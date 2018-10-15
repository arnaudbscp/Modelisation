package ihm;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class RotationObjet {
	@SuppressWarnings("unused")
	private double valeur;
	private Group objet;
	@SuppressWarnings("unused")
	private double idxX;
	@SuppressWarnings("unused")
	private double idxY;
	@SuppressWarnings("unused")
	private double idxZ;
	@SuppressWarnings("unused")
	private Rotate rotateGroup;
	@SuppressWarnings("unused")
	private Translate translateGroup;
	
	public RotationObjet(Group o, double v) {
		valeur = v;
		objet = o;
		rotateGroup = new Rotate(0, 0, 0);
		translateGroup = new Translate(0, 0);
	}
	
	public ObservableList<Double> executer(int index) {
		ObservableList<Double> res = null;
		Polygon tmp = (Polygon) objet.getChildren().get(index);
		idxX = tmp.getPoints().get(0);
		idxY = tmp.getPoints().get(1);
		idxZ = tmp.getPoints().get(2);
		
		return res;
		
	}
}
