package online_voting_system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import online_voting_system.entity.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    boolean existsByVoterIdAndElectionId(
            Long voterId,
            Long electionId
    );
    boolean existsByVoterId(Long voterId);

    Optional<Vote> findByVoterIdAndElectionId(
            Long voterId,
            Long electionId
    );

    long countByCandidateId(Long candidateId);

    long countByElectionId(Long electionId);
}
