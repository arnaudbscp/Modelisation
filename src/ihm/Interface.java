package ihm;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import donnees.Face;
import donnees.Point;

import mecanique.Initialisation;
import mecanique.LoadFile;

import mouvements.Rotation;
import mouvements.Translation;
import mouvements.Zoom;

import exception.MatriceFormatException;
import exception.MatriceNullException;
import exception.WrongFaceLineFormatException;
import exception.WrongFormatFileException;
import exception.WrongPointLineFormatException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Classe représentant la fenêtre graphique et tous les éléments graphiques contenus dedans.
 * @author bascopa & clarissa
 */
public class Interface extends Application {

	/**
	 * Permet de charger le fichier, de le lire séquentiellement afin de ranger les points et les faces dans des tableaux.
	 */
	private LoadFile loadFile;

	/**
	 * le fichier .ply contenant les points et les faces à  dessiner.
	 */
	private File filePly;

	/**
	 * Interprète le LoadFile pour créer les points et les faces, trier les faces et ainsi créer la figure.
	 */
	private Initialisation init;

	/**
	 * Compteur de translation gauche-droite. On l'incrémente lors de l'appui sur le bouton droite et on le 
	 * décrémente lors de l'appui sur le bouton gauche pour déplacer la figure horizontalement.
	 */
	private int cptTranslateGD=0;

	/**
	 * Compteur de translation haut-bas. On l'incrémente lors de l'appui sur le bouton haut et on le décrémente lors de 
	 * l'appui sur le bouton bas pour déplacer la figure verticalement.
	 */
	@SuppressWarnings("unused")
	private int cptTranslateHB=0;

	/**
	 * Couleur de la figure initialisée à  blanche. Elle sera modifiée grâce au colorpicker par l'utilisateur.
	 */
	private Color couleur = Color.WHITE;

	/**
	 * Méthode d'affichage de l'interface graphique.
	 */

