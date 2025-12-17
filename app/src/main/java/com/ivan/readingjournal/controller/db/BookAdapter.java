package com.ivan.readingjournal.controller.db;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ivan.readingjournal.R;
import com.ivan.readingjournal.model.db.Book;

import java.util.List;
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.VH> {
    public interface Listener {
        void onClick(Book book);
    }
    private List<Book> items;
    private Listener listener;
    public BookAdapter(List<Book> items, Listener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        final Book b = items.get(position);
        holder.title.setText(b.title);
        holder.author.setText(b.author == null ? "" : b.author);
        int total = Math.max(1, b.pagesTotal);
        int progress = (int) ((b.pagesRead * 100.0f) / total);
        holder.progressBar.setProgress(progress);
        holder.progressText.setText(b.pagesRead + "/" + b.pagesTotal);
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClick(b);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public void update(List<Book> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }
    static class VH extends RecyclerView.ViewHolder {
        TextView title, author, progressText;
        ProgressBar progressBar;
        VH(@NonNull View v) {
            super(v);
            title = v.findViewById(R.id.itemTitle);
            author = v.findViewById(R.id.itemAuthor);
            progressText = v.findViewById(R.id.itemProgressText);
            progressBar = v.findViewById(R.id.itemProgressBar);
        }
    }
}