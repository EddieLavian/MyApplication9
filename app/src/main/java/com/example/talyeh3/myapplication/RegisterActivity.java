package com.example.talyeh3.myapplication;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth firebaseAuth;
    Button btnMainRegister,btnMainLogin;
    EditText etEmail,etPass,etUserName,etAge;
    Dialog d;
    Button btnReg,btnLogin;
    ProgressDialog progressDialog;

    //for user datails will save on data base
    FirebaseDatabase database;
    DatabaseReference userRef;
    String key;
    User u;

    protected void onCreate(Bundle savedInstanceState) {
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();
        btnMainLogin = (Button)findViewById(R.id.btnLogin);
        btnMainLogin.setOnClickListener(this);
        btnMainRegister = (Button)findViewById(R.id.btnRegister);
        btnMainRegister.setOnClickListener(this);
    }






    public void createRegisterDialog()
    {
        d= new Dialog(this);
        d.setContentView(R.layout.registerlayout);
        d.setTitle("Register");
        d.setCancelable(true);
        etEmail=(EditText)d.findViewById(R.id.etEmail);
        etPass=(EditText)d.findViewById(R.id.etPass);
        etAge=(EditText)d.findViewById(R.id.etAge);
        etUserName=(EditText)d.findViewById(R.id.etUserName);

        btnReg=(Button)d.findViewById(R.id.btnRegister);
        btnReg.setOnClickListener(this);
        d.show();

    }
    public void createLoginDialog()
    {
        d= new Dialog(this);
        d.setContentView(R.layout.login_layout);
        d.setTitle("Login");
        d.setCancelable(true);
        etEmail=(EditText)d.findViewById(R.id.etEmail);
        etPass=(EditText)d.findViewById(R.id.etPass);


        btnLogin=(Button)d.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        d.show();

    }

    public void register()
    {

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(etEmail.getText().toString(),etPass.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Successfully registered", Toast.LENGTH_LONG).show();

                    //for user datails will save on data base
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Toast.makeText(RegisterActivity.this, uid,Toast.LENGTH_SHORT).show();

                    List<String> myTeams;
                    myTeams = new ArrayList<String>();
                    myTeams.add("-1");








                    User u = new User(uid,etUserName.getText().toString(),etEmail.getText().toString(),Integer.valueOf(etAge.getText().toString()),"",myTeams);
                   // userRef = database.getReference("Users").push();
                   // u.key = userRef.getKey();
                   // userRef.setValue(u);




                    DatabaseReference mDatabase;
                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase.child("Users").child(uid).setValue(u);




                    Intent intent = new Intent(RegisterActivity.this, ToBe.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                } else {
                    Toast.makeText(RegisterActivity.this, "Registration Error", Toast.LENGTH_LONG).show();
                }

                d.dismiss();
                progressDialog.dismiss();


            }
        });


    }

    public void login()
    {
        progressDialog.setMessage("Login Please Wait...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(etEmail.getText().toString(),etPass.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            Toast.makeText(RegisterActivity.this, "auth_success",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, ToBe.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        }
                        else
                        {
                            Toast.makeText(RegisterActivity.this, "auth_failed",Toast.LENGTH_SHORT).show();

                        }
                        d.dismiss();
                        progressDialog.dismiss();

                    }
                });

    }
    public void onClick(View v) {

        if(v==btnMainLogin)
        {

            if(btnMainLogin.getText().toString().equals("Login"))
            {
                createLoginDialog();
            }
            else if(btnMainLogin.getText().toString().equals("Logout"))
            {
                firebaseAuth.signOut();
                btnMainLogin.setText("Login");
            }

        }
        else if(v==btnMainRegister)
        {
            createRegisterDialog();
        }
        else if (btnReg==v)
        {
            register();
        }
        else if(v==btnLogin)
        {
            login();
        }
    }

}

