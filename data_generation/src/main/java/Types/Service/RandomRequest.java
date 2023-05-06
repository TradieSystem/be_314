package Types.Service;

import java.util.Date;

/*
We are not using start_date and completion_date in the final product.
Will confirm this but at the moment it is not included
 */
public class RandomRequest {
    private Date request_date;
    private String instruction; // this will use a custom provider
    private String postcode; // should be based on the already generated postcodes
    private int client_id; // should be based on already generated client_ids
    private int professional_id; // can either be null or based on an already generated professional_id
    private int service_id; // must be based on the provided services
    private int request_status_id; // possibly should be generated first and all other attributes based off it
}
