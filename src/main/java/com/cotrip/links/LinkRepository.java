package com.cotrip.links;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LinkRepository extends JpaRepository<LinkModel, UUID> {
   List<LinkModel> findByTripId(UUID trip_id);
}
