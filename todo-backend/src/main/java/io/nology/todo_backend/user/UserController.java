package io.nology.todo_backend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/current")
    public String currentUser() {
        Authentication authenticationObj = SecurityContextHolder.getContext().getAuthentication();
        String currentId = (String) authenticationObj.getPrincipal();
        return String.format("The current user id is %s", currentId);
    }

}
