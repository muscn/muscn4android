package com.awecode.muscn.model.http.signin;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by surensth on 5/24/17.
 */

@Setter
@Getter
public class SignInData {
    String username; //email key is changed to username but pass email data here
    String password;
}
