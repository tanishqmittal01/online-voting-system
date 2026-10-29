package online_voting_system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import online_voting_system.entity.Election;
import online_voting_system.exception.ResourceNotFoundException;
import online_voting_system.repository.ElectionRepository;

@Service
public class ElectionService {

    private final ElectionRepository electionRepository;

    public ElectionService(ElectionRepository electionRepository) {
        this.electionRepository = electionRepository;
    }

    public Election createElection(Election election) {
        return electionRepository.save(election);
    }

    public List<Election> getAllElections() {
        return electionRepository.findAll();
    }

    public Election getElectionById(Long id) {

        return electionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Election not found"));
    }

    public Election updateElection(Long id, Election election) {

        Election existingElection = electionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Election not found"));

        existingElection.setName(election.getName());
        existingElection.setDescription(election.getDescription());
        existingElection.setStartDate(election.getStartDate());
        existingElection.setEndDate(election.getEndDate());
        existingElection.setStatus(election.getStatus());

        return electionRepository.save(existingElection);
    }

    public void deleteElection(Long id) {

        Election existingElection = electionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Election not found"));

        electionRepository.delete(existingElection);
    }
}