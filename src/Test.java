import dao.PersonDao;
import dao.UserDao;
import model.Person;
import model.User;

import java.sql.SQLException;


public class Test {
    public static void main(String[] args) throws SQLException {

        Person person = new Person("tyler", "callaway", "m");
        PersonDao.insertPerson(person);

//        Person person = PersonDao.getPerson("bc8a0321-223d-4f07-985b-827b326edc31");
//        System.out.println(person);

//        PersonDao.deletePerson("f152ab9e-dc4c-416c-9e1c-a4f248a9396b");

        User user = new User("tyler", "secret", "tacallaway@gmail.com", person);
        UserDao.insertUser(user);

//        User user = UserDao.getUser("tyler");
//        System.out.println(user);

//        UserDao.deleteUser("tyler");
    }
}
