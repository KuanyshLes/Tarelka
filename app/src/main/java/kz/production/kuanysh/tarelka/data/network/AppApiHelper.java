package kz.production.kuanysh.tarelka.data.network;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by User on 24.06.2018.
 */
@Singleton
public class AppApiHelper implements ApiHelper {

    private ApiHeader mApiHeader;

    @Inject
    public AppApiHelper(ApiHeader apiHeader) {
        mApiHeader = apiHeader;
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHeader;
    }

}
