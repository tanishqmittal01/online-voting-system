package online_voting_system.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import online_voting_system.entity.Candidate;
import online_voting_system.service.CandidateService;

@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @PostMapping("/election/{electionId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Candidate> addCandidate(
            @PathVariable Long electionId,
            @RequestBody Candidate candidate) {

        Candidate savedCandidate =
                candidateService.addCandidate(electionId, candidate);

        return ResponseEntity.ok(savedCandidate);
    }

    @GetMapping("/election/{electionId}")
    public ResponseEntity<List<Candidate>> getCandidatesByElection(
            @PathVariable Long electionId) {

        return ResponseEntity.ok(
                candidateService.getCandidatesByElection(electionId)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Candidate> updateCandidate(
            @PathVariable Long id,
            @RequestBody Candidate candidate) {

        Candidate updatedCandidate =
                candidateService.updateCandidate(id, candidate);

        return ResponseEntity.ok(updatedCandidate);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCandidate(
            @PathVariable Long id) {

        candidateService.deleteCandidate(id);

        return ResponseEntity.ok("Candidate deleted successfully");
    }
}