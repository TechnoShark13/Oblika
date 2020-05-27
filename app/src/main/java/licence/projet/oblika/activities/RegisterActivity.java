package licence.projet.oblika.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import licence.projet.oblika.R;

import static android.support.constraint.Constraints.TAG;

public class RegisterActivity extends Activity {

    private FirebaseAuth mAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        Button register = findViewById(R.id.registerButton);
        Button goToLogin = findViewById(R.id.goToLogin);
        final EditText edEmail = findViewById(R.id.registerEmail);
        final EditText edPass = findViewById(R.id.registerPass);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edEmail.getText().toString();
                String password = edPass.getText().toString();
                signUp(email, password);
            }
        });

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activity2Intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(activity2Intent);
            }
        });
    }

    private void signUp(String email, String password) {
        //TODO vérifier le mail et le mot de passe
        boolean validInfo = false;

        validInfo = isInfoValid(email, password);

        if (validInfo) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(activity2Intent);
                                //TODO modifier l'UI avec les paramètres de l'utilisateur
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //TODO modifier l'UI avec les paramètres de l'utilisateur updateUI(null);
                            }
                        }
                    });
        }
    }

    private boolean isInfoValid(String mail, String password) {
        return (mail != null && password != null);
    }
}
