package com.awecode.muscn.model.http.signin;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by surensth on 5/24/17.
 */

@Getter
@Setter
public class SignInSuccessData {
    String token;
    String status;
    String email;
    @SerializedName("full_name")
    String fullName;
    String mobile;
    @SerializedName("membership_fee")
    String membershipFee;
    @SerializedName("user_id")
    String userId;
}
