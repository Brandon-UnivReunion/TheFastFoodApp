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

import com.MainActivity;
import com.example.thefastfood.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    /*DEBUT :  Déclaration des variables */
    private static final int RC_SIGN_IN = 100;
    GoogleSignInClient mGoogleSignInClient;

    private TextView errorTextViewLA;
    private TextView resetPass;
    private TextView logToReg;

    private TextInputEditText signInMail;
    private TextInputEditText signInPass;

    private Button loginBtn;
    private SignInButton googleBtn;

    private FirebaseAuth auth;

    /*FIN :  Déclaration des variables */

    /**
     * Méthode lancée à la création de la vue et periodiquement.
     * @param savedInstanceState état sauvegardé de l'instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*DEBUT : Récupération des ID XML */
        errorTextViewLA = findViewById(R.id.errorTextViewLA);
        resetPass = findViewById(R.id.resetPass);
        logToReg = findViewById(R.id.logToReg);
        signInMail = findViewById(R.id.mailLogTEField);
        signInPass = findViewById(R.id.passLogTEField);
        loginBtn = findViewById(R.id.loginBtn);
        googleBtn = findViewById(R.id.signInGoogle);

        auth = FirebaseAuth.getInstance();
        /*DEBUT : Récupération des ID XML */

        // Configuration du bouton Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        /*DEBUT : Récupération des ID XML */

        /* DEBUT : Ecouteur sur le bouton de connexion */
        ////////////////////////////////////////////////////////////////////////////////////////////

        /* Le bouton 'loginBtn' vérifie que l'utilisateur entre les informations correctement avant de terminer l'opération:
         * Il vérifie :
         *       - Si le champ texte d'adresse mail est non vide,
         *       - Si le champ texte mot de passe est non vide.
         *
         * Puis effectue la connexion grâce à la méthode proposée par FireBase pour l'authentification.
         * Celle-ci vérifie que la tâche éffectuée est complète.
         *       - Si oui, renvoie sur l'activité Main(hub),
         *       - Si non, message d'erreur.*/

        ////////////////////////////////////////////////////////////////////////////////////////////

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = signInMail.getText().toString();
                final String pass = signInPass.getText().toString();

                //Si champ Email vide
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(), R.string.mail_empty, Toast.LENGTH_LONG).show();
                    errorTextViewLA.setText(R.string.mail_empty);
                    signInMail.setError("Error");
                    return;
                }

                //Si champ mdp vide
                if(TextUtils.isEmpty(pass)){
                    Toast.makeText(getApplicationContext(),R.string.pw_empty, Toast.LENGTH_LONG).show();
                    errorTextViewLA.setText(R.string.pw_empty);
                    return;
                }

                //Si tout est ok
                auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),R.string.error, Toast.LENGTH_LONG).show();
                            errorTextViewLA.setText(R.string.login_fail);
                        }else{
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });


            }
        });
        /* FIN : D'écoute sur le bouton de connexion */

        /* DEBUT : Lien qui permet d'aller vers l'activité Register en un click */
        ////////////////////////////////////////////////////////////////////////////////////////////

        /* Création d'une instance de l'activité register puis on lance l'activité RegisterActivity pour l'inscription utilisateur */

        ////////////////////////////////////////////////////////////////////////////////////////////
        logToReg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class); // Création d'une instance de l'activité register
                startActivity(intent); //On lance l'activité RegisterActivity pour register l'utilisateur
                finish();
            }
        });
        /* FIN : Lien qui envoie vers l'inscription utilisateur. */

        /* DEBUT : Lien qui permet d'aller vers l'activité Register en un click */
        ////////////////////////////////////////////////////////////////////////////////////////////

        /* Création d'une instance de l'activité Reset password puis on lance l'activité ResetPasswordActivity pour la récupération du mdp utilisateur */

        ////////////////////////////////////////////////////////////////////////////////////////////

        resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });
        /* FIN : Lien qui envoie l'utilisateur vers l'activité récupération du mdp. */

        /* DEBUT : Bouton de connexion avec Google */
        ////////////////////////////////////////////////////////////////////////////////////////////

        /* Création d'une fenêtre de connexion avec Google*/

        ////////////////////////////////////////////////////////////////////////////////////////////
        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
        /* FIN : Bouton de connexion avec Google */
    }

    /**
     * méthode permettant de vérifier le type de connexion et de récupération des données utilisateurs pour l'insérer dans la base de données de Firebase.
     * @param requestCode Code de requête qui permettra de vérifier le type de connexion
     * @param resultCode Le code résultat
     * @param data Données de l'utilisateur connecté
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                // ...
            }
        }

    }

    /**
     * Méthode qui permet l'authentification firebase avec google
     * @param idToken Token utilisé pour la connexion Google
     */
    private void firebaseAuthWithGoogle(String idToken) {
        final String username = getString(R.string.username).trim();;
        final String phoneN = getString(R.string.phoneNum);

        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //Si tout est ok
                                if(task.isSuccessful()){
                                    FirebaseUser user = auth.getCurrentUser();

                                    //Si l'utilisateur se connecte pour la première fois
                                    if(task.getResult().getAdditionalUserInfo().isNewUser()){

                                        //Récupération du mail et de l'id
                                        String email = user.getEmail();
                                        String uid = user.getUid();

                                        //Lorsque l'utilisateur est connecté, on stocke les informations utilisateur dans la base de données firebase en utilisant HashMap
                                        HashMap<Object,String> hashMap = new HashMap<>();

                                        //Transfert de l'information en hasmap
                                        hashMap.put("email",email);
                                        hashMap.put("uid", uid);
                                        // Les informations suivantes seront rajoutées grâce à l'édition de profil, name et phone ont des valeurs par défaut.
                                        hashMap.put("name", username);
                                        hashMap.put("phone", phoneN);
                                        hashMap.put("image", "");

                                        // Instance de la base de données firebase
                                        FirebaseDatabase database = FirebaseDatabase.getInstance();

                                        //Chemin de stockage des données utilisateur dans "Users"
                                        DatabaseReference reference = database.getReference("Users");

                                        // Ajout des données dans la base de données en Hashmap
                                        reference.child(uid).setValue(hashMap);
                                    }

                                    // Redirection vers l'activité principale
                                    Toast.makeText(LoginActivity.this, ""+user.getEmail(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    // Si cela ne fonctionne pas, affiche un message d'erreur
                                    Toast.makeText(LoginActivity.this, R.string.googleErr, Toast.LENGTH_SHORT).show();
                                    errorTextViewLA.setText(R.string.error);
                                }
                            }
                            //Si échec lors de l'appui sur le bouton
                        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Affichage d'erreur
                Toast.makeText(LoginActivity.this, ""+e.getMessage(),Toast.LENGTH_SHORT).show();
                errorTextViewLA.setText(R.string.error);
            }
        });
    }


}