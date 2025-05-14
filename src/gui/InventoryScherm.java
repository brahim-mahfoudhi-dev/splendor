package gui;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import domein.DomeinController;
import dto.SpelerDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import util.EdelsteenFiche;

public class InventoryScherm extends BorderPane {

	private DomeinController dc;

	@FXML
	private Label aantalBlauw;

	@FXML
	private Label aantalGroen;

	@FXML
	private Label aantalRood;

	@FXML
	private Label aantalWit;

	@FXML
	private Label aantalZwart;

	@FXML
	private Circle blauwInBezit;

	@FXML
	private MenuButton btnOverzichtSpelers;

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
	private ImageView edele6;

	@FXML
	private ImageView edele7;

	@FXML
	private ImageView edele8;

	@FXML
	private Circle groenInBezit;

	@FXML
	private ImageView kaart1;

	@FXML
	private ImageView kaart10;

	@FXML
	private ImageView kaart11;

	@FXML
	private ImageView kaart12;

	@FXML
	private ImageView kaart13;

	@FXML
	private ImageView kaart14;

	@FXML
	private ImageView kaart15;

	@FXML
	private ImageView kaart16;

	@FXML
	private ImageView kaart17;

	@FXML
	private ImageView kaart18;

	@FXML
	private ImageView kaart19;

	@FXML
	private ImageView kaart2;

	@FXML
	private ImageView kaart20;

	@FXML
	private ImageView kaart21;

	@FXML
	private ImageView kaart22;

	@FXML
	private ImageView kaart23;

	@FXML
	private ImageView kaart24;

	@FXML
	private ImageView kaart3;

	@FXML
	private ImageView kaart4;

	@FXML
	private ImageView kaart5;

	@FXML
	private ImageView kaart6;

	@FXML
	private ImageView kaart7;

	@FXML
	private ImageView kaart8;

	@FXML
	private ImageView kaart9;

	@FXML
	private Circle roodInBezit;

	@FXML
	private Circle witInBezit;

	@FXML
	private Circle zwartInBezit;

	public InventoryScherm(DomeinController dc) {

		this.dc = dc;

		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("Inventory.fxml"));
		loader.setController(this);
		loader.setRoot(this);

