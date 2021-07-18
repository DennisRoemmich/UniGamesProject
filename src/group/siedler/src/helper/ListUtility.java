package helper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class ListUtility {

    public static <T> void add(Optional<T> optional, List<T> list) {
        if(optional.isPresent()) {
            list.add(optional.get());
        }
    }

    public static <T> void addWithoutDuplicates(T element, List<T> destination) {
        if(!destination.contains(element)) {
            destination.add(element);
        }
    }

    public static <T> void addAllWithoutDuplicates(List<T> origin, List<T> destination) {
        for(T element: origin) {
            addWithoutDuplicates(element, destination);
        }
    }

    public static <T> T getRandomElement(Collection<T> collection) {
        int index = ThreadLocalRandom.current().nextInt(0, collection.size());
        return collection.stream().toList().get(index);
    }

}
