package br.com.animal.controller;

import br.com.animal.model.Animal;
import br.com.animal.repository.AnimalRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AnimalController {

    @Autowired
    private AnimalRepository animalRepository;

    @GetMapping("/gerenciarAnimais")
    public String listarAnimais(Model model) {
        model.addAttribute("listaAnimais", animalRepository.findAll());
        return "gerenciar_animais";
    }

    @GetMapping("/novoAnimal")
    public String novoAnimal(Model model) {
        model.addAttribute("animal", new Animal());
        return "editar_animal";
    }

    @GetMapping("/editarAnimal/{id}")
    public String editarAnimal(@PathVariable("id") long idAnimal, Model model) {
        Optional<Animal> animal = animalRepository.findById(idAnimal);
        model.addAttribute("animal", animal.get());
        return "editar_animal";
    }

    @PostMapping("/salvarAnimal")
    public String salvarAnimal(@ModelAttribute Animal animal, BindingResult result) {
        if (result.hasErrors()) {
            return "editar_animal";
        }
        animalRepository.save(animal);
        return "redirect:/gerenciarAnimais";
    }

    @GetMapping("/excluirAnimal/{id}")
    public String excluirAnimal(@PathVariable("id") long idAnimal) {
        animalRepository.deleteById(idAnimal);
        return "redirect:/gerenciarAnimais";
    }
}
