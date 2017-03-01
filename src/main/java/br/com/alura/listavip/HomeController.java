package br.com.alura.listavip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.alura.enviadorEmail.EmailService;
import br.com.alura.listavip.model.Convidado;
import br.com.alura.listavip.service.ConvidadoService;

@Controller
public class HomeController {

	@Autowired
	ConvidadoService convidadoService;

	Convidado novoConvidado = new Convidado();

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("listaconvidados")
	public String listaConvidados(Model model) {
		Iterable<Convidado> convidados = convidadoService.obterTodos();
		model.addAttribute("convidados", convidados);
		return "listaconvidados";
	}

	@RequestMapping(value = "salvar", method = RequestMethod.POST)
	public String salvar(Convidado novoConvidado, Model model) {

		convidadoService.salvar(novoConvidado);

		new EmailService().enviar(novoConvidado.getNome(), novoConvidado.getEmail());

		Iterable<Convidado> convidados = convidadoService.obterTodos();
		model.addAttribute("convidados", convidados);

		return "listaconvidados";
	}

}
