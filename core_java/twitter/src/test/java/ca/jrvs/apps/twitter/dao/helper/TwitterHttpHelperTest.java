package ca.jrvs.apps.twitter.dao.helper;

import junit.framework.TestCase;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

public class TwitterHttpHelperTest extends TestCase {

    static final Logger logger = LoggerFactory.getLogger(TwitterHttpHelper.class);

    private TwitterHttpHelper twitterHttpHelper;

    @Override
    public void setUp() throws Exception {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");

        twitterHttpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
    }

    public void testHttpPost() throws Exception {
        HttpResponse response = twitterHttpHelper.httpPost(new URI("https://api.twitter.com/1.1/statuses/update.json?status=hello"));

        logger.info(EntityUtils.toString(response.getEntity()));
    }

    public void testHttpGet() throws Exception{
        HttpResponse response = twitterHttpHelper.httpGet(new URI("https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=elonmusk"));

        logger.info(EntityUtils.toString(response.getEntity()));
    }
}