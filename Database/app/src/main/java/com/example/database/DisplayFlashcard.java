package com.example.database;

import android.os.Bundle;
import android.app.Activity;

import android.content.Intent;
import android.database.Cursor;

import android.view.Menu;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayFlashcard extends Activity {
    private DBHelper mydb;
    TextView question;
    TextView answer;
    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_content);
        question = (TextView) findViewById(R.id.editTextQuestion);
        answer = (TextView) findViewById(R.id.editTextAnswer);

        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            int Value = extras.getInt("id");

            if(Value>0){
                Cursor rs = mydb.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();

                String q = rs.getString(rs.getColumnIndex(DBHelper.FLASHCARDS_COLUMN_QUESTION));
                String a = rs.getString(rs.getColumnIndex(DBHelper.FLASHCARDS_COLUMN_ANSWER));

                if (!rs.isClosed())  {
                    rs.close();
                }
                Button b = (Button)findViewById(R.id.button1);
                b.setVisibility(View.INVISIBLE);

                question.setText(q);
                question.setFocusable(false);
                question.setClickable(false);

                answer.setText(a);
                answer.setFocusable(false);
                answer.setClickable(false);
            }
        }
    }



   public void run(View view) {
        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            int Value = extras.getInt("id");
            if(Value > 0) {
                if (mydb.updateFlashcard(id_To_Update, question.getText().toString(),
                        answer.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Not Updated", Toast.LENGTH_SHORT).show();
                }
            }
                else {
                if(mydb.insertFlashcard(question.getText().toString(), answer.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Added to list",
                            Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getApplicationContext(), "Not added!",
                            Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        }
    }
}
