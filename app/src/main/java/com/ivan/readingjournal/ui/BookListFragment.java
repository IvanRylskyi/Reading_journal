package com.ivan.readingjournal.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ivan.readingjournal.R;
import com.ivan.readingjournal.BookAdapter;
import com.ivan.readingjournal.db.DBHelper;
import com.ivan.readingjournal.Book;
import java.util.List;

public class BookListFragment extends Fragment {
    private DBHelper db;
    private BookAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_book_list, container, false);
        db = new DBHelper(requireContext());
        RecyclerView rv = v.findViewById(R.id.recycler);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new BookAdapter(db.getAllBooks(), book -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, BookDetailsFragment.newInstance(book.id))
                    .addToBackStack(null)
                    .commit();
        });
        rv.setAdapter(adapter);
        Button btnAdd = v.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(view -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new AddEditFragment())
                    .addToBackStack(null)
                    .commit();
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Book> all = db.getAllBooks();
        adapter.update(all);
    }
}