package Types.Service;

/*
Some Notes:
    the rating and comment must coincide, this may be difficult to produce but will be attempted, possibly
    there will need to be different sets of comments in the yml and based off the generated rating the comment will be
    created.
 */

import Provider.CustomFaker;
import net.datafaker.transformations.Field;
import net.datafaker.transformations.JavaObjectTransformer;
import net.datafaker.transformations.Schema;

import java.util.concurrent.TimeUnit;

public class RandomReview {
    public static final String TABLE = "review";
    public static int CURRENT_REVIEW_ID = 1;
    public int review_id;
    public int rating; // random number between 1 and 5
    public String comment;
    public int request_id; // must not be randomised, based off a request instead

    public static RandomReview generateReview(int request_id) {
        CustomFaker faker = new CustomFaker();
        JavaObjectTransformer transfomer = new JavaObjectTransformer();

        // generate rating, will be used for below review
        int rating = faker.random().nextInt(1, 5);  // review is between 1 and 5

        // depending on generated rating pick a style of review
        String review;
        if (rating == 1) review = faker.request().review().bad();
        else if (rating > 1 && rating <= 4) review = faker.request().review().fair();
        else if (rating == 5) review = faker.request().review().good();
        else review = "";

        Schema<Object, ?> reviewSchema = Schema.of(
                Field.field("review_id", () -> RandomReview.CURRENT_REVIEW_ID++),
                Field.field("rating", () -> rating),
                Field.field("comment", () -> review),
                Field.field("request_id", () -> request_id)
        );

        return (RandomReview) transfomer.apply(RandomReview.class, reviewSchema);
    }
}
