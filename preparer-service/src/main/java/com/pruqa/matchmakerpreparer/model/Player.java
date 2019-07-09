package com.pruqa.matchmakerpreparer.model;

import com.pruqa.matchmakerpreparer.generated.model.GameSetting;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Map;

@Data
@Document
public class Player {

    @Id
    private String id;

    @Indexed
    private String gameName;

    private String playerId;

    @Indexed
    private Double points;

    private Map<String, Double> characteristics;

    @Indexed
    private MatchMakingStatus status;

    @Indexed
    private Date lastUpdate;

    private GameSetting gameSetting;

    private @Version Long version;
}
