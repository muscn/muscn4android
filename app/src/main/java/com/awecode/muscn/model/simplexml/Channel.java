package com.awecode.muscn.model.simplexml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

import java.util.List;

import lombok.Getter;

/**
 * Created by munnadroid on 6/2/17.
 */

@Getter
@Root(name = "channel",strict = false)
public class Channel {

    @Element(name = "title", required = false)
    String title;


    @Path("link")
    @Text(required = false)
    String link;


    @Element(name = "description", required = false)
    String description;


    @Element(name = "copyright", required = false)
    String copyright;


    @Element(name = "pubDate", required = false)
    String pubDate;


    @ElementList(name = "item", inline = true, required = false)
    List<Item> item;
}

