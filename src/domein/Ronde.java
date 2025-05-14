package domein;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import exceptions.OnvoldoendeEfVoorOkException;
import util.EdelsteenFiche;

public class Ronde {

	private List<Edele> edeleInBezit = new ArrayList<>();
	private List<Edele> kiesEdele = new ArrayList<>();
	private List<Edele> huidigeEdeleOpSpeelbord = new ArrayList<>();
	private int aantalRonde = 0;
//		private int aantalRonde = 1;
	private int teller = 0;
	public final static int MIN_AANTAL_SPELERS = 2;
	public final static int MAX_AANTAL_SPELERS = 4;
	// Class SpeelBeurt
	private Beurt sb;

	public Ronde(String keuze, Map<EdelsteenFiche, Integer> gekozenEdelsteenFiches, List<Edele> gekozenEdele,
			List<Ontwikkelingskaart> gekozenOntwikkelingskaart, boolean pasBeurt, List<Speler> huidigeSpelers,
			List<Edele> edeleOpSpeelbord) throws OnvoldoendeEfVoorOkException {
//		int id = Integer.parseInt(keuze);

//		if (id > 0) {
//			Speler spelerAanDeBeurt = huidigeSpelers.get(teller);
//			speelBeurt(keuze, spelerAanDeBeurt, gekozenEdelsteenFiches, gekozenOntwikkelingskaart, gekozenEdele,
//					pasBeurt, huidigeSpelers, edeleOpSpeelbord, gekozenEdelsteenFiches);
//			teller++;
//			if (teller == huidigeSpelers.size()) {
//				teller = 0;
//			}
//			huidigeSpelers.get(teller).setAanDeBeurt(true);
//			id = 0;
	}

	public void speelBeurt(String keuze, Speler spelerAanDeBeurt, Map<EdelsteenFiche, Integer> gekozenEdelsteenFiches,
			List<Ontwikkelingskaart> gekozenOntwikkelingskaart, List<Edele> gekozenEdele, boolean pasBeurt,
			List<Speler> huidigeSpelers, List<Edele> edeleOpSpeelbord,
			Map<EdelsteenFiche, Integer> edelsteenFicheOpSpeelbord) throws OnvoldoendeEfVoorOkException {
		int id = Integer.parseInt(keuze);
		sb = new Beurt(id, spelerAanDeBeurt, edeleOpSpeelbord, gekozenEdelsteenFiches, edelsteenFicheOpSpeelbord,
				gekozenOntwikkelingskaart, pasBeurt, huidigeSpelers);
		controleerEdele(edeleOpSpeelbord, spelerAanDeBeurt);
	}

