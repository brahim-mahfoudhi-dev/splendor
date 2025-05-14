package domein;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dto.EdeleDTO;
import dto.OntwikkelingskaartDTO;
import dto.SpelerDTO;
import dto.WinnaarDTO;
import exceptions.OngeldigeGeboortejaar;
import exceptions.OngeldigeGebruikersnaam;
import exceptions.OnvoldoendeEfVoorOkException;
import exceptions.SpelerAlToegevoegdException;
import exceptions.SpelerBestaatNietException;
import exceptions.StartSpelException;
import persistentie.EdeleMapper;
import persistentie.OntwikkelingskaartMapper;
import persistentie.SpelerMapper;
import util.EdelsteenFiche;

public class DomeinController {

	private final SpelerMapper spelerMapper;
	private final OntwikkelingskaartMapper om;
	private final EdeleMapper em;
	private final Splendor sp;

	public DomeinController() {
		spelerMapper = new SpelerMapper();
		om = new OntwikkelingskaartMapper();
		em = new EdeleMapper();
		sp = new Splendor();
	}

	public void geefSpelerUitDB(String gebruikersnaam, int geboortejaar) throws OngeldigeGebruikersnaam, OngeldigeGeboortejaar, SpelerAlToegevoegdException, SpelerBestaatNietException {
		sp.controleerGebruikersnaam(gebruikersnaam);
		sp.controleerGeboortejaar(geboortejaar);
		sp.controleerSpelerToegevoegd(gebruikersnaam, geboortejaar);
		Speler s = spelerMapper.geefSpeler(gebruikersnaam, geboortejaar);
		if (s != null) {
			sp.voegSpeler(s);
		}
	}

	public void initialiseerSplendor() throws URISyntaxException {
		int aantalSpelers = geefAantalSpelers();
		geefOntwikkelingskaartenMapper();
		geefEdeleMapper();
		sp.plaatsEdeleOpSpeelbord(aantalSpelers);
		sp.plaatsOntwikkelingskaartOpSpeelbord();
		sp.plaatsEdelsteenFichesOpSpeelbord();
		
	}

	private void geefOntwikkelingskaartenMapper() {
		List<Ontwikkelingskaart> ontwikkelingskaarten = om.geefOntwikkelingskaart();
		sp.voegOntwikkelingskaarten(ontwikkelingskaarten);
	}

	public List<Ontwikkelingskaart> geefOntwikkelingskaartenSpeelbord() {
		List<Ontwikkelingskaart> ontwikkelingskaarten = new ArrayList<>();
		for (Ontwikkelingskaart o : sp.geefOntwikkelingskaartenNiv1OpSpeelbord()) {
			
		}
		ontwikkelingskaarten.addAll(sp.geefOntwikkelingskaartenNiv1OpSpeelbord());
		ontwikkelingskaarten.addAll(sp.geefOntwikkelingskaartenNiv2OpSpeelbord());
		ontwikkelingskaarten.addAll(sp.geefOntwikkelingskaartenNiv3OpSpeelbord());

		return ontwikkelingskaarten;
	}

	public List<Ontwikkelingskaart> geefOntwikkelingskaartenNiveau3() {
		List<Ontwikkelingskaart> ontwikkelingskaarten = sp.geefKaartenNiveau3();
		return ontwikkelingskaarten;
	}

	public List<Ontwikkelingskaart> geefOntwikkelingskaartenNiveau2() {
		List<Ontwikkelingskaart> ontwikkelingskaarten = sp.geefKaartenNiveau2();
		return ontwikkelingskaarten;
	}

	public List<Ontwikkelingskaart> geefOntwikkelingskaartenNiveau1() {
		List<Ontwikkelingskaart> ontwikkelingskaarten = sp.geefKaartenNiveau1();
		return ontwikkelingskaarten;
	}

	public List<Ontwikkelingskaart> geefOntwikkelingskaartenInBezit() {
		return sp.geefOntwikkelingskaartenInBezit();
	}

	private void geefEdeleMapper() throws URISyntaxException {
		List<Edele> edele = em.geefEdele();
		sp.voegEdele(edele);
	}

	public List<Edele> geefEdeleOpSpeelbord() {
		List<Edele> edeleOpSpeelbord = sp.geefEdeleOpSpeelbord();
		return edeleOpSpeelbord;
	}

	public List<Edele> geefEdeleInBezit() {
		// TODO
		return sp.geefEdeleInBezit();
	}

	public Map<EdelsteenFiche, Integer> geefEdelsteenFicheOpSpeelbord() {
		return sp.geefEdelsteenFichesOpSpeelbord();
	}

	public Map<EdelsteenFiche, Integer> geefEdelsteenFicheInBezit() {
		// TODO
		return sp.geefEdelsteenFichesInBezit();
	}

	public void startSpel() {
		sp.sorteerSpelers();
		Speler eersteSpeler = sp.geefHuidigeSpelers().get(0);
		eersteSpeler.setStartSpeler(true);
		eersteSpeler.setAanDeBeurt(true);
	}

	public void speel() throws OnvoldoendeEfVoorOkException {
		sp.speelRonde("0", null, null, null, false);
	}

