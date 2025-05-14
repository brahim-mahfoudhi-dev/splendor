package gui;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import domein.DomeinController;
import domein.Edele;
import domein.Ontwikkelingskaart;
import domein.Speler;
import dto.EdeleDTO;
import dto.OntwikkelingskaartDTO;
import dto.SpelerDTO;
import dto.WinnaarDTO;
import exceptions.OnvoldoendeEfVoorOkException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import util.EdelsteenFiche;
import gui.TaalEnVoegSpelerScherm;

public class SplendorController extends BorderPane {

	private final DomeinController dc;
	String language = TaalEnVoegSpelerScherm.resources.getLocale().toString();

	private ResourceBundle resources = ResourceBundle.getBundle("resources.text", new Locale(language));

	private Map<EdelsteenFiche, Integer> edelsteenOpSpeelbord;

	@FXML
	private ImageView edele1;

	@FXML
	private ImageView edele2;

	@FXML
	private ImageView edele3;

	@FXML
	private ImageView edele4;

	@FXML
	private ImageView edele5;

	@FXML
	private Label aantalBlauw;

	@FXML
	private Label aantalGroen;

	@FXML
	private Label aantalRood;

	@FXML
	private Label lbLError;

	@FXML
	private Label aantalWit;

	@FXML
	private Label aantalZwart;

	@FXML
	private Label aantalNiveau1;

	@FXML
	private Label aantalNiveau2;

	@FXML
	private Label aantalNiveau3;

	@FXML
	private Button btn2Edelsteen;

	@FXML
	private Button btn3Edelsteen;

	@FXML
	private Button btnInvetory;

	@FXML
	private Button btnKoopOntwikkelingskaart;

	@FXML
	private Button btnPasBeurt;

	@FXML
	private Label keuzeText;

	@FXML
	private Circle blackSteen;

	@FXML
	private Circle blueSteen;

	@FXML
	private Circle greenSteen;

	@FXML
	private ListView<String> lijst;

	@FXML
	private ImageView lvl1Downside;

	@FXML
	private ImageView lvl1Kaart1;

	@FXML
	private ImageView lvl1Kaart2;

	@FXML
	private ImageView lvl1Kaart3;

	@FXML
	private ImageView lvl1Kaart4;

	@FXML
	private ImageView lvl2Downside;

	@FXML
	private ImageView lvl2Kaart1;

	@FXML
	private ImageView lvl2Kaart2;

	@FXML
	private ImageView lvl2Kaart3;

	@FXML
	private ImageView lvl2Kaart4;

	@FXML
	private ImageView lvl3Downside;

	@FXML
	private ImageView lvl3Kaart1;

	@FXML
	private ImageView lvl3Kaart2;

	@FXML
	private ImageView lvl3Kaart3;

	@FXML
	private ImageView lvl3Kaart4;

	@FXML
	private Circle redSteen;

	@FXML
	private Circle witeSteen;

	@FXML
	private GridPane gridCards;

	@FXML
	private GridPane gridEdele;

	@FXML
	private GridPane gridEdelsteen;

	private int huidigeAantal = 0;
	private List<OntwikkelingskaartDTO> ontwikkelingskaartenNiv3;
	private List<OntwikkelingskaartDTO> ontwikkelingskaartenNiv2;
	private List<OntwikkelingskaartDTO> ontwikkelingskaartenNiv1;
	private LinkedList<OntwikkelingskaartDTO> ontwikkelingskaartenNiv3OpSpeelbord;
	private LinkedList<OntwikkelingskaartDTO> ontwikkelingskaartenNiv2OpSpeelbord;
	private LinkedList<OntwikkelingskaartDTO> ontwikkelingskaartenNiv1OpSpeelbord;
	private List<ImageView> alleEdele;

	private int countWit = 0;
	private int countRood = 0;
	private int countBlauw = 0;
	private int countGroen = 0;
	private int countZwart = 0;

	/**
	 * -----------------!--------------- Spel componenten doorgeven via DTO
	 **/

	public SplendorController(DomeinController dc) {

		this.dc = dc;
		lijst = new ListView<>();
		this.witeSteen = new Circle();
		this.redSteen = new Circle();
		this.blueSteen = new Circle();
		this.greenSteen = new Circle();
		this.blackSteen = new Circle();
		this.edele1 = new ImageView();
		this.edele2 = new ImageView();
		this.edele3 = new ImageView();
		this.edele4 = new ImageView();
		this.edele5 = new ImageView();
		this.lvl3Downside = new ImageView();
		this.lvl3Kaart1 = new ImageView();
		this.lvl3Kaart2 = new ImageView();
		this.lvl3Kaart3 = new ImageView();
		this.lvl3Kaart4 = new ImageView();
		this.aantalWit = new Label();
		this.aantalRood = new Label();
		this.aantalBlauw = new Label();
		this.aantalGroen = new Label();
		this.aantalZwart = new Label();
		aantalNiveau1 = new Label();
		aantalNiveau2 = new Label();
		aantalNiveau3 = new Label();
		this.keuzeText = new Label();
		btnInvetory = new Button();
		alleEdele = new ArrayList<>();
		alleEdele.add(edele1);
		alleEdele.add(edele2);
		alleEdele.add(edele3);
		alleEdele.add(edele4);
		alleEdele.add(edele5);

		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("Splendor.fxml"));
		loader.setController(this);
		loader.setRoot(this);

		try {
			loader.load();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("error somewhere");
		}

