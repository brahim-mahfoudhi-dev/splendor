package ui;

import java.util.List;
import java.util.Scanner;

import domein.DomeinController;
import domein.Splendor;
import dto.SpelerDTO;
import dto.WinnaarDTO;
//import exceptions.OngeldigeAantalSpelers;
import exceptions.OngeldigeGeboortejaar;
import exceptions.OngeldigeGebruikersnaam;
import exceptions.OnvoldoendeEfVoorOkException;
import exceptions.SpelerAlToegevoegdException;
import exceptions.SpelerBestaatNietException;
import exceptions.StartSpelException;

public class SplendorApplicatie {

	private final DomeinController dc;
	private final Scanner s = new Scanner(System.in);
	private int aantalSpelers = 0;
	private String invoer = "";

	public SplendorApplicatie(DomeinController dc) {

		this.dc = dc;

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
		toonOverzichtSpelers();
	}

	private void voegSpelerToe() {
		do {

			try {
				System.out.print("Gebruikersnaam: ");
				String gebruikersnaam = s.next();
				s.nextLine();
				System.out.printf("Geboortejaar: ");
				String gb = s.next();
				int geboortejaar = Integer.parseInt(gb);
				s.nextLine();
				dc.geefSpelerUitDB(gebruikersnaam, geboortejaar);

				startSpel();

			} catch (OngeldigeGebruikersnaam e) {
				System.err.print(e.getMessage());
				System.out.println();

			} catch (OngeldigeGeboortejaar e) {
				System.err.print(e.getMessage());
				System.out.println();

			} catch (NumberFormatException e) {
				System.err.println("Gelieve een geboortejaar in te geven !");

			} catch (SpelerAlToegevoegdException e) {
				s.nextLine();
				System.err.println(e.getMessage());
				System.out.println();
			} catch (SpelerBestaatNietException e) {
				s.nextLine();
				System.err.println(e.getMessage());
				System.out.println();
			}

		} while (!(aantalSpelers > 3 || invoer.equals("Y")));
	}

	private void startSpel() {

		aantalSpelers = dc.geefAantalSpelers();
		if (aantalSpelers >= Splendor.MIN_AANTAL_SPELERS && aantalSpelers <= Splendor.MAX_AANTAL_SPELERS - 1) {
			do {
				try {
					System.out.printf("Start spel ?: ");
					invoer = s.next();
					dc.controleerStartSpel(invoer);

				} catch (StartSpelException e) {
					s.nextLine();
					System.err.println(e.getMessage());
				}
				if (invoer.equals("Y") && aantalSpelers < 4) {
					try {
						dc.speel();
					} catch (OnvoldoendeEfVoorOkException e) {
						e.getMessage();
					}

				} else if (invoer.equals("N")) {
					voegSpelerToe();
				}
			} while (!(invoer.equals("Y") || invoer.equals("N")));
		}
	}

	private void toonOverzichtSpelers() {

		List<SpelerDTO> lijstSpelerDTO = dc.geefOverzichtSpelers();
		String gebruikersnaam = "", aanBeurt = "", aantalPrestingePunten = "", isStartSpeler = "",
				ontwikkelingskaartenInBezit = "", edelsteenFichesInBezit = "", edeleInBezit = "";
		for (SpelerDTO dto : lijstSpelerDTO) {

			gebruikersnaam = String.format("Gebruikersnaam: %s", dto.gebruiksnaam());
			aantalPrestingePunten = String.format("Prestigepunten: %d", dto.totaalAantalPrestigePunten());
			aanBeurt = String.format("%s%s", dto.gebruiksnaam(),
					dto.aanDeBeurt() == true ? " is aan de beurt" : " is niet aan de beurt");
			isStartSpeler = String.format("%s%s%n", dto.gebruiksnaam(),
					dto.startSpeler() == true ? " is de start speler" : " is niet de start speler");
			ontwikkelingskaartenInBezit = (String.format("Ontwikkelingskaarten: %s",
					dto.ontwikkelingskaartenInBezit()));
			edelsteenFichesInBezit = String.format("EdelsteenFiches: %s", dto.edelsteenfichesInBezit());
			edeleInBezit = String.format("Edele: %s%n",
					dto.EdeleInBezit().size() == 0 ? "Momenteel geen edele in bezit" : dto.EdeleInBezit());

			System.out.println("-----------------Overzicht---------------");
			System.out.println(gebruikersnaam);
			System.out.println(aantalPrestingePunten);
			System.out.println(aanBeurt);
			System.out.println(isStartSpeler);
			System.out.println(ontwikkelingskaartenInBezit);
			System.out.println(edelsteenFichesInBezit);
			System.out.println(edeleInBezit);
		}
	}

	private void toonWinnaar() {

		List<WinnaarDTO> lijstWinnaarDTO = dc.geefWinnaar();
		String gebruikersnaam = "";
		String totaalPrestigePunten;

		System.out.printf("-----------------Overzicht %s---------------",
				lijstWinnaarDTO.size() > 1 ? "winnaars" : "winnaar");

		for (WinnaarDTO dto : lijstWinnaarDTO) {

			gebruikersnaam = String.format("Gebruikersnaam: %s", dto.gebruiksnaam());
			totaalPrestigePunten = String.format("Totale prestige punten", dto.totaalAantalPrestigePunten());

			System.out.println(gebruikersnaam);
			System.out.println(totaalPrestigePunten);
		}
	}
}