package guru.stefma.baking.data.remote;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RetrofitService {

    private static final String BASE_API
            = "http://go.udacity.com/";

    /**
     * Creates the {@link ApiService} with a default {@link Retrofit} object.
     *
     * @return the service which can be used to make network requests.
     */
    public static ApiService getApiService() {
        return createRetrofit().create(ApiService.class);
    }

    /**
     * Creates a default {@link Retrofit} object which uses the {@link RetrofitService#BASE_API} as API,
     * a {@link MoshiConverterFactory} as a convert and {@link RxJava2CallAdapterFactory} as a adapter.
     */
    private static Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_API)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

}
