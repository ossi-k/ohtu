package ohtu;

import java.io.IOException;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.http.client.fluent.Request;

public class Main {

    public static class SortByPoints implements Comparator<Player> {

        @Override
        public int compare(Player a, Player b) {
            return Integer.compare(a.getPoints(), b.getPoints());
        }
    }

    public static void main(String[] args) throws IOException {
        String url = "https://nhlstatisticsforohtu.herokuapp.com/players";

        String bodyText = Request.Get(url).execute().returnContent().asString();

        System.out.println("json-muotoinen data:");
        System.out.println(bodyText);

        Gson mapper = new Gson();
        Player[] players = mapper.fromJson(bodyText, Player[].class);

        System.out.println("Oliot:");
        for (Player player : players) {
            System.out.println(player);
        }
        

        System.out.println("=============================");

        ArrayList<Player> finnishPlayers = new ArrayList<>();
        
        for (Player player : players) {
            if (player.getNationality().equals("FIN")) {
                finnishPlayers.add(player);
            }
        }
        
        Collections.sort(finnishPlayers, new SortByPoints());
        Collections.reverse(finnishPlayers);
        
        System.out.println("Finnish Players sorted by goals and assists");
        for (Player player : finnishPlayers) {
            if (player.getNationality().equals("FIN")) {
                System.out.println(player.getName() + " " + player.getTeam() + " " + 
                        player.getGoals() + " + " + " " + player.getAssists() + " = " + player.getPoints());
            }
        }
    }
}
