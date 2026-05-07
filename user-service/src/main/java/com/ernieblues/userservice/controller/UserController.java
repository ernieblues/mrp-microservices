package com.ernieblues.userservice.controller;

import com.ernieblues.userservice.dto.UserCreateDto;
import com.ernieblues.userservice.dto.UserDto;
import com.ernieblues.userservice.dto.UserLookupDto;
import com.ernieblues.userservice.dto.UserUpdateDto;
import com.ernieblues.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // --------------------------------------------------
    //                       CREATE
    // --------------------------------------------------

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserCreateDto dto) {

        UserDto created = userService.create(dto);

        URI location = URI.create(
                "/api/users/" + created.id()
        );

        return ResponseEntity.created(location).body(created);
    }

    // --------------------------------------------------
    //                        READ
    // --------------------------------------------------

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping("/lookup")
    public ResponseEntity<List<UserLookupDto>> lookup() {
        return ResponseEntity.ok(userService.lookup());
    }

    // --------------------------------------------------
    //                       UPDATE
    // --------------------------------------------------

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(
            @PathVariable Long id,
            @RequestBody UserUpdateDto dto) {

        return ResponseEntity.ok(userService.update(id, dto));
    }

    // --------------------------------------------------
    //                       DELETE
    // --------------------------------------------------

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
