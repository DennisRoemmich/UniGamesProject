package core;

import java.util.List;
import java.util.Optional;

/*
 * Kleine Hilfsklasse, die den Wert eines Optionals zu einer Liste hinzufügt,
 * falls der Wert vorhanden ist (und nicht empty).
 */
public class OptionalAdder {

    private OptionalAdder() {

    }

    public static <T> void add(Optional<T> optional, List<T> list) {
        if(optional.isPresent()) {
            list.add(optional.get());
        }
    }
}
