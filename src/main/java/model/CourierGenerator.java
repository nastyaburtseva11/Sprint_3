package model;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class CourierGenerator {

    public static String getCourierLogin() {
        return randomAlphabetic(10);
    }

    public static String getCourierPassword() {
        return randomAlphabetic(10);
    }

    public static Courier getRandomCourier() {
        // метод randomAlphabetic генерирует строку, состоящую только из букв, в качестве параметра передаём длину строки
        String courierLogin = randomAlphabetic(10);
        // с помощью библиотеки RandomStringUtils генерируем пароль
        String courierPassword = randomAlphabetic(10);
        // с помощью библиотеки RandomStringUtils генерируем имя курьера
        String courierFirstName = randomAlphabetic(10);
        Courier courier = new Courier(courierLogin, courierPassword, courierFirstName);
        return courier;
    }

    public static Courier getRandomCourierWithObligatoryField() {
        // метод randomAlphabetic генерирует строку, состоящую только из букв, в качестве параметра передаём длину строки
        String courierLogin = randomAlphabetic(10);
        // с помощью библиотеки RandomStringUtils генерируем пароль
        String courierPassword = randomAlphabetic(10);
        Courier courier = new Courier(courierLogin, courierPassword);
        return courier;
    }

    public static Courier getRandomCourierWithLoginOnly() {
        return new Courier().setLogin(randomAlphabetic(10));
    }

    public static Courier getRandomCourierWithPasswordOnly() {
        return new Courier().setPassword(randomAlphabetic(10));
    }

    public static Courier getRandomCourierWithFirstNameOnly() {
        return new Courier().setFirstName(randomAlphabetic(10));
    }
}
