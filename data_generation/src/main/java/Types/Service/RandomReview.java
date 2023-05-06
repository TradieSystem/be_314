package Types.Service;

/*
Some Notes:
    the rating and comment must coincide, this may be difficult to produce but will be attempted, possibly
    there will need to be different sets of comments in the yml and based off the generated rating the comment will be
    created.
 */

public class RandomReview {
    public static final String TABLE = "review";
    public int rating; // random number between 1 and 5
    public String comment;
    public int request_id; // must not be randomised, based off a request instead
}
