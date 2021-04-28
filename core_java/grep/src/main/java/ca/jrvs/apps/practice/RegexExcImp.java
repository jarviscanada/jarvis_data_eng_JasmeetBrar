package ca.jrvs.apps.practice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexExcImp implements RegexExc {

    @Override
    public boolean matchJpeg(String filename) {
        String regex = "\\w+\\.jpe?g";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(filename);
        return matcher.find();
    }

    @Override
    public boolean matchIp(String ip) {
        String regex = "\\d{1,3}(\\.\\d{1,3}){3}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ip);
        return matcher.find();
    }

    @Override
    public boolean isEmptyLine(String line) {
        String regex = "\\s*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        return matcher.find();
    }
}
