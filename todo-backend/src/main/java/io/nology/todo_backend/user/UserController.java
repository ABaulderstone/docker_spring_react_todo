package io.nology.todo_backend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Authentication authenticationObj = SecurityContextHolder.getContext().getAuthentication();
        String currentId = (String) authenticationObj.getPrincipal();
        logger.info("Testing info log");
        logger.error("Testing error log");
        User currentUser = this.userService.loadById(Long.parseLong(currentId))
                .orElseThrow(() -> new Exception("No user"));
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }

}
