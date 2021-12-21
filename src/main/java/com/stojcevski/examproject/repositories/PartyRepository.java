package com.stojcevski.examproject.repositories;

import com.stojcevski.examproject.models.Party;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyRepository extends JpaRepository<Party, Long> {

    Party findPartyByName(String politicalParty);
}