	Face[] tabf;
	Point[] tabp;
	public void start(Stage primaryStage){
		//ELEMENTS GRAPHIQUES
		HBox corps = new HBox();
		Scene scene = new Scene(corps, 1280, 600);
		VBox menu = new VBox();
		menu.setMinWidth(150);
		Button b1 = new Button("Importer");
		b1.setMinWidth(150);
		menu.getChildren().add(b1);
		Canvas canv = new Canvas(1100, 600);
		GraphicsContext gc = canv.getGraphicsContext2D();
		HBox.setMargin(menu, new Insets(50, 0, 0, 20));
		FileChooser importer = new FileChooser();
		VBox dessin = new VBox();
		Separator sep = new Separator(Orientation.VERTICAL);
		dessin.getChildren().add(sep);
		corps.getChildren().add(menu);
		corps.getChildren().add(dessin);
		sep.setValignment(VPos.CENTER);
		//A modifier
		sep.setMinHeight(300);
		sep.setStyle("-fx-padding : 0 0 0 30;");
		corps.getChildren().add(canv);

		//SLIDER ZOOM
		Slider zoom = new Slider();
		Label lblZoom = new Label();
		lblZoom.setText("Zoomer");
		lblZoom.setStyle("-fx-padding : 20 0 0 50;");
		zoom.setMin(0);
		zoom.setMax(5);
		zoom.setValue(1);
		zoom.setMajorTickUnit(1);
		zoom.setBlockIncrement(0.2);
		zoom.setShowTickLabels(true);
		menu.getChildren().addAll(lblZoom, zoom);

		//SLIDER TRANSLATION X
		Slider tournerX = new Slider();
		Label lblTournerX = new Label();
		lblTournerX.setText("Tourner X");
		lblTournerX.setStyle("-fx-padding : 20 0 0 50;");
		tournerX.setMin(0);
		tournerX.setMax(360);
		tournerX.setMajorTickUnit(90);
		tournerX.setValue(0);
		tournerX.setShowTickLabels(true);
		menu.getChildren().addAll(lblTournerX, tournerX);

		//SLIDER TRANSLATION Y
		Slider tournerY = new Slider();
		Label lblTournerY = new Label();
		lblTournerY.setText("Tourner Y");
		lblTournerY.setStyle("-fx-padding : 20 0 0 50;");
		tournerY.setMin(0);
		tournerY.setMax(360);
		tournerY.setMajorTickUnit(90);
		tournerY.setValue(0);
		tournerY.setShowTickLabels(true);
		menu.getChildren().addAll(lblTournerY, tournerY);

		//SLIDER TRANSLATION Z
		Slider tournerZ = new Slider();
		Label lblTournerZ = new Label();
		lblTournerZ.setText("Tourner Z");
		lblTournerZ.setStyle("-fx-padding : 20 0 0 50;");
		tournerZ.setMin(-180);
		tournerZ.setMax(180);
		tournerZ.setMajorTickUnit(90);
		tournerZ.setValue(0);
		tournerZ.setShowTickLabels(true);
		menu.getChildren().addAll(lblTournerZ, tournerZ);

		//CROIX DIRECTIONNELLE TRANSLATION
		Label lblTranslation = new Label("Translation");
		HBox hbHaut = new HBox();
		HBox hbGaucheDroite = new HBox();
		HBox hbBas = new HBox();
		Button haut = new Button("H");
		Button gauche = new Button("G");
		Button droite = new Button("D");
		Button bas = new Button("B");
		hbHaut.setPadding(new Insets(0, 0, 0, 28));
		hbBas.setPadding(new Insets(0, 0, 0, 28));
		hbGaucheDroite.setSpacing(28);
		haut.setPrefWidth(25);
		bas.setPrefWidth(25);
		droite.setPrefWidth(25);
		gauche.setPrefWidth(25);
		hbHaut.getChildren().add(haut);
		hbGaucheDroite.getChildren().addAll(gauche, droite);
		hbBas.getChildren().add(bas);
		menu.getChildren().addAll(lblTranslation, hbHaut, hbGaucheDroite, hbBas);

		//CHOIX COULEURS
		Label lcolor = new Label("Couleur");
		ColorPicker cp = new ColorPicker();
		menu.getChildren().addAll(lcolor,cp);


		//---------------------GESTION DES EVENEMENTS------------------


		importer.setTitle("Selectionner un fichier 3D");
		b1.setOnAction(e -> {
			filePly = importer.showOpenDialog(primaryStage);
			if(filePly != null) {
				String extension = "";
				int i = 0;
				while (filePly.getPath().charAt(i) != '.')
					i++;
				extension = filePly.getPath().substring(i, filePly.getPath().length());	

				try {
					if (!extension.equals(".ply"))
						throw new WrongFormatFileException();
					else {
						gc.clearRect(0, 0, 1600, 800);
						loadFile = new LoadFile(filePly);
					}
				} catch (WrongFormatFileException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), e1.getTitle(), JOptionPane.ERROR_MESSAGE);
					System.exit(1);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Erreur", "Erreur Fichier", JOptionPane.ERROR_MESSAGE);
					System.exit(1);
				}
				init = null;
				try {
					init = new Initialisation(filePly);
					tabp = loadFile.getPoints();
					tabf = loadFile.getFaces();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Erreur", "Erreur Fichier", JOptionPane.ERROR_MESSAGE);
					System.exit(1);
				}
				try {
					dessin(gc, loadFile);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Erreur", "Erreur Fichier", JOptionPane.ERROR_MESSAGE);
					System.exit(1);
				}	
			}
		});

		cp.setOnAction(e ->{
			if(filePly != null) {
				couleur = cp.getValue();
				init.creerFigure(gc, loadFile.getFaces(), couleur);
			}
		});


		tournerX.setOnMouseDragged(e-> {
			if(filePly != null)
				miseAJourVue(gc, tournerX.getValue(), tournerY.getValue(), tournerZ.getValue(), zoom.getValue());
		});
		
		tournerX.setOnMouseClicked(e-> {
			if(filePly != null)
				miseAJourVue(gc, tournerX.getValue(), tournerY.getValue(), tournerZ.getValue(), zoom.getValue());
		});

		tournerY.setOnMouseDragged(e ->{
			if(filePly != null)
				miseAJourVue(gc, tournerX.getValue(), tournerY.getValue(), tournerZ.getValue(), zoom.getValue());
		});
		
		tournerY.setOnMouseClicked(e ->{
			if(filePly != null)
				miseAJourVue(gc, tournerX.getValue(), tournerY.getValue(), tournerZ.getValue(), zoom.getValue());
		});

		tournerZ.setOnMouseDragged(e -> {
			if(filePly != null)
				miseAJourVue(gc, tournerX.getValue(), tournerY.getValue(), tournerZ.getValue(), zoom.getValue());
		});
		
		tournerZ.setOnMouseClicked(e -> {
			if(filePly != null)
				miseAJourVue(gc, tournerX.getValue(), tournerY.getValue(), tournerZ.getValue(), zoom.getValue());
		});

		zoom.setOnMouseDragged(e -> {
			if(filePly != null)
				miseAJourVue(gc, tournerX.getValue(), tournerY.getValue(), tournerZ.getValue(), zoom.getValue());
		});
		
		zoom.setOnMouseClicked(e -> {
			if(filePly != null)
				miseAJourVue(gc, tournerX.getValue(), tournerY.getValue(), tournerZ.getValue(), zoom.getValue());
		});

		gauche.setOnAction(e->{
			if(filePly != null) {
				cptTranslateGD += 10;
				miseAJourVue(gc, tournerX.getValue(), tournerY.getValue(), tournerZ.getValue(), zoom.getValue());
			}
		});

		droite.setOnAction(e->{
			if(filePly != null) {
				cptTranslateGD -= 10;
				miseAJourVue(gc, tournerX.getValue(), tournerY.getValue(), tournerZ.getValue(), zoom.getValue());
			}
		});

		//----AFFICHAGE FENETRE------
		primaryStage.setScene(scene);
		primaryStage.setTitle("i3D"); 
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	/**
	 * Retourne le fichier .ply
	 * @return
	 */
	public File getFichier() {
		return filePly;
	}

	/**
	 * Définit le fichier .ply à  utiliser
	 * @param fichier
	 */
	public void setFichier(File fichier) {
		this.filePly = fichier;
	}

	/**
	 * Méthode lançant le programme.
	 * @param args
	 */
	public static void main(String[] args) {
		Application.launch(args);
	}

	/**
	 * Dessine la figure à partir de la création dans Initialisation.
	 * @param gc
	 * @param file
	 * @throws IOException
	 */
	public void dessin(GraphicsContext gc, LoadFile file) throws IOException{
		gc.setLineWidth(1); //epaisseur des lignes
		try {
			file.creerPoints();
			file.creerFaces();
		} catch (WrongPointLineFormatException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), e.getTitle(), JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		} catch (WrongFaceLineFormatException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), e.getTitle(), JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		init.creerFigure(gc, file.getFaces(), couleur);
	}

	/**
	 * 
	 * @param gc
	 * @param xvalue
	 * @param zoomvalue
	 */
	public void miseAJourVue(GraphicsContext gc, double xValue, double yValue, double zValue, double zoomValue) {
		Translation translation = new Translation();
		Rotation rotation = new Rotation();
		Face[] tabf = loadFile.getFaces();
		Point[] tabp = loadFile.getPoints();
		Zoom zoom = new Zoom();

		try {
			tabp = rotation.creerPointRotate(xValue, tabp, 1);
		} catch (MatriceNullException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(), e1.getTitle(), JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		} catch (MatriceFormatException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(), e1.getTitle(), JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		rotation.recopiePoint(tabf, tabp);

		try {
			tabp = rotation.creerPointRotate(yValue, tabp, 0);
		} catch (MatriceNullException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(), e1.getTitle(), JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		} catch (MatriceFormatException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(), e1.getTitle(), JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		rotation.recopiePoint(tabf, tabp);

		try {
			tabp = rotation.creerPointRotate(zValue, tabp, 2);
		} catch (MatriceNullException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(), e1.getTitle(), JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		} catch (MatriceFormatException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(), e1.getTitle(), JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		rotation.recopiePoint(tabf, tabp);

		try {
			tabp = zoom.creerPointZoom(zoomValue, tabp);
		} catch (MatriceNullException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), e.getTitle(), JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		} catch (MatriceFormatException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), e.getTitle(), JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		rotation.recopiePoint(tabf, tabp);

		try {
			tabp = translation.creerPointsTranslate(cptTranslateGD, 0, tabp);
		} catch (MatriceNullException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), e.getTitle(), JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		} catch (MatriceFormatException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), e.getTitle(), JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		rotation.recopiePoint(tabf, tabp);

		gc.clearRect(0, 0, 1280, 600);
		init.creerFigure(gc, tabf,couleur);
	}
}