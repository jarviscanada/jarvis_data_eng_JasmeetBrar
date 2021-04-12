package ca.jrvs.apps.twitter.model;

public class Hashtag {

    private String text;
    private int startIndex;
    private int endIndex;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public int[] getIndices() {
        return new int[]{startIndex, endIndex};
    }

    public void setIndices(int[] indices) {
        startIndex = indices[0];
        endIndex = indices[1];
    }
}
