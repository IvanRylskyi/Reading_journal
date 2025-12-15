package com.ivan.readingjournal.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.ivan.readingjournal.R;
import com.ivan.readingjournal.db.DBHelper;
import com.ivan.readingjournal.Book;

public class BookDetailsFragment extends Fragment {
    private static final String ARG_ID = "id";
    private long id;
    private DBHelper db;
    public static BookDetailsFragment newInstance(long id) {
        BookDetailsFragment f = new BookDetailsFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_ID, id);
        f.setArguments(args);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_book_details, container, false);
        db = new DBHelper(requireContext());
        if (getArguments() != null) id = getArguments().getLong(ARG_ID);
        TextView tvTitle = v.findViewById(R.id.tvTitle);
        TextView tvAuthor = v.findViewById(R.id.tvAuthor);
        TextView tvProgress = v.findViewById(R.id.tvProgress);
        TextView tvRating = v.findViewById(R.id.tvRating);
        TextView tvNotes = v.findViewById(R.id.tvNotes);
        Button btnEdit = v.findViewById(R.id.btnEdit);
        Button btnDelete = v.findViewById(R.id.btnDelete);
        Book b = db.getBook(id);
        if (b != null) {
            tvTitle.setText(b.title);
            tvAuthor.setText("Автор: " + (b.author == null ? "" : b.author));
            tvProgress.setText("Прочитано сторінок: " + b.pagesRead + "/" + b.pagesTotal);
            tvRating.setText("Оцінка: " + b.rating + "/5");
            tvNotes.setText("Примітки: " + (b.notes == null ? "" : b.notes));
        }
        btnEdit.setOnClickListener(view -> {
            AddEditFragment f = new AddEditFragment();
            Bundle args = new Bundle();
            args.putLong("id", id);
            f.setArguments(args);
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, f)
                    .addToBackStack(null)
                    .commit();
        });
        btnDelete.setOnClickListener(view -> {
            db.deleteBook(id);
            Toast.makeText(requireContext(), "Видалено", Toast.LENGTH_SHORT).show();
            requireActivity().getSupportFragmentManager().popBackStack();
        });
        return v;
    }
}