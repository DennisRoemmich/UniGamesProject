package positions;

public class PositionedObject<T, P> {
    protected T mObject;
    protected P mPosition;

    public PositionedObject(T object, P position) {
        this.mObject = object;
        this.mPosition = position;
    }

    public T getObject() {
        return mObject;
    }

    public void setObject(T object) {
        this.mObject = object;
    }

    public P getPosition() {
        return mPosition;
    }

    public void setPosition(P position) {
        this.mPosition = position;
    }

    public String toString() {
        return mObject + " @ " + mPosition;
    }
}
