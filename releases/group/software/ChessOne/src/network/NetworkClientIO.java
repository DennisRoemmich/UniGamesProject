package network;

/**
 * Interface for the noetwork client
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public interface NetworkClientIO {
    String getHostIP();
    
    void presentMessage(NetworkClientMessage message);
}
