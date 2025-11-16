package com.cotrip.participant;


import com.cotrip.participant.DTO.ParticipantCreatedResponse;
import com.cotrip.participant.DTO.ParticipantData;
import com.cotrip.trip.DTO.TripGetDTO;
import com.cotrip.trip.TripModel;
import com.cotrip.trip.TripRepository;
import com.cotrip.trip.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ParticipantService {

  @Autowired
  private ParticipantRepository repository;

  @Autowired
  private TripRepository tripRepository;

  public void registerParticipantsToEvent(List<String> emails, TripModel trip) {
    List<ParticipantModel> res = emails
      .stream()
      .map(email -> new ParticipantModel(email, trip))
      .toList();

    this.repository.saveAll(res);
  };
    @CacheEvict(key="#tripId" , value="Participant")
  public ParticipantCreatedResponse registerParticipantToEvent(String email, UUID tripId) {

    TripModel tripModel = this.tripRepository.findById(tripId).orElse(null);

    ParticipantModel newParticipantModel = new ParticipantModel(email, tripModel);
    this.repository.save(newParticipantModel);

    return new ParticipantCreatedResponse(newParticipantModel.getId());
  }


  public void triggerConfirmationEmailToParticipants(UUID tripId) {};
  public void triggerConfirmationEmailToParticipant(String email) {};
  @Cacheable("Participant")
  public List<ParticipantData> getAllParticipantsByTripId(UUID tripId) {
    return this
      .repository
      .findByTripId(tripId)
      .stream()
      .map(participant -> new ParticipantData(participant.getId(), participant.getName(), participant.getEmail(), participant.getIsConfirmed()))
      .toList();
  }

}

