package io.nology.todo_backend.user;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.nology.todo_backend.category.Category;
import io.nology.todo_backend.common.BaseEntity;
import io.nology.todo_backend.todo.Todo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    @JsonIgnore()
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore()
    private List<Todo> todos;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore()
    private List<Category> categories;

}
