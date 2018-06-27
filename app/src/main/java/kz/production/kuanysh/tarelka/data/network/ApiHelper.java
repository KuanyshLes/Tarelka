package kz.production.kuanysh.tarelka.data.network;

import io.reactivex.Single;
import kz.production.kuanysh.tarelka.data.network.module.Authorization;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by User on 24.06.2018.
 */

public interface ApiHelper {

    
    ApiHeader getApiHeader();

    @FormUrlEncoded
    @POST
    Single<Response<Authorization>> getAccessTokenAndLogin(@Field("phone") String phone);

}
