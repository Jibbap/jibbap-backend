package dev.beomseok.jibbap.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupDetail {
    public String groupName;
    public String uuid;
    public String usernameInGroup;
}
