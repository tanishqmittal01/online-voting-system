package online_voting_system.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import online_voting_system.entity.Candidate;
import online_voting_system.entity.Election;
import online_voting_system.entity.User;
import online_voting_system.entity.Vote;
import online_voting_system.exception.BadRequestException;
import online_voting_system.exception.ConflictException;
import online_voting_system.exception.ResourceNotFoundException;
import online_voting_system.repository.CandidateRepository;
import online_voting_system.repository.ElectionRepository;
import online_voting_system.repository.UserRepository;
import online_voting_system.repository.VoteRepository;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final CandidateRepository candidateRepository;
    private final ElectionRepository electionRepository;

    public VoteService(
            VoteRepository voteRepository,
            UserRepository userRepository,
            CandidateRepository candidateRepository,
            ElectionRepository electionRepository) {

        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.candidateRepository = candidateRepository;
        this.electionRepository = electionRepository;
    }

    public Vote castVote(
            Long voterId,
            Long candidateId,
            Long electionId) {

        User voter = userRepository.findById(voterId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Voter not found"));

        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Candidate not found"));

        Election election = electionRepository.findById(electionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Election not found"));

        LocalDateTime now = LocalDateTime.now();

        if (now.isBefore(election.getStartDate())) {
            throw new BadRequestException(
                    "Voting has not started yet");
        }

        if (now.isAfter(election.getEndDate())) {
            throw new BadRequestException(
                    "Voting has ended");
        }

        if (!"ONGOING".equalsIgnoreCase(election.getStatus())) {
            throw new BadRequestException(
                    "Voting is not currently open");
        }

        if (voteRepository.existsByVoterIdAndElectionId(
                voterId, electionId)) {

            throw new ConflictException(
                    "Voter has already voted in this election");
        }

        if (!candidate.getElection().getId().equals(electionId)) {

            throw new BadRequestException(
                    "Candidate does not belong to this election");
        }

        Vote vote = new Vote();

        vote.setVoter(voter);
        vote.setCandidate(candidate);
        vote.setElection(election);
        vote.setVotedAt(now);

        return voteRepository.save(vote);
    }
}