	public void neem3EdelsteenFiche(String keuze, Map<EdelsteenFiche, Integer> gekozenEdelsteenFiches) throws OnvoldoendeEfVoorOkException {
		sp.speelRonde(keuze, gekozenEdelsteenFiches, null, null, false);
	}

	public void neem2EdelsteenFiche(String keuze, Map<EdelsteenFiche, Integer> gekozenEdelsteenFiches) throws OnvoldoendeEfVoorOkException {
		sp.speelRonde(keuze, gekozenEdelsteenFiches, null, null, false);
	}

	public void koopOntwikkelingskaart(String keuze, OntwikkelingskaartDTO o) throws OnvoldoendeEfVoorOkException {
		sp.speelRonde(keuze, null, o, null, false);
	}

	public void pasBeurt(String keuze, boolean pasBeurt) throws OnvoldoendeEfVoorOkException{
		sp.speelRonde(keuze, null, null, null, pasBeurt);
	}

	public void eindeBeurt(Speler s) {
		s.setAanDeBeurt(false);
	}

	public void startBeurt(Speler s) {
		s.setAanDeBeurt(true);
	}
	
	public void geefEdelsteenTerug() {
		
	}

	public boolean isEindeSpel() {
		return sp.isEindeSpel();
	}
	
	public void neemEdele(List<Edele> gekozenEdele) throws OnvoldoendeEfVoorOkException {
		sp.speelRonde(null, null, null, gekozenEdele, isEindeSpel());
	}

	public List<WinnaarDTO> geefWinnaar() {

		List<WinnaarDTO> winnaarsDTO = new ArrayList<>();
		List<Speler> winnaars = sp.bepaalWinnaar();

		for (Speler s : winnaars) {

			winnaarsDTO.add(new WinnaarDTO(s.getGebruikersnaam(), s.getTotaalAantalPrestigePunten()));
		}

		return winnaarsDTO;
	}

	public List<SpelerDTO> geefOverzichtSpelers() {
		List<SpelerDTO> spelerDTO = new ArrayList<>();

		for (Speler s : sp.geefHuidigeSpelers()) {

			spelerDTO.add(new SpelerDTO(s.getGebruikersnaam(), s.getGeboortejaar(), s.getTotaalAantalPrestigePunten(),
					s.isAanDeBeurt(), s.isStartSpeler(), s.getOntwikkelingskaartenInBezit(), s.getEdeleInBezit(),
					s.getEdelsteenfichesInBezit(), s.getEdelsteenTypeInBezit()));
		}

		return spelerDTO;
	}

	public List<EdeleDTO> geefEdele() {
		List<EdeleDTO> edeleDTO = new ArrayList<>();

		for (Edele e : sp.geefEdele()) {
			edeleDTO.add(new EdeleDTO(e.getPrestige(), e.getRequirements().get(EdelsteenFiche.DIAMANTEN),
					e.getRequirements().get(EdelsteenFiche.ROBIJNEN), e.getRequirements().get(EdelsteenFiche.SAFFIEREN),
					e.getRequirements().get(EdelsteenFiche.SMARAGDEN), e.getRequirements().get(EdelsteenFiche.ONYXEN),
					e.getImage()));
		}
		return edeleDTO;
	}
	
	public List<EdeleDTO> geefTeKiezenEdele() {
		List<EdeleDTO> edeleDTO = new ArrayList<>();

		for (Edele e : sp.geefTeKiezenEdele()) {
			edeleDTO.add(new EdeleDTO(e.getPrestige(), e.getRequirements().get(EdelsteenFiche.DIAMANTEN),
					e.getRequirements().get(EdelsteenFiche.ROBIJNEN), e.getRequirements().get(EdelsteenFiche.SAFFIEREN),
					e.getRequirements().get(EdelsteenFiche.SMARAGDEN), e.getRequirements().get(EdelsteenFiche.ONYXEN),
					e.getImage()));
		}
		return edeleDTO;
	}

	public List<OntwikkelingskaartDTO> geefOntwikkelingsNiv1kaart() {
		List<OntwikkelingskaartDTO> ontwikkelingskaartNiv1DTO = new ArrayList<>();

		for (Ontwikkelingskaart o : geefOntwikkelingskaartenNiveau1()) {
			ontwikkelingskaartNiv1DTO.add(new OntwikkelingskaartDTO(o.getLevel(), o.getPrestige(), o.getEdelsteenType(),
					o.getKost().get(EdelsteenFiche.DIAMANTEN), o.getKost().get(EdelsteenFiche.ROBIJNEN),
					o.getKost().get(EdelsteenFiche.SAFFIEREN), o.getKost().get(EdelsteenFiche.SMARAGDEN),
					o.getKost().get(EdelsteenFiche.ONYXEN), o.getImage()));
		}
		return ontwikkelingskaartNiv1DTO;
	}
	
