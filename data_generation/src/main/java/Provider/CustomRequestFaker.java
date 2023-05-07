package Provider;

import net.datafaker.providers.base.AbstractProvider;
import net.datafaker.providers.base.BaseFaker;
import net.datafaker.providers.base.BaseProviders;

import java.nio.file.Paths;
import java.util.Locale;

public class CustomRequestFaker extends BaseFaker {
    public Request request() {
        return getProvider(Request.class, Request::new, this);
    }

    public static class Request extends AbstractProvider<BaseProviders> {
        private static final String KEY = "request";

        public Request(BaseProviders faker) {
            super(faker);
            faker.addPath(Locale.ENGLISH, Paths.get("src\\main\\resources\\service_request.yml"));
        }

        public String description() {
            return resolve(KEY + ".request_description");
        }

        public Review review() {
            return new Review(this.faker);
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
