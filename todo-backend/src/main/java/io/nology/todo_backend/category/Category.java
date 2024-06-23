package io.nology.todo_backend.category;

import io.nology.todo_backend.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {
    @Column
    private String name;
    @Column
    private String color;

}
