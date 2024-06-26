package dev.beomseok.jibbap.controller;

import dev.beomseok.jibbap.dto.GroupsIncludingUser;
import dev.beomseok.jibbap.dto.UserRequest;
import dev.beomseok.jibbap.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;

    @PostMapping("")
    public ResponseEntity createUser(@RequestBody UserRequest user){
        if(user.getKakaoId()==null || user.getProfileImageUrl() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{kakaoId}")
    public ResponseEntity checkUserIsExisted(@PathVariable String kakaoId){
        if(kakaoId==null || kakaoId==""){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        boolean flag = userService.checkUserIsExisted(kakaoId);
        ResponseEntity response = flag?
                ResponseEntity.status(HttpStatus.OK).build()
                :ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return response;
    }

    @GetMapping("/{kakaoId}/detail")
    public ResponseEntity<List<GroupsIncludingUser>> getAllGroupsIncludingUser(@PathVariable String kakaoId){
        if(kakaoId==null || kakaoId==""){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        List<GroupsIncludingUser> groups = userService.readAllGroupsIncludingUser(kakaoId);
        return ResponseEntity.ok().body(groups);
    }
}
