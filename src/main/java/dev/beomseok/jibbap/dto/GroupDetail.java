package dev.beomseok.jibbap.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

public interface GroupDetail {
    String getGroup_name();
    String getUuid();
    String getUsername_in_group();
}
