
##NOTES


###TODO

* **Design & FXML** `ANDI`
  * Trick anzeigen, Ablage
  * Stacks anzeigen
  * restliche Karten
  * `A→` Action Dialog
  * Redo-Button
  * Highlight von Karten
* **FXController**
  * `MAIK` Presenter Klasse abschließen 
    * Player View (Score, Name, Totalscore, Declarer)
    * Auction Dialog
    * GameMessage anzeigen (und fade-out)
  * ``MAIK`` Testen 
  * `ANDI` FXCard : Größe der Bilder an AnchorPane anpassen 
  * `LATER` Animation: Bewegung der Karten
* **Framework**
  * Redo-Feature
  * `LATER` Laden & Speichern
  
* **General**
  * `LATER` SettingsClass
    * eigene Klasse
    * Unterstützung einfach Game-Settings die als JSON-Objekt ggbf. über das Menü übergeben werden aber auch geändert werden können.
    * Spiel mit offenen Karten; Option für karten sortieren: jack - seven oder anders rum; null abbrechen sobald ein Stich gemacht wurde; Ramsch; Kontra und re
    * Hier auch die ganzen static finals die Einstellungen darstellen. z.B. MinWindowSize


`✅ ❕ ❌`

###BUGS

###MAYDO

* Goldene Ablage wegmachen, ist iwie ohne schöner
* Durch Klicken: Karten vertauschen; Durch ziehen, Karten "einfügen"
* Mit Pfeilen Karten auswählen können, mit Enter spielen, mit Leerzeichen auswählen, mit Leerzeichen halten Groß anzeigen

###DONE

* ✅@Maik : Spielabbruch nach 3 mal pass; GameResult nicht auf die Liste!
* ✅ console.gameloop state entsprechend setzen bei finished und aborted
* ✅ reihenfolge bei declare trump ändern? clubs-spades-hearts-diamonds
* ✅ currentTrick printen
* ✅ playCard wird nicht akzeptiert (außer beim ertsen play)
* ✅ Zeige beendetes Trick an
* ✅ Übersicht am Spielende
* ✅ StartScreen: Übersicht über Skat(Set)Spieler
* ✅ Wahlweise ersetzen der Unicode-Sonderzeichen mit Ascii-Sonderzeichen




