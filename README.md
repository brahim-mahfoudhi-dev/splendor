# Splendor

Splendor is een Java-applicatie gebaseerd op het kaartspel *Splendor*, ontworpen als groepsproject binnen de opleiding Toegepaste Informatica. Het spel is bedoeld voor 2 tot 4 spelers en combineert strategie, resourcebeheer en geluk.

## Over het Spel

In Splendor verzamelen spelers edelstenen waarmee ze ontwikkelingskaarten kopen. Deze kaarten leveren punten op en bieden bonussen die latere aankopen goedkoper maken. Spelers proberen strategische combinaties van kaarten te verzamelen om edellieden aan te trekken, wat extra punten oplevert. De eerste speler die 15 punten bereikt, activeert het einde van het spel.

## Functionaliteiten (Use Cases)

Het project is opgebouwd aan de hand van volgende use cases:

- **UC1** – Maak een nieuw spel  
- **UC2** – Speel spel  
- **UC3** – Speel ronde  
- **UC4** – Speel beurt  

Elke use case werd volledig geanalyseerd, ontworpen, geïmplementeerd en getest.

## Architectuur

De applicatie is opgebouwd volgens het drie-lagenmodel:

- **Presentatielaag**: Console- en grafische gebruikersinterface (JavaFX)
- **Domeinlaag**: Logica en spelregels
- **Persistencelaag**: MySQL-database

## Eindproduct

Het eindresultaat bestaat uit:

- Een volledige Java-toepassing met GUI
- Een console-applicatie voor testdoeleinden
- Ondersteuning voor meertaligheid via `ResourceBundles`
- Volledige JavaDoc-documentatie van de domeinlaag
- UML-diagrammen (Visual Paradigm)
- MySQL-database met ERD, relationeel model en DDL-scripts
- Exportbestand met testdata

## Testen

Elke domeinklasse is getest met behulp van JUnit.  
Controller- en repositoryklassen zijn buiten beschouwing gelaten bij unit testing.

## Tools & Technologieën

- Java 17
- JavaFX
- JUnit
- MySQL
- Eclipse IDE
- Visual Paradigm
- Git / GitHub

## Meertaligheid

De applicatie ondersteunt meerdere talen. Door gebruik te maken van Java `ResourceBundles` kunnen de teksten eenvoudig vertaald worden.
