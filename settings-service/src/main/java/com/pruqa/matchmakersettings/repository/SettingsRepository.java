package com.pruqa.matchmakersettings.repository;

import com.pruqa.matchmakersettings.model.GameSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingsRepository extends JpaRepository<GameSetting,Long> {
}
