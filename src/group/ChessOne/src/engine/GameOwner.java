package engine;

import framework.Player;

import java.util.Optional;

public interface GameOwner {
    Optional<Chess> getGame();
}
