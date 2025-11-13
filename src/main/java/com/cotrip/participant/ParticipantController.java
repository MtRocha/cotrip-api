package com.cotrip.participant;


import com.cotrip.participant.DTO.ParticipantRequestPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/participants")
public class ParticipantController {

  @Autowired
  private ParticipantRepository repository;


  @PostMapping("/{id}/confirm")
  public ResponseEntity<String> confirmParticipation(@PathVariable UUID id, @RequestBody ParticipantRequestPayload payload) {
    Optional<ParticipantModel> paticipant = this.repository.findById(id);

    if(paticipant.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    ParticipantModel rawParticipantModel = paticipant.get();
    rawParticipantModel.setIsConfirmed(true);
    rawParticipantModel.setName(payload.name());
    this.repository.save(rawParticipantModel);

    return ResponseEntity.ok("Participation confirmed for participant ID: " + id);
  };
}
