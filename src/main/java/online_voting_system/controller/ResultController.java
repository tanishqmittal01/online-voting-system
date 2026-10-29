package online_voting_system.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import online_voting_system.entity.Candidate;
import online_voting_system.service.ResultService;

@RestController
@RequestMapping("/api/results")
public class ResultController {

    private final ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping("/election/{electionId}")
    public ResponseEntity<List<ResultResponse>> getResults(
            @PathVariable Long electionId) {

        List<Candidate> candidates =
                resultService.getCandidatesWithResults(electionId);

        List<ResultResponse> results = new ArrayList<>();

        for (Candidate candidate : candidates) {

            long voteCount =
                    resultService.getVoteCountForCandidate(candidate.getId());

            results.add(
                    new ResultResponse(
                            candidate.getId(),
                            candidate.getName(),
                            candidate.getParty(),
                            candidate.getSymbol(),
                            voteCount
                    )
            );
        }

        return ResponseEntity.ok(results);
    }

    @GetMapping("/election/{electionId}/total")
    public ResponseEntity<Long> getTotalVotes(
            @PathVariable Long electionId) {

        return ResponseEntity.ok(
                resultService.getTotalVotes(electionId)
        );
    }

    public static class ResultResponse {

        private Long candidateId;
        private String candidateName;
        private String party;
        private String symbol;
        private long voteCount;

        public ResultResponse(
                Long candidateId,
                String candidateName,
                String party,
                String symbol,
                long voteCount) {

            this.candidateId = candidateId;
            this.candidateName = candidateName;
            this.party = party;
            this.symbol = symbol;
            this.voteCount = voteCount;
        }

        public Long getCandidateId() {
            return candidateId;
        }

        public String getCandidateName() {
            return candidateName;
        }

        public String getParty() {
            return party;
        }

        public String getSymbol() {
            return symbol;
        }

        public long getVoteCount() {
            return voteCount;
        }
    }
}