package com.dayre.obscenerest;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "songs")
public class SongResult implements Serializable {
    @Id
    private long id;

    @Column(name = "tags")
    private String tags;

    @Column(name = "popularity")
    private long popularity;

    public SongResult(long id, String tags) {
        this.id = id;
        this.tags = tags;
        this.popularity = 0;
    }

    public SongResult(long id, String tags, long popularity) {
        this.id = id;
        this.tags = tags;
        this.popularity = popularity;
    }

    public SongResult() {
    }

    public long getId() {
        return id;
    }

    public String getTags() {
        return tags;
    }

    public long getPopularity() { return popularity;}

    public void setPopularity(Long _popularity) { popularity = _popularity;}
}