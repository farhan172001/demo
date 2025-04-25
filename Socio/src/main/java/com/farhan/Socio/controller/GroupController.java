package com.farhan.Socio.controller;

import com.farhan.Socio.dto.GroupMemberUpdateDTO;
import com.farhan.Socio.dto.GroupRequestDTO;
import com.farhan.Socio.entity.Group;
import com.farhan.Socio.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping("/create")
    public ResponseEntity<Group> createGroup(@RequestBody GroupRequestDTO dto) {
        return ResponseEntity.ok(groupService.createGroup(dto));
    }

    @PostMapping("/addUser")
    public ResponseEntity<Group> addUserToGroup(@RequestBody GroupMemberUpdateDTO dto) {
        return ResponseEntity.ok(groupService.addUserToGroup(dto));
    }

    @PostMapping("/removeUser")
    public ResponseEntity<Group> removeUserFromGroup(@RequestBody GroupMemberUpdateDTO dto) {
        return ResponseEntity.ok(groupService.removeUserFromGroup(dto));
    }

    @GetMapping
    public ResponseEntity<List<Group>> getAllGroups(@RequestParam(value = "search", required = false) String search) {
        return ResponseEntity.ok(groupService.getAllGroups(search));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Group>> getUserGroups(@PathVariable Long userId) {
        return ResponseEntity.ok(groupService.getUserGroups(userId));
    }
}
