package guru.stefma.popularmovies.domain.usecase;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import guru.stefma.popularmovies.data.remote.entity.RemoteMovieWrapperEntity;
import guru.stefma.popularmovies.domain.Movie;
import guru.stefma.popularmovies.domain.usecase.base.BaseObservableUseCase;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import java.util.ArrayList;
import java.util.List;

/**
 * A implementation of a {@link BaseObservableUseCase} which could be used to load movies.
 *
 * Call one time {@link #execute(String)} to get access to a {@link Observable} which will be emit directly
 * the "first page".
 *
 * Call {@link #loadMore} to load more movies. The returned {@link Observable} from {@link #execute(String)} will
 * then emit again if more movies are available. The returned {@link Movie} {@link List} will contain all previously
 * loaded movies plus the new loaded movies.
 *
 * If we reached the last page a special {@link LastPageReachedException} will be thrown.
 *
 * Note: It's not allowed to call {@link #loadMore()} before calling {@link #execute(String)}!
 */
public abstract class BaseMoviesUseCase implements BaseObservableUseCase<List<Movie>, String> {

    private int mCurrentPage = 1;

    @NonNull
    private List<Movie> mCurrentMovies = new ArrayList<>();

    @Nullable
    private String mApiKey = null;

    private final PublishSubject<List<Movie>> mMoviesSubject = PublishSubject.create();

    @NonNull
    private final RemoteMovieToMovieMapper mMovieMapper = new RemoteMovieToMovieMapper();

    @Override
    public Observable<List<Movie>> execute(final String params) {
        mApiKey = params;
        return mMoviesSubject
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> loadMore());
    }

    public void loadMore() {
        if (mApiKey == null) {
            throw new IllegalStateException("It's not allowed to call loadMore() before calling execute(String)");
        }

        getSortCriteriaSingle(mApiKey, mCurrentPage)
                .subscribeOn(Schedulers.io())
                .flatMap(moviesWrapper -> {
                    mCurrentPage += 1;

                    if (mCurrentPage > moviesWrapper.mTotalPages) {
                        return Single.error(new LastPageReachedException());
                    }

                    return Single.just(moviesWrapper);
                })
                .map(moviesWrapper -> moviesWrapper.mResults)
                .toObservable()
                .flatMapIterable(results -> results)
                .map(mMovieMapper)
                .toList()
                .subscribe(list -> {
                            mCurrentMovies.addAll(list);
                            mMoviesSubject.onNext(mCurrentMovies);
                        }, mMoviesSubject::onError
                );
    }

    protected abstract Single<RemoteMovieWrapperEntity> getSortCriteriaSingle(
            @NonNull final String apiKey, final int currentPage);

}
