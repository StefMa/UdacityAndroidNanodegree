package guru.stefma.choicm.auth;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import java.util.concurrent.Executor;

public class MockTask<T> extends Task<T>{

    @Override
    public boolean isComplete() {
        return false;
    }

    @Override
    public boolean isSuccessful() {
        return false;
    }

    @Override
    public boolean isCanceled() {
        return false;
    }

    @Override
    public T getResult() {
        return null;
    }

    @Override
    public <X extends Throwable> T getResult(@NonNull final Class<X> aClass) throws X {
        return null;
    }

    @Nullable
    @Override
    public Exception getException() {
        return null;
    }

    @NonNull
    @Override
    public Task<T> addOnSuccessListener(@NonNull final OnSuccessListener<? super T> onSuccessListener) {
        return null;
    }

    @NonNull
    @Override
    public Task<T> addOnSuccessListener(@NonNull final Executor executor,
            @NonNull final OnSuccessListener<? super T> onSuccessListener) {
        return null;
    }

    @NonNull
    @Override
    public Task<T> addOnSuccessListener(@NonNull final Activity activity,
            @NonNull final OnSuccessListener<? super T> onSuccessListener) {
        return null;
    }

    @NonNull
    @Override
    public Task<T> addOnFailureListener(@NonNull final OnFailureListener onFailureListener) {
        return null;
    }

    @NonNull
    @Override
    public Task<T> addOnFailureListener(@NonNull final Executor executor,
            @NonNull final OnFailureListener onFailureListener) {
        return null;
    }

    @NonNull
    @Override
    public Task<T> addOnFailureListener(@NonNull final Activity activity,
            @NonNull final OnFailureListener onFailureListener) {
        return null;
    }
}
