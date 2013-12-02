package com.example.databasedemos;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		DatabaseHelper databaseHelper = new DatabaseHelper(this);
		databaseHelper.open();

		// databaseHelper.insertStudent("Vipul", "27", "Thane");
		// databaseHelper.insertStudent("Darshan", "22", "Borivali");
		// databaseHelper.insertStudent("Hiren", "21", "Goregaon");

		// databaseHelper.displayStudents();
		// databaseHelper.delete("Vipul");
		databaseHelper.displayStudents();
		databaseHelper.update("Hiren");
		databaseHelper.displayStudents();
	}

}