		try {
			dc.initialiseerSplendor();
			edelsteenOpSpeelbord = dc.geefEdelsteenFicheOpSpeelbord();
			ontwikkelingskaartenNiv3 = dc.geefOntwikkelingsNiv3kaart();
			ontwikkelingskaartenNiv2 = dc.geefOntwikkelingsNiv2kaart();
			ontwikkelingskaartenNiv1 = dc.geefOntwikkelingsNiv1kaart();
			ontwikkelingskaartenNiv3OpSpeelbord = new LinkedList<>(dc.geefOntwikkelingsNiv3OpSpeelbordkaart());
			ontwikkelingskaartenNiv2OpSpeelbord = new LinkedList<>(dc.geefOntwikkelingsNiv2OpSpeelbordkaart());
			ontwikkelingskaartenNiv1OpSpeelbord = new LinkedList<>(dc.geefOntwikkelingsNiv1OpSpeelbordkaart());
			initialiseerBord();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		btnInvetory.setOnAction(e -> {
			InventoryController scene = new InventoryController(dc);
			scene.setTitle("Inventory");
			scene.setResizable(false);
			scene.show();
		});

		updateSpelerOverzicht();
		speelBeurt();
		btnKoopOntwikkelingskaart.setDisable(true);

	}

	private void speelBeurt() {

		btn3Edelsteen.setOnAction(e -> {
			lbLError.setText(null);
			btn2Edelsteen.setDisable(true);
			btnKoopOntwikkelingskaart.setDisable(true);
			// btnPasBeurt.setDisable(true);
//			controleerMaxAantalStenen();
			enableStenen();
			btn3EdelsteenFiches();
			geenEdelsteenFichesMeer();

		});

		btn2Edelsteen.setOnAction(e -> {
			lbLError.setText(null);
			btn3Edelsteen.setDisable(true);
			btnKoopOntwikkelingskaart.setDisable(true);
			btnPasBeurt.setDisable(true);
//			controleerMaxAantalStenen();
			enableStenen();
			btn2EdelsteenFiches();
			geenEdelsteenFichesMeer();
			controleer2EdelsteenFichesMagNietNemen();

		});

		btnKoopOntwikkelingskaart.setOnAction(e -> {
			lbLError.setText(null);
			btn3Edelsteen.setDisable(true);
			btn2Edelsteen.setDisable(true);
			btnPasBeurt.setDisable(true);
			koopOntwikkelingskaart();
		});

		btnPasBeurt.setOnAction(e -> {
			lbLError.setText(null);
			btn3Edelsteen.setDisable(true);
			btn2Edelsteen.setDisable(true);
			btnKoopOntwikkelingskaart.setDisable(true);
			pasBeurt();
		});

		enableKeuzeButtons();
		controleerWinnaar();
	}

	private void controleerMaxAantalStenen() {
		Map<EdelsteenFiche, Integer> edelstenen = dc.geefEdelsteenFicheInBezit();
		for (Entry<EdelsteenFiche, Integer> aantalEdelstenen : dc.geefEdelsteenFicheInBezit().entrySet()) {
			if (aantalEdelstenen.getValue() >= 9) {
				disableButton(aantalEdelstenen.getKey());
			} else {
				enableStenen();
			}
		}
	}

	/**
	 * 
	 * @param
	 * 
	 * 
	 * 
	 **/
	private void initialiseerBord() throws URISyntaxException {
		/** Buttons **/
		btnKoopOntwikkelingskaart.setText(resources.getString("KoopKaart"));
		btn3Edelsteen.setText(resources.getString("btn3Edelsteen"));
		btn2Edelsteen.setText(resources.getString("btn2Edelsteen"));
		btnPasBeurt.setText(resources.getString("btnPasBeurt"));
		/** Text Label **/
		keuzeText.setText(resources.getString("keuzeText"));
		/** EdelsteenFiches **/
		initialiseerEdelsteenFiche();
		/** Edele **/
		initialiseerEdele();
		/** Ontwikkelingskaarten **/
		initialiseerOntwikkelingskaarten();
		updateSpelerOverzicht();
	}

	private void initialiseerEdelsteenFiche() throws URISyntaxException {
		/** Edelsteenfiches **/
		Image white = new Image(getClass().getResource("/images/Edelsteenfiche/Witte.png").toURI().toString());
		witeSteen.setFill(new ImagePattern(white));
		Image red = new Image(getClass().getResource("/images/Edelsteenfiche/Red.png").toURI().toString());
		redSteen.setFill(new ImagePattern(red));
		Image blue = new Image(getClass().getResource("/images/Edelsteenfiche/Blue.png").toURI().toString());
		blueSteen.setFill(new ImagePattern(blue));
		Image green = new Image(getClass().getResource("/images/Edelsteenfiche/Green.png").toURI().toString());
		greenSteen.setFill(new ImagePattern(green));
		Image black = new Image(getClass().getResource("/images/Edelsteenfiche/Black.png").toURI().toString());
		blackSteen.setFill(new ImagePattern(black));

		/** label aantal EdelsteenFiches **/
		Map<EdelsteenFiche, Integer> edelsteenOpSpeelbord = dc.geefEdelsteenFicheOpSpeelbord();
		aantalWit.setText(Integer.toString(edelsteenOpSpeelbord.get(EdelsteenFiche.DIAMANTEN).intValue()));
		aantalRood.setText(Integer.toString(edelsteenOpSpeelbord.get(EdelsteenFiche.ROBIJNEN).intValue()));
		aantalBlauw.setText(Integer.toString(edelsteenOpSpeelbord.get(EdelsteenFiche.SAFFIEREN).intValue()));
		aantalGroen.setText(Integer.toString(edelsteenOpSpeelbord.get(EdelsteenFiche.SMARAGDEN).intValue()));
		aantalZwart.setText(Integer.toString(edelsteenOpSpeelbord.get(EdelsteenFiche.ONYXEN).intValue()));

	}

