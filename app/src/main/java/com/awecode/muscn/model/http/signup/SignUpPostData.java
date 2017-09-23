package com.awecode.muscn.model.http.signup;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by surensth on 5/23/17.
 */
@Getter
@Setter
public class SignUpPostData {
    @SerializedName("full_name")
    String fullName;
    String email;
    String password;

    @SerializedName("date_of_birth")
    String dateOfBirth;
    String address;
    String mobile;
}
