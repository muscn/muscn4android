package com.awecode.muscn.model.http.signin;

import com.awecode.muscn.model.http.partners.PartnersResult;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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
    @SerializedName("id")
    String userId;
    @SerializedName("pickup_locations")
    public List<PartnersResult> pickupLocations = null;
}