	private void initialiseerOntwikkelingskaarten() throws URISyntaxException {
		Image downsideLvl3 = new Image(getClass().getResource("/images/backside/lvl3.png").toURI().toString());
		Image downsideLvl2 = new Image(getClass().getResource("/images/backside/lvl2.png").toURI().toString());
		Image downsideLvl1 = new Image(getClass().getResource("/images/backside/lvl1.png").toURI().toString());
		lvl3Downside.setImage(downsideLvl3);
		lvl2Downside.setImage(downsideLvl2);
		lvl1Downside.setImage(downsideLvl1);
		lvl3Kaart1.setImage(ontwikkelingskaartenNiv3OpSpeelbord.get(0).image());
		lvl3Kaart2.setImage(ontwikkelingskaartenNiv3OpSpeelbord.get(1).image());
		lvl3Kaart3.setImage(ontwikkelingskaartenNiv3OpSpeelbord.get(2).image());
		lvl3Kaart4.setImage(ontwikkelingskaartenNiv3OpSpeelbord.get(3).image());
		lvl2Kaart1.setImage(ontwikkelingskaartenNiv2OpSpeelbord.get(0).image());
		lvl2Kaart2.setImage(ontwikkelingskaartenNiv2OpSpeelbord.get(1).image());
		lvl2Kaart3.setImage(ontwikkelingskaartenNiv2OpSpeelbord.get(2).image());
		lvl2Kaart4.setImage(ontwikkelingskaartenNiv2OpSpeelbord.get(3).image());
		lvl1Kaart1.setImage(ontwikkelingskaartenNiv1OpSpeelbord.get(0).image());
		lvl1Kaart2.setImage(ontwikkelingskaartenNiv1OpSpeelbord.get(1).image());
		lvl1Kaart3.setImage(ontwikkelingskaartenNiv1OpSpeelbord.get(2).image());
		lvl1Kaart4.setImage(ontwikkelingskaartenNiv1OpSpeelbord.get(3).image());
		aantalNiveau3.setText(Integer.toString(dc.geefOntwikkelingsNiv3kaart().size()));
		aantalNiveau2.setText(Integer.toString(dc.geefOntwikkelingsNiv2kaart().size()));
		aantalNiveau1.setText(Integer.toString(dc.geefOntwikkelingsNiv1kaart().size()));

	}

	private void initialiseerEdele() {
		List<Edele> edele = dc.geefEdeleOpSpeelbord();
		int aantalSpelers = dc.geefAantalSpelers();

		switch (aantalSpelers) {
		case 2 -> {
			edele1.setImage(edele.get(0).getImage());
			edele2.setImage(edele.get(1).getImage());
			edele3.setImage(edele.get(2).getImage());
			edele4.setVisible(false);
			edele5.setVisible(false);
		}
		case 3 -> {
			edele1.setImage(edele.get(0).getImage());
			edele2.setImage(edele.get(1).getImage());
			edele3.setImage(edele.get(2).getImage());
			edele4.setImage(edele.get(3).getImage());
			edele5.setVisible(false);
		}
		case 4 -> {
			edele1.setImage(edele.get(0).getImage());
			edele2.setImage(edele.get(1).getImage());
			edele3.setImage(edele.get(2).getImage());
			edele4.setImage(edele.get(3).getImage());
			edele5.setImage(edele.get(4).getImage());
		}
		}
	}

	private void updateSpelerOverzicht() {
		List<SpelerDTO> overzichtSpeler = dc.geefOverzichtSpelers();
		List<String> overzicht = new ArrayList<>();

		for (SpelerDTO s : overzichtSpeler) {
			overzicht.add(String.format("%s%s%n%s%d%n%s%s%n%s%s", resources.getString("GebruikersnaamOverzicht"),
					s.gebruiksnaam(), resources.getString("TotaalPrestigePunten"), s.totaalAantalPrestigePunten(),
					resources.getString("AanBeurt"),
					s.aanDeBeurt() == true ? resources.getString("ja") : resources.getString("nee"),
					resources.getString("StartSpel"),
					s.startSpeler() == true ? resources.getString("ja") : resources.getString("nee")));
		}

		ObservableList<String> lijstOverzichtSpelers = FXCollections.observableList(overzicht);
		lijst.setItems(lijstOverzichtSpelers);
		for (OntwikkelingskaartDTO o : ontwikkelingskaartenNiv3OpSpeelbord) {
			System.out.println("Niveau 3");
			System.out.println(String.format("%d | %d | %d | %d | %d", o.diamantcount(), o.robijnenCount(),
					o.saffierCount(), o.smaragdenCount(), o.onyxenCount()));
		}
		for (OntwikkelingskaartDTO o : ontwikkelingskaartenNiv2OpSpeelbord) {
			System.out.println("Niveau 2");
			System.out.println(String.format("%d | %d | %d | %d | %d", o.diamantcount(), o.robijnenCount(),
					o.saffierCount(), o.smaragdenCount(), o.onyxenCount()));
		}
		for (OntwikkelingskaartDTO o : ontwikkelingskaartenNiv1OpSpeelbord) {
			System.out.println("Niveau 1");
			System.out.println(String.format("%d | %d | %d | %d | %d", o.diamantcount(), o.robijnenCount(),
					o.saffierCount(), o.smaragdenCount(), o.onyxenCount()));
		}

		for (SpelerDTO s : overzichtSpeler) {
			System.out.println(String.format("%s%n%n", s.edelsteenfichesInBezit()));
			System.out.println(String.format("%s%n", s.edelsteenType()));

		}

		for (ImageView e : alleEdele) {

			for (SpelerDTO s : overzichtSpeler) {

				for (Edele eb : s.EdeleInBezit()) {

					if (eb.getImage().equals(e.getImage())) {

						e.setImage(null);
					}
				}
			}
		}
		updateAantalEdelstenen();

	}
	

