package com.ddd.mvvmsampleapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.ddd.mvvmsampleapp.model.pojo.movies.upcoming_movies.UpcomingMovies;
import com.ddd.mvvmsampleapp.model.repository.ProjectRepository;

/**
 * Created by S.C. on 17/05/18.
 */

public class UpcomingMovieListViewModel extends AndroidViewModel {
    private LiveData<UpcomingMovies> upcomingMoviesObservable;

    public UpcomingMovieListViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Expose the LiveData upcoming movies query so the UI can observe it.
     */
    public LiveData<UpcomingMovies> getUpcomingMoviesObservable() {
        return upcomingMoviesObservable;
    }

    public void getUpcomingMovies(String region, String language, int page) {
        upcomingMoviesObservable = ProjectRepository.getInstance().upcomingMovieList(region, language, page);
    }
}
