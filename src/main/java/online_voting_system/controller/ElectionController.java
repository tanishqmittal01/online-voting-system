package online_voting_system.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import online_voting_system.entity.Election;
import online_voting_system.service.ElectionService;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/elections")
public class ElectionController {

    private final ElectionService electionService;

    public ElectionController(ElectionService electionService) {
        this.electionService = electionService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Election> createElection(
            @RequestBody Election election) {

        Election savedElection = electionService.createElection(election);

        return ResponseEntity.ok(savedElection);
    }

    @GetMapping
    public ResponseEntity<List<Election>> getAllElections() {

        return ResponseEntity.ok(
                electionService.getAllElections()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Election> getElectionById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                electionService.getElectionById(id)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Election> updateElection(
            @PathVariable Long id,
            @RequestBody Election election) {

        Election updatedElection =
                electionService.updateElection(id, election);

        return ResponseEntity.ok(updatedElection);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteElection(
            @PathVariable Long id) {

        electionService.deleteElection(id);

        return ResponseEntity.ok("Election deleted successfully");
    }
}