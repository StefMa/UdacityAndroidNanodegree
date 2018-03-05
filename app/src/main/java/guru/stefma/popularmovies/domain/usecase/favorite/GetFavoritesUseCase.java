package guru.stefma.popularmovies.domain.usecase.favorite;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import guru.stefma.popularmovies.data.MoviesRepository;
import guru.stefma.popularmovies.data.local.entity.LocalMovieFavoriteEntity;
import guru.stefma.popularmovies.domain.usecase.base.BaseSingleUseCase;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Get all {@link FavoriteMovie}s.
 */
public class GetFavoritesUseCase implements BaseSingleUseCase<List<FavoriteMovie>, Void> {

    @NonNull
    private final MoviesRepository mRepository;

    @Inject
    public GetFavoritesUseCase(@NonNull final MoviesRepository repository) {
        mRepository = repository;
    }

    @Override
    public Single<List<FavoriteMovie>> execute(@Nullable final Void params) {
        return mRepository.getFavoritesMovies()
                .subscribeOn(Schedulers.io())
                .map(this::mapLocalEntityToFavoriteMovie)
                .observeOn(AndroidSchedulers.mainThread());
    }

    private List<FavoriteMovie> mapLocalEntityToFavoriteMovie(final List<LocalMovieFavoriteEntity> localEntities) {
        final List<FavoriteMovie> favMovies = new ArrayList<>();
        for (final LocalMovieFavoriteEntity localEntity : localEntities) {
            favMovies.add(new FavoriteMovie(localEntity.mId, localEntity.mTitle));
        }
        return favMovies;
    }
}
