package io.nology.todo_backend.category;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.nology.todo_backend.common.BaseEntity;
import io.nology.todo_backend.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "categories")
@Getter
@Setter
public class Category extends BaseEntity {
    @Column
    private String name;
    @Column
    private String color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore()
    private User user;

    public Category() {
    }

    @Override
    public String toString() {
        return "Category [name=" + name + ", color=" + color + ", userID=" + user.getId() + "]";
    }

}
