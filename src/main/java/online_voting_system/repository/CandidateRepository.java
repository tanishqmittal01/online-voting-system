package online_voting_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import online_voting_system.entity.Candidate;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    List<Candidate> findByElectionId(Long electionId);
}
