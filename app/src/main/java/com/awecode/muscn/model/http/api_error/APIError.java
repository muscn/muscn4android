package com.awecode.muscn.model.http.api_error;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by surensth on 5/25/17.
 */

@Getter
@Setter
public class APIError {
    private String ErrorDescription;
    private List<String> non_field_errors;
    private String error;
    private String detail;
}
