package com.jf.jobfairpromo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tarik on 9/15/2016.
 */

public class User {
    @SerializedName("ImePrezime")
    private String imePrezime;
    @SerializedName("Email")
    private String email;
    @SerializedName("Telefon")
    private String telefon;

    public User(String imePrezime, String email, String telefon){
        this.imePrezime = imePrezime;
        this.email = email;
        this.telefon = telefon;
    }
}
