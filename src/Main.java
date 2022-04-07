import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        //Найти количество несовершеннолетних (т.е. людей младше 18 лет).
        Stream<Person> personUnderageStream = persons.stream();
        long countUnderage = personUnderageStream
                .filter(person -> person.getAge() < 18)
                .count();

        //Получить список фамилий призывников (т.е. мужчин от 18 и до 27 лет).
        Stream<Person> personConscriptStream = persons.stream();
        List<String> listConscript = personConscriptStream
                .filter(person -> person.getSex().equals(Sex.MAN))
                .filter(person -> person.getAge() >= 18 && person.getAge() < 27)
                .map(Person::getFamily)
                .collect(Collectors.toList());

        //Получить отсортированный по фамилии список
        //потенциально работоспособных людей с высшим образованием
        Stream<Person> personWorkableStream = persons.stream();
        List<Person> listPersonWorkable = personWorkableStream
                .filter(person -> person.getEducation().equals(Education.HIGHER))
                .filter(person -> {
                    int age = person.getAge();
                    Sex sex = person.getSex();

                    if (sex.equals(Sex.MAN)
                            && (age >= 18 && age < 65)) {
                        return true;
                    }
                    if (sex.equals(Sex.WOMAN)
                            && (age >= 18 && age < 60)) {
                        return true;
                    }
                    return false;
                })
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());
    }
}