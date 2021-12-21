package com.stojcevski.examproject.controllers;

import com.stojcevski.examproject.models.Candidate;
import com.stojcevski.examproject.models.Party;
import com.stojcevski.examproject.repositories.CandidateRepository;
import com.stojcevski.examproject.repositories.PartyRepository;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Candidates {

    @Autowired
    CandidateRepository candidates;
    @Autowired
    PartyRepository parties;

    //Get all candidates
    @GetMapping("/candidates")
    public ResponseEntity<Object> getAllCandidates() {
        List<Candidate> allCandidates = candidates.findAll();

        List<JSONObject> entities = new ArrayList<JSONObject>();
        for (Candidate candidate : allCandidates) {
            System.out.println(candidate.getParty().getName());
            JSONObject entity = new JSONObject();
            entity.put("id", candidate.getId());
            entity.put("name", candidate.getName());
            entity.put("municipality", candidate.getMunicipality());
            entity.put("party", candidate.getParty().getName());
            entities.add(entity);
        }


        return new ResponseEntity<Object>(entities, HttpStatus.OK);
    }

    // Get all candidates from name of party
    @GetMapping("/candidates/{party}")
    public List<Candidate> getCandidatesInParty(@PathVariable("party") String name) {
        return candidates.findCandidatesByParty(parties.findPartyByName(name));
    }

    // Add a new candidate
    @PostMapping("/candidate/{partyId}")
    public Candidate addCandidate(@RequestBody Candidate addCandidate, @PathVariable Long partyId) {
        Party politicalParty = parties.findById(partyId).get();

        return candidates.save(new Candidate(addCandidate.getName(), addCandidate.getMunicipality(), politicalParty));
    }

    // Update all attributes regarding the candidate (entire resource)
    @PutMapping("/candidate/{id}")
    public String updateCandidateById(@PathVariable Long id, @RequestBody Candidate candidateToUpdateWith) {
        if (candidates.existsById(id)) {
            candidateToUpdateWith.setId(id);
            candidates.save(candidateToUpdateWith);
            return "Candidate has been updated.";
        } else {
            return "Candidate was not found.";
        }
    }

    // Update specific attributes regarding the candidate (parts of the resource)
    @PatchMapping("/candidate/{id}")
    public String patchCandidate(@PathVariable Long id, @RequestBody Candidate candidateToPatchWith) {
        return candidates.findById(id).map(foundCandidate -> {

            if (candidateToPatchWith.getName() != null)
                foundCandidate.setName(candidateToPatchWith.getName());

            if (candidateToPatchWith.getMunicipality() != null)
                foundCandidate.setMunicipality(candidateToPatchWith.getMunicipality());

            candidates.save(foundCandidate);
            return "The candidate has been patched.";
        }).orElse("The candidate was not found.");

    }

    // Delete a candidate
    @DeleteMapping("/candidate/{id}")
    public void deleteCandidate(@PathVariable Long id) {
        candidates.deleteById(id);
    }
}

