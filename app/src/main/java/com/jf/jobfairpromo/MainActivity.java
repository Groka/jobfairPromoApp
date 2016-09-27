package com.jf.jobfairpromo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {

    private static final String TAG = "com.jf.jobfairpromo";

    private Button dugme;
    private EditText etImePrezime, etEmail, etTelefon;
    private TextView tvSlogan, tvCredits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Povezivanje sa UI


        tvSlogan = (TextView) findViewById(R.id.jfSlogan);
        tvCredits = (TextView) findViewById(R.id.credits);
        etImePrezime = (EditText) findViewById(R.id.etImePrezime);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etTelefon = (EditText) findViewById(R.id.etTelefon);
        dugme = (Button) findViewById(R.id.button);

        Typeface jfont = Typeface.createFromAsset(getAssets(), "fonts/RobotoSlab-Regular.ttf");
        tvSlogan.setTypeface(jfont);
        tvCredits.setTypeface(jfont);
        etImePrezime.setTypeface(jfont);
        etEmail.setTypeface(jfont);
        etTelefon.setTypeface(jfont);
        dugme.setTypeface(jfont);

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
                        if(etImePrezime.getText().toString().isEmpty() ||
                                etTelefon.getText().toString().isEmpty() ||
                                etEmail.getText().toString().isEmpty())
                            displayError(421);
                        else displayError(response.code());
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
                    .setTitle("Postojeci nalog")
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
                    .setTitle("Email error")
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
        else if(status == 421){
            new AlertDialog.Builder(this)
                    .setTitle("Nedostaju podaci")
                    .setMessage("Morate unijeti sve podatke.")
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
