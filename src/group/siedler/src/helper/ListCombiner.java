package helper;

import java.util.List;

public class ListCombiner {


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
}
