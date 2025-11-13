package com.cotrip.participant;


import com.cotrip.participant.DTO.ParticipantCreatedResponse;
import com.cotrip.participant.DTO.ParticipantData;
import com.cotrip.trip.TripModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ParticipantService {

  @Autowired
  private ParticipantRepository repository;

  public void registerParticipantsToEvent(List<String> emails, TripModel trip) {
    List<ParticipantModel> res = emails
      .stream()
      .map(email -> new ParticipantModel(email, trip))
      .toList();

    this.repository.saveAll(res);
  };

  public ParticipantCreatedResponse registerParticipantToEvent(String email, TripModel tripModel) {
    ParticipantModel newParticipantModel = new ParticipantModel(email, tripModel);
    this.repository.save(newParticipantModel);

    return new ParticipantCreatedResponse(newParticipantModel.getId());
  }


  public void triggerConfirmationEmailToParticipants(UUID tripId) {};
  public void triggerConfirmationEmailToParticipant(String email) {};
  public List<ParticipantData> getAllParticipantsByTripId(UUID tripId) {
    return this
      .repository
      .findByTripId(tripId)
      .stream()
      .map(participant -> new ParticipantData(participant.getId(), participant.getName(), participant.getEmail(), participant.getIsConfirmed()))
      .toList();
  }

}

