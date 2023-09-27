package course.spring.hibernate.controller;

import course.spring.hibernate.dao.PersonDAO;
import course.spring.hibernate.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO dao;

    @Autowired
    public PeopleController(PersonDAO dao) {
        this.dao = dao;
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id, Model model) {
        dao.delete(id);
        return "redirect:/people";
    }

    @GetMapping()
    public String index(Model model) {
        List<Person> people = dao.index();
        if (people.isEmpty()) {
            System.out.println("DAMN ERROR AAAAAAAAAA");
            /*OH MY GOD it FINALLY WORKS
              small advice: don't call method if it returns not null
              value in modelAttribute parameter, cuz it won't work*/
        }
        model.addAttribute("people", people);
        return "people/index";
    }
    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "people/edit";
        dao.update(person, id);
        return "redirect:/people";
    }
//    @PostMapping()
//    public String create(@ModelAttribute("person") @Valid Person person,
//                         BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) return "people/new_person";
//        dao.save(person);
//        return "redirect:/people";
//
//    }
    @PostMapping
    public String create(Person person, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "people/new_person";
        model.addAttribute("person", person);
        dao.save(person);
        return "redirect:/people";
    }
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id,Model model) {
        model.addAttribute("person", dao.show(id));
        return "people/edit";
    }
    @GetMapping("/new_person")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new_person";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", dao.show(id));
        return "people/show";
    }

}
