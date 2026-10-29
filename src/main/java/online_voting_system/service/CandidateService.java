package online_voting_system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import online_voting_system.entity.Candidate;
import online_voting_system.entity.Election;
import online_voting_system.exception.ResourceNotFoundException;
import online_voting_system.repository.CandidateRepository;
import online_voting_system.repository.ElectionRepository;

@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final ElectionRepository electionRepository;

    public CandidateService(
            CandidateRepository candidateRepository,
            ElectionRepository electionRepository) {

        this.candidateRepository = candidateRepository;
        this.electionRepository = electionRepository;
    }

    public Candidate addCandidate(
            Long electionId,
            Candidate candidate) {

        Election election = electionRepository.findById(electionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Election not found"));

        candidate.setElection(election);

        return candidateRepository.save(candidate);
    }

    public List<Candidate> getCandidatesByElection(Long electionId) {

        electionRepository.findById(electionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Election not found"));

        return candidateRepository.findByElectionId(electionId);
    }

    public Candidate updateCandidate(
            Long id,
            Candidate candidate) {

        Candidate existingCandidate =
                candidateRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Candidate not found"));

        existingCandidate.setName(candidate.getName());
        existingCandidate.setParty(candidate.getParty());
        existingCandidate.setSymbol(candidate.getSymbol());

        return candidateRepository.save(existingCandidate);
    }

    public void deleteCandidate(Long id) {

        Candidate existingCandidate =
                candidateRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Candidate not found"));

        candidateRepository.delete(existingCandidate);
    }
}