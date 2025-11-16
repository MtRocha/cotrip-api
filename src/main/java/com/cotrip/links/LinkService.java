package com.cotrip.links;

import com.cotrip.links.DTO.LinkData;
import com.cotrip.links.DTO.LinkRequestPayload;
import com.cotrip.links.DTO.LinkResponse;
import com.cotrip.trip.TripModel;
import com.cotrip.trip.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LinkService {

  @Autowired
  private LinkRepository repository;
  @Autowired
  private TripRepository tripRepository;

  @CacheEvict(key="#tripId" , value="Link")
  public void createLink(LinkRequestPayload payload, UUID tripId) {

      var trip = tripRepository.findById(tripId).orElse(null);

      LinkModel newLink = new LinkModel(payload.title(), payload.url(), trip);

    this
      .repository
      .save(newLink);
  }
@Cacheable("Link")
  public List<LinkData> getAllLinkForTrip(UUID trip_id) {
    return this
      .repository
      .findByTripId(trip_id)
      .stream()
      .map(activity -> new LinkData(activity.getId(), activity.getTitle(), activity.getUrl()))
      .toList();
  }
}
