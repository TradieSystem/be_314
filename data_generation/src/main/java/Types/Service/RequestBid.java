package Types.Service;
import java.util.Date;

public class RequestBid {
    public static final String TABLE = "request_bid";
    public int request_bid_id;
    public int request_id;
    public int professional_id;
    public float amount;
    public Date sent_date;
    public int bid_status_id;
}