	private void btn3EdelsteenFiches() {
		gridCards.setDisable(true);
		gridEdele.setDisable(true);
		Map<EdelsteenFiche, Integer> gekozenEdelsteenFiches = new HashMap<>();
		witeSteen.setOnMouseClicked(event -> {
			int aantal = Integer.parseInt(aantalWit.getText());
			if (controleerAantalEdelsteenFiches(aantal)) {
				if (!(aantalWit.isDisable())) {
					aantalWit.setText(String.format("%d", aantal > 0 ? aantal - 1 : 0));
				}
				neemEdelsteenFiche(EdelsteenFiche.DIAMANTEN);
				countWit += 1;
				gekozenEdelsteenFiches.put(EdelsteenFiche.DIAMANTEN, countWit);
				neem3EdelsteenFiches(gekozenEdelsteenFiches.size(), gekozenEdelsteenFiches);
				witeSteen.setDisable(true);
				aantalWit.setDisable(true);
			}
		});

		redSteen.setOnMouseClicked(event -> {
			int aantal = Integer.parseInt(aantalRood.getText());
			if (controleerAantalEdelsteenFiches(aantal)) {

				if (!(aantalRood.isDisable())) {
					aantalRood.setText(String.format("%d", aantal > 0 ? aantal - 1 : 0));
				}
				neemEdelsteenFiche(EdelsteenFiche.ROBIJNEN);
				countRood += 1;
				gekozenEdelsteenFiches.put(EdelsteenFiche.ROBIJNEN, countRood);
				neem3EdelsteenFiches(gekozenEdelsteenFiches.size(), gekozenEdelsteenFiches);
				redSteen.setDisable(true);
				aantalRood.setDisable(true);
			}
		});

		blueSteen.setOnMouseClicked(event -> {
			int aantal = Integer.parseInt(aantalBlauw.getText());
			if (controleerAantalEdelsteenFiches(aantal)) {
				if (!(aantalBlauw.isDisable())) {
					aantalBlauw.setText(String.format("%d", aantal > 0 ? aantal - 1 : 0));
				}
				neemEdelsteenFiche(EdelsteenFiche.SAFFIEREN);
				countBlauw += 1;
				gekozenEdelsteenFiches.put(EdelsteenFiche.SAFFIEREN, countBlauw);
				neem3EdelsteenFiches(gekozenEdelsteenFiches.size(), gekozenEdelsteenFiches);
				blueSteen.setDisable(true);
				aantalBlauw.setDisable(true);
			}
		});

		greenSteen.setOnMouseClicked(event -> {
			int aantal = Integer.parseInt(aantalGroen.getText());
			if (controleerAantalEdelsteenFiches(aantal)) {
				if (!(aantalGroen.isDisable())) {
					aantalGroen.setText(String.format("%d", aantal > 0 ? aantal - 1 : 0));
				}
				neemEdelsteenFiche(EdelsteenFiche.SMARAGDEN);
				countGroen += 1;
				gekozenEdelsteenFiches.put(EdelsteenFiche.SMARAGDEN, countGroen);
				neem3EdelsteenFiches(gekozenEdelsteenFiches.size(), gekozenEdelsteenFiches);
				greenSteen.setDisable(true);
				aantalGroen.setDisable(true);
			}
		});
		blackSteen.setOnMouseClicked(event -> {
			int aantal = Integer.parseInt(aantalZwart.getText());
			if (controleerAantalEdelsteenFiches(aantal)) {
				if (!(aantalZwart.isDisable())) {
					aantalZwart.setText(String.format("%d", aantal > 0 ? aantal - 1 : 0));
				}
				neemEdelsteenFiche(EdelsteenFiche.ONYXEN);
				countZwart += 1;
				gekozenEdelsteenFiches.put(EdelsteenFiche.ONYXEN, countZwart);
				neem3EdelsteenFiches(gekozenEdelsteenFiches.size(), gekozenEdelsteenFiches);
				blackSteen.setDisable(true);
				aantalZwart.setDisable(true);
			}
		});
	}

	private void geenEdelsteenFichesMeer() {

		int aantRed = Integer.parseInt(aantalRood.getText());
		int aantGreen = Integer.parseInt(aantalGroen.getText());
		int aantBlack = Integer.parseInt(aantalZwart.getText());
		int aantBlue = Integer.parseInt(aantalBlauw.getText());
		int aantWhite = Integer.parseInt(aantalWit.getText());

		if (aantWhite < 1) {
			witeSteen.setDisable(true);
		}
		if (aantGreen < 1) {
			greenSteen.setDisable(true);
		}
		if (aantBlack < 1) {
			blackSteen.setDisable(true);
		}
		if (aantBlue < 1) {
			blueSteen.setDisable(true);
		}
		if (aantRed < 1) {
			redSteen.setDisable(true);
		}
		if (aantWhite == 0 && aantGreen == 0 && aantBlack == 0 && aantBlue == 0 && aantRed == 0) {
			btn3Edelsteen.isDisabled();
			updateSpelerOverzicht();
		}

	}

