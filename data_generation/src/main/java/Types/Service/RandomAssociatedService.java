package Types.Service;

import java.util.ArrayList;
import java.util.List;

import Provider.CustomFaker;
import Types.User.RandomProfessional;
import Types.User.RandomUserQuestion;
import net.datafaker.Faker;
import net.datafaker.transformations.Field;
import net.datafaker.transformations.JavaObjectTransformer;
import net.datafaker.transformations.Schema;


public class RandomAssociatedService {
    public static int CURRENT_ID = 1;
    
    public static final String TABLE = "associated_service";
    
    public int provided_service_id;
    public int service_id;
    public int professional_id;
    
    public RandomAssociatedService() {
    }
    
    public static RandomAssociatedService generate(RandomProfessional randomProfessional, List<Service> availableServices) {
        /*
        RandomAssociatedService entity = new RandomAssociatedService();
        entity.provided_service_id = CURRENT_ID++;
        Faker faker = new Faker();
        int pos =  faker.random().nextInt(1,availableServices.size());
        Service sq = availableServices.get(pos);
        availableServices.remove(pos);

        entity.service_id=sq.id;
        entity.professional_id = randomProfessional.professional_id;
        return entity;
         */

        CustomFaker faker = new CustomFaker();
        JavaObjectTransformer transfomer = new JavaObjectTransformer();

        Schema<Object, ?> associatedServiceSchema = Schema.of(
                Field.field("provided_service_id", () -> RandomUserQuestion.CURRENT_ID++),
                Field.field("service_id", () -> faker.random().nextInt(1, availableServices.size())),
                Field.field("professional_id", () -> randomProfessional.professional_id)
        );

        return (RandomAssociatedService) transfomer.apply(RandomAssociatedService.class, associatedServiceSchema);
    }    

    public static int getRandomInt(int min, int max) {
        Faker faker = new Faker();
        return faker.random().nextInt(min, max);
    }
}
