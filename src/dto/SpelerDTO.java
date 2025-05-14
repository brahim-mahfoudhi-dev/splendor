package dto;

import java.util.List;
import java.util.Map;

import domein.Edele;
import domein.Ontwikkelingskaart;
import util.EdelsteenFiche;

public record SpelerDTO(String gebruiksnaam, int geboortejaar, int totaalAantalPrestigePunten, boolean aanDeBeurt, boolean startSpeler, List<Ontwikkelingskaart> ontwikkelingskaartenInBezit, List<Edele> EdeleInBezit, Map<EdelsteenFiche, Integer> edelsteenfichesInBezit,  Map<EdelsteenFiche, Integer> edelsteenType) {



}
