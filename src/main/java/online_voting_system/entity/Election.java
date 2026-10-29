package online_voting_system.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "elections")
public class Election {


@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Column(nullable = false)
private String name;

private String description;

@Column(nullable = false)
private LocalDateTime startDate;

@Column(nullable = false)
private LocalDateTime endDate;

@Column(nullable = false)
private String status;

// Default constructor
public Election() {
}

// Get ID
public Long getId() {
    return id;
}

// Set ID
public void setId(Long id) {
    this.id = id;
}

// Get Name
public String getName() {
    return name;
}

// Set Name
public void setName(String name) {
    this.name = name;
}

// Get Description
public String getDescription() {
    return description;
}

// Set Description
public void setDescription(String description) {
    this.description = description;
}

// Get Start Date
public LocalDateTime getStartDate() {
    return startDate;
}

// Set Start Date
public void setStartDate(LocalDateTime startDate) {
    this.startDate = startDate;
}

// Get End Date
public LocalDateTime getEndDate() {
    return endDate;
}

// Set End Date
public void setEndDate(LocalDateTime endDate) {
    this.endDate = endDate;
}

// Get Status
public String getStatus() {
    return status;
}

// Set Status
public void setStatus(String status) {
    this.status = status;
}


}
