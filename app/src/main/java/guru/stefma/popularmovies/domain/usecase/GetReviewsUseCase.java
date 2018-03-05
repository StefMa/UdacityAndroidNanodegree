package guru.stefma.popularmovies.domain.usecase;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import guru.stefma.popularmovies.data.MoviesRepository;
import guru.stefma.popularmovies.data.remote.entity.RemoteReviewWrapperEntity.RemoteReviewEntity;
import guru.stefma.popularmovies.domain.Review;
import guru.stefma.popularmovies.domain.usecase.GetReviewsUseCase.Params;
import guru.stefma.popularmovies.domain.usecase.base.BaseObservableUseCase;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

// TODO: Document
// TODO: Think about a generic version like "PaginatedObservableUseCase"
public class GetReviewsUseCase implements BaseObservableUseCase<List<Review>, Params> {

    public static class Params {

        private String mApiKey;

        private int mMovieId;

        public Params(final String apiKey, final int movieId) {
            mApiKey = apiKey;
            mMovieId = movieId;
        }

    }

    private int mCurrentPage = 1;

    @NonNull
    private List<Review> mCurrentReviews = new ArrayList<>();

    @NonNull
    private final MoviesRepository mMovieRepo;

    @Nullable
    private Params mParams = null;

    private final PublishSubject<List<Review>> mReviewsSubject = PublishSubject.create();

    @Inject
    public GetReviewsUseCase(@NonNull final MoviesRepository repository) {
        mMovieRepo = repository;
    }

    @Override
    public Observable<List<Review>> execute(@NonNull final Params params) {
        mParams = params;
        return mReviewsSubject
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> loadMore());
    }

    public void loadMore() {
        if (!mReviewsSubject.hasComplete() && !mReviewsSubject.hasThrowable()) {
            if (mParams == null) {
                throw new IllegalStateException("It's not allowed to call loadMore() before calling execute(String)");
            }

            mMovieRepo.getReviewsForMovie(mParams.mApiKey, mCurrentPage, mParams.mMovieId)
                    .subscribeOn(Schedulers.io())
                    .flatMap(reviewsWrapper -> {
                        if (mCurrentPage > reviewsWrapper.mTotalPages) {
                            return Single.error(new LastPageReachedException());
                        }

                        mCurrentPage += 1;

                        return Single.just(reviewsWrapper);
                    })
                    .map(moviesWrapper -> moviesWrapper.mResults)
                    .toObservable()
                    .flatMapIterable(results -> results)
                    .map(this::mapRemoteReviewsToReviews)
                    .toList()
                    .subscribe(list -> {
                                mCurrentReviews.addAll(list);
                                mReviewsSubject.onNext(mCurrentReviews);
                            }, mReviewsSubject::onError
                    );
        }
    }

    private Review mapRemoteReviewsToReviews(final RemoteReviewEntity reviewEntity) {
        return new Review(reviewEntity.mAuthor, reviewEntity.mContent, reviewEntity.mContent);
    }
}
