package com.example.content_resolver;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static final String AUTHORITY = "com.example.content_provider.DataProvider";
    static final String CONTENT_PATH = "backupdata";
    static final String URL = "content://" + AUTHORITY + "/" + CONTENT_PATH;
    static final Uri CONTENT_URI = Uri.parse(URL);

    static final String _ID = "_id";
    static final String VALUE = "_value";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickShowStudent(View view) {
        Cursor c = getContentResolver().query(CONTENT_URI, null, null, null, "_value");
        TextView txtResultView = (TextView) findViewById(R.id.txtText);

        if (c != null && c.moveToFirst()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("List of student");
            while (!c.isAfterLast()) {
                stringBuilder.append("\n" + "ID: " + c.getString(c.getColumnIndex(_ID)) + ", " + "Name: " + c.getString(c.getColumnIndex(VALUE)));
                c.moveToNext();
            }
            txtResultView.setText(stringBuilder);
        } else {
            txtResultView.setText("No student to show");
        }
    }

    public void onClickAddStudent(View view) {
        // Add a new Student record
        ContentValues values = new ContentValues();
        values.put(VALUE, ((EditText) findViewById(R.id.edtStudentName)).getText().toString());

        Uri result = getContentResolver().insert(CONTENT_URI, values);

        if (result != null) {
            Toast.makeText(getBaseContext(), "Add new student successfully", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getBaseContext(), "Add new student failed", Toast.LENGTH_LONG).show();
        }

    }

    public void onClickUpdateStudent(View view) {
        // Update student based on ID
        String id = ((EditText) findViewById(R.id.edtStudentId)).getText().toString();
        String selection = "_id = \"" + id + "\"";
        ContentValues values = new ContentValues();
        values.put(VALUE, ((EditText) findViewById(R.id.edtStudentName)).getText().toString());

        int result = getContentResolver().update(CONTENT_URI, values, selection, null);
        if (result > 0) {
            Toast.makeText(getBaseContext(), "Update student with ID " + id + " successfully", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getBaseContext(), "Update student failed", Toast.LENGTH_LONG).show();

        }
    }

    public void onClickDeleteStudent(View view) {
        // Delete student based on ID
        String id = ((EditText) findViewById(R.id.edtStudentId)).getText().toString();
        String selection = "_id = \"" + id + "\"";
        ContentValues values = new ContentValues();
        values.put(VALUE, ((EditText) findViewById(R.id.edtStudentName)).getText().toString());

        int result = getContentResolver().delete(CONTENT_URI, selection, null);
        if (result > 0) {
            Toast.makeText(getBaseContext(), "Delete student with ID " + id + " successfully", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getBaseContext(), "Delete student failed", Toast.LENGTH_LONG).show();

        }
    }

}