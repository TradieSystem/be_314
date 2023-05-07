import Provider.CustomRequestFaker;
import Types.User.RandomBilling;

public class Main {
    public static void main(String args[]) {
        DataGenerator dataGenerator = new DataGenerator(40, 20);
        dataGenerator.GenerateData();

        CustomRequestFaker faker = new CustomRequestFaker();
        System.out.println(faker.request().requestDescription());
    }
}