		try {
			loader.load();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("error somewhere");
		}
		buildGui();
	}

	private void buildGui() {
		initialiseerMenuButton();
	}

	private void initialiseerMenuButton() {
		List<SpelerDTO> spelers = dc.geefOverzichtSpelers();
		btnOverzichtSpelers.setText(spelers.get(0).gebruiksnaam());
		for (SpelerDTO s : dc.geefOverzichtSpelers()) {
			btnOverzichtSpelers.getItems().addAll(new MenuItem(s.gebruiksnaam()));
		}

		try {
			initialiseerEdelsteenInBezit(spelers.get(0));
			initialiseerEdeleInBezit(spelers.get(0));
			initialiseerOntwikkelingskaartenInBezit(spelers.get(0));

		} catch (URISyntaxException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		btnOverzichtSpelers.getItems().get(0).setOnAction(e -> {
			btnOverzichtSpelers.setText(dc.geefOverzichtSpelers().get(0).gebruiksnaam());
			try {
				initialiseerEdelsteenInBezit(dc.geefOverzichtSpelers().get(0));
				initialiseerEdeleInBezit(dc.geefOverzichtSpelers().get(0));
				initialiseerOntwikkelingskaartenInBezit(dc.geefOverzichtSpelers().get(0));
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		btnOverzichtSpelers.getItems().get(1).setOnAction(e -> {
			btnOverzichtSpelers.setText(dc.geefOverzichtSpelers().get(1).gebruiksnaam());
			try {
				initialiseerEdelsteenInBezit(dc.geefOverzichtSpelers().get(1));
				initialiseerEdeleInBezit(dc.geefOverzichtSpelers().get(1));
				initialiseerOntwikkelingskaartenInBezit(dc.geefOverzichtSpelers().get(1));
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

	}

	private void initialiseerEdelsteenInBezit(SpelerDTO s) throws URISyntaxException {
		// Aantal in bezit
		aantalWit.setText(s.edelsteenfichesInBezit().get(EdelsteenFiche.DIAMANTEN) == null
				|| s.edelsteenfichesInBezit().get(EdelsteenFiche.DIAMANTEN) < 0 ? Integer.toString(0)
						: Integer.toString(s.edelsteenfichesInBezit().get(EdelsteenFiche.DIAMANTEN)));
		aantalRood.setText(s.edelsteenfichesInBezit().get(EdelsteenFiche.ROBIJNEN) == null
				|| s.edelsteenfichesInBezit().get(EdelsteenFiche.ROBIJNEN) < 0 ? Integer.toString(0)
						: Integer.toString(s.edelsteenfichesInBezit().get(EdelsteenFiche.ROBIJNEN)));
		aantalBlauw.setText(s.edelsteenfichesInBezit().get(EdelsteenFiche.SAFFIEREN) == null
				|| s.edelsteenfichesInBezit().get(EdelsteenFiche.SAFFIEREN) < 0 ? Integer.toString(0)
						: Integer.toString(s.edelsteenfichesInBezit().get(EdelsteenFiche.SAFFIEREN)));
		aantalGroen.setText(s.edelsteenfichesInBezit().get(EdelsteenFiche.SMARAGDEN) == null
				|| s.edelsteenfichesInBezit().get(EdelsteenFiche.SMARAGDEN) < 0 ? Integer.toString(0)
						: Integer.toString(s.edelsteenfichesInBezit().get(EdelsteenFiche.SMARAGDEN)));
		aantalZwart.setText(s.edelsteenfichesInBezit().get(EdelsteenFiche.ONYXEN) == null
				|| s.edelsteenfichesInBezit().get(EdelsteenFiche.ONYXEN) < 0 ? Integer.toString(0)
						: Integer.toString(s.edelsteenfichesInBezit().get(EdelsteenFiche.ONYXEN)));
		// Images
		Image white = new Image(getClass().getResource("/images/Edelsteenfiche/Witte.png").toURI().toString());
		witInBezit.setFill(new ImagePattern(white));
		Image red = new Image(getClass().getResource("/images/Edelsteenfiche/Red.png").toURI().toString());
		roodInBezit.setFill(new ImagePattern(red));
		Image blue = new Image(getClass().getResource("/images/Edelsteenfiche/Blue.png").toURI().toString());
		blauwInBezit.setFill(new ImagePattern(blue));
		Image green = new Image(getClass().getResource("/images/Edelsteenfiche/Green.png").toURI().toString());
		groenInBezit.setFill(new ImagePattern(green));
		Image black = new Image(getClass().getResource("/images/Edelsteenfiche/Black.png").toURI().toString());
		zwartInBezit.setFill(new ImagePattern(black));
	}

	private void initialiseerEdeleInBezit(SpelerDTO s) {

		int aantalEdele = s.EdeleInBezit().size();

		if (aantalEdele > 0) {

			switch (aantalEdele) {
			case 1 -> {
				edele1.setImage(s.EdeleInBezit().get(0).getImage());
				edele2.setVisible(false);
				edele3.setVisible(false);
				edele4.setVisible(false);
				edele5.setVisible(false);
			}
			case 2 -> {
				edele1.setImage(s.EdeleInBezit().get(0).getImage());
				edele2.setImage(s.EdeleInBezit().get(1).getImage());
				edele3.setVisible(false);
				edele4.setVisible(false);
				edele5.setVisible(false);
			}
			case 3 -> {
				edele1.setImage(s.EdeleInBezit().get(0).getImage());
				edele2.setImage(s.EdeleInBezit().get(1).getImage());
				edele3.setImage(s.EdeleInBezit().get(2).getImage());
				edele4.setVisible(false);
				edele5.setVisible(false);
			}

			case 4 -> {
				edele1.setImage(s.EdeleInBezit().get(0).getImage());
				edele2.setImage(s.EdeleInBezit().get(1).getImage());
				edele3.setImage(s.EdeleInBezit().get(2).getImage());
				edele4.setImage(s.EdeleInBezit().get(3).getImage());
				edele5.setVisible(false);
			}

			case 5 -> {
				edele1.setImage(s.EdeleInBezit().get(0).getImage());
				edele2.setImage(s.EdeleInBezit().get(1).getImage());
				edele3.setImage(s.EdeleInBezit().get(2).getImage());
				edele4.setImage(s.EdeleInBezit().get(3).getImage());
				edele5.setImage(s.EdeleInBezit().get(4).getImage());
			}
			}
		} else {
			edele1.setImage(null);
			edele2.setImage(null);
			edele3.setImage(null);
			edele4.setImage(null);
			edele5.setImage(null);
		}
	}

	private void initialiseerOntwikkelingskaartenInBezit(SpelerDTO s) {

		int aantalOntwikkelingskaartenInBezit = s.ontwikkelingskaartenInBezit().size();
		List<ImageView> lijst = new ArrayList<>();

		lijst.add(kaart1);
		lijst.add(kaart2);
		lijst.add(kaart3);
		lijst.add(kaart4);
		lijst.add(kaart5);
		lijst.add(kaart6);
		lijst.add(kaart7);
		lijst.add(kaart8);
		lijst.add(kaart9);
		lijst.add(kaart10);
		lijst.add(kaart11);
		lijst.add(kaart12);
		lijst.add(kaart13);
		lijst.add(kaart14);
		lijst.add(kaart15);
		lijst.add(kaart16);
		lijst.add(kaart17);
		lijst.add(kaart18);
		lijst.add(kaart19);
		lijst.add(kaart20);
		lijst.add(kaart21);
		lijst.add(kaart22);
		lijst.add(kaart23);
		lijst.add(kaart24);

		if (aantalOntwikkelingskaartenInBezit > 0) {

			for (int index = 0; index < aantalOntwikkelingskaartenInBezit; index++) {
				lijst.get(index).setImage(s.ontwikkelingskaartenInBezit().get(index).getImage());
			}
		}

		else {
			for (int index = 0; index < lijst.size(); index++) {
				lijst.get(index).setImage(null);
			}
		}
	}

}