	private void controleerEdele(List<Edele> edeleOpSpeelbord, Speler s) {
		boolean canBuy = true;
		int currentValue = 0;
		Map<EdelsteenFiche, Integer> edelstenenOntwikkelingskaartenInBezit = new HashMap<>();
		Map<EdelsteenFiche, Integer> edelstenenEdele = new HashMap<>();
		Map<EdelsteenFiche, Integer> gefilterdeEdelstenen = new HashMap<>();

		List<Edele> edele = new ArrayList<>();

		for (Ontwikkelingskaart o : s.getOntwikkelingskaartenInBezit()) {

			if (s.getEdelsteenTypeInBezit().containsKey(o.getEdelsteenType())) {
				currentValue = s.getEdelsteenTypeInBezit().get(o.getEdelsteenType());
				s.getEdelsteenTypeInBezit().put(o.getEdelsteenType(), currentValue + 1);
			}
		}

		for (Entry<EdelsteenFiche, Integer> ef : s.getEdelsteenTypeInBezit().entrySet()) {
			if (ef.getValue() > 0) {
				edelstenenOntwikkelingskaartenInBezit.put(ef.getKey(), ef.getValue());
			}
		}
		if (!edelstenenOntwikkelingskaartenInBezit.isEmpty()) {
			for (Edele e : edeleOpSpeelbord) {
				for (Entry<EdelsteenFiche, Integer> ef : e.getRequirements().entrySet()) {
					if (ef.getValue() > 0) {
						edelstenenEdele.put(ef.getKey(), ef.getValue());
					}
				}

				for (Entry<EdelsteenFiche, Integer> ef : edelstenenOntwikkelingskaartenInBezit.entrySet()) {
					if (edelstenenEdele.containsKey(ef.getKey())) {
						gefilterdeEdelstenen.put(ef.getKey(), ef.getValue());
					} else {
						gefilterdeEdelstenen.remove(ef.getKey());
					}
				}
				if (!gefilterdeEdelstenen.isEmpty()) {
					for (Entry<EdelsteenFiche, Integer> ee : edelstenenEdele.entrySet()) {
						EdelsteenFiche key = ee.getKey();
						int value = ee.getValue();

						if (!gefilterdeEdelstenen.containsKey(key) || gefilterdeEdelstenen.get(key) < value) {
							canBuy = false;
							break;
						}
					}

					edelstenenEdele = new HashMap<>();

					if (canBuy) {
						huidigeEdeleOpSpeelbord.remove(e);
						edele.add(e);
						
					}

					if (edele.size() > 0) {
						if (edele.size() == 1) {
							s.setEdeleInBezit(edele);
						} else {
							kiesEdele(edele);
						}
					} else {
						for (Ontwikkelingskaart o : s.getOntwikkelingskaartenInBezit()) {

							if (s.getEdelsteenTypeInBezit().containsKey(o.getEdelsteenType())) {

								currentValue = s.getEdelsteenTypeInBezit().get(o.getEdelsteenType());
								s.getEdelsteenTypeInBezit().put(o.getEdelsteenType(), 0);
							}
						}
					}
				}
			}
		}
	}

	private void kiesEdele(List<Edele> e) {
		kiesEdele.addAll(e);
	}

	public List<Edele> geefTeKiezenEdele() {
		if (kiesEdele.size() >= 2) {
			return kiesEdele;
		}
		return edeleInBezit;
	}

	public Map<EdelsteenFiche, Integer> geefTerugTeVoegenEdelstenen() {

		return sb.terugTeVoegenEdelstenenOpSpeelbord();
	}

	public List<Edele> geefEdeleOpSpeelbord() {

		return huidigeEdeleOpSpeelbord;
	}

	// **********************************Spelfunctionaliteiten**********************************//

//	private void neemEdelsteenFiche(Speler s) {
//
////		s.setEdelsteenfichesInBezit(edelsteenOpSpeelbord.get(keuze));
//
//	}

//TODO: TESTEN
//	private void vulOntwikkelingskaartenBij(int plaats) {
//		if (ontwikkelingskaartenNiveau1OpSpeelbord.size() < MAX_AANTAL_ONTWIKKELINGSKAARTEN_OP_SPEELBORD) {
//			ontwikkelingskaartenNiveau1OpSpeelbord.add(plaats, ontwikkelingsKaartNiveau1.get(0));
//		}
//	}

////TODO: TESTEN
//	private void koopOntwikkelingskaart(Speler s) {
//		// TODO
//	}
//
////TODO: TESTEN
//	private void voegOntwKaartBijOntwInBezit(Speler s) {
//
//		s.setOntwikkelingskaartenInBezit(ontwikkelingskaartenInBezit);
//	}
//
////TODO: TESTEN
//	private void neemOntwikkelingskaart(int niveau) {
//
//		switch (niveau) {
//		case 1 -> ontwikkelingsKaartNiveau1.get(0);
//		case 2 -> ontwikkelingsKaartNiveau2.get(0);
//		case 3 -> ontwikkelingsKaartNiveau3.get(0);
//
//		}
//	}

//	private void berekenPrestigePunten(Speler s) {
//		int prestigePunten = 0;
//		for (Ontwikkelingskaart o : ontwikkelingskaartenInBezit) {
//			prestigePunten += o.getPrestige();
//		}
//		for (Edele e : edeleInBezit) {
//			prestigePunten += e.getPrestige();
//		}
//		s.setTotaalAantalPrestigePunten(prestigePunten);
//	}
//
//	public void isStartSpeler(Speler s) {
//		s.setStartSpeler(true);
//	}
//
//	private List<Edele> geefTeKiezenEdele() {
//		return teKiezenEdele;
//
//	}
}
