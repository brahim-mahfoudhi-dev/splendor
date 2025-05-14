package domein;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import dto.OntwikkelingskaartDTO;
import exceptions.OngeldigeGeboortejaar;
import exceptions.OngeldigeGebruikersnaam;
import exceptions.OnvoldoendeEfVoorOkException;
import exceptions.SpelerAlToegevoegdException;
import exceptions.StartSpelException;
import util.EdelsteenFiche;

public class Splendor {
	
	private ResourceBundle resources;

// Lijst EdelsteenFiches
	private Map<EdelsteenFiche, Integer> steenLijst;
// Class Ontwikkelingskaarten
	private final static int ONTWIKKELINGSKAART_NIVEAU_1 = 1;
	private final static int ONTWIKKELINGSKAART_NIVEAU_2 = 2;
	private final static int ONTWIKKELINGSKAART_NIVEAU_3 = 3;
	private static final int MAX_AANTAL_ONTWIKKELINGSKAARTEN_OP_SPEELBORD = 4;
	private List<Ontwikkelingskaart> ontwikkelingskaartenNiveau1OpSpeelbord;
	private List<Ontwikkelingskaart> ontwikkelingskaartenNiveau2OpSpeelbord;
	private List<Ontwikkelingskaart> ontwikkelingskaartenNiveau3OpSpeelbord;
	private List<Ontwikkelingskaart> ontwikkelingskaarten;
	private List<Ontwikkelingskaart> ontwikkelingskaartenInBezit;
	private List<Ontwikkelingskaart> ontwikkelingsKaartNiveau1;
	private List<Ontwikkelingskaart> ontwikkelingsKaartNiveau2;
	private List<Ontwikkelingskaart> ontwikkelingsKaartNiveau3;
// Class Edele
	private List<Edele> edeleOpSpeelbord;
	private List<Edele> edele;
	private List<Edele> edeleInBezit;
// Class EdelsteenFiche
	private Map<EdelsteenFiche, Integer> edelsteenOpSpeelbord;
	private Map<EdelsteenFiche, Integer> edelsteenInBezit;
// Class OntwikkelingsMapper
//	private List<Ontwikkelingskaart> ontwikkelingskaartenMapper;
// Class EdeleMapper
	private List<Edele> edeleMapper;
// Class Speler
	private Speler s;
	public final static int MIN_AANTAL_SPELERS = 2;
	public final static int MAX_AANTAL_SPELERS = 4;
	private List<Speler> huidigeSpelers;
// Beurt
	private final static int MAX_AANTAL_PUNTEN = 15;
	private List<Speler> winnaar;
// Class Ronde
	private Ronde sr;
	private int aantalRonde = 0;
//	private int aantalRonde = 1;
	private int teller = 0;

// Class SpeelBeurt;
	private Beurt sb;

	public Splendor() {
		// Lijst spelers
		huidigeSpelers = new ArrayList<>();
		// Lijst met alle 90 ontwikkelingskaarten
		ontwikkelingskaarten = new ArrayList<>();
		// lijst ontwikkelingskaarten in bezit
		ontwikkelingskaartenInBezit = new ArrayList<>();
		// Lijst met alle 10 Edele
		edele = new ArrayList<>();
		// Lijst met Edele op speelbord volgens aantal spelers
		edeleOpSpeelbord = new ArrayList<>();
		// Lijst met Edele in bezit van speler
		edeleInBezit = new ArrayList<>();
		// map met EdelsteenFiches op speelbord volgens aantal spelers
		edelsteenOpSpeelbord = new HashMap<>();
		// map met EdelsteenFiches in bezit
		edelsteenInBezit = new HashMap<>();
		// Lijst met winnaar(s)
		winnaar = new ArrayList<>();
		// Lijsten met ontwikkelingskaarten op speelbord volgens niveau
		ontwikkelingsKaartNiveau1 = new ArrayList<>();
		ontwikkelingsKaartNiveau2 = new ArrayList<>();
		ontwikkelingsKaartNiveau3 = new ArrayList<>();
		// Lijsten met ontwikkelingskaarten op speelbord volgens niveau op speelbord
		ontwikkelingskaartenNiveau1OpSpeelbord = new ArrayList<>();
		ontwikkelingskaartenNiveau2OpSpeelbord = new ArrayList<>();
		ontwikkelingskaartenNiveau3OpSpeelbord = new ArrayList<>();
	}

