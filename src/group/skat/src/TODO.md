
###TODO


* ALLGEMEIN
* ✅ Set up Project Structure
* Settings:
    * Klasse anlegen
    * Unterstützung einfach Game-Settings die als JSON-Objekt ggbf. über das Menü übergeben werden aber auch geändert werden können.
    * Spiel mit offenen Karten; ...
    * Hier auch die ganzen static finals die Einstellungen darstellen. z.B. MinWindowSize


* ENGINE
* ✅@Maik : Spielabbruch nach 3 mal pass; GameResult nicht auf die Liste!


* KONSOLE
* ✅ console.gameloop state entsprechend setzen bei finished und aborted
* ✅ reihenfolge bei declare trump ändern? clubs-spades-hearts-diamonds
* ✅ currentTrick printen
* ✅ playCard wird nicht akzeptiert (außer beim ertsen play)
* ❕ Zeige beendetes Trick an
* ❕ Übersicht am Spielende
* StartScreen: Übersicht über Skat(Set)Spieler
* Wahlweise ersetzen der Unicode-Sonderzeichen mit Ascii-Sonderzeichen


* GUI - DESIGN
* Goldene Ablage wegmachen, ist iwie ohne schöner
  

* GUI - CODE
* (✅)Set up FX Structure
* ...

#### Auf lange Sicht

* option für karten sortieren, jack - seven oder anders rum
* null abbrechen sobald ein stich gemacht wurde
* ramsch
* kontra und re

`✅ ❕ ❌`

###BUGS
__
* -

###MAYDO

* Durch klicken: Karten vertauschen; Durch ziehen, Karten "einfügen"
* Mit Pfeilen Karten auswählen können, mit Enter spielen, mit Leerzeichen auswählen, mit Leerzeichen halten Groß anzeigen

###NOTES

* in Schleifen oder statements mit `()` innerhalb der Klammern immer ein leer lassen - oder vlt doch nicht?
* isTrump in Card class und Trump class
