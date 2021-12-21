package com.stojcevski.examproject.repositories;

import com.stojcevski.examproject.models.Candidate;
import com.stojcevski.examproject.models.Party;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    List<Candidate> findCandidatesByParty(Party politicalParty);
}
