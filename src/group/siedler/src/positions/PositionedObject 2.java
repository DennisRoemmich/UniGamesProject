package positions;

import javax.swing.text.Position;

public class PositionedObject<T, P> {
    protected T object;
    protected P position;

    public PositionedObject(T object, P position) {
        this.object = object;
        this.position = position;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public P getPosition() {
        return position;
    }

    public void setPosition(P position) {
        this.position = position;
    }

    public String toString() {
        return object + " @ " + position;
    }
}
