
# Aanpak

1. Eerste stap is het document lezen en het spelletje spelen om te kijken 
   wat de bedoeling is.
1. Volgende stap is de techniek bepalen. Ik heb vele ervaring met Kotlin, dus dat wil ik gebruiken.
   Met het maken van user interfaces in Java/Kotlin heb ik de meeste ervaring met QtJambi (de Java port van Qt), dus dat gebruik ik.
   Ik gebruik de versie van Qt die ik eens geinstalleerd had.
1. Eerst eens een hello world maken om te verifieren dat libraries goed werken. **Eerste commit**
1. Het is de bedoeling om stap voor stap functionaliteit toe te voegen. De kern van het spel is het grid,
   dus daar begin ik mee. Ik maak een class `Grid` voor het model en `GridView` voor de view. En een `Window`.
   Ik kopieer wat bestanden van een ander project met handige functies voor QtJambi. Veel functies daaruit zal ik niet gebruiken,
   maar die gooi ik dan later weg.
1. Een mooie volgende stap lijkt me om alle model classes te maken die nodig zijn om de staat van het spel bij te houden.
   Hierbij kijk ik naar de punten in "Minimale Vereisten". Uitzoeken welke woorden mogelijk zijn lijkt me lastig (wel te doen),
   dus dat doe ik nog even niet. Ik maak de `Game` class.
1. Nu is het leuk om de view te implementeren van wat ik zojuist gemaakt heb. Ik ben flink doorgegaan en heb
   een hoop dingen veranderd in de code, te veel om op te noemen. Ik heb de grid size even op 10 gezet om
   makkelijker dingen uit te kunnen proberen. Het spel werkt. Enige eis waar nog niet aan voldaan is, is welke
   woorden er nog gevonden moeten worden.
1. Nu een algoritme bedenken die zoekt welke woorden er gevonden kunnen worden. De runtime hiervan kan al
   snel uit de hand lopen. Hier is het idee:
   1. Maak een set met word prefixes. Voorbeeld, als je woordenlijst `{"hi", "hello"}` is, dan is je word prefix
      set `{"h", "hi", "he", "hel", "hell", "hello"}`. Dit kan gedaan worden bij application startup.
   2. Ga alle combinaties van letters die je aan kan klikken af met een recursieve functie, een depth-first search.
      Bij iedere stap, kijk of het huidige woord in de prefix set zit om te checken of je verder kan.
      Als het woord in de woordenlijst zit, voeg het toe aan de set van mogelijke woorden.
1. Nu de boel een beetje opschonen, eerst overbodige code weggooien. Dan een beetje rondkijken wat er beter kan.
   Meer comments toevoegen.
1. Ik bekijk het instructiedocument nog een keer. Sommige functionaliteit is makkelijk toe te voegen. De
   "Als speler wil ik mijn voortgang kunnen resetten, zodat ik opnieuw kan beginnen" functie is makkelijk,
   dus die doe ik er in.



# Installation
Although this application can easily be made cross-platform, currently only Windows is supported.

- Install Java
- Install Qt 6.7.0 and make sure the bin directory (`C:\Qt\6.7.0\msvc2019_64\bin`) is in the `Path` environment variable.

Then execute `gradlew run` on the command line in the root directory of the project to run the program.
