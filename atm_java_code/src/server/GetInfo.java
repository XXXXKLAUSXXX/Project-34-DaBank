package server;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class GetInfo {
    // response codes
    public static final int OK = 200;
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORISED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int NO_BALLANCE = 412;
    public static final int SERVER_ERROR = 500;

    private static final String USER_AGENT = "BANK/DA";
    private static int status;
    private static final String NOOBTOKEN;

    static {
        try {
            NOOBTOKEN = new BufferedReader(new FileReader("resources/noobtoken.txt")).readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String post(String urlString, String json) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("NOOB-TOKEN", NOOBTOKEN);
        con.setDoOutput(true);
        con.setUseCaches(false);

        OutputStream out = con.getOutputStream();
        out.write(json.getBytes(StandardCharsets.UTF_8));
        out.close();

        status = con.getResponseCode();
        System.out.println("GET Response code: " + status);

        if (status == OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response);
            return response.toString();
        }
        else {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getErrorStream(), "utf-8"));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response);
            return response.toString();
        }
    }
    public static String get(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("NOOB-TOKEN", NOOBTOKEN);

        status = con.getResponseCode();
        System.out.println("Get response code: " + status);

        if (status == OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response);
            return response.toString();
        }
        else {
            System.out.println("No Cheese");
        }
        return "";
    }
    public static int getStatus() {
        return status;
    }
}
