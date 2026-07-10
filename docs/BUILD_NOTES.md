# Rekonstruktion des Quellstands (2026)

Das Uni-GitLab-Repo (`gitlab.inf.uni-konstanz.de/ag-schilling/software-projekt-2021-gruppe-03`,
36 Branches, 699 Commits) wurde nie vollständig auf GitHub gespiegelt — auf GitHub lag nur das
fertige `EotSC.jar`. Der Quellcode hier wurde aus einem Backup der damaligen Arbeitskopien
rekonstruiert (mehrere vollständige Clones, jeder auf einem anderen Branch ausgecheckt).

Als Referenz („Ground Truth") diente das originale `EotSC.jar` vom 02.08.2021: für jedes
Package im JAR wurde die Quelle gesucht, deren Klassen die JAR-Klassen abdecken.

## Herkunft der Quellen

| Package(s) | Quelle (Branch / Pfad) |
|---|---|
| `menumain` | `MenuFinal`-Branch, `src/MenuFinal/src` |
| Schach (`chess*`, `engine`, `npc`, `network`, `torpedo`) | `fixedJar2`-Branch, `releases/group/software/ChessOne/src` |
| Skat GUI (`gui*`) | `fixedJar2`-Branch, `releases/group/software/skat/src` |
| Skat Konsole (`console`, `controller`, `engine`, `javaFX`, `main`, `test`, `framework`) | `fixedJar2`-Branch, `releases/group/milestone4/skat/src` |
| Siedler (`siedler`, `buildings`, `cards`, `dice`, `helper`, `map`, `materials`, `moves`, `player`, `positions`, `streets`, `tiles`) | `fixedJar2`-Branch, `releases/group/software/Siedler/src` |
| Rummikub (`rummikub*`, `Tests`) | `fixedJar2`-Branch, `src/group/RummikubFinal/src` |
| Tic Tac Toe T1 (`game`) | `releases/barrios-herrera/v3` |
| Tic Tac Toe T2 (`graphicalUI`, `consoleUI`, `model`) | `releases/deboer/v3` |
| Tic Tac Toe T3 (`TicTacToeFX`) | `releases/kahabka/v3` |
| Tic Tac Toe T4 (`application`, `run`, `simulation`) | `releases/roemmich/v3/TicTacToe2` |
| Tic Tac Toe T5 (`tictactoe`) | `releases/tietz/v3` |

Die Skat-Konsole und Schach teilen sich das Package `engine` (disjunkte Klassennamen) —
das war im Original-JAR genauso.

## Bewusste Abweichungen vom damaligen Datei-Stand

Alles Folgende reproduziert, was beim manuellen IntelliJ-Build 2021 ad hoc gemacht wurde,
bzw. behebt reine Packaging-Fehler. Am Verhalten des Spielcodes wurde nichts geändert.

1. **Rummikub-Packages umbenannt** — `main` → `rummikubmain`, `javafx` → `rummikubjavafx`,
   `framework` → `rummikubframework` (inkl. Imports). Ohne die Umbenennung kollidieren
   `main`/`framework` mit den Skat-Packages und `javafx` mit der JavaFX-Plattform.
   Das Original-JAR enthält exakt diese umbenannten Packages; das Menü ruft
   `rummikubmain.Main` auf. Als `.java`-Dateien existierte die umbenannte Fassung nie im Git.

2. **`package game;` ergänzt** — die drei Dateien von T1 (barrios-herrera v3) lagen im
   Default-Package, im JAR liegen sie unter `game/`, und das Menü ruft `game.TicTacToe` auf.

3. **`graphicalUI/GUIStarter.java` rekonstruiert** — die Klasse existiert im JAR, aber in
   keiner Arbeitskopie. Aus dem Bytecode rekonstruiert (`javap`): sie ruft nur
   `Main.main(args)` auf (gleiches Muster wie `tictactoe.GUIStarter`).

4. **`Chess.fxml` aus dem Quellordner statt aus dem JAR** — das Original-JAR enthielt ein
   veraltetes `Chess.fxml` ohne das `mConfigPane`-Element, das der mitkompilierte
   Controller erwartet: **die Schach-GUI stürzte schon im 2021er Release beim Start ab.**
   Mit dem zu den Quellen gehörenden FXML startet sie.

5. **`Resources/` (T3) als eigener Classpath-Eintrag** (`src/main/resources-ttt/`) —
   macOS-Dateisysteme sind case-insensitiv, `resources/` und `Resources/` können nicht
   nebeneinander im selben Ordner liegen. Im JAR landen wie im Original beide Bäume.

6. **Alle Quelldateien nach UTF-8/LF normalisiert** (Original: Mischung aus cp1252/UTF-8
   und CRLF/LF).

Nicht übernommen aus dem JAR: `group/HelloGitLabCi.class` (CI-Teststub), das komplette
eingepackte JavaFX SDK inkl. Quellcode (~80 MB des alten JARs), IDE-Metadaten
(`.idea/`, `.classpath`), `module-info.class` sowie die macOS-x86_64-Natives —
JavaFX kommt jetzt zur Laufzeit passend von Maven Central.

## Bekannte Eigenheiten (unverändertes Originalverhalten)

- `Q` im Hauptmenü ist nicht implementiert (nur im Skat-Untermenü) — Beenden per `Ctrl+C`
  oder `S` → `Q`.
- Nach dem Schließen eines GUI-Spiels kann kein zweites GUI-Spiel gestartet werden
  (JavaFX-Plattform lässt sich nicht neu starten); das Menü fängt das ab und bittet
  um Neustart.
- Schach schreibt Spielstände als `YYMMDD_HHMMSS.json` ins aktuelle Arbeitsverzeichnis.
