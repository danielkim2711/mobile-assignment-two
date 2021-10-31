package cs.daniel.mobileassignmenttwo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {

    EditText name, email, position, country;
    Button btnAdd, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        name = (EditText)findViewById(R.id.etName);
        email = (EditText)findViewById(R.id.etEmail);
        position = (EditText)findViewById(R.id.etPosition);
        country = (EditText)findViewById(R.id.etCountry);

        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnBack = (Button)findViewById(R.id.btnBack);

        // Set onClickListener for add button

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData();
                clearAll();
            }
        });

        // Set onClickListener for back button

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runMainActivity(view);
            }
        });
    }

    // Add data into Firebase Database

    private void addData() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name.getText().toString());
        map.put("email", email.getText().toString());
        map.put("position", position.getText().toString());
        map.put("country", country.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("employees").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddActivity.this, "Added successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddActivity.this, "Error: failed to add.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Clear input once it's successfully added.

    private void clearAll() {
        name.setText("");
        email.setText("");
        position.setText("");
        country.setText("");
    }

    // Go back to main page once delete button is clicked

    public void runMainActivity(View view) {
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}