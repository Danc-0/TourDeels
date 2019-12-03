package com.example.designtest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class InsertActivity extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    EditText txtTitle, txtDescription, txtPrice;
    Button btnSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mFirebaseDatabase = FirebaseDatabase.getInstance;
        mDatabaseReference = FirebaseDatabase.getReference.child("traveldeal");

        txtTitle = findViewById(R.id.txtTitle);
        txtDescription = findViewById(R.id.txtDescription);
        txtPrice = findViewById(R.id.txtPrice);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        saveDeal();
                        Toast.makeText(InsertActivity.this, "Deal saved", Toast.LENGTH_SHORT).show();
                        clean();

                }
        });
    }

    private void saveDeal() {
        String title = txtTitle.getText().toString();
        String description = txtPrice.getText().toString();
        String price = txtPrice.getText().toString();
        TravelDeal deal = new TravelDeal(title, description, price, "");
        mDatabaseReference.push().setValue(deal);
    }

    private void clean() {
    txtTitle.setText(" ");
    txtDescription.setText(" ");
    txtPrice.setText(" ");
    txtTitle.requestFocus();
    }

}

