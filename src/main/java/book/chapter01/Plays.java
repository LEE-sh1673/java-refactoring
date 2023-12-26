package book.chapter01;

import java.util.Map;

class Plays {

    private final Map<String, Play> plays;

    Plays(final Map<String, Play> plays) {
        this.plays = plays;
    }

    Play findById(final String playID) {
        return plays.get(playID);
    }
}
