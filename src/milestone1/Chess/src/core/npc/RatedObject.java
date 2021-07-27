package core.npc;

import java.util.Objects;

public class RatedObject<T> {
    private T object;
    private double rating;

    public RatedObject(T object, double rating) {
        this.object = object;
        this.rating = rating;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RatedObject<?> that = (RatedObject<?>) o;
        return Double.compare(that.getRating(), getRating()) == 0 && Objects.equals(getObject(), that.getObject());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getObject(), getRating());
    }
}
