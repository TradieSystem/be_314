package Types.Service;
import Provider.CustomFaker;
import net.datafaker.transformations.Field;
import net.datafaker.transformations.JavaObjectTransformer;
import net.datafaker.transformations.Schema;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class RandomRequestBid {
    public static final String TABLE = "request_bid";
    public static int CURRENT_REQUEST_BID_ID = 1;
    public int request_bid_id;
    public int request_id;
    public int professional_id;
    public double amount;
    public Date sent_date;
    public int bid_status_id;

    public static RandomRequestBid generateRequestBid(int request_id, int professional_id, boolean approved) {
        CustomFaker faker = new CustomFaker();
        JavaObjectTransformer transfomer = new JavaObjectTransformer();

        Schema<Object, ?> requestBidSchema = Schema.of(
                Field.field("request_bid_id", () -> RandomRequestBid.CURRENT_REQUEST_BID_ID++),
                Field.field("request_id", () -> request_id),
                Field.field("professional_id", () -> professional_id),
                Field.field("amount", () -> faker.random().nextDouble(10,200)),
                Field.field("sent_date", () -> faker.date().future(30, TimeUnit.DAYS)),
                Field.field("bid_status_id", () -> !approved ? faker.random().nextInt(1,3) : 3)
        );

        return (RandomRequestBid) transfomer.apply(RandomRequestBid.class, requestBidSchema);
    }
}
