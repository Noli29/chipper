package com.noli.chipper.rest.models;

import com.google.gson.annotations.SerializedName;

public class UserResponse {

    @SerializedName("id")
    public int id;

    @SerializedName("auth_token")
    public String token;

    @SerializedName("name")
    public String name;

    @SerializedName("surname")
    public String surname;

    @SerializedName("email")
    public String email;

    @SerializedName("avatar_file_name")
    public String userpicfn;

    @SerializedName("avatar")
    public String userpic;

    public UserResponse() {
    }

    public UserResponse(int id, String token, String name, String surname, String email, String userpicfn, String userpic) {
        this.id = id;
        this.token = token;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.userpicfn = userpicfn;
        this.userpic = userpic;
    }

    public UserResponse(String userpicfn) {
        this.userpicfn = userpicfn;
    }

    public int getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getUserpic() {
        return userpic;
    }
}
