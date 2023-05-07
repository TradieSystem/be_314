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

        public String requestDescription() {
            return resolve(KEY + ".request_description");
        }
    }

    public static class Review {

    }
}