	// *********************************spel
	// componenten**********************************//

	public void voegSpeler(Speler s) throws OngeldigeGebruikersnaam, OngeldigeGeboortejaar {
		huidigeSpelers.add(s);
	}

	public void speelRonde(String keuze, Map<EdelsteenFiche, Integer> gekozenEdelsteenFiches, OntwikkelingskaartDTO o, List<Edele> gekozenEdele, boolean pasBeurt) throws OnvoldoendeEfVoorOkException {
		int index = 1;
		int id = Integer.parseInt(keuze);
		edelsteenInBezit = new HashMap<>();
		List<Ontwikkelingskaart> gekozenOntwikkelingskaart = new ArrayList<>();
		if (id == 3) {
			for (Ontwikkelingskaart ok : ontwikkelingskaarten) {
				if (ok.getImage().equals(o.image())) {
					gekozenOntwikkelingskaart.add(ok);
				}
			}
		}

		if (!(isEindeSpel()) && teller <= huidigeSpelers.size()) {
			if (aantalRonde < index) {
				sr = new Ronde(keuze, gekozenEdelsteenFiches, gekozenEdele, gekozenOntwikkelingskaart, pasBeurt, huidigeSpelers, edeleOpSpeelbord);
				aantalRonde++;
				isEindeSpel();
				isWinnaar();
			}
		}

		if (id > 0) {
			Speler spelerAanDeBeurt = huidigeSpelers.get(teller);
			sr.speelBeurt(keuze, spelerAanDeBeurt, gekozenEdelsteenFiches, gekozenOntwikkelingskaart, gekozenEdele, pasBeurt, huidigeSpelers, edeleOpSpeelbord, edelsteenOpSpeelbord);
			teller++;
			if (teller == huidigeSpelers.size()) {
				index++;
				teller = 0;
			}
		
			huidigeSpelers.get(teller).setAanDeBeurt(true);
			if (id == 3) {
				terugTePlaatsenEfOpSpeelbord(edelsteenOpSpeelbord);
			}
			if (huidigeSpelers.get(teller).getEdeleInBezit().size() > 0) {
				updateEdeleOpSpeelbord();
			}
		}

	}

	public void sorteerSpelers() {
		Collections.sort(huidigeSpelers, new CompareStartSpeler().reversed());
	}

	public void plaatsEdelsteenFichesOpSpeelbord() {

		int aantalSpelers = geefAantalHuidigeSpelers();

		if (aantalSpelers == 2) {
			edelsteenOpSpeelbord.put(EdelsteenFiche.DIAMANTEN, 4);
			edelsteenOpSpeelbord.put(EdelsteenFiche.ROBIJNEN, 4);
			edelsteenOpSpeelbord.put(EdelsteenFiche.SAFFIEREN, 4);
			edelsteenOpSpeelbord.put(EdelsteenFiche.SMARAGDEN, 4);
			edelsteenOpSpeelbord.put(EdelsteenFiche.ONYXEN, 4);

		} else if (aantalSpelers == 3) {
			edelsteenOpSpeelbord.put(EdelsteenFiche.DIAMANTEN, 5);
			edelsteenOpSpeelbord.put(EdelsteenFiche.ROBIJNEN, 5);
			edelsteenOpSpeelbord.put(EdelsteenFiche.SAFFIEREN, 5);
			edelsteenOpSpeelbord.put(EdelsteenFiche.SMARAGDEN, 5);
			edelsteenOpSpeelbord.put(EdelsteenFiche.ONYXEN, 5);

		} else {
			edelsteenOpSpeelbord.put(EdelsteenFiche.DIAMANTEN, 7);
			edelsteenOpSpeelbord.put(EdelsteenFiche.ROBIJNEN, 7);
			edelsteenOpSpeelbord.put(EdelsteenFiche.SAFFIEREN, 7);
			edelsteenOpSpeelbord.put(EdelsteenFiche.SMARAGDEN, 7);
			edelsteenOpSpeelbord.put(EdelsteenFiche.ONYXEN, 7);
		}
	}

