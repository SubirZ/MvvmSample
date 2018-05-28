package com.ddd.mvvmsampleapp.view.ui.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.ddd.mvvmsampleapp.R;
import com.ddd.mvvmsampleapp.databinding.FragmentMovieDetailsBinding;
import com.ddd.mvvmsampleapp.model.pojo.movies.movie_details.MoviesDetails;
import com.ddd.mvvmsampleapp.model.repository.ProjectRepository;
import com.ddd.mvvmsampleapp.view.adapter.GenreAdapter;
import com.ddd.mvvmsampleapp.view.ui.util.Constants;
import com.ddd.mvvmsampleapp.viewmodel.MovieDetailViewModel;

/**
 * Created by S.C. on 11/05/18.
 */

public class MovieDetailsFragment extends Fragment {
    private FragmentMovieDetailsBinding binding;
    private int movieId;
    private MoviesDetails movie;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        movieId = getArguments().getInt(Constants.ID, 0);
        getMovieDetails();
    }

    /**
     * initialise observer to fetch movie details
     */
    private void getMovieDetails() {
        final MovieDetailViewModel viewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                viewModel.getMovieDetails(movieId);
                viewModel.getMovieDetailObservable().observe(MovieDetailsFragment.this, new Observer<MoviesDetails>() {
                    @Override
                    public void onChanged(@Nullable MoviesDetails moviesDetails) {
                        movie = moviesDetails;
                        binding.setMovie(moviesDetails);
                        updateView();
                    }
                });
            }
        };
        handler.postDelayed(runnable, 100);
    }

    private void updateView() {
        Glide.with(this).load(ProjectRepository.getInstance().WS_BASE_IMAGE_URL + movie.getPosterPath()).into(binding.ivPoster);
        if (movie.getBackdropPath() != null) {
            Glide.with(this).load(ProjectRepository.getInstance().WS_BASE_IMAGE_URL + movie.getBackdropPath()).into(binding.ivBanner);
        } else {
            Glide.with(this).load(ProjectRepository.getInstance().WS_BASE_IMAGE_URL + movie.getPosterPath()).into(binding.ivBanner);
        }
        binding.tvTitle.setText(movie.getTitle());
        binding.tvTagLine.setText(movie.getTagline());
        binding.tvOverview.setText(movie.getOverview());
        if (movie.getVoteAverage() != null && movie.getVoteAverage() > 0) {
            binding.tvCount.setText(String.valueOf(movie.getVoteAverage()));
            binding.saVoteCount.setProgress((int) (movie.getVoteAverage() * 10));
        }
        if (!movie.getGenres().isEmpty()) {
            final GenreAdapter genreAdapter = new GenreAdapter(getActivity(), movie.getGenres());
            binding.rvGenre.hasFixedSize();
            binding.rvGenre.setAdapter(genreAdapter);
        }

    }
}