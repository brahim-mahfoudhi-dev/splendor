package domein;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exceptions.OngeldigeGeboortejaar;
import exceptions.OngeldigeGebruikersnaam;
import util.EdelsteenFiche;

//import java.time.LocalDate;

//Use Case 1 Done
//USe Case 2

public class Speler {

	public static final int HUIDIG_JAAR = 2023;// LocalDate.now().getYear() have problems with this
	public static final int MIN_GEBOORTEJAAR = HUIDIG_JAAR - 6;

	private String gebruikersnaam;
	private int geboortejaar;
	private int aantalKerenAanDeBeurt;
	private int totaalAantalPrestigePunten; // Default 0
	private boolean aanDeBeurt; // Automaticly Selected by a methode of name and age
	private boolean startSpeler;
	private List<Ontwikkelingskaart> ontwikkelingskaartInBezit;
	private List<Edele> EdeleInBezit;
	private Map<EdelsteenFiche, Integer> edelsteenficheInBezit;
	private Map<EdelsteenFiche, Integer> edelsteenTypeInBezit;

	public Speler(String gebruiksnaam, int geboortejaar, boolean aanDeBeurt, boolean startSpeler) throws OngeldigeGebruikersnaam, OngeldigeGeboortejaar {

		this.gebruikersnaam = gebruiksnaam;
		setGeboortejaar(geboortejaar);
		this.aantalKerenAanDeBeurt = 0;
		this.totaalAantalPrestigePunten = 0;
		this.aanDeBeurt = false;
		this.startSpeler = false;
		this.ontwikkelingskaartInBezit = new ArrayList<>();
		this.EdeleInBezit = new ArrayList<>();
		this.edelsteenficheInBezit = new HashMap<>();
		this.edelsteenficheInBezit.put(EdelsteenFiche.DIAMANTEN, 0);
		this.edelsteenficheInBezit.put(EdelsteenFiche.ROBIJNEN, 0);
		this.edelsteenficheInBezit.put(EdelsteenFiche.SAFFIEREN, 0);
		this.edelsteenficheInBezit.put(EdelsteenFiche.SMARAGDEN, 0);
		this.edelsteenficheInBezit.put(EdelsteenFiche.ONYXEN, 0);
		this.edelsteenTypeInBezit = new HashMap<>();
		this.edelsteenTypeInBezit.put(EdelsteenFiche.DIAMANTEN, 0);
		this.edelsteenTypeInBezit.put(EdelsteenFiche.ROBIJNEN, 0);
		this.edelsteenTypeInBezit.put(EdelsteenFiche.SAFFIEREN, 0);
		this.edelsteenTypeInBezit.put(EdelsteenFiche.SMARAGDEN, 0);
		this.edelsteenTypeInBezit.put(EdelsteenFiche.ONYXEN, 0);
	}

	@Override
	public String toString() {
		return String.format(
				"Speler: %s\nGeboortejaar: %d\nTotaal aantal prestige punten: %d\nAan de beurt: %b\nStartspeler: %b\nKaarten in bezit: %s\nEdele in bezit: %s\nFiches in bezit: %s\n",
				getGebruikersnaam(), getGeboortejaar(), getTotaalAantalPrestigePunten(), isAanDeBeurt(),
				isStartSpeler(), getOntwikkelingskaartenInBezit().toString(), getEdeleInBezit().toString(),
				getEdelsteenfichesInBezit().toString());
	}

	public String getGebruikersnaam() {
		return gebruikersnaam;
	}

	public int getGeboortejaar() {
		return geboortejaar;
	}

	public int getTotaalAantalPrestigePunten() {
		return totaalAantalPrestigePunten;
	}

	public int getAantalKerenAanDeBeurt() {
		return aantalKerenAanDeBeurt;
	}

	public void setAantalKerenAanDeBeurt(int aantalKerenAanDeBeurt) {
		this.aantalKerenAanDeBeurt = aantalKerenAanDeBeurt;
	}

	public boolean isGewonnen() {
		return getTotaalAantalPrestigePunten() > 15;
	}

