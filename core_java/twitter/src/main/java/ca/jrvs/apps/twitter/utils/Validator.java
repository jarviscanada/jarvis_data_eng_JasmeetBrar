package ca.jrvs.apps.twitter.utils;

import ca.jrvs.apps.twitter.model.Tweet;

import java.util.List;

public class Validator {

    public static boolean validatePostTweet(Tweet tweet) {
        String text = tweet.getText();
        List<Double> coordinates = tweet.getCoordinates().getCoordinates();
        Double latitude = coordinates.get(0);
        Double longitude = coordinates.get(1);

        return text.length() > 0 && text.length() <= 140
                && latitude >= -90 && latitude <= 90
                && longitude >= -180 && longitude <= 180;
    }

    public static boolean validateTweetId(String id) {
        try {
            Long.parseLong(id);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
