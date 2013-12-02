package com.example.databaseexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper {

	public static final String DATABASE_NAME = "student.db";
	private static final int DATABASE_VERSION = 1;
	public static final String TABEL_NAME = "student";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_AGE = "age";
	public static final String COLUMN_LOCATION = "location";
	private static final String TABLE_CREATION_QUERY = "CREATE TABLE STUDENT ( "
			+ COLUMN_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_NAME
			+ " TEXT NOT NULL, "
			+ COLUMN_AGE
			+ " TEXT NOT NULL, "
			+ COLUMN_LOCATION + " TEXT NOT NULL );";

	private DatabaseEngine databaseEngine;
	private SQLiteDatabase sqLiteDatabase;

	public DatabaseHelper(Context context) {
		databaseEngine = new DatabaseEngine(context);
	}

	public DatabaseHelper open() {
		sqLiteDatabase = databaseEngine.getWritableDatabase();
		return this;
	}

	public void createStudent(String name, String age, String location) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(COLUMN_NAME, name);// HashMap=key & valuess
		contentValues.put(COLUMN_AGE, age);
		contentValues.put(COLUMN_LOCATION, location);

		if (sqLiteDatabase.insert(TABEL_NAME, null, contentValues) != -1) {
			Log.i("database", "Insert Success");
		} else {
			Log.i("database", "Insert Failure");
		}

	}

	public void showStudents() {
		// Cursor cursor = sqLiteDatabase.query(TABEL_NAME, null, null, null,
		// null, null, COLUMN_NAME + " ASC");

		Cursor cursor = sqLiteDatabase.query(TABEL_NAME, null, null, null,
				null, null, COLUMN_NAME + " ASC");

		if (cursor != null && cursor.moveToFirst()) {
			do {
				String name = cursor.getString(cursor
						.getColumnIndex(COLUMN_NAME));
				String age = cursor
						.getString(cursor.getColumnIndex(COLUMN_AGE));
				String location = cursor.getString(cursor
						.getColumnIndex(COLUMN_LOCATION));

				Log.i("database", name + " " + age + " " + location);
			} while (cursor.moveToNext());
		}
	}

	public Cursor fetchStudents() {
		return sqLiteDatabase.query(TABEL_NAME, null, null, null, null, null,
				COLUMN_NAME + " ASC");
	}

	public void deleteStudent(String name) {
		sqLiteDatabase.delete(TABEL_NAME, COLUMN_NAME + " =?",
				new String[] { name });
	}

	public void updateStudent(String name, String age) {
		ContentValues values = new ContentValues();
		values.put(COLUMN_AGE, age);
		sqLiteDatabase.update(TABEL_NAME, values, COLUMN_NAME + " =?",
				new String[] { name });
	}

	private class DatabaseEngine extends SQLiteOpenHelper {
		public DatabaseEngine(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(TABLE_CREATION_QUERY);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			if (newVersion > oldVersion) {
				db.execSQL("DROP TABLE IF EXISTS " + TABEL_NAME);
				onCreate(db);
			}
		}
	}

}
