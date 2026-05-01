package com.ernieblues.masterdataservice.config;

import com.ernieblues.masterdataservice.entity.CostCenter;
import com.ernieblues.masterdataservice.entity.Vendor;
import com.ernieblues.masterdataservice.repository.CostCenterRepository;
import com.ernieblues.masterdataservice.repository.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedData(
            CostCenterRepository costCenterRepository,
            VendorRepository vendorRepository)
    {
        return args -> {

            if (costCenterRepository.count() == 0) {

                List<CostCenter> costCenters = List.of(
                        new CostCenter("ENG", "Engineering"),
                        new CostCenter("IT", "Information Technology"),
                        new CostCenter("MFG", "Manufacturing")
                );

                costCenterRepository.saveAll(costCenters);
            }

            if (vendorRepository.count() == 0) {

                List<Vendor> vendors = List.of(
                        new Vendor("ACME", "Acme Corporation", "sales@acme.com"),
                        new Vendor("GIS", "Global Office Supply", "sales@gis.com"),
                        new Vendor("PPC", "Precision Parts Co.", "sales@ppc.com")
                );

                vendorRepository.saveAll(vendors);
            }
        };
    }
}
