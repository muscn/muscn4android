package com.awecode.muscn.model.simplexml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import lombok.Getter;

/**
 * Created by munnadroid on 6/2/17.
 */


@Getter
@Root(name = "item", strict = false)
public class Item extends RealmObject {

    @Element(name = "title", required = false)
    String title;


    @Path("link")
    @Text(required = false)
    String link;


    @Element(name = "description", required = false)
    String description;


    @Element(name = "pubDate", required = false)
    String pubDate;


    @Ignore
    @Element(name = "guid", required = false)
    Guid guid;

}