	public void setTotaalAantalPrestigePunten(int totaalAantalPrestigePunten) {
		this.totaalAantalPrestigePunten = totaalAantalPrestigePunten;
	}

	public boolean isAanDeBeurt() {
		return this.aanDeBeurt;
	}

	public void setAanDeBeurt(boolean aanDeBeurt) {
		this.aanDeBeurt = aanDeBeurt;
	}

	public boolean isStartSpeler() {
		return this.startSpeler;
	}

	public void setStartSpeler(boolean startSpeler) {
		this.startSpeler = startSpeler;
		
	}

	public List<Ontwikkelingskaart> getOntwikkelingskaartenInBezit() {
		return ontwikkelingskaartInBezit;
	}

	public void setOntwikkelingskaartenInBezit(List<Ontwikkelingskaart> ontwikkelingskaartenInBezit) {
		this.ontwikkelingskaartInBezit.addAll(ontwikkelingskaartenInBezit);
	}

	public List<Edele> getEdeleInBezit() {
		return EdeleInBezit;
	}

	public void setEdeleInBezit(List<Edele> edeleInBezit) {
		EdeleInBezit = edeleInBezit;
	}

	public Map<EdelsteenFiche, Integer> getEdelsteenfichesInBezit() {
		return edelsteenficheInBezit;
	}
	

	public Map<EdelsteenFiche, Integer> getEdelsteenTypeInBezit() {
		return edelsteenTypeInBezit;
	}

	public void setEdelsteenTypeInBezit(Map<EdelsteenFiche, Integer> edelsteenType) {
		this.edelsteenTypeInBezit = edelsteenType;

	}

	public void setEdelsteenfichesInBezit(Map<EdelsteenFiche, Integer> edelsteenfichesInBezit) {
		this.edelsteenficheInBezit = edelsteenfichesInBezit;
	}
	

	public void setGebruikersnaam(String gebruikersnaam) {
		this.gebruikersnaam = gebruikersnaam;
	}

//	private void setGebruiksnaam(String gebruikersnaam) throws OngeldigeGebruikersnaam {
//
//		// needs still to make our own exeption to not block program
//
//		if (!(gebruikersnaam.matches("^[a-zA-Z][a-zA-Z0-9_\\s]*$"))) {
//			throw new OngeldigeGebruikersnaam(
//					"Gebruikersnaam mag spaties of _ bevatten maar geen andere speciale tekens, enkel cijfers en letters. De\r\n"
//							+ "gebruikersnaam moet altijd starten met een letter (groot of klein).");
//		}
//
//		this.gebruikersnaam = gebruikersnaam;
//
//	}
	private void setGeboortejaar(int geboortejaar) throws OngeldigeGeboortejaar {

		if (geboortejaar > MIN_GEBOORTEJAAR) {
			throw new OngeldigeGeboortejaar("Geboortejaar moet geldig zijn en elke gebruiker is minstens 6 jaar oud");
		}
		this.geboortejaar = geboortejaar;
	}

	public void voegEdelsteenFiches(EdelsteenFiche key, Integer value) {
		
		if (value > 0) {
		this.edelsteenficheInBezit.put(key, value);
		
		}else {
			this.edelsteenficheInBezit.put(key, 0);
		}
			
	}
	
	public void voegEdelsteenTypeInBezit(EdelsteenFiche key, Integer value) {
				switch (key) {
				case DIAMANTEN -> {
					this.edelsteenTypeInBezit.put(key, value);
				}
				case ROBIJNEN -> {
					this.edelsteenTypeInBezit.put(key, value);

				}
				case SAFFIEREN -> {
					this.edelsteenTypeInBezit.put(key, value);

				}
				case SMARAGDEN -> {
					this.edelsteenTypeInBezit.put(key, value);

				}
				case ONYXEN -> {
					this.edelsteenTypeInBezit.put(key, value);
				}
				}
	}
	
	public void voegEdelsteenFicheOntwikkelingskaarten(EdelsteenFiche e) {
		
	}
}
