package by.clevertec;

import by.clevertec.model.Animal;
import by.clevertec.model.Car;
import by.clevertec.model.Examination;
import by.clevertec.model.Flower;
import by.clevertec.model.House;
import by.clevertec.model.Person;
import by.clevertec.model.Student;
import by.clevertec.util.Util;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        task1();
        task2();
        task3();
        task4();
        task5();
        task6();
        task7();
        task8();
        task9();
        task10();
        task11();
        task12();
        task13();
        task14();
        task15();
        task16();
        task17();
        task18();
        task19();
        task20();
        task21();
        task22();
    }

    public static void task1() {
        int animalsPerZoo = 7;
        int zooNumber = 3;
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> animal.getAge() >= 10 && animal.getAge() <= 20)
                .sorted(Comparator.comparingInt(animal -> animal.getAge()))
                .skip(animalsPerZoo * (zooNumber - 1))
                .limit(animalsPerZoo)
                .forEach(System.out::println);
    }

    public static void task2() {
        List<Animal> animals = Util.getAnimals();
        animals.stream().filter(animal -> "Japanese".equals(animal.getOrigin()))
                .peek(animal -> animal.setBread(animal.getBread().toUpperCase()))
                .filter(animal -> "Female".equals(animal.getGender()))
                .map(animal -> animal.getBread())
                .forEach(System.out::println);
    }

    public static void task3() {
        List<Animal> animals = Util.getAnimals();
        animals.stream().filter(animal -> animal.getAge() > 30)
                .map(animal -> animal.getOrigin())
                .filter(origin -> origin.startsWith("A"))
                .distinct()
                .forEach(System.out::println);
    }

    public static void task4() {
        List<Animal> animals = Util.getAnimals();
        var countAnimals = animals.stream()
                .filter(animal -> "Female".equals(animal.getGender()))
                .count();
        System.out.println(countAnimals);
    }

    public static void task5() {
        List<Animal> animals = Util.getAnimals();
        var isAnyHungarianAnimal = animals.stream()
                .filter(animal -> animal.getAge() >= 20 && animal.getAge() <= 30)
                .map(animal -> "Hungarian".equals(animal.getOrigin()))
                .findAny();
        System.out.println(isAnyHungarianAnimal.get());
    }

    public static void task6() {
        List<Animal> animals = Util.getAnimals();
        var isMaleOrFemaleAnimal = animals.stream()
                .allMatch(animal -> animal.getGender().equals("Female")
                        || animal.getGender().equals("Male"));
        System.out.println(isMaleOrFemaleAnimal);
    }

    public static void task7() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream().noneMatch(animal -> animal.getOrigin().equals("Oceania")));
    }

    public static void task8() {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .sorted(Comparator.comparing(Animal::getBread))
                .limit(100)
                .max(Comparator.comparing(Animal::getAge))
                .ifPresent(animal -> System.out.println(animal.getAge()));
    }

    public static void task9() {
        List<Animal> animals = Util.getAnimals();
        animals.stream().map(animal -> animal.getBread())
                .map(String::toCharArray)
                .min(Comparator.comparing(arr -> arr.length))
                .ifPresent(arr -> System.out.println(arr.length));
    }

    public static void task10() {
        List<Animal> animals = Util.getAnimals();
        animals.stream().map(animal -> animal.getAge())
                .reduce((age1, age2) -> (age1 + age2))
                .ifPresent(System.out::println);
    }

    public static void task11() {
        List<Animal> animals = Util.getAnimals();
        var averageAge = animals.stream().filter(animal -> "Indonesian".equals(animal.getOrigin()))
                .collect(Collectors.averagingDouble(animal -> animal.getAge()));
        System.out.println(averageAge);
    }

    public static void task12() {
        List<Person> persons = Util.getPersons();
        persons.stream().filter(person -> "Male".equals(person.getGender()))
                .filter(person -> {
                    int age = Period.between(person.getDateOfBirth(), LocalDate.now()).getYears();
                    return age >= 18 && age < 27;
                })
                .sorted(Comparator.comparing(person -> person.getRecruitmentGroup()))
                .limit(200)
                .forEach(System.out::println);
    }

    public static void task13() {
        List<House> houses = Util.getHouses();
        Predicate<String> hospitals = str -> "Hospital".equals(str);

        List<Person> fromHospitals = houses.stream()
                .filter(house -> hospitals.test(house.getBuildingType()))
                .map(house -> house.getPersonList())
                .flatMap(List::stream)
                .collect(Collectors.toList());

        List<Person> childrenAndElderly = houses.stream()
                .filter(house -> !hospitals.test(house.getBuildingType()))
                .map(house -> house.getPersonList())
                .flatMap(List::stream)
                .filter(person -> {
                    int age = Period.between(person.getDateOfBirth(), LocalDate.now()).getYears();
                    return age < 18
                            || "Female".equals(person.getGender()) && age >= 58
                            || age >= 63;
                })
                .collect(Collectors.toList());

        List<Person> others = houses.stream()
                .map(house -> house.getPersonList())
                .flatMap(List::stream)
                .filter(person -> !childrenAndElderly.contains(person))
                .filter(person -> !fromHospitals.contains(person))
                .collect(Collectors.toList());
        Stream.concat(Stream.concat(fromHospitals.stream(), childrenAndElderly.stream()), others.stream())
                .limit(500)
                .forEach(System.out::println);
    }

    public static void task14() {
        List<Car> cars = Util.getCars();
        double pricePerTons = 7.14;

        List<Car> toTurkmenistan = cars.stream()
                .filter(car -> "White".equals(car.getColor()) || "Jaguar".equals(car.getCarMake()))
                .collect(Collectors.toList());
        cars.removeAll(toTurkmenistan);

        Set<String> toUzbekistanCars = Set.of("BMW", "Lexus", "Chrysler", "Toyota");
        List<Car> toUzbekistan = cars.stream()
                .filter(car -> car.getMass() < 1500 || toUzbekistanCars.contains(car.getCarMake()))
                .collect(Collectors.toList());
        cars.removeAll(toUzbekistan);

        List<Car> toKazakhstan = cars.stream()
                .filter(car -> car.getMass() > 4000
                        || car.getCarModel().equals("GMC")
                        || car.getCarModel().equals("Dodge"))
                .collect(Collectors.toList());
        cars.removeAll(toKazakhstan);

        List<Car> toKyrgyzstan = cars.stream()
                .filter(car -> car.getReleaseYear() < 1982
                        || car.getCarModel().equals("Civic")
                        || car.getCarModel().equals("Cherokee"))
                .collect(Collectors.toList());
        cars.removeAll(toKyrgyzstan);

        List<Car> toRussia = cars.stream()
                .filter(car -> {
                    String color = car.getColor();
                    return !(color.equals("Yellow")
                            || color.equals("Red")
                            || color.equals("Green")
                            || color.equals("Blue"))
                            || car.getPrice() > 40000;

                })
                .collect(Collectors.toList());
        cars.removeAll(toRussia);

        List<Car> toMongolia = cars.stream()
                .filter(car -> car.getVin().contains("59"))
                .collect(Collectors.toList());
        cars.removeAll(toMongolia);
    }

    public static void task15() {
        List<Flower> flowers = Util.getFlowers();
        var expenses = flowers.stream()
                .sorted(Comparator.comparing(Flower::getOrigin).reversed()
                        .thenComparing(Comparator.comparingDouble(Flower::getPrice))
                        .thenComparing(Comparator.comparingDouble(Flower::getWaterConsumptionPerDay).reversed()))
                .filter(flower -> {
                    char c = flower.getCommonName().charAt(0);
                    return c < 'S' && c > 'C';
                }).filter(flower -> {
                    List<String> flowerVaseMaterial = flower.getFlowerVaseMaterial();
                    return flowerVaseMaterial.contains("Glass")
                            || flowerVaseMaterial.contains("Aluminum")
                            || flowerVaseMaterial.contains("Steel");
                }).map(flower -> {
                    return flower.getPrice()
                            + flower.getWaterConsumptionPerDay() * 5 * 365 * 1.39;
                })
                .reduce((a, b) -> (a + b));
        System.out.println("Expenses " + expenses.get());
    }

    public static void task16() {
        List<Student> students = Util.getStudents();
        students.stream().filter(student -> student.getAge() < 18)
                .sorted(Comparator.comparing(student -> student.getSurname()))
                .forEach(student -> System.out.println(student.getSurname() + " " + student.getAge()));
    }

    public static void task17() {
        List<Student> students = Util.getStudents();
        students.stream().map(student -> student.getGroup())
                .distinct()
                .forEach(System.out::println);
    }

    public static void task18() {
        List<Student> students = Util.getStudents();
        students.stream()
                .collect(Collectors.groupingBy(student -> student.getFaculty(),
                        Collectors.averagingInt(student -> student.getAge())))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .forEach(System.out::println);
    }

    public static void task19() {
        List<Student> students = Util.getStudents();
        List<Examination> examinations = Util.getExaminations();
        List<Integer> passedExaminationsStudents = examinations.stream()
                .filter(examination -> examination.getExam3() > 4)
                .map(examination -> examination.getStudentId())
                .collect(Collectors.toList());
        students.stream().filter(student -> passedExaminationsStudents.contains(student.getId()))
                .map(student -> student.getSurname())
                .forEach(System.out::println);
    }

    public static void task20() {
        List<Student> students = Util.getStudents();
        List<Examination> examinations = Util.getExaminations();
        Map<String, Double> exams = students.stream().collect(Collectors.groupingBy(student -> student.getFaculty(),
                Collectors.averagingDouble(student -> {
                    return examinations.stream()
                            .filter(examination -> examination.getStudentId() == student.getId())
                            .map(examination -> examination.getExam1())
                            .findFirst().orElse(0);

                })));
        Optional<String> facultyWithMaxMark = exams.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(entry -> entry.getKey());
    }

    public static void task21() {
        List<Student> students = Util.getStudents();
        students.stream()
                .collect(Collectors.groupingBy(student -> student.getGroup(), Collectors.counting()));
    }

    public static void task22() {
        List<Student> students = Util.getStudents();
        students.stream()
                .collect(Collectors.groupingBy(student -> student.getFaculty(),
                        Collectors.minBy(Comparator.comparing(student -> student.getAge()))));
    }
}
