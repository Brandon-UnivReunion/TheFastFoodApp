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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    /*DEBUT : Déclaration des variables */
    private TextView errorTextViewRA;
    private TextView regToLog;
    TextInputEditText signUpMail;
    TextInputEditText signUpPass;
    private Button regBtn;
    private FirebaseAuth auth;
    /*FIN : Déclaration des variables */

    /**
     * Méthode utilisé lors de l'appel à la vue correspondante et relancée periodiquement.
     * @param savedInstanceState Etat sauvegardé de l'instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        /*DEBUT : Récupération des ID XML */
        errorTextViewRA = findViewById(R.id.errorTextViewRA);
        regToLog = findViewById(R.id.regToLog);
        signUpMail = findViewById(R.id.mailRegTEField);
        signUpPass = findViewById(R.id.passRegTEField);
        regBtn = findViewById(R.id.regBtn);
        auth = FirebaseAuth.getInstance();
        /*FIN : Récupération des ID XML */


        /* DEBUT : Ecouteur du bouton d'inscription*/
        ////////////////////////////////////////////////////////////////////////////////////////////

        /* Le bouton 'regBtn' vérifie que l'utilisateur entre les informations correctement avant de terminer l'opération:
         * Il vérifie :
         *       - Si le champ texte d'adresse mail est non vide,
         *       - Si le champ texte mot de passe est non vide,
         *       - Si le mot de passe contient au moins huit caractères.
         *
         * Puis effectue l'inscription grâce à la méthode proposée par FireBase pour l'inscription.
         * Celle-ci vérifie que la tâche éffectuée est complète.
         *       - Si oui, renvoie sur l'activité Login(connexion),
         *       - Si non, message d'erreur.*/

        ////////////////////////////////////////////////////////////////////////////////////////////

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Objects.requireNonNull(signUpMail.getText()).toString().trim();
                String pass = Objects.requireNonNull(signUpPass.getText()).toString();

                final String username = getString(R.string.username);
                final String phoneN = getString(R.string.phoneNum);

                /* Conditions de confirmation de l'adresse mail et de mot de passe */

                //Si le champ Email est vide
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(), R.string.mail_empty, Toast.LENGTH_LONG).show();
                    errorTextViewRA.setText(R.string.mail_empty);
                    signUpMail.setError("Error");
                    return;
                }

                //Si le champ MDP est vide
                if(TextUtils.isEmpty(pass)){
                    Toast.makeText(getApplicationContext(), R.string.pw_empty, Toast.LENGTH_LONG).show();
                    errorTextViewRA.setText(R.string.pw_empty);
                    signUpPass.setError("Error");
                    return;
                }

                //Si le champ MDP contient moins de 8 caractères
                if(pass.length() < 8){
                    Toast.makeText(getApplicationContext(), R.string.short_pw, Toast.LENGTH_LONG).show();
                    errorTextViewRA.setText(R.string.short_pw);
                    signUpPass.setError("Error");
                }else{
                    auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            //Si la tâche n'est pas réussie
                            if(!task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                                errorTextViewRA.setText(R.string.reg_fail);
                            }else{
                                //Récupération du
                                FirebaseUser user = auth.getCurrentUser();

                                assert user != null;
                                String email = user.getEmail();
                                String uid = user.getUid();

                                //Lorsque l'utilisateur est connecté, on stocke les informations utilisateur dans la base de données firebase en utilisant HashMap
                                HashMap<Object,String> hashMap = new HashMap<>();

                                //Transfert de l'information en hasmap
                                hashMap.put("email",email);
                                hashMap.put("uid", uid);
                                // Les informations suivantes seront rajouter grâce à l'édition de profil
                                hashMap.put("name", username);
                                hashMap.put("phone", phoneN);
                                hashMap.put("image", "");

                                // Instance de la base de données firebase
                                FirebaseDatabase database = FirebaseDatabase.getInstance();

                                //Chemin de stockage des données utilisateur dans "Users"
                                DatabaseReference reference = database.getReference("Users");

                                // Ajout des données dans la base de données en Hashmap
                                reference.child(uid).setValue(hashMap);


                                //Redirection vers l'activité de connexion
                                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                finish();
                            }
                        }
                    });
                }

            }
        });
        /* FIN :  Ecouteur du bouton d'inscription*/


        /* DEBUT : Lien qui permet d'aller vers l'activité Login en un click */
        ////////////////////////////////////////////////////////////////////////////////////////////

        /* Création d'une instance de l'activité register puis on lance l'activité RegisterActivity pour register l'utilisateur*/

        ////////////////////////////////////////////////////////////////////////////////////////////
        regToLog.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        /* FIN : Lien qui permet d'aller vers l'activité Login en un click */

    }
}