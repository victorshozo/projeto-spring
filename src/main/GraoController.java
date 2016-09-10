package main;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GraoController {

	@RequestMapping("/graos")
	public List<Grao> grao() {
		List<Grao> graos = new ArrayList<>();
		graos.add(new Grao(1, "Milho", 30));
		graos.add(new Grao(2, "Soja", 45));
		graos.add(new Grao(3, "Sordo", 75));

		return graos;
	}

}