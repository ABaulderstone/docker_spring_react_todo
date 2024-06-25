package io.nology.todo_backend.todo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.nology.todo_backend.category.Category;
import io.nology.todo_backend.common.BaseEntity;
import io.nology.todo_backend.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "todos")
@Getter
@Setter
public class Todo extends BaseEntity {

    @Column(nullable = false)
    private String title;
    @Column()
    private Date dueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore()
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "category_id", nullable = true)
    private Category category;

    @Column(nullable = false)
    private boolean isComplete = false;

}
