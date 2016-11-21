package com.graincare.farm;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class FarmToFarmDTOConverter implements Converter<Farm, FarmDTO>{

	@Override
	public FarmDTO convert(Farm farm) {
		return new FarmDTO(farm.getId(), farm.getName());
	}

}
