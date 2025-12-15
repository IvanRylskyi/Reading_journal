package com.ivan.readingjournal;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.ivan.readingjournal.ui.BookListFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new BookListFragment())
                    .commit();
        }
    }
}