	private void neem3EdelsteenFiches(int size, Map<EdelsteenFiche, Integer> gekozenEdelsteenFiches) {
		controleerAantalEdeleEnMaxEdelstenen();
		if (size == 3) {
			gridEdelsteen.setDisable(true);
			try {
				dc.neem3EdelsteenFiche(btn3Edelsteen.getId(), gekozenEdelsteenFiches);
			} catch (OnvoldoendeEfVoorOkException e) {
				System.err.println(e.getMessage());
			}
			enableKeuzeButtons();
			resetTeller();
			updateSpelerOverzicht();
			controleerWinnaar();
		}
	}

	private void btn2EdelsteenFiches() {
		gridCards.setDisable(true);
		gridEdele.setDisable(true);
		Map<EdelsteenFiche, Integer> gekozenEdelsteenFiches = new HashMap<>();
		witeSteen.setOnMouseClicked(event -> {
			huidigeAantal++;
			int aantal = Integer.parseInt(aantalWit.getText());
			if (controleerAantalEdelsteenFiches(aantal)) {
				if (!(aantalWit.isDisable())) {
					aantalWit.setText(String.format("%d", aantal > 0 ? aantal - 1 : 0));
				}
				countWit += 1;
				neemEdelsteenFiche(EdelsteenFiche.DIAMANTEN);
				gekozenEdelsteenFiches.put(EdelsteenFiche.DIAMANTEN, countWit);
				neem2EdelsteenFiches(gekozenEdelsteenFiches.size(), gekozenEdelsteenFiches);
				redSteen.setDisable(true);
				aantalRood.setDisable(true);
				blueSteen.setDisable(true);
				aantalBlauw.setDisable(true);
				greenSteen.setDisable(true);
				aantalGroen.setDisable(true);
				blackSteen.setDisable(true);
				aantalZwart.setDisable(true);
			}
		});

		redSteen.setOnMouseClicked(event -> {
			int aantal = Integer.parseInt(aantalRood.getText());
			if (controleerAantalEdelsteenFiches(aantal)) {
				if (!(aantalRood.isDisable())) {
					aantalRood.setText(String.format("%d", aantal > 0 ? aantal - 1 : 0));
				}
				neemEdelsteenFiche(EdelsteenFiche.ROBIJNEN);
				countRood += 1;
				gekozenEdelsteenFiches.put(EdelsteenFiche.ROBIJNEN, countRood);
				neem2EdelsteenFiches(gekozenEdelsteenFiches.size(), gekozenEdelsteenFiches);
				witeSteen.setDisable(true);
				aantalWit.setDisable(true);
				blueSteen.setDisable(true);
				aantalBlauw.setDisable(true);
				greenSteen.setDisable(true);
				aantalGroen.setDisable(true);
				blackSteen.setDisable(true);
				aantalZwart.setDisable(true);
			}
		});

		blueSteen.setOnMouseClicked(event -> {
			int aantal = Integer.parseInt(aantalBlauw.getText());
			if (controleerAantalEdelsteenFiches(aantal)) {
				if (!(aantalBlauw.isDisable())) {
					aantalBlauw.setText(String.format("%d", aantal > 0 ? aantal - 1 : 0));
				}
				neemEdelsteenFiche(EdelsteenFiche.SAFFIEREN);
				countBlauw += 1;
				gekozenEdelsteenFiches.put(EdelsteenFiche.SAFFIEREN, countBlauw);
				neem2EdelsteenFiches(gekozenEdelsteenFiches.size(), gekozenEdelsteenFiches);
				witeSteen.setDisable(true);
				aantalWit.setDisable(true);
				redSteen.setDisable(true);
				aantalRood.setDisable(true);
				greenSteen.setDisable(true);
				aantalGroen.setDisable(true);
				blackSteen.setDisable(true);
				aantalZwart.setDisable(true);
			}
		});

		greenSteen.setOnMouseClicked(event -> {
			int aantal = Integer.parseInt(aantalGroen.getText());
			if (controleerAantalEdelsteenFiches(aantal)) {
				if (!(aantalGroen.isDisable())) {
					aantalGroen.setText(String.format("%d", aantal > 0 ? aantal - 1 : 0));
				}
				neemEdelsteenFiche(EdelsteenFiche.SMARAGDEN);
				countGroen += 1;
				gekozenEdelsteenFiches.put(EdelsteenFiche.SMARAGDEN, countGroen);
				neem2EdelsteenFiches(gekozenEdelsteenFiches.size(), gekozenEdelsteenFiches);
				witeSteen.setDisable(true);
				aantalWit.setDisable(true);
				redSteen.setDisable(true);
				aantalRood.setDisable(true);
				blueSteen.setDisable(true);
				aantalBlauw.setDisable(true);
				blackSteen.setDisable(true);
				aantalZwart.setDisable(true);
			}
		});
		blackSteen.setOnMouseClicked(event -> {
			int aantal = Integer.parseInt(aantalZwart.getText());
			if (controleerAantalEdelsteenFiches(aantal)) {
				if (!(aantalZwart.isDisable())) {
					aantalZwart.setText(String.format("%d", aantal > 0 ? aantal - 1 : 0));
				}
				neemEdelsteenFiche(EdelsteenFiche.ONYXEN);
				countZwart += 1;
				gekozenEdelsteenFiches.put(EdelsteenFiche.ONYXEN, countZwart);
				neem2EdelsteenFiches(gekozenEdelsteenFiches.size(), gekozenEdelsteenFiches);
				witeSteen.setDisable(true);
				aantalWit.setDisable(true);
				redSteen.setDisable(true);
				aantalRood.setDisable(true);
				blueSteen.setDisable(true);
				aantalBlauw.setDisable(true);
				greenSteen.setDisable(true);
				aantalGroen.setDisable(true);
			}
		});
	}

