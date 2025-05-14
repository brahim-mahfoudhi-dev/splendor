package gui;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import domein.DomeinController;
import dto.EdeleDTO;
import dto.SpelerDTO;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import util.EdelsteenFiche;

public class KeuzeScherm extends GridPane {

	private DomeinController dc;
	private ImageView edele1;
	private ImageView edele2;
	private ImageView edele3;
	private ImageView edele4;
	private ImageView edele5;
	private Circle wit;
	private Circle rood;
	private Circle blauw;
	private Circle groen;
	private Circle zwart;
	private Label aantalWit;
	private Label aantalRood;
	private Label aantalBlauw;
	private Label aantalGroen;
	private Label aantalZwart;
	private Label lblTitle;
	private Button minusButtonWit;
	private Button minusButtonRood;
	private Button minusButtonBlauw;
	private Button minusButtonGroen;
	private Button minusButtonZwart;
	private List<ImageView> imageEdele;
	private List<Circle> edelstenen;
	private List<Label> aantalEdelstenen;
	private List<Button> minusButtons;
	private int countWit = 0;
	private int countRood = 0;
	private int countBlauw = 0;
	private int countGroen = 0;
	private int countZwart = 0;

//	/* Scherm Edele */
	public KeuzeScherm(DomeinController dc, List<EdeleDTO> kiesEdele, SpelerDTO s) {
		this.dc = dc;
		this.getChildren().clear();
		imageEdele = new ArrayList<>();
		edele1 = new ImageView();
		edele1.setFitWidth(250);
		edele1.setFitHeight(250);
		edele2 = new ImageView();
		edele2.setFitWidth(250);
		edele2.setFitHeight(250);
		edele3 = new ImageView();
		edele3.setFitWidth(250);
		edele3.setFitHeight(250);
		edele4 = new ImageView();
		edele4.setFitWidth(250);
		edele4.setFitHeight(250);
		edele5 = new ImageView();
		edele5.setFitWidth(250);
		edele5.setFitHeight(250);
		imageEdele.add(edele1);
		imageEdele.add(edele2);
		imageEdele.add(edele3);
		imageEdele.add(edele4);
		imageEdele.add(edele5);
		buildGuiEdele(kiesEdele, s);
	}

//
	private void buildGuiEdele(List<EdeleDTO> kiesEdele, SpelerDTO s) {
		this.getChildren().clear();
		int index = 0;
		List<EdeleDTO> gekozenEdele = new ArrayList<>();
		for (ImageView i : imageEdele) {
			i.setImage(kiesEdele.get(index).image());
			index++;
		}
		lblTitle = new Label();
		lblTitle.setText("Kies een edele");
		lblTitle.setFont(new Font("Arial", 20));
		lblTitle.setPrefWidth(200);
		lblTitle.setMaxWidth(248);
		this.setMinWidth(10);
		this.setPrefWidth(200);
		this.setMaxWidth(247);
		GridPane.setMargin(lblTitle, new Insets(25, 0, 0, 105));
		GridPane.setMargin(edele1, new Insets(20, 0, 0, 0));
		GridPane.setMargin(edele2, new Insets(20, 0, 0, 0));
		GridPane.setMargin(edele3, new Insets(20, 0, 0, 0));
		GridPane.setMargin(edele4, new Insets(20, 0, 0, 0));
		GridPane.setMargin(edele5, new Insets(20, 0, 0, 0));
		this.add(lblTitle, 0, 0);
		this.add(edele1, 0, 1, 1, 1);
		this.add(edele2, 1, 1, 1, 1);
		this.add(edele3, 2, 1, 1, 1);
		this.add(edele4, 3, 1, 1, 1);
		this.add(edele5, 4, 1, 1, 1);

		for (ImageView i : imageEdele) {

			i.setStyle("-fx-cursor: hand");
			i.setOnMouseClicked(event -> {
				for (EdeleDTO e : kiesEdele) {
					if (e.image().equals(i.getImage())) {
						gekozenEdele.add(e);
					}
				}
			});
		}
	}

