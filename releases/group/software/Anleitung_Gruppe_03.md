# Anleitung für Gruppe 03



## Benutzung der Software "EotSC"

### Benötigte Ressourcen

* JavaFX Libraries
* json-simple-1.1.1.jar

### Startbefehl für die JAR-Datei

```java -jar EotSC.jar```

wenn das nicht funktionieren sollte:

```java -jar --module-path JAVAFX_PATH --add-modules=javafx.controls,javafx.fxml EotSC.jar```

(EotSC: "Example of the Software Crisis")


### Steuerung (z.B. Befehle in der Konsole)

* Menü
	* Wenn die Spielesammlung geöffnet wird, sind die verschiedenen Spiele über folgende Tastaturinputs + ENTER erreichbar:
		* Schach (Konsole): "C" oder "c"
		* Schach (GUI): "CG" oder "cg"
		* Rummikub: "R" oder "r"
		* Skat (Konsole): erst "S", dann "C"
		* Skat (GUI): erst "S", dann "G"
		* Siedler von Konstanz: "K" oder "k"	
		* Tic Tac Toe (GUI): "T1", "T2", "T3", "T4", "T5"
			* Die Codierung der  ist hierbei wie folgt:
			 
			``` 
			T1: Maria Fernanda Barrios Herrera
			T2: Jan de Boer
			T3: Andreas Kahabka
			T4: Dennis Römmich
			T5: Maik Tietz 
			```

* Schach
	* Konsole:
		* Input erfolgt über die gezeigten Buchstaben in eckigen Klammern bspw. "[P]lay Chess"
		* Innerhalb eines Schachspiels können mit dem Befehl "help" die Befehler zum spielen angezeigt werden.
	* GUI :
		* Input entweder über das Textfeld oder über Klick auf die Figuren

* Rummikub
	* GUI:
		* Buttons
			* klicken
		* Steine
			* Spielstein und Zielfeld anklicken
			* oder Stein auf Zielfeld ziehen

* Skat
	* Konsole
		* Spiel starten: egal
		* Sortieren: s
		* Stein auswählen: entsprechende Nummer
		* Stein auf der Hand bewegen: zwei Steine hintereinander auswählen
		* Reizwert akzeptieren: y
		* Reizwert ablehnen: n
		* Skat akzeptieren: a
		* Trumpf wählen: entsprechenden Buchstaben
		* Stein spielen: a
	* GUI
		* Buttons
			* klicken
			* oder per Tastatur:
				* neues spiel starten: enter
				* Sortieren: s
				* Reizwert akzeptieren: enter
				* Reizwert ablehnen: #
				* Skat akzeptieren: enter
		* Karten
			* auswählen: klicken
			* tauschen: nacheinander auswählen
			* spielen: Karte auswählen und auf den Stich in der Mitte legen
			* oder per Tastatur:
				* auswählen: links, rechts, oben
				* auswahl wechseln: links, rechts, oben, unten
				* mit Skat tauschen: Karte unten und oben auswählen und enter
				* spielen: karte auswählen und enter


* Siedler von Konstanz
	* GUI
		* Klicke auf die Map um Siedlungen/Straßen/Räuber zu setzen oder auf den Button um zu würfeln/den Zug zu beenden.
		* Für Ressourceninputs dienen die Tasten "1" bis "5" auf der Tastatur.
		* Um Karten zu kaufen drücke "D", um sie zu spielen entsprechend die Taste "K", "R", "I", "M".
		* Zum Tauschen mit der Bank (1:4) drücke "D" und anschließend die jeweiligen Ressourcen Tastaturinputs.
		* Die Tastaturinputs werden nochmals im Hilfescreen (Klicke I-Icon oben links) erläutert (siehe auch auf die Konsole für weitere Infos) 

		



### Replay-Feature und Speicherstände (z.B. Dateiformat, Erstellung, Ort)

* Schach
	* Speicherstände werden automatisch bei jedem angefangenen Spiel im Projekt Ordner (eine Ebene über dem src Ordner) erzeugt.
	* Das Dateiformat ist eine .json mit dem Datum und der Uhrzeit mit Codierung YYMMDD_HHMMSS wobei YY für das Jahr und SS für die Sekunden stehen.
	* Zum Aufrufen wähle "[R]eplay from Save file" mit Input "R" und anschließend den Namen der .json als Input.
* Rummikub
	* kein replay-feature implementiert, daher keine speicherstände
* Skat
	* da skat ein vergleichsweise sehr kurzes Spiel ist, wurde kein Replay-Feature implementiert. Damit gibt es auch keine Speicherstände
* Siedler von Konstanz
	* Keine Replays oder Speicherstände


## Tests

### Wo sind Tests zu finden?

* Schach
	* Tests für Schach liegen im test package
	* Speicherstände auch vorhanden
* Rummikub
	* ein Test vorhanden auf Abruf im Konsolen-Menü
