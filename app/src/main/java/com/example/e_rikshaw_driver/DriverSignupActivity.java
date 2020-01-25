package com.example.e_rikshaw_driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DriverSignupActivity extends AppCompatActivity {
    EditText username,name,password;
    EditText phone;
    DatabaseReference current_user_db;
    FirebaseAuth mAuth;
    Button signup;
    TextView already;
    drivers mDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_signup);

        mAuth=FirebaseAuth.getInstance();
        mDriver =new drivers();
        startService(new Intent(DriverSignupActivity.this,onAppKilled.class));
        username=(EditText)findViewById(R.id.username);
        name=(EditText)findViewById(R.id.name);
        phone=(EditText)findViewById(R.id.phone);
        password=(EditText)findViewById(R.id.password);
        signup=(Button) findViewById(R.id.signup);
        already=(TextView) findViewById(R.id.already);
/*        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
         public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(DriverSignupActivity.this, DriverMapActivity.class);
                    startActivity(intent);
                }
            }
        };

*/

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDriver.setPhone(phone.getText().toString().trim());
                mDriver.setName(name.getText().toString().trim());


                String email = username.getText().toString();
                String pwd = password.getText().toString();

                if (email.isEmpty()) {
                    username.setError("Please enter the email id");
                    username.requestFocus();

                } else if (pwd.isEmpty()) {
                    password.setError("Please enter the password");
                    password.requestFocus();
                } else if (email.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(DriverSignupActivity.this, "Fields Are Empty", Toast.LENGTH_SHORT).show();
                } else if (!(email.isEmpty() && pwd.isEmpty())) {

                    mAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(DriverSignupActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(DriverSignupActivity.this, "SignUp Unsuccessful, Please try again later", Toast.LENGTH_SHORT).show();

                            } else {


                                Intent i = new Intent(DriverSignupActivity.this, DriverMapActivityFinal.class);
                                startActivity(i);
                                String user_id = mAuth.getCurrentUser().getUid();

                                current_user_db = FirebaseDatabase.getInstance().getReference().child("users").child("drivers").child(user_id);
                                current_user_db.setValue(mDriver);


                            }
                        }
                    });
                }
                else{
                    Toast.makeText(DriverSignupActivity.this,"SignUp Error",Toast.LENGTH_SHORT).show();
                }
            }

        });
        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(DriverSignupActivity.this,MainActivity.class);
                startActivity(i);
            }
        });



    }
}
