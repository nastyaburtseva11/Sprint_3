package model;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class OrderGenerator {


    public static Order getRandomOrder() {

        int maxDays = 30;

        String firstName = randomAlphabetic(10);
        String lastName = randomAlphabetic(10);
        String address = randomAlphabetic(10);
        String metroStation = randomAlphabetic(10);
        int rentTime = (int) (Math.random() * ++maxDays) + 1;
        String phone = randomAlphabetic(10);
        String deliveryDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        String comment = randomAlphabetic(10);
        Order order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment);

        return order;
    }
}
