package com.pruqa.matchmakersettings.repository;

import com.pruqa.matchmakersettings.model.GameAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeRepository extends JpaRepository<GameAttribute,Long> {
}
