package com.ivan.readingjournal.controller.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.ivan.readingjournal.R;
import com.ivan.readingjournal.controller.db.DBHelper;
import com.ivan.readingjournal.model.db.Book;

public class AddEditFragment extends Fragment {
    private EditText etTitle, etAuthor, etTotal, etRead, etRating, etNotes;
    private DBHelper db;
    private Long editId = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_edit, container, false);
        db = new DBHelper(requireContext());
        etTitle = v.findViewById(R.id.etTitle);
        etAuthor = v.findViewById(R.id.etAuthor);
        etTotal = v.findViewById(R.id.etTotal);
        etRead = v.findViewById(R.id.etRead);
        etRating = v.findViewById(R.id.etRating);
        etNotes = v.findViewById(R.id.etNotes);
        Button btnSave = v.findViewById(R.id.btnSave);
        if (getArguments() != null && getArguments().containsKey("id")) {
            editId = getArguments().getLong("id");
            Book b = db.getBook(editId);
            if (b != null) {
                etTitle.setText(b.title);
                etAuthor.setText(b.author);
                etTotal.setText(String.valueOf(b.pagesTotal));
                etRead.setText(String.valueOf(b.pagesRead));
                etRating.setText(String.valueOf(b.rating));
                etNotes.setText(b.notes);
            }
        }
        btnSave.setOnClickListener(view -> {
            String title = etTitle.getText().toString().trim();
            if (TextUtils.isEmpty(title)) {
                Toast.makeText(requireContext(), "Введіть назву!", Toast.LENGTH_SHORT).show();
                return;
            }
            Book b = new Book();
            b.title = title;
            b.author = etAuthor.getText().toString().trim();
            b.pagesTotal = parseIntSafe(etTotal.getText().toString(), 0);
            b.pagesRead = parseIntSafe(etRead.getText().toString(), 0);
            b.rating = Math.max(0, Math.min(5, parseIntSafe(etRating.getText().toString(), 0)));
            b.notes = etNotes.getText().toString().trim();
            if (editId != null) {
                b.id = editId;
                db.updateBook(b);
            } else {
                db.insertBook(b);
            }
            requireActivity().getSupportFragmentManager().popBackStack();
        });
        return v;
    }
    private int parseIntSafe(String s, int def) {
        try {
            return Integer.parseInt(s);
        }
        catch (Exception e) {
            return def;
        }
    }
}