	public List<OntwikkelingskaartDTO> geefOntwikkelingsNiv1OpSpeelbordkaart() {
		List<OntwikkelingskaartDTO> ontwikkelingskaartNiv1DTO = new ArrayList<>();

		for (Ontwikkelingskaart o : sp.geefOntwikkelingskaartenNiv1OpSpeelbord()) {
			ontwikkelingskaartNiv1DTO.add(new OntwikkelingskaartDTO(o.getLevel(), o.getPrestige(), o.getEdelsteenType(),
					o.getKost().get(EdelsteenFiche.DIAMANTEN), o.getKost().get(EdelsteenFiche.ROBIJNEN),
					o.getKost().get(EdelsteenFiche.SAFFIEREN), o.getKost().get(EdelsteenFiche.SMARAGDEN),
					o.getKost().get(EdelsteenFiche.ONYXEN), o.getImage()));
		}
		return ontwikkelingskaartNiv1DTO;
	}
	
	public List<OntwikkelingskaartDTO> geefOntwikkelingsNiv2kaart() {
		List<OntwikkelingskaartDTO> ontwikkelingskaartNiv2DTO = new ArrayList<>();

		for (Ontwikkelingskaart o : geefOntwikkelingskaartenNiveau2()) {
			ontwikkelingskaartNiv2DTO.add(new OntwikkelingskaartDTO(o.getLevel(), o.getPrestige(), o.getEdelsteenType(),
					o.getKost().get(EdelsteenFiche.DIAMANTEN), o.getKost().get(EdelsteenFiche.ROBIJNEN),
					o.getKost().get(EdelsteenFiche.SAFFIEREN), o.getKost().get(EdelsteenFiche.SMARAGDEN),
					o.getKost().get(EdelsteenFiche.ONYXEN), o.getImage()));
		}
		return ontwikkelingskaartNiv2DTO;
	}
	
	public List<OntwikkelingskaartDTO> geefOntwikkelingsNiv2OpSpeelbordkaart() {
		List<OntwikkelingskaartDTO> ontwikkelingskaartNiv2DTO = new ArrayList<>();

		for (Ontwikkelingskaart o : sp.geefOntwikkelingskaartenNiv2OpSpeelbord()) {
			ontwikkelingskaartNiv2DTO.add(new OntwikkelingskaartDTO(o.getLevel(), o.getPrestige(), o.getEdelsteenType(),
					o.getKost().get(EdelsteenFiche.DIAMANTEN), o.getKost().get(EdelsteenFiche.ROBIJNEN),
					o.getKost().get(EdelsteenFiche.SAFFIEREN), o.getKost().get(EdelsteenFiche.SMARAGDEN),
					o.getKost().get(EdelsteenFiche.ONYXEN), o.getImage()));
		}
		return ontwikkelingskaartNiv2DTO;
	}
	
	public List<OntwikkelingskaartDTO> geefOntwikkelingsNiv3kaart() {
		List<OntwikkelingskaartDTO> ontwikkelingskaartNiv3DTO = new ArrayList<>();

		for (Ontwikkelingskaart o : geefOntwikkelingskaartenNiveau3()) {
			ontwikkelingskaartNiv3DTO.add(new OntwikkelingskaartDTO(o.getLevel(), o.getPrestige(), o.getEdelsteenType(),
					o.getKost().get(EdelsteenFiche.DIAMANTEN), o.getKost().get(EdelsteenFiche.ROBIJNEN),
					o.getKost().get(EdelsteenFiche.SAFFIEREN), o.getKost().get(EdelsteenFiche.SMARAGDEN),
					o.getKost().get(EdelsteenFiche.ONYXEN), o.getImage()));
		}
		return ontwikkelingskaartNiv3DTO;
	}
	
	public List<OntwikkelingskaartDTO> geefOntwikkelingsNiv3OpSpeelbordkaart() {
		List<OntwikkelingskaartDTO> ontwikkelingskaartNiv3DTO = new ArrayList<>();

		for (Ontwikkelingskaart o : sp.geefOntwikkelingskaartenNiv3OpSpeelbord()) {
			ontwikkelingskaartNiv3DTO.add(new OntwikkelingskaartDTO(o.getLevel(), o.getPrestige(), o.getEdelsteenType(),
					o.getKost().get(EdelsteenFiche.DIAMANTEN), o.getKost().get(EdelsteenFiche.ROBIJNEN),
					o.getKost().get(EdelsteenFiche.SAFFIEREN), o.getKost().get(EdelsteenFiche.SMARAGDEN),
					o.getKost().get(EdelsteenFiche.ONYXEN), o.getImage()));
		}
		return ontwikkelingskaartNiv3DTO;
	}

	public int geefAantalSpelers() {
		int aantalHuidigeSpelers = sp.geefAantalHuidigeSpelers();
		return aantalHuidigeSpelers;
	}

	public void controleerStartSpel(String invoer) throws StartSpelException {
		sp.controleerInvoerStartSpel(invoer);

	}
	
	public void terugTePlaatsenEfOpSpeelbord(Map<EdelsteenFiche, Integer> kiesEdelsteen) {
		sp.terugTePlaatsenMaxEfOpSpeelbord(kiesEdelsteen);
	}

	public void taalSelectie(String taal) {
		sp.selectedTaal(taal);
		
	}


}
