package com.ernieblues.masterdataservice.controller;

import com.ernieblues.masterdataservice.dto.VendorDto;
import com.ernieblues.masterdataservice.dto.VendorLookupDto;
import com.ernieblues.masterdataservice.service.VendorService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import java.util.List;

@RestController
@RequestMapping("/api/vendors")
@RequiredArgsConstructor
public class VendorController
{
    private final VendorService vendorService;

    // --------------------------------------------------
    //                       CREATE
    // --------------------------------------------------

    @PostMapping
    public ResponseEntity<VendorDto> create(
            @Valid @RequestBody VendorDto dto)
    {
        VendorDto created = vendorService.create(dto);

        return ResponseEntity
                .created(URI.create("/api/vendors/" + created.id()))
                .body(created);
    }

    // --------------------------------------------------
    //                        READ
    // --------------------------------------------------

    @GetMapping
    public ResponseEntity<List<VendorDto>> getAll()
    {
        return ResponseEntity.ok(vendorService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendorDto> getById(
            @PathVariable Integer id)
    {
        return ResponseEntity.ok(
                vendorService.getById(id));
    }

    // --------------------------------------------------
    //                       UPDATE
    // --------------------------------------------------

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable Integer id,
            @Valid @RequestBody VendorDto dto)
    {
        vendorService.update(id, dto);

        return ResponseEntity.noContent().build();
    }

    // --------------------------------------------------
    //                       DELETE
    // --------------------------------------------------

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer id)
    {
        vendorService.delete(id);

        return ResponseEntity.noContent().build();
    }

    // --------------------------------------------------
    //                      HELPERS
    // --------------------------------------------------

    @GetMapping("/lookup")
    public ResponseEntity<List<VendorLookupDto>> lookup()
    {
        return ResponseEntity.ok(
                vendorService.lookup());
    }
}
