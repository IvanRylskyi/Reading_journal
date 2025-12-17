package com.ivan.readingjournal.controller.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.ivan.readingjournal.model.db.Book;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "reading_journal.db";
    private static final int DB_VER = 1;
    public static final String TABLE_BOOKS = "books";
    public static final String COL_ID = "id";
    public static final String COL_TITLE = "title";
    public static final String COL_AUTHOR = "author";
    public static final String COL_PAGES_TOTAL = "pages_total";
    public static final String COL_PAGES_READ = "pages_read";
    public static final String COL_RATING = "rating";
    public static final String COL_NOTES = "notes";
    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_BOOKS + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_TITLE + " TEXT NOT NULL,"
                + COL_AUTHOR + " TEXT,"
                + COL_PAGES_TOTAL + " INTEGER DEFAULT 0,"
                + COL_PAGES_READ + " INTEGER DEFAULT 0,"
                + COL_RATING + " INTEGER DEFAULT 0,"
                + COL_NOTES + " TEXT"
                + ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
        onCreate(db);
    }

    public long insertBook(Book b) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_TITLE, b.title);
        cv.put(COL_AUTHOR, b.author);
        cv.put(COL_PAGES_TOTAL, b.pagesTotal);
        cv.put(COL_PAGES_READ, b.pagesRead);
        cv.put(COL_RATING, b.rating);
        cv.put(COL_NOTES, b.notes);
        long id = db.insert(TABLE_BOOKS, null, cv);
        db.close();
        return id;
    }

    public int updateBook(Book b) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_TITLE, b.title);
        cv.put(COL_AUTHOR, b.author);
        cv.put(COL_PAGES_TOTAL, b.pagesTotal);
        cv.put(COL_PAGES_READ, b.pagesRead);
        cv.put(COL_RATING, b.rating);
        cv.put(COL_NOTES, b.notes);
        int rows = db.update(TABLE_BOOKS, cv, COL_ID + "=?", new String[]{String.valueOf(b.id)});
        db.close();
        return rows;
    }

    public int deleteBook(long id) {
        SQLiteDatabase db = getWritableDatabase();
        int rows = db.delete(TABLE_BOOKS, COL_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return rows;
    }

    public Book getBook(long id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE_BOOKS, null, COL_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        Book b = null;
        if (c != null) {
            if (c.moveToFirst()) {
                b = readBookFromCursor(c);
            }
            c.close();
        }
        db.close();
        return b;
    }

    public List<Book> getAllBooks() {
        List<Book> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE_BOOKS, null, null, null, null, null, COL_ID + " DESC");
        if (c != null) {
            while (c.moveToNext()) {
                list.add(readBookFromCursor(c));
            }
            c.close();
        }
        db.close();
        return list;
    }

    private Book readBookFromCursor(Cursor c) {
        Book b = new Book();
        b.id = c.getLong(c.getColumnIndexOrThrow(COL_ID));
        b.title = c.getString(c.getColumnIndexOrThrow(COL_TITLE));
        b.author = c.getString(c.getColumnIndexOrThrow(COL_AUTHOR));
        b.pagesTotal = c.getInt(c.getColumnIndexOrThrow(COL_PAGES_TOTAL));
        b.pagesRead = c.getInt(c.getColumnIndexOrThrow(COL_PAGES_READ));
        b.rating = c.getInt(c.getColumnIndexOrThrow(COL_RATING));
        b.notes = c.getString(c.getColumnIndexOrThrow(COL_NOTES));
        return b;
    }
}