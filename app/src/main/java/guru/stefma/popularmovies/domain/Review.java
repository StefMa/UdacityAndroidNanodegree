package guru.stefma.popularmovies.domain;

public class Review {

    private String mAuthor;

    private String mContent;

    private String mUrl;

    public Review(final String author, final String content, final String url) {
        mAuthor = author;
        mContent = content;
        mUrl = url;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getContent() {
        return mContent;
    }

    public String getUrl() {
        return mUrl;
    }

}
