package com.awecode.muscn.model.simplexml;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import lombok.Getter;

/**
 * Created by munnadroid on 6/2/17.
 */


@Getter
@Root(name = "guid", strict = false)
public class Guid {

    @Attribute(name = "isPermaLink", required = false)
    Boolean isPermaLink;

}