* Skat
	* direkt nach Spielstart (!) "v" drücken und "ENDGAME" eingeben. Daraufhin wird zum Spielende vorgespielt
	* zum Verbergen erneut "v" drücken
* Siedler von Konstanz
	* Zum Testen existiert die cheating Funktion mit Taste "C", die dem jeweiligen Spieler pro Tastendruck mehr Ressourcen schenkt. Des Weiteren ist es möglich mit bis zu 12 menschlichen als auch KI Gegnern zu spielen und Startressourcen zu vergeben.
	* Die Straßensetzung lässt sich sehr schön über die AiPlayer Klasse testen. Hierzu fügt man einen boolean Wert in die handleOptionalMoves() Methode hinzu, sodass die KI versucht in einem Zug 60 Straßen zu bauen. Anschließend muss ein CodeBlock in tryCreatingStreets() auskommentiert werden, damit die KI ohne Siedlungen zu bauen Straßen setzt. Und zuletzt muss beim Spielanfang genügend Baumaterial vorhanden sein (300 im Textfeld sollten mehr als genügen).
	


## Notenverbesserungen

### Liste der Spiele

* Schach
	* anspruchsvoll
		* Konsole 
			* Hotseat Modus
			* sinnvolle KI
				* Spielt Züge anhand einem Figurenrating und Stellungsratingsystem.
			* Save/Load Feature (mit .json files)
			* Replay feature im Save/Load Feature integriert (beim Laden eines .json files)
			* Zug rückgängig (undo) möglich
			* Spieloption des Torpedo Chess
			* Netzwerkunterstützung
			
		* GUI
			* Hotseat Modus
			* sinnvolle KI
				* Spielt Züge anhand einem Figurenrating und Stellungsratingsystem.
			* Save/Load Feature (mit .json files)
			* Replay feature im Save/Load Feature integriert (beim Laden eines .json files)
			* Zug rückgängig (undo) möglich
			* Spieloption des Torpedo Chess
			* bedienbar mit Maus und Tastatur (Inputs in das Textfeld wie in der Konsole)
			* Netzwerkunterstützung
* Rummikub
	* anspruchsvoll
		* sehr viele verschiedene Aktionsmöglichkeiten, sehr komplexes Spiel
		* GUI
		* Spielen über mehrere Runden möglich
		
* Skat
	* anspruchsvoll
		* komplexe Spiellogik mit Reizen, Spielen und Spielauswertung
		* Konsolen- und Gui-Version vorhanden
		* Konsole
			* alle 3 Spielmodi Suit, Grand und Null vorhanden
		* GUI
			* hochwertige, zeitaufwendige GUI
			* alle 3 Spielmodi Suit, Grand und Null vorhanden
			* sinnvolle KI
			* Spielen sowohl mit Maus als auch mit Tastatur möglich
			* mehrere Runden hintereinander möglich
		
* Siedler von Konstanz
	* anspruchsvoll
		* GUI
			* komplexe Map-Generierung mit Siedlungs- und Straßennodes
			* unterschiedliche Phasen mit dem Räuber
			* Ressourcenmanagement mit verschiedenen Entwicklungskarten
			* Standardkarte und Konstanz-Karte mit Schiffen und Wasserfeldern
			* Hotseat Modus mit bis zu 12 Spielern
			* sinnvolle KI
				* Spielt die Setup-Phase klug, kann Räuber ziehen, baut Straßen, Dörfer, Städte, kann mit der Bank handeln und Entwicklungskarten kaufen und einsetzen.

		
* Tic Tac Toe Spiele

Zusätzlich wurde ausgehandelt, dass bei einer Umsetzung einer GUI für jedes Spiel ein extra Notenpunkt als Bonus angerechnet wird.

### Liste der Spiele mit Netzwerkunterstützung

* Schach



## Weitere Anmerkungen

Mit JavaFX hatten wir als 5er-Gruppe alle gesamt keine nennenswerte Erfahrung. Im Programmier-Kurs im ersten Semester wurde zwar mit JavaFX gearbeitet, jedoch gab es damals (wie auch heute noch) große Probleme mit der GUI, sodass dort so gut wie keine Erfahrung gesammelt werden konnten.
Wir bekamen das zu spüren, als wir uns bei unserer ersten GUI, Rummikub, sehr stark in der Zeit verschätzen und vieles nicht so nach Plan lief wie wir es uns erhofft hatten. Auch bei den anderen GUIs wurde es immer knapp, da es weiterhin Probleme gab, die zu lösen uns aus mangelnder Erfahrung schwer fiel.

Auch in sonstigem Programmieren ist keiner von uns besonders fortschrittlich. Diese Unerfahrenheit bitten wir bei der Benotung etwas zu berücksichtigen, da wir als gesamte Gruppe vor allem gegen Ende des Projekts so deutlich mehr als die vorgesehenen 13h/Woche investieren mussten, um dieses Ergebnis zu erreichen.