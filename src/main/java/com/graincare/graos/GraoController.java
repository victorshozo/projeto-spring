package com.graincare.graos;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GraoController {

	@RequestMapping("/graos")
	public ArrayList<Grao> grao() {
		return getGraos();
	}

	public ArrayList<Grao> getGraos() {
		ArrayList<Grao> graos = new ArrayList<>();
		return graos;
	}
}