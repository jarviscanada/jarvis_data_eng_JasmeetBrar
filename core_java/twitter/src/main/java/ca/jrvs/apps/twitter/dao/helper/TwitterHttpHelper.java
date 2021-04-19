package ca.jrvs.apps.twitter.dao.helper;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;

@Component
public class TwitterHttpHelper implements HttpHelper {

    /**
     * Dependencies are specified as private member variables.
     */
    private OAuthConsumer consumer;
    private HttpClient httpClient;

    @Autowired
    public TwitterHttpHelper() {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");
    }

    public TwitterHttpHelper(String consumerKey, String consumerSecret, String accessToken, String tokenSecret) {
        consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
        consumer.setTokenWithSecret(accessToken, tokenSecret);

        // Default = single connection
        httpClient = new DefaultHttpClient();
    }

    @Override
    public HttpResponse httpPost(URI uri) {
        return execute(HttpMethod.POST, uri);
    }

    @Override
    public HttpResponse httpGet(URI uri) {
        return execute(HttpMethod.GET, uri);
    }

    private HttpResponse execute(HttpMethod method, URI uri) {
        HttpUriRequest request;

        if(method == HttpMethod.GET) {
            request = new HttpGet(uri);
        }
        else if(method == HttpMethod.POST) {
            request = new HttpPost(uri);
        }
        else {
            throw new IllegalArgumentException("Provided method is not supported");
        }

        try {
            consumer.sign(request);
            return httpClient.execute(request);
        } catch (OAuthMessageSignerException | OAuthExpectationFailedException | OAuthCommunicationException e) {
            e.printStackTrace();
            throw new RuntimeException("Encountered an issue when signing the request with OAuth", e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Encountered an issue when attempting to send the request to the API", e);
        }
    }
}
