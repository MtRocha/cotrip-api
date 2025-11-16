package com.cotrip.links;

import com.cotrip.links.DTO.LinkData;
import com.cotrip.links.DTO.LinkRequestPayload;
import com.cotrip.links.DTO.LinkResponse;
import com.cotrip.trip.TripModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LinkService {

  @Autowired
  private LinkRepository repository;

  public void createLink(LinkRequestPayload payload, TripModel trip) {
    LinkModel newLink = new LinkModel(payload.title(), payload.url(), trip);

    this
      .repository
      .save(newLink);
  }

  public List<LinkData> getAllLinkForTrip(UUID trip_id) {
    return this
      .repository
      .findByTripId(trip_id)
      .stream()
      .map(activity -> new LinkData(activity.getId(), activity.getTitle(), activity.getUrl()))
      .toList();
  }
}