	private void neem2EdelsteenFiches(int size, Map<EdelsteenFiche, Integer> gekozenEdelsteenFiches) {
		controleerAantalEdeleEnMaxEdelstenen();
		gekozenEdelsteenFiches.forEach((key, value) -> {
			if (value == 2) {
				disableButton(key);
				gridEdelsteen.setDisable(true);
				try {
					dc.neem2EdelsteenFiche(btn2Edelsteen.getId(), gekozenEdelsteenFiches);
				} catch (OnvoldoendeEfVoorOkException e) {
					lbLError.setText(resources.getString("leeg"));
				}
				enableKeuzeButtons();
				resetTeller();
				updateSpelerOverzicht();
				controleerWinnaar();
				controleer2EdelsteenFichesMagNietNemen();
			}
		});
	}

	private void controleer2EdelsteenFichesMagNietNemen() {

		int aantRed = Integer.parseInt(aantalRood.getText());
		int aantGreen = Integer.parseInt(aantalGroen.getText());
		int aantBlack = Integer.parseInt(aantalZwart.getText());
		int aantBlue = Integer.parseInt(aantalBlauw.getText());
		int aantWhite = Integer.parseInt(aantalWit.getText());

		if (aantWhite < 2) {
			disableButton(EdelsteenFiche.DIAMANTEN);
		}
		if (aantGreen < 2) {
			disableButton(EdelsteenFiche.SMARAGDEN);
		}
		if (aantBlack < 2) {
			disableButton(EdelsteenFiche.ONYXEN);
		}
		if (aantBlue < 2) {
			disableButton(EdelsteenFiche.SAFFIEREN);
		}
		if (aantRed < 2) {
			disableButton(EdelsteenFiche.ROBIJNEN);
		}
		{
			if (aantWhite == 0 && aantGreen == 0 && aantBlack == 0 && aantBlue == 0 && aantRed == 0) {
				btn2Edelsteen.isDisabled();
				speelBeurt();
			}

		}

	}

	private void neemEdelsteenFiche(EdelsteenFiche e) {
		int currentValue = edelsteenOpSpeelbord.get(e);
		if (currentValue > 1) {
			edelsteenOpSpeelbord.put(e, currentValue - 1);
		} else {
			edelsteenOpSpeelbord.remove(e);
		}
		edelsteenOpSpeelbord.put(e, currentValue - 1);

	}

	private void koopOntwikkelingskaart() {

		lvl3Kaart1.setOnMouseClicked(event -> {
			neemKaart(3, lvl3Kaart1, aantalNiveau3, ontwikkelingskaartenNiv3OpSpeelbord);
		});

		lvl3Kaart2.setOnMouseClicked(event -> {
			neemKaart(3, lvl3Kaart2, aantalNiveau3, ontwikkelingskaartenNiv3OpSpeelbord);
		});

		lvl3Kaart3.setOnMouseClicked(event -> {
			neemKaart(3, lvl3Kaart3, aantalNiveau3, ontwikkelingskaartenNiv3OpSpeelbord);
		});

		lvl3Kaart4.setOnMouseClicked(event -> {
			neemKaart(3, lvl3Kaart4, aantalNiveau3, ontwikkelingskaartenNiv3OpSpeelbord);
		});

		lvl2Kaart1.setOnMouseClicked(event -> {
			neemKaart(2, lvl2Kaart1, aantalNiveau2, ontwikkelingskaartenNiv2OpSpeelbord);
		});

		lvl2Kaart2.setOnMouseClicked(event -> {
			neemKaart(2, lvl2Kaart2, aantalNiveau2, ontwikkelingskaartenNiv2OpSpeelbord);
		});

		lvl2Kaart3.setOnMouseClicked(event -> {
			neemKaart(2, lvl2Kaart3, aantalNiveau2, ontwikkelingskaartenNiv2OpSpeelbord);
		});

		lvl2Kaart4.setOnMouseClicked(event -> {
			neemKaart(2, lvl2Kaart4, aantalNiveau2, ontwikkelingskaartenNiv2OpSpeelbord);
		});

		lvl1Kaart1.setOnMouseClicked(event -> {
			neemKaart(1, lvl1Kaart1, aantalNiveau1, ontwikkelingskaartenNiv1OpSpeelbord);
		});

		lvl1Kaart2.setOnMouseClicked(event -> {
			neemKaart(1, lvl1Kaart2, aantalNiveau1, ontwikkelingskaartenNiv1OpSpeelbord);
		});

		lvl1Kaart3.setOnMouseClicked(event -> {
			neemKaart(1, lvl1Kaart3, aantalNiveau1, ontwikkelingskaartenNiv1OpSpeelbord);
		});

		lvl1Kaart4.setOnMouseClicked(event -> {
			neemKaart(1, lvl1Kaart4, aantalNiveau1, ontwikkelingskaartenNiv1OpSpeelbord);
		});
	}

	private void neemKaart(int niveau, ImageView kaart, Label aantal,
			LinkedList<OntwikkelingskaartDTO> ontwikkelingskaartOpSpeelbord) {
		int teller = 0;
		OntwikkelingskaartDTO oudeKaart = null;
		OntwikkelingskaartDTO nieuweKaart = null;
		controleerAantalEdeleEnMaxEdelstenen();
		int index = 0;
		for (OntwikkelingskaartDTO o : ontwikkelingskaartOpSpeelbord) {
			teller++;
			if (kaart.imageProperty().getValue().equals(o.image())) {
				index = ontwikkelingskaartOpSpeelbord.indexOf(o);
				oudeKaart = o;
			}
			if (teller == ontwikkelingskaartOpSpeelbord.size()) {
				neemOntwikkelingskaart(oudeKaart);
				if (controleerOfKaartToegevoegd(oudeKaart)) {
					kaart.setImage(null);
					ontwikkelingskaartOpSpeelbord.remove(oudeKaart);
					nieuweKaart = neemNieuweKaart(niveau, aantal);
					ontwikkelingskaartOpSpeelbord.add(index, nieuweKaart);
					kaart.setImage(nieuweKaart.image());
					updateSpelerOverzicht();

				} else {
					lbLError.setText(resources.getString("geenfichesgenoeg"));
				}
				enableKeuzeButtons();
			}
		}
	}

