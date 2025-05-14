package domein;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import exceptions.OnvoldoendeEfVoorOkException;
import util.EdelsteenFiche;

public class Beurt {

	private List<Ontwikkelingskaart> ontwikkelingskaartenInBezit = new ArrayList<>();
	private List<Edele> edeleInBezit = new ArrayList<>();
	private List<Edele> teKiezenEdele = new ArrayList<>();
	private List<Edele> edeleOpSpeelbord = new ArrayList<>();
	private List<EdelsteenFiche> edelsteenFichesInBezit = new ArrayList<>();
	private Map<EdelsteenFiche, Integer> edelstenen = new HashMap<>();
	private Map<EdelsteenFiche, Integer> terugTePlaatsenEdelstenen = new HashMap<>();
	private int aantalWit = 0;
	private int aantalRood = 0;
	private int aantalBlauw = 0;
	private int aantalGroen = 0;
	private int aantalZwart = 0;

	public Beurt(int id, Speler spelerAanDeBeurt, List<Edele> gekozenEdele,
			Map<EdelsteenFiche, Integer> gekozenEdelsteenFiches,
			Map<EdelsteenFiche, Integer> edelsteenFichesOpSpeelbord, List<Ontwikkelingskaart> gekozenOntwikkelingskaart,
			boolean pasBeurt, List<Speler> huidigeSpelers) throws OnvoldoendeEfVoorOkException {
		aanDeBeurt(spelerAanDeBeurt);
		switch (id) {
		case 1 -> neemEdelsteenFiches(spelerAanDeBeurt, gekozenEdelsteenFiches);
		case 2 -> neemEdelsteenFiches(spelerAanDeBeurt, gekozenEdelsteenFiches);
		case 3 -> controleerOntwikkelingskaart(spelerAanDeBeurt, gekozenOntwikkelingskaart, edelsteenFichesOpSpeelbord);
		case 4 -> pasBeurt(spelerAanDeBeurt);
		}
		if (gekozenEdele != null && gekozenEdele.size() == 1) {
			neemEdele(spelerAanDeBeurt, gekozenEdele);
			gekozenEdele.remove(0);
		}
		berekenPrestigePunten(spelerAanDeBeurt);
		eindeBeurt(spelerAanDeBeurt);
	}

	private void aanDeBeurt(Speler s) {
		s.setAanDeBeurt(true);
	}

	public void neemEdelsteenFiches(Speler s, Map<EdelsteenFiche, Integer> gekozenEdelsteenFiches) {

		gekozenEdelsteenFiches.forEach((key, value) -> {
			if (s.getEdelsteenfichesInBezit().get(key) < 10) {
				switch (key) {
				case DIAMANTEN -> {
					int aantalInBezit = s.getEdelsteenfichesInBezit().get(EdelsteenFiche.DIAMANTEN);
					aantalInBezit += value;
					s.voegEdelsteenFiches(key, aantalInBezit);
				}
				case ROBIJNEN -> {
					int aantalInBezit = s.getEdelsteenfichesInBezit().get(EdelsteenFiche.ROBIJNEN);
					aantalInBezit += value;
					s.voegEdelsteenFiches(key, aantalInBezit);
				}
				case SAFFIEREN -> {
					int aantalInBezit = s.getEdelsteenfichesInBezit().get(EdelsteenFiche.SAFFIEREN);
					aantalInBezit += value;
					s.voegEdelsteenFiches(key, aantalInBezit);
				}
				case SMARAGDEN -> {
					int aantalInBezit = s.getEdelsteenfichesInBezit().get(EdelsteenFiche.SMARAGDEN);
					aantalInBezit += value;
					s.voegEdelsteenFiches(key, aantalInBezit);
				}
				case ONYXEN -> {
					int aantalInBezit = s.getEdelsteenfichesInBezit().get(EdelsteenFiche.ONYXEN);
					aantalInBezit += value;
					s.voegEdelsteenFiches(key, aantalInBezit);
				}
				}
			}
		});

	}

