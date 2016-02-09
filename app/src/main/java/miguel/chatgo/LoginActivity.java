package miguel.chatgo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameField, passwordField;
    private ProgressBar progressBar;
    private TextView titleField,subtitleField;
    public AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        titleField = (TextView) findViewById(R.id.title);
        subtitleField =(TextView) findViewById(R.id.subtitle);
        usernameField = (EditText) findViewById(R.id.usernameField);
        passwordField = (EditText) findViewById(R.id.passwordField);
        progressBar = (ProgressBar) findViewById(R.id.progressBarLogin);
        progressBar.setVisibility(View.INVISIBLE);
        //getSupportActionBar().hide();
        Typeface font = Typeface.createFromAsset(getApplicationContext().getAssets(), "Lighthouse_PersonalUse.ttf");
        titleField.setTypeface(font);
        subtitleField.setTypeface(font);
        usernameField.setTypeface(font);
        passwordField.setTypeface(font);


    }



    public boolean logearse(View v) throws ParseException {
        if(checkeoCampos()){
            progressBar.setVisibility(View.VISIBLE);
            ParseUser.logInInBackground(usernameField.getText().toString(), passwordField.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    if(e==null){
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        cleanEditText();
                    }else{
                        generarDialogo(e.getMessage()).show();
                    }
                }
            });
            //ParseUser.logIn(usernameField.getText().toString(), passwordField.getText().toString());
            return true;
        }else{
            generarDialogo("Introduce los campos").show();
            return false;
        }
    }


    public boolean checkeoCampos() {
        if (!usernameField.getText().toString().equals("") && !passwordField.getText().toString().equals(""))
            return true;
        return false;
    }


    public Dialog generarDialogo(String error) {
        builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage(error);
        builder.setTitle("Atención");
        builder.setCancelable(true);
        return builder.create();
    }

    public void signUpOnClick(View v){
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void cleanEditText(){
        usernameField.setText("");
        passwordField.setText("");

    }

    public AlertDialog getLastDialog(){
        return builder.show();
    }
}
