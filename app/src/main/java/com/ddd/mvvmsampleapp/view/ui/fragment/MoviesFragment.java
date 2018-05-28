package com.ddd.mvvmsampleapp.view.ui.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ddd.mvvmsampleapp.R;
import com.ddd.mvvmsampleapp.databinding.FragmentDiscoverMoviesBinding;
import com.ddd.mvvmsampleapp.model.pojo.movies.DiscoverMovieResult;
import com.ddd.mvvmsampleapp.model.pojo.movies.DiscoverMovies;
import com.ddd.mvvmsampleapp.view.adapter.DiscoverMoviesAdapter;
import com.ddd.mvvmsampleapp.view.callback.OnMovieItemClick;
import com.ddd.mvvmsampleapp.view.ui.activity.MainActivity;
import com.ddd.mvvmsampleapp.view.ui.util.Constants;
import com.ddd.mvvmsampleapp.viewmodel.DiscoverMoviesViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by S.C. on 17/05/18.
 */

public class MoviesFragment extends Fragment {
    private FragmentDiscoverMoviesBinding binding;
    private DiscoverMoviesAdapter movieAdapter;
    private List<DiscoverMovieResult> moviesList = new ArrayList<>();
    /**
     * handle click event of movie
     */
    private OnMovieItemClick movieItemClick = new OnMovieItemClick() {
        @Override
        public void onClick(int position, int id) {
            final MovieDetailsFragment detailsFragment = new MovieDetailsFragment();
            final Bundle bundle = new Bundle();
            bundle.putInt(Constants.ID, id);
            detailsFragment.setArguments(bundle);
            ((MainActivity) getActivity()).addFragment(R.id.flContainer, MoviesFragment.this, detailsFragment, false);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_discover_movies, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final DiscoverMoviesViewModel moviesViewModel = ViewModelProviders.of(this).get(DiscoverMoviesViewModel.class);
        movieAdapter = new DiscoverMoviesAdapter(getActivity(), moviesList, movieItemClick);
        binding.rvDiscoverMovies.hasFixedSize();
        binding.rvDiscoverMovies.setAdapter(movieAdapter);
        initMoviesObserver(moviesViewModel);
    }

    /**
     * initialise observer for getting movies
     *
     * @param viewModel viewModel
     */
    private void initMoviesObserver(DiscoverMoviesViewModel viewModel) {
        viewModel.getDiscoverMoviesObservable().observe(this, new Observer<DiscoverMovies>() {
            @Override
            public void onChanged(@Nullable DiscoverMovies discoverMovies) {
                assert discoverMovies != null;
                moviesList.addAll(discoverMovies.getResults());
                movieAdapter.notifyDataSetChanged();
            }
        });
    }
}
