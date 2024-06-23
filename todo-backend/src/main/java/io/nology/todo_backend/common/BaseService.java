package io.nology.todo_backend.common;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class BaseService {

    public Long getCurrentUserId() {
        Authentication authenticationObj = SecurityContextHolder.getContext().getAuthentication();
        String currentId = (String) authenticationObj.getPrincipal();
        return Long.parseLong(currentId);
    }
}
