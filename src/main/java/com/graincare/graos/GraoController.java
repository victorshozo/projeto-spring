package com.graincare.graos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GraoController {

	@RequestMapping("/graos")
<<<<<<< HEAD:src/main/java/com/graincare/graos/GraoController.java
	public ArrayList<Grao> grao() {
		return getGraos();
	}

	public ArrayList<Grao> getGraos() {
		ArrayList<Grao> graos = new ArrayList<>();
	       graos.add(new Grao(1, "Milho", 30));
	        graos.add(new Grao(2, "Soja", 45));
	        graos.add(new Grao(3, "Sordo", 75));
=======
	public List<Grao> grao() {
		List<Grao> graos = new ArrayList<>();
		graos.add(new Grao(1, "Milho", 30));
		graos.add(new Grao(2, "Soja", 45));
		graos.add(new Grao(3, "Sordo", 75));
>>>>>>> bf122af322a7dd9756e39454e762aba5d18553eb:src/main/GraoController.java

		return graos;
	}

}