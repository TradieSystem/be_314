package Types.Service;

/*
Some Notes:
    the rating and comment must coincide, this may be difficult to produce but will be attempted, possibly
    there will need to be different sets of comments in the yml and based off the generated rating the comment will be
    created.
 */

public class RandomReview {
    private int rating; // random number between 1 and 5
    private String comment;
    private int request_id; // must not be randomised, based off a request instead
}
