package network;

public interface NetworkClientIO {
    String getHostIP();
    void presentMessage(NetworkClientMessage message);
}
