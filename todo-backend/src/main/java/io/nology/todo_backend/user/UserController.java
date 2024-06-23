package io.nology.todo_backend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/current")
    public ResponseEntity<User> currentUser() throws NumberFormatException, Exception {

        logger.info("Testing info log");
        logger.error("Testing error log");
        User currentUser = this.userService.currentUser();
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }

}
