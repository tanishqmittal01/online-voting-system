package online_voting_system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import online_voting_system.entity.Vote;
import online_voting_system.entity.User;
import online_voting_system.repository.UserRepository;
import online_voting_system.service.VoteService;

@RestController
@RequestMapping("/api/votes")
public class VoteController {

    private final VoteService voteService;
    private final UserRepository userRepository;

    public VoteController(
            VoteService voteService,
            UserRepository userRepository) {

        this.voteService = voteService;
        this.userRepository = userRepository;
    }

    @PostMapping
    @PreAuthorize("hasRole('VOTER')")
    public ResponseEntity<Vote> castVote(
            @RequestParam Long candidateId,
            @RequestParam Long electionId,
            Authentication authentication) {

        User user = userRepository
                .findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new RuntimeException("Logged-in user not found"));

        Vote vote = voteService.castVote(
                user.getId(),
                candidateId,
                electionId
        );

        return ResponseEntity.ok(vote);
    }
}