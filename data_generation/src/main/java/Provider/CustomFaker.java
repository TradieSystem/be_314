package Provider;

import net.datafaker.providers.base.AbstractProvider;
import net.datafaker.providers.base.BaseFaker;
import net.datafaker.providers.base.BaseProviders;

import java.nio.file.Paths;
import java.util.Locale;

public class CustomFaker extends BaseFaker {
    public Request request() {
        return getProvider(Request.class, Request::new, this);
    }

    public static class Request extends AbstractProvider<BaseProviders> {
        private static final String KEY = "request";

        public Request(BaseProviders faker) {
            super(faker);
            faker.addPath(Locale.ENGLISH, Paths.get("src\\main\\resources\\service_request.yml"));
        }

        public Description description() {
            return new Description(this.faker);
        }

        public Review review() {
            return new Review(this.faker);
        }

        public static class Description extends AbstractProvider<BaseProviders> {

            public Description(BaseProviders faker) {
                super(faker);
            }

            public String tree() {
                String greeting = resolve(KEY + ".description" + ".greeting");
                String issue = resolve(KEY + ".description" + ".tree");
                String urgency = resolve(KEY +  ".description" + ".urgency");

                return String.format("%s %s %s", greeting, issue, urgency);
            }

            public String roof() {
                String greeting = resolve(KEY + ".description" + ".greeting");
                String issue = resolve(KEY + ".description" + ".roof");
                String urgency = resolve(KEY +  ".description" + ".urgency");

                return String.format("%s %s %s", greeting, issue, urgency);
            }

            public String fence() {
                String greeting = resolve(KEY + ".description" + ".greeting");
                String issue = resolve(KEY + ".description" + ".fence");
                String urgency = resolve(KEY +  ".description" + ".urgency");

                return String.format("%s %s %s", greeting, issue, urgency);
            }

            public String plumbing() {
                String greeting = resolve(KEY + ".description" + ".greeting");
                String issue = resolve(KEY + ".description" + ".plumbing");
                String urgency = resolve(KEY +  ".description" + ".urgency");

                return String.format("%s %s %s", greeting, issue, urgency);
            }

            public String oven() {
                String greeting = resolve(KEY + ".description" + ".greeting");
                String issue = resolve(KEY + ".description" + ".oven");
                String urgency = resolve(KEY +  ".description" + ".urgency");

                return String.format("%s %s %s", greeting, issue, urgency);
            }
        }

        public static class Review extends AbstractProvider<BaseProviders> {
            public Review(BaseProviders faker) {
                super(faker);
            }

            public String good() {
                return resolve(KEY + ".good_review");
            }

            public String fair() {
                return resolve(KEY + ".fair_review");
            }

            public String bad() {
                return resolve(KEY + ".bad_review");
            }
        }
    }
}
