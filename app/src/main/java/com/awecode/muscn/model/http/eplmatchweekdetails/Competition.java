
package com.awecode.muscn.model.http.eplmatchweekdetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Competition {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("short_name")
    @Expose
    private String shortName;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("order")
    @Expose
    private Integer order;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Competition() {
    }

    /**
     * 
     * @param id
     * @param order
     * @param name
     * @param slug
     * @param shortName
     */
    public Competition(Integer id, String name, String shortName, String slug, Integer order) {
        super();
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.slug = slug;
        this.order = order;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

}
