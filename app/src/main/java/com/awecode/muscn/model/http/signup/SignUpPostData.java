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
    String username;
    String email;
    String password;
}
