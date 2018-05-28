package com.ddd.mvvmsampleapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.ddd.mvvmsampleapp.model.pojo.movies.DiscoverMovies;
import com.ddd.mvvmsampleapp.model.repository.ProjectRepository;

/**
 * Created by S.C. on 11/05/18.
 */

public class DiscoverMoviesViewModel extends AndroidViewModel {
    LiveData<DiscoverMovies> discoverMoviesObservable;

    public DiscoverMoviesViewModel(@NonNull Application application) {
        super(application);

        discoverMoviesObservable = ProjectRepository.getInstance().discoverMovieList("IN");
    }

    /**
     * Expose the LiveData movies query so the UI can observe it.
     */
    public LiveData<DiscoverMovies> getDiscoverMoviesObservable() {
        return discoverMoviesObservable;
    }
}
