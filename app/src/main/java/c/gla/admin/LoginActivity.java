package c.gla.admin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {
    private EditText mname, mpassword;
    private TextView Info;
    private Button btn;
    private int counter = 5;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth=FirebaseAuth.getInstance();
        mname = findViewById(R.id.tvName);
        mpassword = findViewById(R.id.tvPassword);
        Info = findViewById(R.id.tvInfo);
        btn = findViewById(R.id.btLogin);

        Info.setText("No of attempts remaining:5");


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Validate(mname.getText().toString().trim(), mpassword.getText().toString().trim());
            }
        });

    }

    private void Validate(final String userName, final String userPassword) {
          if (TextUtils.isEmpty(userName))
        {
            mname.setError("UserName cannot be empty");
            return;
        }
         if (TextUtils.isEmpty(userPassword))
        {
            mpassword.setError("Password cannot be empty");
            return;
        }
        if (!userName.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+"))
        {
            mname.setError("Invalid Username");
            return;
        }


        firebaseAuth.signInWithEmailAndPassword(userName,userPassword
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();

                }

                else {

                    counter--;
                    Toast.makeText(LoginActivity.this, "Enter correct Username and Password",
                            Toast.LENGTH_SHORT).show();
                    Info.setText("No of attempts remaining" + String.valueOf(counter));
                    if (counter == 0) {
                        btn.setEnabled(false);
                    }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


                //startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                Toast.makeText(LoginActivity.this, "Enter correct Username and Password",
                        Toast.LENGTH_SHORT).show();


                Toast.makeText(LoginActivity.this, ""+e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });










        /*if ((userName.equals("Admin")) && (userPassword.equals("1234"))) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();

        } else {
            counter--;
            Toast.makeText(this, "Enter correct Username and Password", Toast.LENGTH_SHORT).show();
            Info.setText("No of attempts remaining" + String.valueOf(counter));
            if (counter == 0) {
                btn.setEnabled(false);
            }*/
        }
    }

