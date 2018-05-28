package com.ddd.mvvmsampleapp.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ddd.mvvmsampleapp.R;
import com.ddd.mvvmsampleapp.databinding.RowGenreBinding;
import com.ddd.mvvmsampleapp.model.pojo.movies.movie_details.Genre;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by S.C. on 22/04/18.
 */

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreHolder> {
    private Context context;
    private List<Genre> genreList = new ArrayList<>();
    private LayoutInflater inflater;

    public GenreAdapter(Context context, List<Genre> arrayList) {
        this.context = context;
        this.genreList = arrayList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public GenreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GenreHolder((RowGenreBinding) DataBindingUtil.inflate(inflater, R.layout.row_genre, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GenreHolder holder, int position) {
        holder.binding.btnGenre.setText(genreList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }

    public interface OnClickListener {

        void onClick(int position);
    }

    class GenreHolder extends RecyclerView.ViewHolder {
        private RowGenreBinding binding;

        public GenreHolder(RowGenreBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