	public void plaatsEdeleOpSpeelbord(int aantalSpelers) {

		switch (aantalSpelers) {
		case 2 -> {

			for (int index = 0; index < 3; index++) {
				edeleOpSpeelbord.add(edele.get(index));
			}
		}
		case 3 -> {
			for (int index = 0; index < 4; index++) {
				edeleOpSpeelbord.add(edele.get(index));
			}

		}
		case 4 -> {
			for (int index = 0; index < 5; index++) {
				edeleOpSpeelbord.add(edele.get(index));
			}
		}
		}
	}

	private void voegOntwikkelingskaartenNiveau1() {

		for (Ontwikkelingskaart o : ontwikkelingskaarten) {
			if (o.getLevel() == ONTWIKKELINGSKAART_NIVEAU_1) {
				ontwikkelingsKaartNiveau1.add(o);

			}
		}
	}

	private void voegOntwikkelingskaartenNiveau2() {

		for (Ontwikkelingskaart o : ontwikkelingskaarten) {

			if (o.getLevel() == ONTWIKKELINGSKAART_NIVEAU_2) {
				ontwikkelingsKaartNiveau2.add(o);
			}
		}

	}

	private void voegOntwikkelingskaartenNiveau3() {

		for (Ontwikkelingskaart o : ontwikkelingskaarten) {
			if (o.getLevel() == ONTWIKKELINGSKAART_NIVEAU_3) {
				ontwikkelingsKaartNiveau3.add(o);
			}
		}
	}

	public void plaatsOntwikkelingskaartOpSpeelbord() {

		shuffleOntwikkelingskaarten();

		for (int index = 0; index < MAX_AANTAL_ONTWIKKELINGSKAARTEN_OP_SPEELBORD; index++) {

			ontwikkelingskaartenNiveau1OpSpeelbord.add(ontwikkelingsKaartNiveau1.get(index));
			ontwikkelingsKaartNiveau1.remove(index);

			ontwikkelingskaartenNiveau2OpSpeelbord.add(ontwikkelingsKaartNiveau2.get(index));
			ontwikkelingsKaartNiveau2.remove(index);

			ontwikkelingskaartenNiveau3OpSpeelbord.add(ontwikkelingsKaartNiveau3.get(index));
			ontwikkelingsKaartNiveau3.remove(index);
		}
	}

	private void shuffleOntwikkelingskaarten() {
		Collections.shuffle(ontwikkelingsKaartNiveau1);
		Collections.shuffle(ontwikkelingsKaartNiveau2);
		Collections.shuffle(ontwikkelingsKaartNiveau3);
	}

	public boolean isEindeSpel() {
		for (Speler s : huidigeSpelers) {

			if (s.getTotaalAantalPrestigePunten() >= MAX_AANTAL_PUNTEN) {
				winnaar.add(s);
			}
		}

		if (winnaar.size() > 0) {
			return true;
		}
		return false;
	}

	public List<Speler> bepaalWinnaar() {
		List<Speler> winnaars = new ArrayList<>();
		for (Speler s : winnaar) {
			winnaars.add(s);
		}

		int prestigePunten = 0;
		int hoogstePrestigePunten = 0;

		for (Speler s : winnaar) {

			prestigePunten = s.getTotaalAantalPrestigePunten();

			if (prestigePunten > hoogstePrestigePunten) {

				hoogstePrestigePunten = prestigePunten;
				winnaar.clear();
				winnaar.add(s);
			} else if (prestigePunten == hoogstePrestigePunten) {
				winnaar.add(s);
			}
		}

		if (winnaar.size() > 1) {

			int minAantalOntwikkelingskaarten = Integer.MAX_VALUE;
			for (Speler speler : winnaar) {
				int aantalOntwikkelingskaarten = speler.getOntwikkelingskaartenInBezit().size();
				if (aantalOntwikkelingskaarten < minAantalOntwikkelingskaarten) {
					minAantalOntwikkelingskaarten = aantalOntwikkelingskaarten;
					winnaar.add(speler);
				}
			}
		}
		return winnaar;
	}

	// **********************************Mapper
	// methodes**********************************//

	public void voegOntwikkelingskaarten(List<Ontwikkelingskaart> o) {
		ontwikkelingskaarten.addAll(o);
		voegOntwikkelingskaartenNiveau1();
		voegOntwikkelingskaartenNiveau2();
		voegOntwikkelingskaartenNiveau3();
	}