	private OntwikkelingskaartDTO neemNieuweKaart(int niveau, Label aantal) {
		OntwikkelingskaartDTO nieuweKaart = null;

		switch (niveau) {
		case 3: {
			nieuweKaart = ontwikkelingskaartenNiv3.get(0);
			ontwikkelingskaartenNiv3.remove(0);
			aantal.setText(Integer.toString(ontwikkelingskaartenNiv3.size()));
			return nieuweKaart;

		}
		case 2: {
			nieuweKaart = ontwikkelingskaartenNiv2.get(0);
			ontwikkelingskaartenNiv2.remove(0);
			aantal.setText(Integer.toString(ontwikkelingskaartenNiv2.size()));
			return nieuweKaart;

		}
		case 1: {
			nieuweKaart = ontwikkelingskaartenNiv1.get(0);
			ontwikkelingskaartenNiv1.remove(0);
			aantal.setText(Integer.toString(ontwikkelingskaartenNiv1.size()));
			return nieuweKaart;
		}
		}
		return nieuweKaart;
	}

	private boolean controleerOfKaartToegevoegd(OntwikkelingskaartDTO oudeKaart) {
		for (SpelerDTO s : dc.geefOverzichtSpelers()) {
			for (Ontwikkelingskaart kaart : s.ontwikkelingskaartenInBezit()) {
				if (kaart.getImage().equals(oudeKaart.image())) {
					return true;
				}
			}
		}
		return false;
	}

	private void neemOntwikkelingskaart(OntwikkelingskaartDTO o) {
		try {
			dc.koopOntwikkelingskaart(btnKoopOntwikkelingskaart.getId(), o);
		} catch (OnvoldoendeEfVoorOkException e) {
			lbLError.setText(resources.getString("geenfichesgenoeg"));
		}
	}

	private void pasBeurt() {
		btnPasBeurt.setOnMouseClicked(event -> {
			try {
				dc.pasBeurt(btnPasBeurt.getId(), false);
			} catch (OnvoldoendeEfVoorOkException e) {
				lbLError.setText(resources.getString("leeg"));
			}
			enableKeuzeButtons();
			updateSpelerOverzicht();
			controleerWinnaar();
		});
	}

	private void enableKeuzeButtons() {
		gridCards.setDisable(false);
		btn3Edelsteen.setDisable(false);
		btn2Edelsteen.setDisable(false);
		btnKoopOntwikkelingskaart.setDisable(false);
		btnPasBeurt.setDisable(false);
	}

	private void enableStenen() {

		gridEdelsteen.setDisable(false);
		witeSteen.setDisable(false);
		aantalWit.setDisable(false);
		redSteen.setDisable(false);
		aantalRood.setDisable(false);
		blueSteen.setDisable(false);
		aantalBlauw.setDisable(false);
		greenSteen.setDisable(false);
		aantalGroen.setDisable(false);
		blackSteen.setDisable(false);
		aantalZwart.setDisable(false);
	}

	private void updateAantalEdelstenen() {
		Map<EdelsteenFiche, Integer> edelsteenOpSpeelbord = dc.geefEdelsteenFicheOpSpeelbord();
		aantalWit.setText(Integer.toString(edelsteenOpSpeelbord.get(EdelsteenFiche.DIAMANTEN).intValue()));
		aantalRood.setText(Integer.toString(edelsteenOpSpeelbord.get(EdelsteenFiche.ROBIJNEN).intValue()));
		aantalBlauw.setText(Integer.toString(edelsteenOpSpeelbord.get(EdelsteenFiche.SAFFIEREN).intValue()));
		aantalGroen.setText(Integer.toString(edelsteenOpSpeelbord.get(EdelsteenFiche.SMARAGDEN).intValue()));
		aantalZwart.setText(Integer.toString(edelsteenOpSpeelbord.get(EdelsteenFiche.ONYXEN).intValue()));
	}

	private boolean controleerAantalEdelsteenFiches(int aantal) {
		if (aantal < 0) {
			lbLError.setText(resources.getString("geenfichesgenoeg"));
			return false;
		}
		return true;
	}

	private void controleerAantalEdeleEnMaxEdelstenen() {
		for (SpelerDTO s : dc.geefOverzichtSpelers()) {
			if (s.aanDeBeurt()) {
				controleerAantalEdelstenenEnEdele(s);
			}
		}
	}

