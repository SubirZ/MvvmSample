package com.ddd.mvvmsampleapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.ddd.mvvmsampleapp.model.pojo.movies.movie_details.MoviesDetails;
import com.ddd.mvvmsampleapp.model.repository.ProjectRepository;

/**
 * Created by S.C. on 11/05/18.
 */

public class MovieDetailViewModel extends AndroidViewModel {
    private LiveData<MoviesDetails> movieDetailObservable;

    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Expose the LiveData movies query so the UI can observe it.
     */
    public LiveData<MoviesDetails> getMovieDetailObservable() {
        return movieDetailObservable;
    }

    public void getMovieDetails(int movieId) {
        movieDetailObservable = ProjectRepository.getInstance().movieDetails(movieId);
    }
}
