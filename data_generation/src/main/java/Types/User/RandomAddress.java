package Types.User;

import Types.Service.Suburb;
import net.datafaker.Faker;

public class RandomAddress {
    public static final String TABLE = "address";
    public static int CURRENT_ID = 1;
    public int address_id;
    public int street_number;
    public String street_name;
    public String suburb;
    public String postcode;
    public int user_id; // should not be randomised here

    public RandomAddress(int user_id) {
        this.user_id = user_id;
    }

    public static RandomAddress generate(int user_id) {
        RandomAddress entity = new RandomAddress(user_id);
        entity.address_id = CURRENT_ID++;
        Faker faker = new Faker();
        entity.street_number = faker.random().nextInt(1,200);
        entity.street_name = faker.address().streetName();
        int pos = faker.random().nextInt(0,Suburb.values().length-1);
        Suburb suburb = Suburb.values()[pos];
        entity.suburb = suburb.suburbName;
        entity.postcode = suburb.postcode;
        return entity;        
    }
}
