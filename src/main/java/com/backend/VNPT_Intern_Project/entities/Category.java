package com.backend.VNPT_Intern_Project.entities;

import jakarta.persistence.*;

@Entity
public class Category {
    @Id
    private String uuid_category;

    private String title;

    private String meta_title;

    private String slug;

    private String content;

    // Getters and Setters


    public String getUuid_category() {
        return uuid_category;
    }

    public void setUuid_category(String uuid_category) {
        this.uuid_category = uuid_category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMeta_title() {
        return meta_title;
    }

    public void setMeta_title(String meta_title) {
        this.meta_title = meta_title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
