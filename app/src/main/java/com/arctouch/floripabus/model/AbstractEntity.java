package com.arctouch.floripabus.model;

import java.io.Serializable;


public abstract class AbstractEntity implements Serializable {

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
