package Types.User;

import java.util.List;
import java.util.concurrent.TimeUnit;

import Provider.CustomFaker;
import Types.Service.RandomRequest;
import net.datafaker.Faker;
import net.datafaker.transformations.Field;
import net.datafaker.transformations.JavaObjectTransformer;
import net.datafaker.transformations.Schema;


public class RandomUserQuestion {
 
    public static final String TABLE = "user_question";
    public static int secuirty_question_id = 0;
    public static int CURRENT_ID = 1;
    
    public int user_question_id;
    public int user_id;
    public int security_question_id;
    public String answer;

    public RandomUserQuestion() {}

    public static RandomUserQuestion generate(final RandomUser user, final List<SecurityQuestion> availableQuestions) {
        /*RandomUserQuestion entity = new RandomUserQuestion();
        entity.user_question_id = CURRENT_ID++;
        Faker faker = new Faker();
        int pos =  faker.random().nextInt(1,availableQuestions.size());
        SecurityQuestion sq = availableQuestions.get(pos);
        entity.security_question_id=sq.id;
        availableQuestions.remove(pos);
        entity.answer = .toString();
        return entity;
        */

        CustomFaker faker = new CustomFaker();
        JavaObjectTransformer transfomer = new JavaObjectTransformer();

        int securityQuestionId = secuirty_question_id <= 3 ? ++secuirty_question_id : (secuirty_question_id = 1);

        Schema<Object, ?> userQuestionSchema = Schema.of(
                Field.field("user_question_id", () -> RandomUserQuestion.CURRENT_ID++),
                Field.field("user_id", () -> user.user_id),
                Field.field("security_question_id", () -> securityQuestionId),
                Field.field("answer", () -> faker.funnyName().name().replace("\'",""))
        );

        return (RandomUserQuestion) transfomer.apply(RandomUserQuestion.class, userQuestionSchema);
    }
}
