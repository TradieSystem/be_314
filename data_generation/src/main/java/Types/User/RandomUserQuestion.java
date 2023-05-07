package Types.User;

import java.util.List;

import net.datafaker.Faker;


public class RandomUserQuestion {
 
    public static final String TABLE = "user_question";
    public static int CURRENT_ID = 1;
    
    public int user_question_id;
    public int user_id;
    public int security_question_id;
    public String answer;

    public RandomUserQuestion() {}

    public static RandomUserQuestion generate(final RandomUser user, final List<SecurityQuestion> availableQuestions) {
        RandomUserQuestion entity = new RandomUserQuestion();
        entity.user_question_id = CURRENT_ID++;
        Faker faker = new Faker();
        int pos =  faker.random().nextInt(1,availableQuestions.size());
        SecurityQuestion sq = availableQuestions.get(pos);
        entity.security_question_id=sq.id;
        availableQuestions.remove(pos);
        entity.answer = faker.funnyName().toString();        
        return entity;
    }
}
