package com.graincare.configuration;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomDoubleSerializer extends JsonSerializer<Double> {

	@Override
	public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		BigDecimal bigDecimal = new BigDecimal(value).setScale(2, RoundingMode.FLOOR);
		bigDecimal.doubleValue();
		
		gen.writeNumber(bigDecimal.doubleValue());
	}

}
