package online_voting_system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import online_voting_system.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByStudentId(String studentId);

    Optional<User> findByEmail(String email);
}