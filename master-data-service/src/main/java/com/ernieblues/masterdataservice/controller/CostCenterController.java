package com.ernieblues.masterdataservice.controller;

import com.ernieblues.masterdataservice.dto.CostCenterDto;
import com.ernieblues.masterdataservice.dto.CostCenterLookupDto;
import com.ernieblues.masterdataservice.service.CostCenterService;

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
@RequestMapping("/api/cost-centers")
@RequiredArgsConstructor
public class CostCenterController
{
    private final CostCenterService costCenterService;

    // --------------------------------------------------
    //                       CREATE
    // --------------------------------------------------

    @PostMapping
    public ResponseEntity<CostCenterDto> create(
            @Valid @RequestBody CostCenterDto dto)
    {
        CostCenterDto created = costCenterService.create(dto);

        return ResponseEntity
                .created(URI.create("/api/cost-centers/" + created.id()))
                .body(created);
    }

    // --------------------------------------------------
    //                        READ
    // --------------------------------------------------

    @GetMapping
    public ResponseEntity<List<CostCenterDto>> getAll()
    {
        return ResponseEntity.ok(costCenterService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CostCenterDto> getById(
            @PathVariable Integer id)
    {
        return ResponseEntity.ok(
                costCenterService.getById(id));
    }

    // --------------------------------------------------
    //                       UPDATE
    // --------------------------------------------------

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable Integer id,
            @Valid @RequestBody CostCenterDto dto)
    {
        costCenterService.update(id, dto);

        return ResponseEntity.noContent().build();
    }

    // --------------------------------------------------
    //                       DELETE
    // --------------------------------------------------

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer id)
    {
        costCenterService.delete(id);

        return ResponseEntity.noContent().build();
    }

    // --------------------------------------------------
    //                      HELPERS
    // --------------------------------------------------

    @GetMapping("/lookup")
    public ResponseEntity<List<CostCenterLookupDto>> lookup()
    {
        return ResponseEntity.ok(
                costCenterService.lookup());
    }
}
