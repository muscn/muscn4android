package com.awecode.muscn.model.simplexml;

/**
 * Created by munnadroid on 6/2/17.
 */

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import lombok.Getter;

@Getter
@Root(name = "rss", strict = false)
public class Rss {

    @Element(name = "channel", required = false)
    Channel channel;


    @Attribute(name = "atom", required = false)
    String atom;


    @Attribute(name = "version", required = false)
    String version;

}