package gui;

import java.util.Locale;
import java.util.ResourceBundle;

import domein.DomeinController;
import domein.Splendor;
import exceptions.OngeldigeGeboortejaar;
import exceptions.OngeldigeGebruikersnaam;
import exceptions.OnvoldoendeEfVoorOkException;
import exceptions.SpelerAlToegevoegdException;
import exceptions.SpelerBestaatNietException;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TaalEnVoegSpelerScherm extends Stage {

	private static final String DEFAULT_LANGUAGE = "nl";
	private final DomeinController dc;
	private int aantalSpelers = 0;
	public static ResourceBundle resources;
	private Label gebNaam;
	private Label gebJaarL;
	private Label lblfoutMelding;
	private Label lblfoutMeldingGeboortejaar;
	private Button startSpel;
	private Button addSpeler;
	private TextField inputName;
	private TextField inputJaarL;

	public TaalEnVoegSpelerScherm(DomeinController dc) {

		this.dc = dc;
		// Load resource bundle (with default language)
		resources = ResourceBundle.getBundle("resources.text", new Locale(DEFAULT_LANGUAGE));

		Group group1 = new Group();
		Scene scene1 = new Scene(group1, 534, 300);

		Group group2 = new Group();
		Scene scene2 = new Scene(group2, 447, 320);

		inputName = new TextField();
		inputJaarL = new TextField();
		new Tooltip();

		
		// Scene1 The Welkom Schem en Taal Selectie
		// --------------------------------------------------

		Label welkomL = new Label("Welkom bij Splendor"); // "Welkom bij Splendor"
		welkomL.setFont(new Font(31));
		welkomL.setTextFill(javafx.scene.paint.Color.web("#272bb5"));
		welkomL.setLayoutX(131);
		welkomL.setLayoutY(66);
		group1.getChildren().add(welkomL);

		Label languagePrompt = new Label("Gelieve Taal te kiezen / Please choose Language");
		languagePrompt.setLayoutX(147);
		languagePrompt.setLayoutY(170);
		group1.getChildren().add(languagePrompt);

		Button btnNL = new Button("Nederlands");
		btnNL.setOnAction(e -> {
			setTitle("Splendor");
			// Load the Dutch language resource bundle
			resources = ResourceBundle.getBundle("resources.text", new Locale("nl"));
			dc.taalSelectie("nl");
			setScene(scene2);
			updateLabels();
		});
		btnNL.setLayoutX(147);
		btnNL.setLayoutY(227);
		group1.getChildren().add(btnNL);

		Button btnEN = new Button("English");
		btnEN.setOnAction(e -> {
			setTitle("Adding Players");
			// Load the English language resource bundle
			resources = ResourceBundle.getBundle("resources.text", new Locale("en"));
			dc.taalSelectie("en");
			setScene(scene2);
			updateLabels();
		});
		btnEN.setLayoutX(320);
		btnEN.setLayoutY(227);
		group1.getChildren().add(btnEN);

		// Scene1 End
		// -----------------------------------------Button(resources.getString("StartSpel"));

		// Scene2 De Speler Toevoegen Scherm -----------------------------------------

//		gebNaam = new Label(resources.getString("Gebruikersnaam"));
//		gebNaam.setFont(new Font(19));
//		gebNaam.setLayoutX(25);
//		gebNaam.setLayoutY(230);
//		gebNaam.setPrefHeight(44);
//		gebNaam.setPrefWidth(147);
//		group2.getChildren().add(gebNaam);

		inputName = new TextField();
		inputName.setLayoutX(34);
		inputName.setLayoutY(45);
		inputName.setPrefHeight(40);
		inputName.setPrefWidth(380);
		inputName.setPromptText(resources.getString("Gebruikersnaam"));
		group2.getChildren().add(inputName);

//		gebJaarL = new Label(resources.getString("Geboortejaar"));
//		gebJaarL.setFont(new Font(19));
//		// gebJaar.setTextFill(javafx.scene.paint.Color.web("#272bb5"));
//		gebJaarL.setLayoutX(34);
//		gebJaarL.setLayoutY(133);
//		gebJaarL.setPrefHeight(44);
//		gebJaarL.setPrefWidth(147);
//		group2.getChildren().add(gebJaarL);

		inputJaarL = new TextField();
		inputJaarL.setLayoutX(34);
		inputJaarL.setLayoutY(113);
		inputJaarL.setPrefHeight(40);
		inputJaarL.setPrefWidth(380);
		inputJaarL.setPromptText(resources.getString("Geboortejaar"));
		group2.getChildren().add(inputJaarL);

		lblfoutMelding = new Label();
		lblfoutMelding.setLayoutX(34);
		lblfoutMelding.setLayoutY(160);
		lblfoutMelding.setPrefHeight(40);
		lblfoutMelding.setPrefWidth(380);
		lblfoutMelding.setTextFill(Color.RED);
		lblfoutMelding.setFont(new Font(12));
		group2.getChildren().add(lblfoutMelding);

		startSpel = new Button(resources.getString("StartSpel"));
		startSpel.setOnAction(e -> {
			dc.startSpel();
			startSpel();
			
			
		});
		startSpel.setLayoutX(34);
		startSpel.setLayoutY(244);
		startSpel.setPrefHeight(34);
		startSpel.setPrefWidth(380);
		group2.getChildren().add(startSpel);
		startSpel.setVisible(false);

		addSpeler = new Button(resources.getString("Addspeler"));
		addSpeler.setOnAction(e -> {
			voegSpelerToe();
			aantalSpelers = dc.geefAantalSpelers();
			if (aantalSpelers > 1) {
				startSpel.setVisible(true);
			}
		});

		addSpeler.setLayoutX(34);
		addSpeler.setLayoutY(204);
		addSpeler.setPrefHeight(34);
		addSpeler.setPrefWidth(380);
		group2.getChildren().add(addSpeler);

		Button backButton = new Button("back");
		backButton.setOnAction(e -> setScene(scene1));
		backButton.setLayoutX(362);
		backButton.setLayoutY(14);
		group2.getChildren().add(backButton);
		backButton.setVisible(false); // for Testing

//      Scene2   De Speler Toevoegen Scherm   END       ---------------

		setScene(scene1);
		show();
	}

	private void startSpel() {
		SplendorController sc = new SplendorController(dc); // <1>
		Scene scene = new Scene(sc, 1920, 1080.0);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}

	private void updateLabels() {
		startSpel.setText(resources.getString("StartSpel"));
		addSpeler.setText(resources.getString("Addspeler"));
		inputName.setPromptText(resources.getString("Gebruikersnaam"));
		inputJaarL.setPromptText(resources.getString("Geboortejaar"));
	}

	public void start() {

		voegSpelerToe();
		int aantalSpelers = dc.geefAantalSpelers();
		if (aantalSpelers == 4) {
			try {
				dc.speel();
			} catch (OnvoldoendeEfVoorOkException e) {
				e.getMessage();
			}
		}
	}

	private void voegSpelerToe() {

		final Tooltip tooltip = new Tooltip();

		try {
			String gebruikersnaam = inputName.getText();
			int geboortejaar = Integer.parseInt(inputJaarL.getText());
			dc.geefSpelerUitDB(gebruikersnaam, geboortejaar);

			lblfoutMelding.setText(null);
			inputName.clear();
			inputJaarL.clear();
			controleerAantalSpelers();

		} catch (OngeldigeGebruikersnaam e) {
			lblfoutMelding.setText(null);
			tooltip.setText(e.getMessage());
			lblfoutMelding.setText(resources.getString("OngeldigeGebruikersnaam"));

		} catch (OngeldigeGeboortejaar e) {
			lblfoutMelding.setText(null);
			tooltip.setText(e.getMessage());
			lblfoutMelding.setText(resources.getString("OngeldigeGeboortejaar"));

		} catch (NumberFormatException e) {
			lblfoutMelding.setText(null);
			lblfoutMelding.setText(resources.getString("leegvoegspelertoe"));

		} catch (SpelerAlToegevoegdException e) {
			lblfoutMelding.setText(null);
			lblfoutMelding.setText(resources.getString("SpelerAlToegevoegdException"));
			
		} catch (SpelerBestaatNietException e) {
			lblfoutMelding.setText(null);
			lblfoutMelding.setText(resources.getString("SpelerBestaatNietException"));
		}
	}

	private void controleerAantalSpelers() {
		aantalSpelers = dc.geefAantalSpelers();
		if (aantalSpelers == Splendor.MAX_AANTAL_SPELERS) {
			addSpeler.setVisible(false);
			inputName.setDisable(true);
			inputJaarL.setDisable(true);
			startSpel.setPrefWidth(310);
		}
	}


}
