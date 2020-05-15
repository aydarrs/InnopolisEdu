package part1.lesson08.task02;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Game.
 * Serializable class.
 *
 * @author Aydar_Safiullin
 */
public class Game implements Serializable {
    private static final long serialVersionUID = 11136714777799L;
    private Map<Gamer, Integer> gamersScore = new HashMap<>();
    private Date date;
    private SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

    public Game() {
        date = new Date();
    }

    public Map<Gamer, Integer> getGamersScore() {
        return gamersScore;
    }

    public Date getDate() {
        return date;
    }

    /**
     * Generate random scores for gamers.
     *
     * @param gamers - list of gamers.
     */
    public void saveGame(Gamer[] gamers) {
        Random random = new Random();
        for (Gamer gamer : gamers)
            gamersScore.put(gamer, random.nextInt(10000));


    }

    @Override
    public String toString() {
        StringBuilder sB = new StringBuilder();
        sB.append("Время: ")
                .append(df.format(date))
                .append("\n");
        for (Map.Entry<Gamer, Integer> entry : gamersScore.entrySet())
            sB.append(entry.getKey())
                    .append(", счёт ")
                    .append(entry.getValue())
                    .append("\n");
        return sB.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(gamersScore, game.gamersScore) &&
                Objects.equals(date, game.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gamersScore, date);
    }
}
