package TranslateAPI;

import java.io.*;
import java.net.*;
import java.util.*;
import com.google.gson.*;
import com.squareup.okhttp.*;
import org.json.simple.*;

public class Translate {
    private final static String subscriptionKey = "YOUR_KEY";

    HttpUrl url = new HttpUrl.Builder()
            .scheme("https")
            .host("api.cognitive.microsofttranslator.com")
            .addPathSegment("/translate")
            .addQueryParameter("api-version", "3.0")
            .addQueryParameter("from", "en")
            .addQueryParameter("to", "vi")
            .build();

    // Instantiates the OkHttpClient.
    OkHttpClient client = new OkHttpClient();

    // This function performs a POST request.
    /** this methos post a request. */
    public String Post(String text) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,
                "[{\"Text\": \"" + text + "\"}]");
        Request request = new Request.Builder().url(url).post(body)
                .addHeader("Ocp-Apim-Subscription-Key", subscriptionKey).addHeader("Content-type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    // This function prettifies the json response.
    /** change to json. */
    public static String prettify(String json_text) {
        JsonParser parser = new JsonParser();
        JsonElement json = parser.parse(json_text);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(json);
    }

    /** extract expected path from result. */
    public static String getTranslatedStatement(String text) {
        try {
            int cd = 2;
            int pos = 0;

            while(true) {
                while (text.charAt(pos) != '\"') {
                    pos++;
                }

                for (int i = pos+1; ;i++) {
                    if (text.charAt(i) != '\"') {
                    } else if (cd == 0) {
                        return text.substring(pos + 1, i);
                    } else {
                        cd --;
                        pos = i + 1;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Get Translate Extract Exception " + e);
        }
        return null;
    }

    /** read the name. */
    public static String translatetext(String text) {
        try {
            Translate translateRequest = new Translate();
            String response = translateRequest.Post(text);
            return getTranslatedStatement(response);
        } catch (Exception e) {
            System.out.println("Translate Exception " + e);
        }
        return null;
    }
}