package Types.User;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import net.datafaker.Faker;

public class RandomSession {

    public static final String TABLE = "session";
    private static int  CURRENT_ID = 1; 
    
    public int session_id;
    public int authorisation_id;
    public LocalDateTime expiry_date;
    public String access_token;
    
    public RandomSession() {        
    }
    
    public static RandomSession generate(RandomAuthorisation randomAuthorisation, boolean expired) { 
        RandomSession entity = new RandomSession();
        entity.session_id = CURRENT_ID++;
        entity.authorisation_id = randomAuthorisation.authorisation_id;
        Faker fake =new Faker();
        if (expired) {
            int elapsed = fake.random().nextInt(1, 60*60*10);
            entity.expiry_date=LocalDateTime.now().minus(elapsed, ChronoUnit.SECONDS);
        } else {
            int elapsed = fake.random().nextInt(1, 60*5);
            entity.expiry_date=LocalDateTime.now().plus(elapsed, ChronoUnit.SECONDS);
        }
        entity.access_token = java.util.UUID.randomUUID().toString();
        return entity;
    }
}
