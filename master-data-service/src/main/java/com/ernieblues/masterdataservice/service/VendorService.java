package com.ernieblues.masterdataservice.service;

import com.ernieblues.masterdataservice.dto.VendorDto;
import com.ernieblues.masterdataservice.dto.VendorLookupDto;
import com.ernieblues.masterdataservice.entity.Vendor;
import com.ernieblues.masterdataservice.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorService
{
    private final VendorRepository vendorRepository;

    // --------------------------------------------------
    //                       CREATE
    // --------------------------------------------------

    public VendorDto create(VendorDto dto)
    {
        Vendor vendor = new Vendor();

        vendor.setCode(dto.code());
        vendor.setName(dto.name());
        vendor.setContactName(dto.contactName());
        vendor.setEmail(dto.email());
        vendor.setPhone(dto.phone());

        vendor.setAddressLine1(dto.addressLine1());
        vendor.setAddressLine2(dto.addressLine2());
        vendor.setCity(dto.city());
        vendor.setState(dto.state());
        vendor.setPostalCode(dto.postalCode());
        vendor.setCountry(dto.country());

        vendor.setActive(dto.active());

        Vendor saved = vendorRepository.save(vendor);

        return mapToDto(saved);
    }

    // --------------------------------------------------
    //                        READ
    // --------------------------------------------------

    public List<VendorDto> getAll()
    {
        return vendorRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public VendorDto getById(Integer id)
    {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Vendor not found: " + id));

        return mapToDto(vendor);
    }

    public List<VendorLookupDto> lookup()
    {
        return vendorRepository.findAll()
                .stream()
                .filter(Vendor::isActive)
                .sorted((a, b) ->
                {
                    String codeA = a.getCode() == null ? "" : a.getCode();
                    String codeB = b.getCode() == null ? "" : b.getCode();

                    return codeA.compareToIgnoreCase(codeB);
                })
                .map(vendor ->
                {
                    String code = vendor.getCode();
                    String name = vendor.getName();

                    String display = code == null || code.isBlank()
                            ? name
                            : code + " - " + name;

                    return new VendorLookupDto(
                            vendor.getId(),
                            display
                    );
                })
                .toList();
    }

    // --------------------------------------------------
    //                       UPDATE
    // --------------------------------------------------

    public void update(Integer id, VendorDto dto)
    {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Vendor not found: " + id));

        vendor.setCode(dto.code());
        vendor.setName(dto.name());
        vendor.setContactName(dto.contactName());
        vendor.setEmail(dto.email());
        vendor.setPhone(dto.phone());

        vendor.setAddressLine1(dto.addressLine1());
        vendor.setAddressLine2(dto.addressLine2());
        vendor.setCity(dto.city());
        vendor.setState(dto.state());
        vendor.setPostalCode(dto.postalCode());
        vendor.setCountry(dto.country());

        vendor.setActive(dto.active());

        vendorRepository.save(vendor);
    }

    // --------------------------------------------------
    //                       DELETE
    // --------------------------------------------------

    public void delete(Integer id)
    {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Vendor not found: " + id));

        vendorRepository.delete(vendor);
    }

    // --------------------------------------------------
    //                      HELPERS
    // --------------------------------------------------

    private VendorDto mapToDto(Vendor vendor)
    {
        return new VendorDto(
                vendor.getId(),
                vendor.getCode(),
                vendor.getName(),
                vendor.getContactName(),
                vendor.getEmail(),
                vendor.getPhone(),
                vendor.getAddressLine1(),
                vendor.getAddressLine2(),
                vendor.getCity(),
                vendor.getState(),
                vendor.getPostalCode(),
                vendor.getCountry(),
                vendor.isActive()
        );
    }
}
