package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class TwitterDao implements CrdDao<Tweet, String> {

    // URI constants
    private static final String API_BASE_URI = "https://api.twitter.com";
    private static final String POST_PATH = "/1,1/statuses/update.json";
    private static final String SHOW_PATH = "/1.1/statuses/show.json";
    private static final String DELETE_PATH = "/1.1/statuses/destroy";

    // URI symbols
    private static final String QUERY_SYM = "?";
    private static final String AMPERSAND = "&";
    private static final String EQUAL = "=";

    // Response code
    private static final int HTTP_OK = 200;

    private HttpHelper httpHelper;

    @Autowired
    public TwitterDao(HttpHelper httpHelper) {
        this.httpHelper = httpHelper;
    }

    @Override
    public Tweet create(Tweet entity) {

        try {
            String uriString = getPostUri(entity);

            URI uri = new URI(uriString);

            HttpResponse response = this.httpHelper.httpPost(uri);

            return parseResponseBody(response);

        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new IllegalStateException("Constructed Tweet's URI contains syntax errors", e);
        }

    }

    @Override
    public Tweet findById(String s) {
        return null;
    }

    @Override
    public Tweet deleteById(String s) {
        return null;
    }

    private String getPostUri(Tweet tweet) {
        String uriString = API_BASE_URI + POST_PATH + QUERY_SYM;

        Map<String, String> parameters = new HashMap<String, String>()
        {{
           put("status", tweet.getText());
           put("lat", "" + tweet.getCoordinates().getLatitude());
           put("long", "" + tweet.getCoordinates().getLongitude());

        }};

        try {
            parameters.put("status", URLEncoder.encode(parameters.get("status"), StandardCharsets.UTF_8.toString()));

            uriString += parameters.keySet().stream()
                    .map(key -> key + EQUAL + parameters.get(key))
                    .reduce("", (first, second) -> first + AMPERSAND + second)
                    .substring(1);

            return uriString;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Given Tweet's query parameters cannot be encoded with UTF-8", e);
        }
    }

    private Tweet parseResponseBody(HttpResponse response) {
        return null;
    }
}
