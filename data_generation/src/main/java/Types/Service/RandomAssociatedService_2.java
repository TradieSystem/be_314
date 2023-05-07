package Types.Service;

import java.util.List;

import Types.User.RandomProfessional;
import net.datafaker.Faker;


public class RandomAssociatedService_2 {
    public static int CURRENT_ID = 1;
    
    public static final String TABLE = "associated_service";
    
    public int provided_service_id;
    public int service_id;
    public int professional_id;
    
    public RandomAssociatedService_2() {
    }
    
    public static RandomAssociatedService_2 generate(RandomProfessional randomProfessional, List<Service> availableServices) {
        RandomAssociatedService_2 entity = new RandomAssociatedService_2();
        entity.provided_service_id = CURRENT_ID++;
        Faker faker = new Faker();
        int pos =  faker.random().nextInt(1,availableServices.size());
        Service sq = availableServices.get(pos);
        availableServices.remove(pos);

        entity.service_id=sq.id;
        entity.professional_id = randomProfessional.professional_id;
        return entity;
    }    

    public static int getRandomInt(int min, int max) {
        Faker faker = new Faker();
        return faker.random().nextInt(min, max);
    }
}
