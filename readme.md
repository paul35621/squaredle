
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
   maar die gooi ik dan later weg. **Commit**


# Installation
Although this application can easily be made cross-platform, currently only Windows is supported.

- Install Java
- Install Qt 6.7.0 and make sure the bin directory (`C:\Qt\6.7.0\msvc2019_64\bin`) is in the `Path` environment variable.

Then execute `gradlew run` on the command line in the root directory of the project to run the program.
