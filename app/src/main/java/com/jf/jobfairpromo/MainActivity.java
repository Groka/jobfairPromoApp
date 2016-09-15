package com.jf.jobfairpromo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {

    private static final String TAG = "com.jf.jobfairpromo";

    private Button dugme;
    private EditText etImePrezime, etEmail, etTelefon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Povezivanje sa UI

        etImePrezime = (EditText) findViewById(R.id.etImePrezime);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etTelefon = (EditText) findViewById(R.id.etTelefon);
        dugme = (Button) findViewById(R.id.button);

        // Kreiranje servisa

        final ApiInteface apiInteface = JFAPI.createService(ApiInteface.class);


        dugme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User(etImePrezime.getText().toString(), etEmail.getText().toString(), etTelefon.getText().toString());

                final Call<User> call = apiInteface.createUser(user);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        displayError(response.code());
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getBaseContext(), "Prijava uspješna !", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


    }

    private void displayError(int status){
        if(status == 409){
            new AlertDialog.Builder(this)
                    .setTitle("409")
                    .setMessage("Već postoji korisnik sa istom email adresom ili brojem telefona")
                    .setNeutralButton("Zatvori", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        else if(status == 451){
            new AlertDialog.Builder(this)
                    .setTitle("451")
                    .setMessage("Kod nije poslan na Vaš email. Molimo Vas provjerite ispravnost unesene email adrese.")
                    .setNeutralButton("Zatvori", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }
}
