package Types.Service;

import java.util.Date;

/*
We are not using start_date and completion_date in the final product.
Will confirm this but at the moment it is not included
 */
public class RandomRequest {
    public static final String TABLE = "request";
    public Date request_date;
    public String instruction; // this will use a custom provider
    public String postcode; // should be based on the already generated postcodes
    public int client_id; // should be based on already generated client_ids
    public int professional_id; // can either be null or based on an already generated professional_id
    public int service_id; // must be based on the provided services
    public int request_status_id; // possibly should be generated first and all other attributes based off it
}

