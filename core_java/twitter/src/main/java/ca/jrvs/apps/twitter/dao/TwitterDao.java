package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.utils.JsonParser;
import com.google.gdata.util.common.base.PercentEscaper;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class TwitterDao implements CrdDao<Tweet, String> {

    // URI constants
    private static final String API_BASE_URI = "https://api.twitter.com";
    private static final String POST_PATH = "/1.1/statuses/update.json";
    private static final String SHOW_PATH = "/1.1/statuses/show.json";
    private static final String DELETE_PATH = "/1.1/statuses/destroy/";

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
            String uriString = getCreateUri(entity);
            URI uri = new URI(uriString);
            HttpResponse response = this.httpHelper.httpPost(uri);
            return parseResponseBody(response, HTTP_OK);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException("Constructed Tweet's URI contains syntax errors", e);
        }

    }

    @Override
    public Tweet findById(String s) {
        try {
            String uriString = getShowUri(s);
            URI uri = new URI(uriString);
            HttpResponse response = this.httpHelper.httpGet(uri);
            return parseResponseBody(response, HTTP_OK);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException("Constructed Tweet's URI contains syntax errors", e);
        }
    }

    @Override
    public Tweet deleteById(String s) {
        try {
            String uriString = getDeleteUri(s);
            URI uri = new URI(uriString);
            HttpResponse response = this.httpHelper.httpPost(uri);
            return parseResponseBody(response, HTTP_OK);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException("Constructed Tweet's URI contains syntax errors", e);
        }
    }

    private String getCreateUri(Tweet tweet) {
        String uriString = API_BASE_URI + POST_PATH + QUERY_SYM;
        PercentEscaper percentEscaper = new PercentEscaper("", false);

        uriString += "status" + EQUAL + percentEscaper.escape(tweet.getText());
        uriString += AMPERSAND + "lat" + EQUAL + tweet.getCoordinates().getCoordinates().get(0);
        uriString += AMPERSAND + "long" + EQUAL + tweet.getCoordinates().getCoordinates().get(1);

        return uriString;

    }

    private String getShowUri(String id) {
        return API_BASE_URI + SHOW_PATH + QUERY_SYM + "id" + EQUAL + id;
    }

    private String getDeleteUri(String id) {
        return API_BASE_URI + DELETE_PATH + id + ".json";
    }

    public Tweet parseResponseBody(HttpResponse response, Integer expectedStatusCode) {
        String jsonString;

        int status = response.getStatusLine().getStatusCode();

        if(status != expectedStatusCode) {
            throw new RuntimeException("Unexpected HTTP status: " + status);
        }

        try {
            jsonString = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Response has no entity", e);
        }

        try {
            return JsonParser.toObjectFromJson(jsonString, Tweet.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot parse JSON into a Tweet object", e);
        }

    }
}
