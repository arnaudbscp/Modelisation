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
import exception.MissingFaceLineException;
import exception.MissingPointLineException;
import exception.TooMuchFaceLineException;
import exception.TooMuchPointLineException;
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

	StrategyRotationX stratX = new StrategyRotationX(0);
	StrategyRotationY stratY = new StrategyRotationY(0);
	StrategyRotationZ stratZ = new StrategyRotationZ(0);

	boolean flagX = false;
	boolean flagY = false;
	boolean flagZ = false;

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
		Slider sliderRotation = new Slider();
		Label lblTournerX = new Label();
		lblTournerX.setText("Tourner X");
		//lblTournerX.setStyle("-fx-padding : 20 0 0 50;");
		lblTournerX.setPadding(new Insets(50,1,1,50));
		sliderRotation.setMin(0);
		sliderRotation.setMax(360);
		sliderRotation.setMajorTickUnit(90);
		sliderRotation.setValue(0);
		sliderRotation.setShowTickLabels(true);
		//tournerX.setPadding(new Insets(50,1,1,1));
		menu.getChildren().addAll(lblTournerX, sliderRotation);


		//BOUTON DIRECTIONNELLE ROTATION

		Button X = new Button();
		X.setText("X");
		Button Y = new Button();
		Y.setText("Y");
		Button Z = new Button();
		Z.setText("Z");
		HBox alignementbutton = new HBox();
		alignementbutton.getChildren().add(X);
		alignementbutton.getChildren().add(Y);
		alignementbutton.getChildren().add(Z);
		alignementbutton.setPadding(new Insets(25,1,1,10));
		alignementbutton.setSpacing(30);
		menu.getChildren().addAll(alignementbutton);


		//CROIX DIRECTIONNELLE TRANSLATION
		Label lblTranslation = new Label("Translation");
		lblTranslation.setPadding(new Insets(30,1,1,45));
		HBox hbHaut = new HBox();
		HBox hbGaucheDroite = new HBox();
		HBox hbBas = new HBox();
		Button haut = new Button("H");
		Button gauche = new Button("G");
		Button droite = new Button("D");
		Button bas = new Button("B");
		hbHaut.setPadding(new Insets(10, 0, 0, 60));
		hbBas.setPadding(new Insets(0, 0, 0,60));
		hbGaucheDroite.setPadding(new Insets(0, 0, 0,33));
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
		lcolor.setPadding(new Insets(30, 0, 10,50));
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
				cptTranslateGD = 0;
				cptTranslateHB = 0;
				cp.setValue(Color.WHITE);
				//On simule un premier mouvement de la figure pour que tous les mouvements fonctionnent correctement.
				miseAJourVue(gc, sliderRotation.getValue(), zoom.getValue());
			}
		});

		cp.setOnAction(e ->{
			if(filePly != null) {
				couleur = cp.getValue();
				init.creerFigure(gc, loadFile.getFaces(), couleur);
			}
		});



		X.setOnAction(e -> {
			lblTournerX.setText("Tourner X");
			sliderRotation.setValue(stratX.getValeurrotation());
			flagX = true;
			flagY = false;
			flagZ = false;
		});
		Y.setOnAction(e -> {
			lblTournerX.setText("Tourner Y");
			sliderRotation.setValue(stratY.getValeurrotation());
			flagY = true;
			flagX = false;
			flagZ = false;
		});
		Z.setOnAction(e -> {
			lblTournerX.setText("Tourner Z");
			sliderRotation.setValue(stratZ.getValeurrotation());
			flagZ = true;
			flagX = false;
			flagY = false;
		});
		sliderRotation.setOnMouseDragged(e-> {
			miseAJourVue(gc, sliderRotation.getValue(), zoom.getValue());
		});

		sliderRotation.setOnMouseClicked(e-> {
			miseAJourVue(gc, sliderRotation.getValue(),  zoom.getValue());
		});

		zoom.setOnMouseDragged(e -> {
			miseAJourVue(gc, sliderRotation.getValue(), zoom.getValue());
		});

		zoom.setOnMouseClicked(e -> {
			miseAJourVue(gc, sliderRotation.getValue(), zoom.getValue());
		});

		gauche.setOnAction(e->{
			cptTranslateGD += 10;
			miseAJourVue(gc, sliderRotation.getValue(), zoom.getValue());
		});

		gauche.setOnMouseDragged(e->{
			cptTranslateGD += 10;
			miseAJourVue(gc, sliderRotation.getValue(), zoom.getValue());
		});

		droite.setOnAction(e->{
			cptTranslateGD -= 10;
			miseAJourVue(gc, sliderRotation.getValue(), zoom.getValue());
		});

		droite.setOnMouseDragged(e->{
			cptTranslateGD -= 10;
			miseAJourVue(gc, sliderRotation.getValue(),zoom.getValue());
		});

		haut.setOnAction(e->{
			cptTranslateHB += 10;
			miseAJourVue(gc, sliderRotation.getValue(), zoom.getValue());
		});

		haut.setOnMouseDragged(e->{
			cptTranslateHB += 10;
			miseAJourVue(gc, sliderRotation.getValue(), zoom.getValue());
		});

		bas.setOnAction(e->{
			cptTranslateHB -= 10;
			miseAJourVue(gc, sliderRotation.getValue(), zoom.getValue());
		});

		bas.setOnMouseDragged(e->{
			cptTranslateHB -= 10;
			miseAJourVue(gc, sliderRotation.getValue(), zoom.getValue());
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
		} catch (MissingPointLineException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), e.getTitle(), JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		} catch (TooMuchPointLineException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), e.getTitle(), JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		} catch (MissingFaceLineException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), e.getTitle(), JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		} catch (TooMuchFaceLineException e) {
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
	public void miseAJourVue(GraphicsContext gc, double rotationvalue, double zoomValue) {
		if(filePly!=null) {
			Translation translation = new Translation();
			Rotation rotation = new Rotation();
			Face[] tabf = loadFile.getFaces();
			Point[] tabp = loadFile.getPoints();
			Zoom zoom = new Zoom();

			try {
				if(flagX) {
					tabp = rotation.creerPointRotate(rotationvalue, tabp, stratX.execute());
					stratX.setValeurrotation(rotationvalue);
				}else {
					tabp = rotation.creerPointRotate(stratX.getValeurrotation(), tabp, stratX.execute());
				}
			} catch (MatriceNullException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), e1.getTitle(), JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			} catch (MatriceFormatException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), e1.getTitle(), JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
			rotation.recopiePoint(tabf, tabp);

			try {
				if(flagY) {
					tabp = rotation.creerPointRotate(rotationvalue, tabp, stratY.execute());
					stratY.setValeurrotation(rotationvalue);
				}else {
					tabp = rotation.creerPointRotate(stratY.getValeurrotation(), tabp, stratY.execute());
				}
			} catch (MatriceNullException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), e1.getTitle(), JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			} catch (MatriceFormatException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), e1.getTitle(), JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
			rotation.recopiePoint(tabf, tabp);

			try {
				if(flagZ) {
				tabp = rotation.creerPointRotate(rotationvalue, tabp, stratZ.execute());
				stratZ.setValeurrotation(rotationvalue);
				}else {
					tabp = rotation.creerPointRotate(stratZ.getValeurrotation(), tabp, stratZ.execute());
				}
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
			zoom.recopiePoint(tabf, tabp);

			try {
				tabp = translation.creerPointsTranslate(cptTranslateGD, cptTranslateHB, tabp);
			} catch (MatriceNullException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), e.getTitle(), JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			} catch (MatriceFormatException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), e.getTitle(), JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
			translation.recopiePoint(tabf, tabp);

			gc.clearRect(0, 0, 1280, 600);
			init.creerFigure(gc, tabf,couleur);
		}
	}
}