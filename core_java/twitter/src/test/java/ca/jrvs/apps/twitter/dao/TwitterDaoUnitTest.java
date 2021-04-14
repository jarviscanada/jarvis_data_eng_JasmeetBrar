package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.utils.JsonParser;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TwitterDaoUnitTest extends TestCase {

    @Mock
    HttpHelper mockHelper;

    @InjectMocks
    TwitterDao dao;

    // Test correct JSON
    String tweetJsonStr = "{\n"
            + "\"created_at\":\"Mon Feb 18 21:24:39 +0000 2019\",\n"
            + "\"id\":1,\n"
            + "\"text\":\"Test\",\n"
            + "\"coordinates\":null"
            + "}";

    @Test
    public void create() throws Exception {
        // Test failed request
        String hashTag = "#abc";
        String text = "@someone sometext" + hashTag + " " + System.currentTimeMillis();
        Double lat = 1d;
        Double lon = -1d;

        //Exception is expected here
        when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));

        Tweet tweet = new Tweet();
        tweet.setText(text);
        Coordinates coordinates = new Coordinates();
        coordinates.setCoordinates(new ArrayList<Double>() {{
            add(lon);
            add(lat);
        }});
        tweet.setCoordinates(coordinates);

        try {
            dao.create(tweet);
            fail();
        } catch(RuntimeException e) {
            assertTrue(true);
        }

        when(mockHelper.httpPost(isNotNull())).thenReturn(null);
        TwitterDao spyDao = Mockito.spy(dao);
        Tweet expectedTweet = JsonParser.toObjectFromJson(tweetJsonStr, Tweet.class);
        // Mock parseResponseBody
        doReturn(expectedTweet).when(spyDao).parseResponseBody(any(), anyInt());
        tweet = spyDao.create(tweet);
        assertNotNull(tweet);
        assertNotNull(tweet.getText());
    }

    @Test
    public void findById() throws Exception {
        //Exception is expected here
        when(mockHelper.httpGet(isNotNull())).thenThrow(new RuntimeException("mock"));

        try {
            dao.findById("1");
            fail();
        } catch(RuntimeException e) {
            assertTrue(true);
        }

        Tweet tweet;

        when(mockHelper.httpGet(isNotNull())).thenReturn(null);
        TwitterDao spyDao = Mockito.spy(dao);
        Tweet expectedTweet = JsonParser.toObjectFromJson(tweetJsonStr, Tweet.class);
        // Mock parseResponseBody
        doReturn(expectedTweet).when(spyDao).parseResponseBody(any(), anyInt());
        tweet = spyDao.findById("1");
        assertNotNull(tweet);
        assertNotNull(tweet.getText());
    }

    @Test
    public void deleteById() throws Exception {
        //Exception is expected here
        when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));

        try {
            dao.deleteById("1");
            fail();
        } catch(RuntimeException e) {
            assertTrue(true);
        }

        Tweet tweet;

        when(mockHelper.httpPost(isNotNull())).thenReturn(null);
        TwitterDao spyDao = Mockito.spy(dao);
        Tweet expectedTweet = JsonParser.toObjectFromJson(tweetJsonStr, Tweet.class);
        // Mock parseResponseBody
        doReturn(expectedTweet).when(spyDao).parseResponseBody(any(), anyInt());
        tweet = spyDao.deleteById("1");
        assertNotNull(tweet);
        assertNotNull(tweet.getText());
    }
}