	private void controleerOntwikkelingskaart(Speler s, List<Ontwikkelingskaart> gekozenOntwikkelingskaart,
			Map<EdelsteenFiche, Integer> edelsteenFichesOpSpeelbord) throws OnvoldoendeEfVoorOkException {
		Map<EdelsteenFiche, Integer> gekozenOntwikkelingskaarten = new HashMap<>();
		Map<EdelsteenFiche, Integer> edelstenen = new HashMap<>();
		Map<EdelsteenFiche, Integer> teBetalenEdelstenen = new HashMap<>();
		Map<EdelsteenFiche, Integer> gefilterdeEdelsteenTypeInBezit = new HashMap<>();
		Map<EdelsteenFiche, Integer> edelsteenOntwikkelingskaarten = gekozenOntwikkelingskaart.get(0).getKost();
		Map<EdelsteenFiche, Integer> edelsteenInBezit = s.getEdelsteenfichesInBezit();
		Map<EdelsteenFiche, Integer> edelsteenTypeInBezit = berekenAantalEdelsteenTypeInBezit(
				s.getOntwikkelingskaartenInBezit());

		terugTePlaatsenEdelstenen = new HashMap<>();

		boolean canBuy = true;

		for (Entry<EdelsteenFiche, Integer> ef : edelsteenOntwikkelingskaarten.entrySet()) {
			if (ef.getValue() > 0) {
				gekozenOntwikkelingskaarten.put(ef.getKey(), ef.getValue());
			}
		}

		if (!edelsteenTypeInBezit.isEmpty()) {
			for (Entry<EdelsteenFiche, Integer> ok : gekozenOntwikkelingskaarten.entrySet()) {
				EdelsteenFiche key = ok.getKey();
				for (Entry<EdelsteenFiche, Integer> et : edelsteenTypeInBezit.entrySet()) {
					if (key.equals(et.getKey())) {
						gefilterdeEdelsteenTypeInBezit.put(et.getKey(), et.getValue());

					}
				}
			}
		}

		for (Entry<EdelsteenFiche, Integer> ef : edelsteenInBezit.entrySet()) {

			for (Entry<EdelsteenFiche, Integer> ge : gefilterdeEdelsteenTypeInBezit.entrySet()) {

				if (!gefilterdeEdelsteenTypeInBezit.isEmpty()) {
					if (ef.getKey().equals(ge.getKey())) {
						if (ge.getValue() > 0 && !gefilterdeEdelsteenTypeInBezit.containsKey(ef)) {
							int aantal = ge.getValue();
							edelstenen.put(ef.getKey(), ef.getValue() + aantal);
						}
					} else {
						if (ef.getValue() > 0) {
							edelstenen.put(ef.getKey(), ef.getValue());
						}
					}
				}
			}
			if (gefilterdeEdelsteenTypeInBezit.isEmpty()) {
				if (ef.getValue() > 0) {
					edelstenen.put(ef.getKey(), ef.getValue());
				}
			}
		}

		for (Entry<EdelsteenFiche, Integer> gk : gekozenOntwikkelingskaarten.entrySet()) {
			EdelsteenFiche key = gk.getKey();
			int value = gk.getValue();

			if (!edelstenen.containsKey(key) || edelstenen.get(key) < value) {
				canBuy = false;
				break;
			}
		}

		if (!canBuy) {
			Ontwikkelingskaart o = gekozenOntwikkelingskaart.get(0);
			for (Ontwikkelingskaart ok : s.getOntwikkelingskaartenInBezit()) {
				if (ok.equals(o)) {
					s.getOntwikkelingskaartenInBezit().remove(o);
					break;
				}
			}
			gekozenOntwikkelingskaart.remove(0);
			if (gekozenOntwikkelingskaart.isEmpty()) {
				throw new OnvoldoendeEfVoorOkException();
			}
		} else {

			Iterator<Entry<EdelsteenFiche, Integer>> iterator = gekozenOntwikkelingskaarten.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<EdelsteenFiche, Integer> e = iterator.next();
				for (Entry<EdelsteenFiche, Integer> ef : gefilterdeEdelsteenTypeInBezit.entrySet()) {
					if (ef.getKey().equals(e.getKey())) {
						if (ef.getValue() >= e.getValue()) {
							gekozenOntwikkelingskaarten.put(ef.getKey(), ef.getValue() - e.getValue());
						}
						if (ef.getValue() <= e.getValue()) {
							gekozenOntwikkelingskaarten.put(ef.getKey(), e.getValue() - ef.getValue());
						}
					}
					if (e.getValue() == 0) {
						iterator.remove();
						break;
					}
				}

			}
			for (Entry<EdelsteenFiche, Integer> e : gekozenOntwikkelingskaarten.entrySet()) {
				int huidigeValue = s.getEdelsteenfichesInBezit().get(e.getKey());
				terugTePlaatsenEdelstenen.put(e.getKey(), e.getValue());
				for (Entry<EdelsteenFiche, Integer> et : gekozenOntwikkelingskaarten.entrySet()) {
					if (e.getKey().equals(et.getKey()) && e.getValue().equals(et.getValue())) {
						teBetalenEdelstenen.put(e.getKey(), huidigeValue - e.getValue());
					}
				}
			}
			s.setOntwikkelingskaartenInBezit(gekozenOntwikkelingskaart);
		}
		voegEdelsteenFiche(s, teBetalenEdelstenen);
	}

	private void voegEdelsteenFiche(Speler s, Map<EdelsteenFiche, Integer> gekozenEdelsteenFiches) {
		if (s.getEdelsteenfichesInBezit().size() < 10) {
			gekozenEdelsteenFiches.forEach((key, value) -> {
				switch (key) {
				case DIAMANTEN -> {
					s.voegEdelsteenFiches(key, value);
				}
				case ROBIJNEN -> {
					s.voegEdelsteenFiches(key, value);
				}
				case SAFFIEREN -> {
					s.voegEdelsteenFiches(key, value);
				}
				case SMARAGDEN -> {
					s.voegEdelsteenFiches(key, value);
				}
				case ONYXEN -> {
					s.voegEdelsteenFiches(key, value);
				}
				}
			});
		}
	}

	private void neemEdele(Speler s, List<Edele> gekozenEdele) {
		s.setEdeleInBezit(gekozenEdele);

	}

	private void berekenPrestigePunten(Speler s) {
		int prestigePunten = 0;
		for (Ontwikkelingskaart o : s.getOntwikkelingskaartenInBezit()) {
			prestigePunten += o.getPrestige();
		}
		for (Edele e : edeleInBezit) {
			prestigePunten += e.getPrestige();
		}
		s.setTotaalAantalPrestigePunten(prestigePunten);
	}

	private void pasBeurt(Speler s) {
		s.setAanDeBeurt(false);
	}

	private void eindeBeurt(Speler s) {
		s.setAanDeBeurt(false);
	}

	public Map<EdelsteenFiche, Integer> terugTeVoegenEdelstenenOpSpeelbord() {
		return terugTePlaatsenEdelstenen;
	}

	public Map<EdelsteenFiche, Integer> geefEdeleOpSpeelbord() {

		return null;
	}

	private Map<EdelsteenFiche, Integer> berekenAantalEdelsteenTypeInBezit(
			List<Ontwikkelingskaart> ontwikkelingskaarten) {

		Map<EdelsteenFiche, Integer> e = new HashMap<EdelsteenFiche, Integer>();

		for (Ontwikkelingskaart o : ontwikkelingskaarten) {

			switch (o.getEdelsteenType()) {
			case DIAMANTEN -> {
				aantalWit = berekenAantal(o.getEdelsteenType(), ontwikkelingskaarten);
				e.put(EdelsteenFiche.DIAMANTEN, aantalWit);
			}

			case ROBIJNEN -> {
				aantalRood = berekenAantal(o.getEdelsteenType(), ontwikkelingskaarten);
				e.put(EdelsteenFiche.ROBIJNEN, aantalRood);
			}

			case SAFFIEREN -> {
				aantalBlauw = berekenAantal(o.getEdelsteenType(), ontwikkelingskaarten);
				e.put(EdelsteenFiche.SAFFIEREN, aantalBlauw);
			}

			case SMARAGDEN -> {
				aantalGroen = berekenAantal(o.getEdelsteenType(), ontwikkelingskaarten);
				e.put(EdelsteenFiche.SMARAGDEN, aantalGroen);
			}

			case ONYXEN -> {
				aantalZwart = berekenAantal(o.getEdelsteenType(), ontwikkelingskaarten);
				e.put(EdelsteenFiche.ONYXEN, aantalZwart);
			}
			}

		}
		return e;
	}

	private int berekenAantal(EdelsteenFiche edelsteenFiche, List<Ontwikkelingskaart> ontwikkelingskaarten) {
		int aantal = 0;
		for (Ontwikkelingskaart o : ontwikkelingskaarten) {
			if (o.getEdelsteenType().equals(edelsteenFiche)) {
				aantal++;
			}
		}
		return aantal;
	}
}
