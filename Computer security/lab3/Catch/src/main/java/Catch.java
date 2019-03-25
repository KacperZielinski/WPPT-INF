import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Catch {
    public static void main(String[] args) {
        List<String> lines = loadUniqueData();
        Map<String, String> cookiesMap = getCookiesMap(lines);

        setCookies(cookiesMap);
    }

    private static List<String> loadUniqueData() {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("uniqueData.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    private static Map<String, String> getCookiesMap(List<String> lines) {
        Map<String, String> cookiesMap = new HashMap<>();

        for (String line : lines) {
            int indexOfFirstWhiteSpace = getFirstWhiteSpace(line);

            if(indexOfFirstWhiteSpace == -1) {
                break;
            }

            String host = line.substring(0, indexOfFirstWhiteSpace);
            String cookie = line.substring(indexOfFirstWhiteSpace + 1);

            cookiesMap.put(host, cookie);
        }
        return cookiesMap;
    }

    private static int getFirstWhiteSpace(String line) {
        int index = -1;
        for(int i = 0; i < line.length(); i++ ){
            if(Character.isWhitespace(line.charAt(i))){
                index = i;
                break;
            }
        }
        return index;
    }

    private static void setCookies(Map<String, String> cookiesMap) {
        System.setProperty("webdriver.gecko.driver","C:\\Users\\Kacper Zielinski\\Desktop\\geckodriver\\geckodriver.exe");

        cookiesMap.forEach((host, cookie) -> {
            WebDriver driver = new FirefoxDriver();
            if(!host.startsWith("http://")) {
                host = "http://" + host;
            }
            driver.get(host);
            driver.manage().deleteAllCookies();

            new WebDriverWait(driver, 10000).until(
                    webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));

            ((JavascriptExecutor) driver).executeScript("document.cookie = \"" + cookie + "\";");
        });
    }
}

