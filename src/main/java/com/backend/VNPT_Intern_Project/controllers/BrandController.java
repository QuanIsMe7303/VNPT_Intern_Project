package com.backend.VNPT_Intern_Project.controllers;

import com.backend.VNPT_Intern_Project.entities.Brand;
import com.backend.VNPT_Intern_Project.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/brands")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping("")
    public ResponseEntity<?> getAllBrands(){
        List<Brand> brandList = brandService.getAllBrands();
        return new ResponseEntity<>(brandList, HttpStatus.OK);
    }
}
