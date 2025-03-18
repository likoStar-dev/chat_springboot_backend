package com.woromedia.api.task.controller;

import com.woromedia.api.task.dto.ContactDto;
import com.woromedia.api.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/{userId}/contacts")
    public ResponseEntity<Void> addContact(@PathVariable Long userId, @RequestBody ContactDto contactDto) {
        userService.addContact(userId, contactDto.getUserId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/contacts")
    public ResponseEntity<List<Long>> getAllContacts(@PathVariable Long userId) {
        List<Long> contacts = userService.getAllContacts(userId);
        return ResponseEntity.ok(contacts);
    }
}
