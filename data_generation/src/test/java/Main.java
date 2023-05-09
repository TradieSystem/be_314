import Provider.CustomFaker;
import Types.User.RandomBilling;

public class Main {
    public static void main(String args[]) {
        //DataGenerator dataGenerator = new DataGenerator(40, 20);
        //dataGenerator.GenerateData();

        CustomFaker faker = new CustomFaker();
        System.out.println(faker.request().description().tree());
        System.out.println(faker.request().description().roof());
        System.out.println(faker.request().description().fence());
        System.out.println(faker.request().description().plumbing());
        System.out.println(faker.request().description().oven());
    }
}
