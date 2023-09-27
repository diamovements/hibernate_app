package course.spring.hibernate.dao;

import course.spring.hibernate.models.Person;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class PersonDAO {
    private final SessionFactory sessionFactory;

    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Person> index() {
        Session session = sessionFactory.getCurrentSession();

        List<Person> people = session.createQuery("select p from Person p", Person.class)
                .getResultList();

        return people;
    }
    @Transactional
    public void save(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.save(person);
    }
    @Transactional
    public Person show(int id) {
        Session session = sessionFactory.getCurrentSession();
        Person showedPerson = session.get(Person.class, id);
        return showedPerson;
    }
    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Person deletedPerson = session.get(Person.class, id);
        session.remove(deletedPerson);
    }
    @Transactional
    //pass new person and id  of person we want to update
    public void update(Person person, int id) {
        Session session = sessionFactory.getCurrentSession();
        Person updatedPerson = session.get(Person.class, id);
        updatedPerson.setName(person.getName());
        updatedPerson.setAge(person.getAge());
        updatedPerson.setEmail(person.getEmail());
    }
}

