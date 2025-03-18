package com.woromedia.api.task.service;

import com.woromedia.api.task.entity.User;
import com.woromedia.api.task.payload.SignUpDTO;
import com.woromedia.api.task.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList; // Add this import
import java.util.Optional;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors; // Add this import

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveUser(SignUpDTO signUpDTO) {
        // Implementation for saving user
    }

    @Override
    public User findUserByEmail(String email) {
        // Implementation for finding user by email
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        // Implementation for getting all users
        return null;
    }

    @Override
    public void addContact(Long userId, Long contactId) {
        // Implementation for adding a contact
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<User> contactOptional = userRepository.findById(contactId);
        
        if (userOptional.isPresent() && contactOptional.isPresent()) {
            User user = userOptional.get();
            User contact = contactOptional.get();
            user.getContacts().add(contact);
            userRepository.save(user);
        }
    }

    public List<Long> getAllContacts(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        
        if (userOptional.isEmpty()) {
            return Collections.emptyList();
        }
        
        User user = userOptional.get();

        // Getting contacts of the user
        Set<Long> contactIds = user.getContacts().stream()
            .map(User::getId)
            .collect(Collectors.toSet());

        // Getting users who have the userId in their contacts list
        List<User> reverseContacts = userRepository.findByContactsContaining(user);
        for (User reverseUser : reverseContacts) {
            contactIds.add(reverseUser.getId());
        }

        return new ArrayList<>(contactIds);
    }
}
