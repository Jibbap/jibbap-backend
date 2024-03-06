package dev.beomseok.jibbap.controller;

import dev.beomseok.jibbap.dto.GroupInfo;
import dev.beomseok.jibbap.dto.MealInfoInGroup;
import dev.beomseok.jibbap.dto.GroupRequest;
import dev.beomseok.jibbap.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupApiController {
    private final GroupService groupService;

    @PostMapping("")
    public ResponseEntity createGroup(@RequestBody GroupRequest groupRequest){
        if(groupRequest.getGroupName()==null || groupRequest.getUsernameInGroup()==null || groupRequest.getUuid()==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        groupService.CreateGroup(groupRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{uuid}/meal-info")
    public ResponseEntity<MealInfoInGroup> getMealInfoInGroup(@PathVariable String uuid){
        if(uuid==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        MealInfoInGroup mealInfoInGroup = groupService.readMealInfosInGroup(uuid);
        ResponseEntity responseEntity = mealInfoInGroup == null
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.ok().body(mealInfoInGroup);
        
        return responseEntity;
    }

    @GetMapping("/{uuid}/user-info")
    public ResponseEntity<GroupInfo> getGroupInfo(@PathVariable String uuid){
        if(uuid==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        GroupInfo groupInfo = groupService.readGroupInfo(uuid);
        ResponseEntity responseEntity = groupInfo == null
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.ok().body(groupInfo);

        return responseEntity;
    }

    @PutMapping("/{uuid}")
    public ResponseEntity updateGroupName(@PathVariable String uuid, @RequestBody Map<String,String> RequestBodyMap){
        if(uuid==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        groupService.updateGroupName(uuid,RequestBodyMap.get("group_name"));

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{uuid}/users/{kakaoId}")
    public ResponseEntity updateUsernameInGroup(
            @PathVariable String uuid,
            @PathVariable String kakaoId,
            @RequestBody Map<String,String> requestBodyMap
    ){
        if(uuid==null || kakaoId==null||requestBodyMap.get("username_in_group")==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        boolean isAccepted = groupService.updateUsernameInGroup(uuid,kakaoId,requestBodyMap.get("username_in_group"));
        return ResponseEntity.status(isAccepted?HttpStatus.NO_CONTENT:HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/{uuid}/users/{kakaoId}")
    public ResponseEntity joinGroup(
            @PathVariable String uuid,
            @PathVariable String kakaoId,
            @RequestBody Map<String,String> requestBodyMap){
        if(uuid==null || kakaoId==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        boolean isAccepted = groupService.joinGroup(uuid,kakaoId,requestBodyMap.get("username_in_group"));
        return ResponseEntity.status(isAccepted?HttpStatus.NO_CONTENT:HttpStatus.BAD_REQUEST).build();
    }
}
