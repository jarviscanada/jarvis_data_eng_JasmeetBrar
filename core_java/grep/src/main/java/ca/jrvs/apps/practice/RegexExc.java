package ca.jrvs.apps.practice;

public interface RegexExc {

    /**
     * return true if filename extension is jpg or jpeg (case insensitive)
     * @param filename the name of a file
     * @return true if and only if it is a jpg or jpeg file (case insensitive)
     */
    public boolean matchJpeg(String filename);

    /**
     * return true if ip is valid
     * to simplify the problem, IP address range is from 0.0.0.0 to 999.999.999.999
     * @param ip an IP address
     * @return true if and only if it is a valid IP address
     */
    public boolean matchIp(String ip);

    /**
     * return true if line is empty (e.g. empty, white space, tabs, etc.)
     * @param line a string
     * @return true if and only if the line is empty
     */
    public boolean isEmptyLine(String line);
}
