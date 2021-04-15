package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceUnitTest {

    @Mock
    TwitterDao dao;

    @InjectMocks
    TwitterService service;

    @Mock
    ArrayList<Double> latAndLong;

    @Spy
    @InjectMocks
    Coordinates coordinates;

    @Spy
    @InjectMocks
    Tweet tweet;

    @Before
    public void setUp() throws Exception {
        when(tweet.getText()).thenReturn("Hello");
        when(tweet.getCoordinates()).thenReturn(coordinates);
        when(coordinates.getCoordinates()).thenReturn(latAndLong);
        when(latAndLong.get(0)).thenReturn(25.0);
        when(latAndLong.get(1)).thenReturn(30.0);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void failToPostTweetWithInvalidStatus() {
        // Should fail when Tweet status is blank
        when(tweet.getText()).thenReturn("");
        try {
            service.postTweet(tweet);
            fail();
        } catch(IllegalArgumentException e) {
            assertTrue(true);
        }

        // Should fail when Tweet status is more than 140 characters
        String longString = "" +
                "AAAAAAAAAA" +
                "AAAAAAAAAA" +
                "AAAAAAAAAA" +
                "AAAAAAAAAA" +
                "AAAAAAAAAA" +
                "AAAAAAAAAA" +
                "AAAAAAAAAA" +
                "AAAAAAAAAA" +
                "AAAAAAAAAA" +
                "AAAAAAAAAA" +
                "AAAAAAAAAA" +
                "AAAAAAAAAA" +
                "AAAAAAAAAA" +
                "AAAAAAAAAA" +
                "AAAAAAAAAA";

        when(tweet.getText()).thenReturn(longString);
        try {
            service.postTweet(tweet);
            fail();
        } catch(IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @Test
    public void failToPostTweetWithInvalidLatitude() {
        // Should fail when latitude < -90.0
        when(latAndLong.get(0)).thenReturn(-95.0);
        try {
            service.postTweet(tweet);
            fail();
        } catch(IllegalArgumentException e) {
            assertTrue(true);
        }

        // Should fail when latitude > 90.0
        when(latAndLong.get(0)).thenReturn(95.0);
        try {
            service.postTweet(tweet);
            fail();
        } catch(IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @Test
    public void failToPostTweetWithInvalidLongitude() {
        // Should fail when longitude < -180.0
        when(latAndLong.get(1)).thenReturn(-185.0);
        try {
            service.postTweet(tweet);
            fail();
        } catch(IllegalArgumentException e) {
            assertTrue(true);
        }

        // Should fail when longitude > 180.0
        when(latAndLong.get(1)).thenReturn(185.0);
        try {
            service.postTweet(tweet);
            fail();
        } catch(IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @Test
    public void shouldPostTweetIfValid() {
        service.postTweet(tweet);
        verify(dao).create(eq(tweet));
    }

    @Test
    public void failToShowTweetIfIdIsInvalid() {
        // Fail when id cannot be parsed as a long
        String id = "ugriojed243289fn@#";
        try {
            service.showTweet(id, null);
            fail();
        } catch(IllegalArgumentException e) {
            assertTrue(true);
        }

        // Fail when id is negative
        id = "-23";
        try {
            service.showTweet(id, null);
            fail();
        } catch(IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @Test
    public void shouldShowTweetIfIdIsValid() {
        String id = "25";
        service.showTweet(id, null);
        verify(dao).findById(eq(id));
    }

    @Test
    public void failToDeleteTweetWhenIdsAreInvalid() {
        String[] ids = new String[]{"5", "25", "hdgi", "30", "-2"};
        try {
            service.deleteTweets(ids);
            fail();
        } catch(IllegalArgumentException e) {
            assertTrue(true);
        }

        verify(dao, times(0)).deleteById(any());
    }

    @Test
    public void shouldDeleteTweetsWhenIdsAreValid() {
        String[] ids = new String[]{"25", "47", "2", "145"};
        service.deleteTweets(ids);
        verify(dao, times(ids.length)).deleteById(any());
    }
}