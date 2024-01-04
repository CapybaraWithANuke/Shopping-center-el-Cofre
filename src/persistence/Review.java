package persistence;

public class Review {
    private short stars;
    private String comment;

    public Review(short stars, String comment) {
        this.stars = stars;
        this.comment = comment;
    }

    public short getStars() {
        return stars;
    }

    public String getComment() {
        return comment;
    }
}
