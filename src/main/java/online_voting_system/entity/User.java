package online_voting_system.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "app_users")
public class User {


@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

private String name;

@Column(unique = true, nullable = false)
private String email;

@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
@Column(nullable = false)
private String password;

@Column(unique = true, nullable = false)
private String voterId;

@Column(unique = true, nullable = false)
private String studentId;

private String role;

public User() {
}

public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}

public String getEmail() {
    return email;
}

public void setEmail(String email) {
    this.email = email;
}

public String getPassword() {
    return password;
}

public void setPassword(String password) {
    this.password = password;
}

public String getVoterId() {
    return voterId;
}

public void setVoterId(String voterId) {
    this.voterId = voterId;
}

public String getStudentId() {
    return studentId;
}

public void setStudentId(String studentId) {
    this.studentId = studentId;
}

public String getRole() {
    return role;
}

public void setRole(String role) {
    this.role = role;
}


}