	public void setEdelsteenOpSpeelbord(Map<EdelsteenFiche, Integer> edelsteenOpSpeelbord) {
		this.edelsteenOpSpeelbord = edelsteenOpSpeelbord;
	}

	public void voegEdele(List<Edele> e) {
		edele.addAll(e);
	}

	// **********************************Return
	// methodes**********************************//

	public List<Edele> geefEdele() {
		return edele;
	}

	public List<Edele> geefEdeleOpSpeelbord() {
		return edeleOpSpeelbord;
	}

	public List<Edele> geefEdeleInBezit() {
		return edeleInBezit;
	}

	public Map<EdelsteenFiche, Integer> geefEdelsteenFichesOpSpeelbord() {
		return edelsteenOpSpeelbord;
	}

	public Map<EdelsteenFiche, Integer> geefEdelsteenFichesInBezit() {
//		for (Entry<EdelsteenFiche, Integer> edelstenen : spelerAanDeBeurt.getEdelsteenfichesInBezit().entrySet()) {
//			edelsteenInBezit.put(edelstenen.getKey(), edelstenen.getValue());
//		}
		return edelsteenInBezit;
	}

	public List<Ontwikkelingskaart> geefKaartenNiveau1() {

		return ontwikkelingsKaartNiveau1;
	}

	public List<Ontwikkelingskaart> geefKaartenNiveau2() {

		return ontwikkelingsKaartNiveau2;
	}

	public List<Ontwikkelingskaart> geefKaartenNiveau3() {

		return ontwikkelingsKaartNiveau3;
	}

	public List<Ontwikkelingskaart> geefOntwikkelingskaartenInBezit() {
		return ontwikkelingskaartenInBezit;
	}

	public int geefAantalHuidigeSpelers() {
		return huidigeSpelers.size();
	}

	public List<Speler> geefHuidigeSpelers() {
		sorteerSpelers();
		return huidigeSpelers;
	}

	public List<Ontwikkelingskaart> geefOntwikkelingskaartenNiv1OpSpeelbord() {
		return ontwikkelingskaartenNiveau1OpSpeelbord;
	}

	public List<Ontwikkelingskaart> geefOntwikkelingskaartenNiv2OpSpeelbord() {
		return ontwikkelingskaartenNiveau2OpSpeelbord;
	}

	public List<Ontwikkelingskaart> geefOntwikkelingskaartenNiv3OpSpeelbord() {
		return ontwikkelingskaartenNiveau3OpSpeelbord;
	}

	public boolean isWinnaar() {
		return false;

	}
	// **********************************Controlleer methodes**********************************//

	public void controleerGebruikersnaam(String gebruikersnaam) throws OngeldigeGebruikersnaam {
		if (!(gebruikersnaam.matches("^[a-zA-Z][a-zA-Z0-9_\\s]*$"))) {
			throw new OngeldigeGebruikersnaam(String.format(("Geef een geldige gebruikersnaam in !")));
		}
	}

	public void controleerGeboortejaar(int geboortejaar) throws OngeldigeGeboortejaar {
		if (geboortejaar >= Speler.MIN_GEBOORTEJAAR) {
			throw new OngeldigeGeboortejaar();
			// "Geboortejaar moet geldig zijn en elke gebruiker is minstens 6 jaar oud."
		}
	}

	public void controleerInvoerStartSpel(String invoer) throws StartSpelException {
		if (!(invoer.equals("Y") || invoer.equals("N"))) {
			throw new StartSpelException();
			// "Geef [Y] in om te starten of [N] om een nog een speler toe te voegen ! "
		}
	}

	public void controleerSpelerToegevoegd(String gebruikersnaam, int geboortejaar) throws SpelerAlToegevoegdException {

		for (Speler speler : huidigeSpelers) {

			if (speler.getGebruikersnaam().equals(gebruikersnaam) && speler.getGeboortejaar() == geboortejaar) {
				throw new SpelerAlToegevoegdException();
				// "De huidige speler werd al toegevoegd !"
			}
		}
	}

	public int geefKeuzeEdelsteenFiche(int keuzeEdelsteenFiche) {

		return keuzeEdelsteenFiche;

	}

	public void voegEdelsteenInBezit(Speler s, Map<EdelsteenFiche, Integer> edelsteen) {
		s.setEdelsteenfichesInBezit(edelsteen);

	}

	public void voegEdeleInBezit(Speler s, List<Edele> edele) {
		s.setEdeleInBezit(edele);

	}

