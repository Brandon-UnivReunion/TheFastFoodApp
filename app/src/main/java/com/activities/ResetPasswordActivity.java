package com.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thefastfood.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ResetPasswordActivity extends AppCompatActivity {

    /* DEBUT : Déclaration des variables */

    private TextView errorTextViewRPA;
    private TextInputEditText mailAddr;
    private Button resetPBtn;
    private Button cancelBtn;
    private FirebaseAuth auth;

    /* DEBUT : Déclaration des variables */

    /**
     * Méthode lancée à la création de la vue et periodiquement.
     * @param savedInstanceState état sauvegardé de l'instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        /* DEBUT : Récupération des ID XML */
        errorTextViewRPA = findViewById(R.id.errorTextViewRPA);
        mailAddr = findViewById(R.id.mailResetTEField);
        resetPBtn = findViewById(R.id.resetPassBtn);
        cancelBtn = findViewById(R.id.cancelRPBtn);
        auth = FirebaseAuth.getInstance();
        /*FIN : Récupération des ID XML */

        /* DEBUT : Ecouteur du bouton de récupération du mot de passe */
        ////////////////////////////////////////////////////////////////////////////////////////////

        /* Le bouton 'resetBtn' vérifie que l'utilisateur entre les informations correctement avant de terminer l'opération:
         * Il vérifie :
         *       - Si le champ texte d'adresse mail est non vide.
         *
         * Puis effectue la récupération grâce à la méthode proposée par FireBase pour les mots de passes oubliés.
         * Celle-ci vérifie que la tâche éffectuée est complète.
         *       - Si oui, renvoie sur l'activité Login(connexion),
         *       - Si non, message d'erreur.*/

        ////////////////////////////////////////////////////////////////////////////////////////////
        resetPBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String email = Objects.requireNonNull(mailAddr.getText()).toString().trim();

                //Si champ mail vide
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplication(), R.string.mail_empty, Toast.LENGTH_SHORT).show();
                    errorTextViewRPA.setText(R.string.mail_empty);
                    mailAddr.setError("Error");
                }else{
                    //Si le champ mail est bien rempli, un mail est envoyé à l'utilisateur
                    auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ResetPasswordActivity.this, R.string.email_sent, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(ResetPasswordActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        /*FIN : Ecouteur du bouton de récupération du mot de passe*/

        /* DEBUT : Lien qui permet d'aller vers l'activité Login en un click */
        ////////////////////////////////////////////////////////////////////////////////////////////

        /* Création d'une instance de l'activité ResetPassowrd puis on lance l'activité LoginActivity pour connecter l'utilisateur*/

        ////////////////////////////////////////////////////////////////////////////////////////////
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        /* FIN : Lien qui permet d'aller vers l'activité Login en un click */

    }
}