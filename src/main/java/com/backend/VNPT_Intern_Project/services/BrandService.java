package com.backend.VNPT_Intern_Project.services;

import com.backend.VNPT_Intern_Project.dtos.BrandDTO.BrandDTO;
import com.backend.VNPT_Intern_Project.entities.Brand;
import com.backend.VNPT_Intern_Project.repositories.BrandRepository;
import com.backend.VNPT_Intern_Project.services.interfaces.IBrandInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService implements IBrandInterface {
    @Autowired
    private BrandRepository brandRepository;

    @Override
    public List<BrandDTO> getAllBrands() throws Exception {
        List<BrandDTO> brandList = brandRepository.findAll();
        if(brandList.isEmpty()) {
            throw new Exception("Brands are not found!");
        }
        return brandList;
    }

}
