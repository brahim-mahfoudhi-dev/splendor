package gui;

import java.util.List;
import java.util.Map;

import domein.DomeinController;
import dto.EdeleDTO;
import dto.SpelerDTO;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.EdelsteenFiche;

public class KeuzeController extends Stage {
	
	public KeuzeController(DomeinController dc, SpelerDTO speler, Map<EdelsteenFiche, Integer> kiesEdelsteen) {
		KeuzeScherm keuze = new KeuzeScherm(dc, kiesEdelsteen, speler);
		Scene scene = new Scene(keuze, 1250, 350);
		this.setScene(scene);
	}

	public KeuzeController(DomeinController dc, SpelerDTO s, List<EdeleDTO> kiesEdele) {
		KeuzeScherm keuze = new KeuzeScherm(dc, kiesEdele, s);
		Scene scene = new Scene(keuze, 1250, 350);
		this.setScene(scene);
	
	}

	public static void main(String[] args) {
        

	}

}
