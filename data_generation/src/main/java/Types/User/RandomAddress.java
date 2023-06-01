package Types.User;

import Provider.CustomFaker;
import Types.Service.Suburb;
import net.datafaker.Faker;
import net.datafaker.transformations.Field;
import net.datafaker.transformations.JavaObjectTransformer;
import net.datafaker.transformations.Schema;

public class RandomAddress {
    public static final String TABLE = "address";
    public static int CURRENT_ID = 1;
    public int address_id;
    public int street_number;
    public String street_name;
    public String suburb;
    public String postcode;
    public int user_id; // should not be randomised here

    public RandomAddress() {}

    public static RandomAddress generate(int user_id) {
        CustomFaker faker = new CustomFaker();
        JavaObjectTransformer transfomer = new JavaObjectTransformer();

        var randomSuburb = faker.user().suburb();

        Schema<Object, ?> addressSchema = Schema.of(
                Field.field("address_id", () -> RandomAddress.CURRENT_ID++),
                Field.field("street_number", () -> faker.random().nextInt(1,200)),
                Field.field("street_name", () -> faker.address().streetName().replace("'","")),
                Field.field("suburb", () ->  randomSuburb.suburb),
                Field.field("postcode", () -> randomSuburb.postcode),
                Field.field("user_id", () -> user_id)
        );

        return (RandomAddress) transfomer.apply(RandomAddress.class, addressSchema);
    }
}
