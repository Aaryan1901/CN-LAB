import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class Download {
    public static void main(String[] args) {
        String fileName = "Omen.jpg"; // Name of the file to save
        String website = "https://i.pinimg.com/originals/93/31/24/933124fe6b508c2ab7cec6770bc085f5.jpg";
        System.out.println("Downloading File From: " + website);

        try {
            // Use URI to build the URL object
            URI uri = new URI(website);
            URL url = uri.toURL();
            
            // Open connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
            connection.connect();
            
            // Check if the response code is HTTP OK (200)
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                System.out.println("HTTP error code: " + responseCode);
                return;
            }

            // Input stream to read data from the URL
            InputStream inputStream = connection.getInputStream();
            
            // File output stream to write data to a file
            FileOutputStream outputStream = new FileOutputStream(fileName);

            byte[] buffer = new byte[2048];
            int bytesRead;
            
            // Read data into the buffer and write it to the file
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                //System.out.println("Buffer Read of length: " + bytesRead);
                outputStream.write(buffer, 0, bytesRead);
            }

            // Close streams
            inputStream.close();
            outputStream.close();
            
            System.out.println("Download Complete: " + fileName);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
