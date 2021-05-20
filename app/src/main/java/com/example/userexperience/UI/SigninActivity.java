package com.example.userexperience.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.userexperience.R;
import com.firebase.ui.auth.AuthUI;



import java.util.Arrays;
import java.util.List;

public class SigninActivity extends AppCompatActivity {


    private SigninViewModel viewModel;
    private Button signin;

    private final int RC_SIGN_IN = 42;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SigninViewModel.class);

        checkIfSignedIn();
        setContentView(R.layout.signin_activity);
        signin = findViewById(R.id.googlebutton);

        signin.setOnClickListener(v -> {
            signIn(v);
        });





    }

    private void checkIfSignedIn() {
        viewModel.getCurrentUser().observe(this, user -> {
            if (user != null){
                goToMainActivity();
            }

        });
    }

    private void goToMainActivity(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }





    void signIn(View v) {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.toiletseat)
                .build();

        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RC_SIGN_IN) {
            handleSignInResult(resultCode);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleSignInResult(int resultCode) {
        if (resultCode == RESULT_OK) {
            goToMainActivity();
        } else {
            //Wrong credentials
        }
    }
}