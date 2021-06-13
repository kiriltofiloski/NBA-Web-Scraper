import java.io.IOException;

import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
    public static void main(String[] args) {
        while (true){
            Scanner playerInput = new Scanner(System.in);
            System.out.println("Enter player name:");

            String playerName = playerInput.nextLine().toLowerCase().trim();
            String output = searchPlayer(playerName);
            if(output.equals("q")){
                break;
            }
            printResult(output);
        }
    }

    public static String searchPlayer(String playerName){ //search for player

        String searchURL = "https://www.basketball-reference.com/search/search.fcgi?search=" + playerName;

        //quit the program
        if (playerName.equals("q")){
            return "q";
        }

        try {
            Document doc = Jsoup.connect(searchURL).get();

            Elements searchResults = doc.getElementsByClass("search-item-name");

            try {
                //get first result from search
                Element result = searchResults.get(0);
                String playerPageURL = "https://www.basketball-reference.com" + result.siblingElements().get(0).text();
                return result.text() + "\n" + getStats(playerPageURL);
            }catch (IndexOutOfBoundsException e){
                return "No match found.";
            }

        } catch (IOException e) {
            e.printStackTrace();
            return "IO Error.";
        }
    }

    public static String getStats(String URL){ //get stats from player page
        try {
            Document doc = Jsoup.connect(URL).get();

            Element table = doc.getElementById("per_game");
            Elements rows = table.getElementsByClass("full_table");
            StringBuilder playerData = new StringBuilder();
            for (Element row : rows){
                String season = row.select("th[data-stat=season] a").text();
                String threePointAverage = row.select("td[data-stat=fg3a_per_g]").text();
                playerData.append(season).append(" ").append(threePointAverage).append("\n");
            }
            return String.valueOf(playerData);
        } catch (IOException e) {
            e.printStackTrace();
            return "IO Error.";
        }
    }

    public static void printResult(String result){
        System.out.println(result);
    }
}
