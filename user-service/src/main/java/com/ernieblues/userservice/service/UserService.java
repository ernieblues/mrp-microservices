package com.ernieblues.userservice.service;

import com.ernieblues.userservice.dto.UserCreateDto;
import com.ernieblues.userservice.dto.UserDto;
import com.ernieblues.userservice.dto.UserLookupDto;
import com.ernieblues.userservice.dto.UserUpdateDto;
import com.ernieblues.userservice.entity.User;
import com.ernieblues.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // --------------------------------------------------
    //                       CREATE
    // --------------------------------------------------

    public UserDto create(UserCreateDto dto) {

        User user = new User();

        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());

        User saved = userRepository.save(user);

        return mapToDto(saved);
    }

    // --------------------------------------------------
    //                        READ
    // --------------------------------------------------

    public List<UserDto> getAll() {

        return userRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .sorted((a, b) ->
                        a.displayName().compareToIgnoreCase(b.displayName()))
                .toList();
    }

    public UserDto getById(Long id) {

        User user = findUserById(id);

        return mapToDto(user);
    }

    public UserLookupDto getLookupById(Long id) {

        User user = findUserById(id);

        return mapToLookupDto(user);
    }

    public List<UserLookupDto> lookup() {

        return userRepository.findAll()
                .stream()
                .map(this::mapToLookupDto)
                .sorted((a, b) ->
                        a.displayName().compareToIgnoreCase(b.displayName()))
                .toList();
    }

    // --------------------------------------------------
    //                       UPDATE
    // --------------------------------------------------

    public UserDto update(Long id, UserUpdateDto dto) {

        User user = findUserById(id);

        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());

        User saved = userRepository.save(user);

        return mapToDto(saved);
    }

    // --------------------------------------------------
    //                       DELETE
    // --------------------------------------------------

    public void delete(Long id) {

        User user = findUserById(id);

        userRepository.delete(user);
    }

    // --------------------------------------------------
    //                      HELPERS
    // --------------------------------------------------

    private User findUserById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found with id: " + id));
    }

    private UserDto mapToDto(User user) {

        String displayName = user.getFirstName() + " " + user.getLastName();

        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhone(),
                user.getAddressLine1(),
                user.getAddressLine2(),
                user.getCity(),
                user.getState(),
                user.getPostalCode(),
                user.getCountry(),
                user.getDisplayName()
        );
    }

    private UserLookupDto mapToLookupDto(User user) {

        return new UserLookupDto(
                user.getId(),
                user.getDisplayName()
        );
    }
}
