import Provider.CustomFaker;
import Types.Service.RandomRequest;
import Types.Service.RandomReview;
import Types.User.RandomBilling;

public class Main {
    public static void main(String args[]) {
        DataGenerator dataGenerator = new DataGenerator(40, 20);
        dataGenerator.GenerateData();
    }
}
