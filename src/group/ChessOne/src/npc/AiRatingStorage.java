package npc;

import engine.Chess;

import java.util.ArrayList;
import java.util.List;

public class AiRatingStorage {

    private List<RatingHashPair> storedRatings = new ArrayList<>();

    public boolean isRatingStored(Chess game, int depth) {
        return storedRatings.stream().anyMatch(r -> r.getGameHash() == game.hashCode() && r.getDepth() >= depth);
    }

    public void addRating(Chess game, double rating, int depth) {
        if (!isRatingStored(game, depth)) {
            var storageEntry = new RatingHashPair(game, rating, depth);
            storedRatings.add(storageEntry);
        }
    }

    public double getRating(Chess game, int depth) {
        var rating = storedRatings.stream()
                                                        .filter(r -> r.getGameHash() == game.hashCode() && r.getDepth() >= depth)
                                                        .findFirst();
        return rating.isPresent() ? rating.get().getRating() : 0;
    }
}