	public List<Edele> geefTeKiezenEdele() {
		return sr.geefTeKiezenEdele();
	}

	public void terugTePlaatsenEfOpSpeelbord(Map<EdelsteenFiche, Integer> edelsteenFichesOpSpeelbord) {

		Map<EdelsteenFiche, Integer> terugTeVoegenEdelstenenOpSpeelbord = sr.geefTerugTeVoegenEdelstenen();
		

		for (Entry<EdelsteenFiche, Integer> ef : terugTeVoegenEdelstenenOpSpeelbord.entrySet()) {

			switch (ef.getKey()) {
			case DIAMANTEN -> {
				int huidigeAantal = edelsteenFichesOpSpeelbord.get(EdelsteenFiche.DIAMANTEN);
				edelsteenFichesOpSpeelbord.put(EdelsteenFiche.DIAMANTEN, huidigeAantal + ef.getValue());
			}
			case ROBIJNEN -> {
				int huidigeAantal = edelsteenFichesOpSpeelbord.get(EdelsteenFiche.ROBIJNEN);
				edelsteenFichesOpSpeelbord.put(EdelsteenFiche.ROBIJNEN, huidigeAantal + ef.getValue());
			}
			case SAFFIEREN -> {
				int huidigeAantal = edelsteenFichesOpSpeelbord.get(EdelsteenFiche.SAFFIEREN);
				edelsteenFichesOpSpeelbord.put(EdelsteenFiche.SAFFIEREN, huidigeAantal + ef.getValue());
			}
			case SMARAGDEN -> {
				int huidigeAantal = edelsteenFichesOpSpeelbord.get(EdelsteenFiche.SMARAGDEN);
 				edelsteenFichesOpSpeelbord.put(EdelsteenFiche.SMARAGDEN, huidigeAantal + ef.getValue());
			}
			case ONYXEN -> {
				int huidigeAantal = edelsteenFichesOpSpeelbord.get(EdelsteenFiche.ONYXEN);
				edelsteenFichesOpSpeelbord.put(EdelsteenFiche.ONYXEN, huidigeAantal + ef.getValue());
			}

			}
		}
	}
	
	private void updateEdeleOpSpeelbord() {
		List<Edele> e = sr.geefEdeleOpSpeelbord();
		edeleOpSpeelbord.remove(e);
	}

	public void terugTePlaatsenMaxEfOpSpeelbord(Map<EdelsteenFiche, Integer> kiesEdelsteen) {
		
		for (Entry<EdelsteenFiche, Integer> ef : kiesEdelsteen.entrySet()) {

			switch (ef.getKey()) {
			case DIAMANTEN -> {
				int huidigeAantal = edelsteenOpSpeelbord.get(EdelsteenFiche.DIAMANTEN);
				edelsteenOpSpeelbord.put(EdelsteenFiche.DIAMANTEN, huidigeAantal + ef.getValue());
			}
			case ROBIJNEN -> {
				int huidigeAantal = edelsteenOpSpeelbord.get(EdelsteenFiche.ROBIJNEN);
				edelsteenOpSpeelbord.put(EdelsteenFiche.ROBIJNEN, huidigeAantal + ef.getValue());
			}
			case SAFFIEREN -> {
				int huidigeAantal = edelsteenOpSpeelbord.get(EdelsteenFiche.SAFFIEREN);
				edelsteenOpSpeelbord.put(EdelsteenFiche.SAFFIEREN, huidigeAantal + ef.getValue());
			}
			case SMARAGDEN -> {
				int huidigeAantal = edelsteenOpSpeelbord.get(EdelsteenFiche.SMARAGDEN);
				edelsteenOpSpeelbord.put(EdelsteenFiche.SMARAGDEN, huidigeAantal + ef.getValue());
			}
			case ONYXEN -> {
				int huidigeAantal = edelsteenOpSpeelbord.get(EdelsteenFiche.ONYXEN);
				edelsteenOpSpeelbord.put(EdelsteenFiche.ONYXEN, huidigeAantal + ef.getValue());
			}

			}
		}
		
	}

	public void selectedTaal(String taal) {
		 resources = ResourceBundle.getBundle("resources.text", new Locale(taal));
			
		}
	public ResourceBundle geeftaal() {
			return  resources;
			
	}
}
