package online_voting_system.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import online_voting_system.entity.User;
import online_voting_system.exception.BadRequestException;
import online_voting_system.exception.ConflictException;
import online_voting_system.exception.ResourceNotFoundException;
import online_voting_system.repository.UserRepository;
import online_voting_system.repository.VoteRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VoteRepository voteRepository;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            VoteRepository voteRepository) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.voteRepository = voteRepository;
    }

    public User registerUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ConflictException("Email already registered");
        }

        if (user.getStudentId() == null ||
                user.getStudentId().trim().isEmpty()) {

            throw new BadRequestException("Student ID is required");
        }

        if (userRepository.existsByStudentId(user.getStudentId())) {
            throw new ConflictException("Student ID already registered");
        }

        String voterId = "VOTER" + System.currentTimeMillis();

        user.setVoterId(voterId);

        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        // Public registration can only create VOTER accounts.
        // ADMIN accounts must be created/assigned separately.
        user.setRole("VOTER");

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
    }

    public User updateUser(Long id, User user) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        if (user.getStudentId() != null &&
                !user.getStudentId().trim().isEmpty() &&
                !user.getStudentId().equals(existingUser.getStudentId())) {

            if (userRepository.existsByStudentId(user.getStudentId())) {
                throw new ConflictException(
                        "Student ID already registered");
            }

            existingUser.setStudentId(user.getStudentId());
        }

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setRole(user.getRole());

        if (user.getPassword() != null &&
                !user.getPassword().isEmpty()) {

            existingUser.setPassword(
                    passwordEncoder.encode(user.getPassword())
            );
        }

        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        if (voteRepository.existsByVoterId(id)) {

            throw new ConflictException(
                    "Cannot delete user because the user has already voted");
        }

        userRepository.delete(existingUser);
    }
}