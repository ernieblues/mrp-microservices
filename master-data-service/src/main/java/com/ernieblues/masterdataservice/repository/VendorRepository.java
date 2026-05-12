package com.ernieblues.masterdataservice.repository;

import com.ernieblues.masterdataservice.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Integer> {
}
