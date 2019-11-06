package com.example.psw_app;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private GoogleSignInClient googleSignInClient;
    private TextInputLayout tiEmail, tiPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_login);

        tiEmail = findViewById(R.id.login_textInput_email);
        tiPassword = findViewById(R.id.login_textInput_password);
        initButtons();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    void initButtons() {
        Button btnLogin = findViewById(R.id.login_button_login);
        SignInButton btnGoogle = findViewById(R.id.login_button_google);
        Button btnRegister = findViewById(R.id.login_button_register);

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = Objects.requireNonNull(tiEmail.getEditText()).getText().toString().trim();
                final String password = Objects.requireNonNull(tiPassword.getEditText()).getText().toString().trim();

                if(validateInput(email, password))
                    return;

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                } else {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = Objects.requireNonNull(tiEmail.getEditText()).getText().toString().trim();
                String password = Objects.requireNonNull(tiPassword.getEditText()).getText().toString().trim();

                if(validateInput(email, password))
                    return;

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(LoginActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                if (!task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                }
                            }
                        });
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuth(Objects.requireNonNull(account));
            } catch (ApiException e) {
                Log.w("login", "Login failed.");
            }
        }
    }

    private void firebaseAuth(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()) {
                            Log.w("login", "Login failed.");
                            Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                });
    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public boolean validateInput(String email, String password) {
        boolean hasError = false;

        if(email.isEmpty()) {
            tiEmail.setError("Field can't be empty.");
            hasError = true;
        } else {
            tiEmail.setError(null);
        }

        if(password.isEmpty()) {
            tiPassword.setError("Field can't be empty.");
            hasError = true;
        } else if (password.length() < 6) {
            tiPassword.setError("Password is too short.");
            hasError = true;
        } else {
            tiPassword.setError(null);
        }

        return hasError;
    }
}