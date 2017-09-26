package com.awecode.muscn.model.membership;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by surensth on 9/17/17.
 */
@Getter
@Setter
public class MembershipResponse {
    Integer id;
    String date_of_birth;
    String gender;
    String temporary_address;
    String permanent_address;
    String mobile;
    String telephone;
    String identification_file;
}