	private void controleerAantalEdelstenenEnEdele(SpelerDTO speler) {

		Map<EdelsteenFiche, Integer> kiesEdelsteen = new HashMap<>();
		Map<EdelsteenFiche, Integer> huidigeEdelstenen = new HashMap<>();
//		List<EdeleDTO> teKiezenEdele = dc.geefTeKiezenEdele();
		boolean keuzeEdelsteen = false;
		boolean keuzeEdele = false;
		Alert alert = new Alert(AlertType.WARNING);
		for (Entry<EdelsteenFiche, Integer> ef : speler.edelsteenfichesInBezit().entrySet()) {
			if (ef.getValue() >= 9) {
				kiesEdelsteen.put(ef.getKey(), ef.getValue());
				disableButton(ef.getKey());
				keuzeEdelsteen = true;
			}
		}
//		if (teKiezenEdele.size() >= 2) {
//			teKiezenEdele.addAll(dc.geefTeKiezenEdele());
//			keuzeEdele = true;
//		}

		if (keuzeEdelsteen == true) {

			for (Entry<EdelsteenFiche, Integer> e : kiesEdelsteen.entrySet()) {
				if (e.getValue() >= 10) {
					huidigeEdelstenen.put(e.getKey(), e.getValue());
				}

			}
			KeuzeController scene = new KeuzeController(dc, speler, kiesEdelsteen);
			scene.setResizable(false);
			scene.show();
			disableSpeelbord();
			scene.setOnCloseRequest(e -> {
				if (!geldigeAantalEdelstenen(kiesEdelsteen)) {
					e.consume();
					alert.setContentText(
							"Ongeldig aantal edelstenen Het aantal edelstenen is niet minder dan 10. Kan het venster niet sluiten.");
					alert.show();
				} else {
					scene.close();
					alert.close();
					dc.terugTePlaatsenEfOpSpeelbord(kiesEdelsteen);
					for (Entry<EdelsteenFiche, Integer> entry : kiesEdelsteen.entrySet()) {
						speler.edelsteenfichesInBezit().put(entry.getKey(),
								huidigeEdelstenen.get(entry.getKey()) - entry.getValue());
					}
					updateAantalEdelstenen();
					btn3Edelsteen.setDisable(false);
					btn2Edelsteen.setDisable(false);
					btnKoopOntwikkelingskaart.setDisable(false);
					btnPasBeurt.setDisable(false);
				}
			});
		}

//		if (keuzeEdele == true) {
//			KeuzeController scene = new KeuzeController(dc, speler, teKiezenEdele);
//			scene.setResizable(false);
//			scene.show();
//		}
	}

	private boolean geldigeAantalEdelstenen(Map<EdelsteenFiche, Integer> kiesEdelsteen) {
		// Replace with your logic to check if the values in kiesEdelstenen are less
		// than 10
		for (Entry<EdelsteenFiche, Integer> entry : kiesEdelsteen.entrySet()) {
			if (entry.getValue() > 9) {
				return false;
			}
		}
		return true;
	}

	private void toonWinnaar() {

		List<WinnaarDTO> lijstWinnaarDTO = dc.geefWinnaar();
		String naamWinaar = "";
		// String totaalPrestigePunten;

		if (lijstWinnaarDTO.size() < 2) {

			for (WinnaarDTO dto : lijstWinnaarDTO) {
				naamWinaar += String.format("Winnaar: %s", dto.gebruiksnaam());
				// totaalPrestigePunten =
				// String.format("%s%s",resources.getString("TotaalPrestigePunten")
				// ,dto.totaalAantalPrestigePunten());

			}
		} else {
			naamWinaar += String.format("De winnaars van het spel : ");
			for (WinnaarDTO dto : lijstWinnaarDTO) {
				naamWinaar += String.format("Gebruikersnaam : %s", dto.gebruiksnaam());
				// totaalPrestigePunten =
				// String.format("%s%s",resources.getString("TotaalPrestigePunten")
				// ,dto.totaalAantalPrestigePunten());

			}
		}

		Alert a = new Alert(AlertType.WARNING);
		a.setContentText(naamWinaar);
		a.show();

	}

	private void disableButton(EdelsteenFiche key) {
		switch (key) {
		case DIAMANTEN -> {
			witeSteen.setDisable(true);
			aantalWit.setDisable(true);
		}
		case ROBIJNEN -> {
			redSteen.setDisable(true);
			aantalRood.setDisable(true);
		}
		case SAFFIEREN -> {
			blueSteen.setDisable(true);
			aantalBlauw.setDisable(true);
		}
		case SMARAGDEN -> {
			greenSteen.setDisable(true);
			aantalGroen.setDisable(true);
		}
		case ONYXEN -> {
			blackSteen.setDisable(true);
			aantalZwart.setDisable(true);
		}
		}
	}

//	private void enableEdelstenen(EdelsteenFiche key) {
//		switch (key) {
//		case DIAMANTEN -> {
//			witeSteen.setDisable(false);
//			aantalWit.setDisable(false);
//		}
//		case ROBIJNEN -> {
//			redSteen.setDisable(false);
//			aantalRood.setDisable(false);
//		}
//		case SAFFIEREN -> {
//			blueSteen.setDisable(false);
//			aantalBlauw.setDisable(false);
//		}
//		case SMARAGDEN -> {
//			greenSteen.setDisable(false);
//			aantalGroen.setDisable(false);
//		}
//		case ONYXEN -> {
//			blackSteen.setDisable(false);
//			aantalZwart.setDisable(false);
//		}
//		}
//	}

	private void resetTeller() {
		countWit = 0;
		countRood = 0;
		countBlauw = 0;
		countGroen = 0;
		countZwart = 0;
	}

	private void disableSpeelbord() {
		gridCards.setDisable(true);
		gridEdele.setDisable(true);
		gridEdelsteen.setDisable(true);
		btn2Edelsteen.setDisable(true);
		btn3Edelsteen.setDisable(true);
		btnKoopOntwikkelingskaart.setDisable(true);
		btnPasBeurt.setDisable(true);
	}

	private void controleerWinnaar() {

		boolean isGewonnen = dc.isEindeSpel();

		// int aantalSpelers = dc.geefAantalSpelers();

		// tellerEindeRonde++;
		if (isGewonnen == true) {
			disableSpeelbord();
			toonWinnaar();

		}
//		if (tellerEindeRonde == 3) {
//			tellerEindeRonde = 0;
//		}

	}

}
