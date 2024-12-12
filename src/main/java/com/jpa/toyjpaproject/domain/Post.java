package com.jpa.toyjpaproject.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Post {
    @Id
    private int id;
}
