package com.icycraft.league_lecture.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Data
public class DefaultConfig {

    @Value("${config.upload.version}")
    private Integer uploadVersion;

}
