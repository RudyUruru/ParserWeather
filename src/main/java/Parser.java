import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

public class Parser {
    public static Document getPage() throws IOException {
        String url = "https://www.pogoda.msk.ru/";
        Document page = Jsoup.parse(new URL(url), 3000);
        return page;
    }

    public static String getDateFromString(String day) {
        return day.substring(0, 5);
    }

    public static void printHeader(Element header) {
        String date = getDateFromString(header.text());
        System.out.println(date);
    }

    private static void printRow(Element row) {
        Elements cells = row.select("td");
        System.out.print(cells.get(0).text() + " || ");
        System.out.print("Погодные явления: " + cells.get(1).text() + " || ");
        System.out.print("Температура: " + cells.get(2).text() + " || ");
        System.out.print("Давление: " + cells.get(3).text() + " || ");
        System.out.print("Относительная влажность: " + cells.get(4).text() + " || ");
        System.out.println("Ветер: " + cells.get(5).text());
    }

    public static void main(String[] args) throws IOException {
        Document page = getPage();
        Element weatherTable = page.selectFirst("table[class=wt]");
        Elements tableRows = weatherTable.select("tr");
        for (Element row : tableRows) {
            if (row.hasAttr("class")) {
                printHeader(row);
            } else if (row.hasAttr("valign")){
                printRow(row);
            }
        }
    }
}
