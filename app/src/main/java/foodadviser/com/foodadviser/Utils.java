package foodadviser.com.foodadviser;

import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Utils {
    public static InputStream openStreamForURL(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        int responseCode = conn.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new FileNotFoundException("Unexpected HTTP response: " + responseCode
                    + ", " + conn.getResponseMessage());
        }
        return conn.getInputStream();
    }
}