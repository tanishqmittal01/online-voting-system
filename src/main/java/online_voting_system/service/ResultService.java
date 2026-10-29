package online_voting_system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import online_voting_system.entity.Candidate;
import online_voting_system.exception.ResourceNotFoundException;
import online_voting_system.repository.CandidateRepository;
import online_voting_system.repository.ElectionRepository;
import online_voting_system.repository.VoteRepository;

@Service
public class ResultService {

    private final CandidateRepository candidateRepository;
    private final ElectionRepository electionRepository;
    private final VoteRepository voteRepository;

    public ResultService(
            CandidateRepository candidateRepository,
            ElectionRepository electionRepository,
            VoteRepository voteRepository) {

        this.candidateRepository = candidateRepository;
        this.electionRepository = electionRepository;
        this.voteRepository = voteRepository;
    }

    public List<Candidate> getCandidatesWithResults(Long electionId) {

        electionRepository.findById(electionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Election not found"));

        return candidateRepository.findByElectionId(electionId);
    }

    public long getVoteCountForCandidate(Long candidateId) {

        return voteRepository.countByCandidateId(candidateId);
    }

    public long getTotalVotes(Long electionId) {

        electionRepository.findById(electionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Election not found"));

        return voteRepository.countByElectionId(electionId);
    }
}