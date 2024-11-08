import java.net.HttpURLConnection;
import java.net.URL;

public class ApplicationHealthChecker {

    public static void main(String[] args) {
        String url = "http://your-application-url.com";  // Replace with your actual URL
        checkApplicationHealth(url);
    }

    public static void checkApplicationHealth(String url) {
        try {
            URL siteURL = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                System.out.println("The application is up (Status Code: " + responseCode + ")");
            } else {
                System.out.println("The application might be down (Status Code: " + responseCode + ")");
            }
        } catch (Exception e) {
            System.out.println("The application is down. Error: " + e.getMessage());
        }
    }
}