	/* Scherm edelsteen */
	public KeuzeScherm(DomeinController dc, Map<EdelsteenFiche, Integer> kiesEdelsteen, SpelerDTO s) {
		this.dc = dc;
		this.getChildren().clear();
		wit = new Circle();
		rood = new Circle();
		blauw = new Circle();
		groen = new Circle();
		zwart = new Circle();
		aantalWit = new Label();
		aantalRood = new Label();
		aantalBlauw = new Label();
		aantalGroen = new Label();
		aantalZwart = new Label();
		edelstenen = new ArrayList<>();
		aantalEdelstenen = new ArrayList<>();
		minusButtons = new ArrayList<>();
		edelstenen.add(wit);
		edelstenen.add(rood);
		edelstenen.add(blauw);
		edelstenen.add(groen);
		edelstenen.add(zwart);
		aantalEdelstenen.add(aantalWit);
		aantalEdelstenen.add(aantalRood);
		aantalEdelstenen.add(aantalBlauw);
		aantalEdelstenen.add(aantalGroen);
		aantalEdelstenen.add(aantalZwart);
		minusButtons.add(minusButtonWit);
		minusButtons.add(minusButtonRood);
		minusButtons.add(minusButtonBlauw);
		minusButtons.add(minusButtonGroen);
		minusButtons.add(minusButtonZwart);

		try {
			buildGuiEdelsteen(kiesEdelsteen, s);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	private void buildGuiEdelsteen(Map<EdelsteenFiche, Integer> kiesEdelsteen, SpelerDTO s) throws URISyntaxException {
		wit.setRadius(80);
		wit.setStroke(Color.WHITE);
		wit.setStrokeWidth(3);
		rood.setRadius(80);
		rood.setStroke(Color.RED);
		rood.setStrokeWidth(3);
		blauw.setRadius(80);
		blauw.setStroke(Color.ROYALBLUE);
		blauw.setStrokeWidth(3);
		groen.setRadius(80);
		groen.setStroke(Color.GREEN);
		groen.setStrokeWidth(3);
		zwart.setRadius(80);
		zwart.setStroke(Color.BLACK);
		zwart.setStrokeWidth(3);
		Image white = new Image(getClass().getResource("/images/Edelsteenfiche/Witte.png").toURI().toString());
		wit.setFill(new ImagePattern(white));
		Image red = new Image(getClass().getResource("/images/Edelsteenfiche/Red.png").toURI().toString());
		rood.setFill(new ImagePattern(red));
		Image blue = new Image(getClass().getResource("/images/Edelsteenfiche/Blue.png").toURI().toString());
		blauw.setFill(new ImagePattern(blue));
		Image green = new Image(getClass().getResource("/images/Edelsteenfiche/Green.png").toURI().toString());
		groen.setFill(new ImagePattern(green));
		Image black = new Image(getClass().getResource("/images/Edelsteenfiche/Black.png").toURI().toString());
		zwart.setFill(new ImagePattern(black));
		GridPane.setMargin(wit, new Insets(60, 0, 0, 75));
		GridPane.setMargin(rood, new Insets(60, 0, 0, 75));
		GridPane.setMargin(blauw, new Insets(60, 0, 0, 75));
		GridPane.setMargin(groen, new Insets(60, 0, 0, 75));
		GridPane.setMargin(zwart, new Insets(60, 0, 0, 75));
		this.add(wit, 0, 1);
		this.add(rood, 1, 1);
		this.add(blauw, 2, 1);
		this.add(groen, 3, 1);
		this.add(zwart, 4, 1);
		aantalWit.setText(Integer.toString(s.edelsteenfichesInBezit().get(EdelsteenFiche.DIAMANTEN).intValue()));
		aantalRood.setText(Integer.toString(s.edelsteenfichesInBezit().get(EdelsteenFiche.ROBIJNEN).intValue()));
		aantalBlauw.setText(Integer.toString(s.edelsteenfichesInBezit().get(EdelsteenFiche.SAFFIEREN).intValue()));
		aantalGroen.setText(Integer.toString(s.edelsteenfichesInBezit().get(EdelsteenFiche.SMARAGDEN).intValue()));
		aantalZwart.setText(Integer.toString(s.edelsteenfichesInBezit().get(EdelsteenFiche.ONYXEN).intValue()));
		for (Label l : aantalEdelstenen) {
			l.setFont(new Font("Arial", 20));
			int aantal = Integer.parseInt(l.getText());
			if (aantal == 10) {

			}
		}

		minusButtonWit = new Button();
		minusButtonWit.setText("➖");
		minusButtonWit.setMinWidth(100);
		minusButtonWit.setMaxWidth(150);
		minusButtonRood = new Button();
		minusButtonRood.setText("➖");
		minusButtonRood.setMinWidth(100);
		minusButtonRood.setMaxWidth(150);
		minusButtonBlauw = new Button();
		minusButtonBlauw.setText("➖");
		minusButtonBlauw.setMinWidth(100);
		minusButtonBlauw.setMaxWidth(150);
		minusButtonGroen = new Button();
		minusButtonGroen.setText("➖");
		minusButtonGroen.setMinWidth(100);
		minusButtonGroen.setMaxWidth(150);
		minusButtonZwart = new Button();
		minusButtonZwart.setText("➖");
		minusButtonZwart.setMinWidth(100);
		minusButtonZwart.setMaxWidth(150);
		GridPane.setMargin(minusButtonWit, new Insets(0, 0, 0, 83));
		GridPane.setMargin(minusButtonRood, new Insets(0, 0, 0, 83));
		GridPane.setMargin(minusButtonBlauw, new Insets(0, 0, 0, 83));
		GridPane.setMargin(minusButtonGroen, new Insets(0, 0, 0, 83));
		GridPane.setMargin(minusButtonZwart, new Insets(0, 0, 0, 83));
		minusButtonWit.setFont((new Font("Arial", 15)));
		minusButtonRood.setFont((new Font("Arial", 15)));
		minusButtonBlauw.setFont((new Font("Arial", 15)));
		minusButtonGroen.setFont((new Font("Arial", 15)));
		minusButtonZwart.setFont((new Font("Arial", 15)));
		this.add(minusButtonWit, 0, 4);
		this.add(minusButtonRood, 1, 4);
		this.add(minusButtonBlauw, 2, 4);
		this.add(minusButtonGroen, 3, 4);
		this.add(minusButtonZwart, 4, 4);
		this.add(aantalWit, 1, 2);
		this.add(aantalRood, 2, 2);
		this.add(aantalBlauw, 3, 2);
		this.add(aantalGroen, 4, 2);
		this.add(aantalZwart, 5, 2);
		resetTeller();

		minusButtonWit.setOnAction(e -> {
			int aantal = Integer.parseInt(aantalWit.getText());
			if (aantal > 0) {
				aantal--;
			}
			aantalWit.setText(Integer.toString(aantal));
			countWit++;
			if (Integer.parseInt(aantalWit.getText()) < 10) {
				kiesEdelsteen.put(EdelsteenFiche.DIAMANTEN, countRood);
			}

		});
		minusButtonRood.setOnAction(e -> {
			int aantal = Integer.parseInt(aantalRood.getText());
			if (aantal > 0) {
				aantal--;
			}
			aantalRood.setText(Integer.toString(aantal));
			countRood++;
			if (Integer.parseInt(aantalRood.getText()) < 10) {
				kiesEdelsteen.put(EdelsteenFiche.ROBIJNEN, countRood);
			}
		});
		minusButtonBlauw.setOnAction(e -> {
			int aantal = Integer.parseInt(aantalBlauw.getText());
			if (aantal > 0) {
				aantal--;
			}
			aantalBlauw.setText(Integer.toString(aantal));
			countBlauw++;
			if (Integer.parseInt(aantalBlauw.getText()) < 10) {
				kiesEdelsteen.put(EdelsteenFiche.SAFFIEREN, countRood);
			}

		});
		minusButtonGroen.setOnAction(e -> {
			int aantal = Integer.parseInt(aantalGroen.getText());
			if (aantal > 0) {
				aantal--;
			}
			aantalGroen.setText(Integer.toString(aantal--));
			countGroen++;
			if (Integer.parseInt(aantalGroen.getText()) < 10) {
				kiesEdelsteen.put(EdelsteenFiche.SMARAGDEN, countGroen);
			}
		});
		minusButtonZwart.setOnAction(e -> {
			int aantal = Integer.parseInt(aantalZwart.getText());
			if (aantal > 0) {
				aantal--;
			}
			aantalZwart.setText(Integer.toString(aantal));
			countZwart++;
			if (Integer.parseInt(aantalZwart.getText()) < 10) {
				kiesEdelsteen.put(EdelsteenFiche.ONYXEN, countZwart);
			}
		});

	}

	private void resetTeller() {
		countWit = 0;
		countRood = 0;
		countBlauw = 0;
		countGroen = 0;
		countZwart = 0;

	}
}
