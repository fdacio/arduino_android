package br.com.daciosoftware.degustlanches.webservice;

import android.net.SSLCertificateSocketFactory;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Dácio Braga on 26/07/2016.
 */
public class HttpConnection {

    public String getContentJSON(String url) throws IOException {
        HttpURLConnection conn = getConnection(url);
        try {
            conn.setRequestMethod("GET");
            try {
                conn.connect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                return String.valueOf(responseCode);
            }

            StringBuilder sb = new StringBuilder();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            in.close();
            return sb.toString();

        } catch (IOException ioe) {
            return ioe.toString();

        } finally {
            conn.disconnect();
        }
    }

    private HttpURLConnection getConnection(String url) throws IOException {

        URL urlParse = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) urlParse.openConnection();
        if (conn instanceof HttpsURLConnection) {
            HttpsURLConnection httpsConn = (HttpsURLConnection) conn;
            httpsConn.setSSLSocketFactory(SSLCertificateSocketFactory.getInsecure(0, null));
            httpsConn.setHostnameVerifier(new AllowAllHostnameVerifier());
        }
        return conn;
    }

}