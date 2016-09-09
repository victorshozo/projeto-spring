package main;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GraoController {

	@RequestMapping("/grao")
	public ArrayList<Grao> grao() {
		return getGraos();
	}

	public ArrayList<Grao> getGraos() {
		ArrayList<Grao> graos = new ArrayList<>();
	       graos.add(new Grao(1, "Milho", 30));
	        graos.add(new Grao(2, "Soja", 45));
	        graos.add(new Grao(3, "Sordo", 75));

		return graos;
	}
}