package com.ddd.mvvmsampleapp.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.ddd.mvvmsampleapp.R;
import com.ddd.mvvmsampleapp.databinding.RowDiscoverMoviesBinding;
import com.ddd.mvvmsampleapp.model.pojo.movies.DiscoverMovieResult;
import com.ddd.mvvmsampleapp.model.repository.ProjectRepository;
import com.ddd.mvvmsampleapp.view.callback.OnMovieItemClick;
import com.ddd.mvvmsampleapp.view.ui.util.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by S.C. on 11/05/18.
 */

public class DiscoverMoviesAdapter extends RecyclerView.Adapter<DiscoverMoviesAdapter.MoviesViewHolder> {
    private Context mContext;
    private List<DiscoverMovieResult> moviesList = new ArrayList<>();
    private OnMovieItemClick listener;
    private LayoutInflater inflater;

    public DiscoverMoviesAdapter(Context mContext, List<DiscoverMovieResult> moviesList, OnMovieItemClick listener) {
        this.mContext = mContext;
        this.moviesList = moviesList;
        this.listener = listener;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MoviesViewHolder((RowDiscoverMoviesBinding) DataBindingUtil.inflate(inflater, R.layout.row_discover_movies, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MoviesViewHolder holder, final int position) {
        holder.binding.tvTitle.setText(moviesList.get(position).getTitle());
        holder.binding.tvReleaseDate.setText(DateUtils.getFormattedDate(DateUtils.DATE_FORMAT_YYYY_MM_DD_OSP, DateUtils.DATE_FORMAT_EEEE_DD_MMM_YYYY, moviesList.get(position).getReleaseDate()));

        if (moviesList.get(position).getBackdropPath() != null) {
            Glide.with(mContext).load(ProjectRepository.getInstance().WS_BASE_IMAGE_URL + moviesList.get(position).getBackdropPath()).into(holder.binding.ivImage);
        } else {
            Glide.with(mContext).load(ProjectRepository.getInstance().WS_BASE_IMAGE_URL + moviesList.get(position).getPosterPath()).into(holder.binding.ivImage);
        }
        holder.binding.cvContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(holder.getAdapterPosition(), moviesList.get(holder.getAdapterPosition()).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder {
        private RowDiscoverMoviesBinding binding;

        public MoviesViewHolder(RowDiscoverMoviesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}