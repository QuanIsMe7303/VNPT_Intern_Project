package com.backend.VNPT_Intern_Project.services.interfaces;

import com.backend.VNPT_Intern_Project.dtos.BrandDTO.BrandDTOResponse;

import java.util.List;

public interface IBrandInterface {
    List<BrandDTOResponse> getAllBrands() throws Exception;

}
