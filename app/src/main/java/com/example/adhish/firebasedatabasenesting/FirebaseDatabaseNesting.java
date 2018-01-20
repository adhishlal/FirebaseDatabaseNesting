package com.example.adhish.firebasedatabasenesting;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseDatabaseNesting extends AppCompatActivity {

    String TAG = getClass().getSimpleName();
    FirebaseDatabase db;


    TextView tvLat, tvLon;
    EditText etUser, etLat, etLon;
    Button btnSave;

    EditText etUserRead;
    Button btnGetData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_database_nesting);

        db = FirebaseDatabase.getInstance();

        initializeViews();



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etUser.getText().toString().trim().length()<=0)
                {
                    etUser.setError("Cannot be blank");
                    return;
                }

                if(etLat.getText().toString().trim().length()<=0)
                {
                    etLat.setError("Cannot be blank");
                    return;
                }

                if(etLon.getText().toString().trim().length()<=0)
                {
                    etLon.setError("Cannot be blank");
                    return;
                }

                writeData(etUser.getText().toString().trim(),etLat.getText().toString().trim(),etLon.getText().toString().trim());

            }
        });


        btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etUserRead.getText().toString().trim().length()<=0)
                {
                    etUserRead.setError("Cannot be blank");
                    return;
                }

                readData(etUserRead.getText().toString().trim());
            }
        });

    }

    private void writeData(String user, String lat, String lon)
    {
        DatabaseReference myRef = db.getReference("user");

        myRef.child(user).child("start").child("Latitude").setValue(lat);
        myRef.child(user).child("start").child("Longitude").setValue(lon);

        myRef.child(user).child("end").child("Latitude").setValue(lat+"22");
        myRef.child(user).child("end").child("Longitude").setValue(lon+"33");

        Toast.makeText(FirebaseDatabaseNesting.this,"Saved",Toast.LENGTH_SHORT).show();

        etLat.setText("");
        etLon.setText("");
        etUser.setText("");


        hideKeyboard(FirebaseDatabaseNesting.this);
    }

    public static void hideKeyboard(Activity activity) {
        View view = activity.findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void readData(String user)
    {
        DatabaseReference myRefLat = db.getReference("user").child(user).child("start").child("Latitude");
        myRefLat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);

                tvLat.setText(value);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });



        DatabaseReference myRefLon = db.getReference("user").child(user).child("start").child("Longitude");
        myRefLon.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);

                tvLon.setText(value);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        //For End

        DatabaseReference myRefLatEnd = db.getReference("user").child(user).child("end").child("Latitude");
        myRefLatEnd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "End is: " + value);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });



        DatabaseReference myRefLonEnd = db.getReference("user").child(user).child("end").child("Longitude");
        myRefLonEnd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "End is: " + value);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        hideKeyboard(FirebaseDatabaseNesting.this);

    }


    private void initializeViews()
    {

        tvLat = findViewById(R.id.tvLat);
        tvLon = findViewById(R.id.tvLon);

        etUser = findViewById(R.id.etUser);
        etLat = findViewById(R.id.etLat);
        etLon = findViewById(R.id.etLon);

        btnSave = findViewById(R.id.btnSave);

        etUserRead = findViewById(R.id.etUserRead);
        btnGetData = findViewById(R.id.btnGetData);
    }

}
