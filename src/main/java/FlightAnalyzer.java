import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class FlightAnalyzer {

    public static void main(String[] args) {
        List<Ticket> ticketList = readTicketsFromJson("tickets.json");
        List<Ticket> filteredTicket = filterTicketsByCities(ticketList, "VVO", "TLV");

        checkMinTime(filteredTicket);
        checkPrices(filteredTicket);
    }

    /**
     * Читает билеты из JSON файла.
     *
     * @param file путь к JSON файлу
     * @return список билетов
     */
    public static List<Ticket> readTicketsFromJson(String file) {
        List<Ticket> ticketsList = new ArrayList<>();
        JsonNode ticketsArray;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(new File(file));
            ticketsArray = jsonNode.get("tickets");
        } catch (IOException e) {
            System.err.println("Ошибка при чтении JSON файла: " + e.getMessage());
            return Collections.emptyList();
        }
        if (ticketsArray != null) {
            ticketsList = makeTickets(ticketsArray);
        }
        return ticketsList;
    }

    /**
     * Создает список билетов из узла JSON.
     *
     * @param ticketNode узел JSON с билетами
     * @return список билетов
     */
    public static List<Ticket> makeTickets(JsonNode ticketNode){
        List<Ticket> ticketsList = new ArrayList<>();
        for (JsonNode node: ticketNode){
            Map<String, String> origin = new HashMap<>();
            origin.put(node.get("origin").asText(), node.get("origin_name").asText());

            Map<String, String> destination = new HashMap<>();
            destination.put(node.get("destination").asText(), node.get("destination_name").asText());

            LocalDate departure_date = getDate(node.get("departure_date").asText());
            LocalTime departure_time = getTime(node.get("departure_time").asText());

            LocalDate arrival_date = getDate(node.get("arrival_date").asText());
            LocalTime arrival_time = getTime(node.get("arrival_time").asText());

            String carrier = node.get("carrier").asText();
            int stops = node.get("stops").asInt();
            int price = node.get("price").asInt();

            Ticket ticket = new Ticket(origin, destination, departure_date, departure_time,
                    arrival_date, arrival_time, carrier, stops, price);
            ticketsList.add(ticket);
        }
    return ticketsList;
    }

    /**
     * Фильтрует билеты по городам отправления и прибытия.
     *
     * @param tickets        список билетов
     * @param originCity     код города отправления
     * @param destinationCity код города прибытия
     * @return отфильтрованный список билетов
     */
    public static List<Ticket> filterTicketsByCities(List<Ticket> tickets, String originCity, String destinationCity) {
        return tickets.stream().filter(ticket -> ticket.getOrigin().containsKey(originCity) && ticket.getDestination()
                .containsKey(destinationCity)).collect(Collectors.toList());
    }

    /**
     * Преобразует строку в LocalDate.
     *
     * @param date строковое представление даты
     * @return объект LocalDate
     */
    public static LocalDate getDate(String date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yy");
        return LocalDate.parse(date, dtf);
    }

    /**
     * Преобразует строку в LocalTime.
     *
     * @param time строковое представление времени
     * @return объект LocalTime
     */
    public static LocalTime getTime(String time) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("H:mm");
        return LocalTime.parse(time, dtf);
    }

    /**
     * Проверяет и выводит минимальное время полета между городами.
     *
     * @param tiketsList список билетов
     */
    public static void checkMinTime(List<Ticket> tiketsList) {
        if (tiketsList.isEmpty()) {
            System.out.println("Список билетов пуст");
        } else {

            Map<String, Integer> result = new HashMap<>();

            for (Ticket ticket : tiketsList) {
                String carrier = ticket.getCarrier();
                LocalDateTime departure = LocalDateTime.of(ticket.getDepDate(), ticket.getDepTime());
                LocalDateTime arrival = LocalDateTime.of(ticket.getArrDate(), ticket.getArrTime());
                int difference = (int) Duration.between(departure, arrival).toMinutes();

                if (result.get(carrier) == null || difference < result.get(carrier)) {
                    result.put(carrier, difference);
                }
            }
            printMinFlyTime(result);
        }
    }
    /**
            * Печатает информацию о минимальном времени полета.
            *
            * @param timeMap карта с временами полета
     */
    public static void printMinFlyTime (Map<String, Integer> timeMap){
        System.out.println("Минимальное время полета между городами Владивосток и Тель-Авив");

        Set<Map.Entry<String, Integer>> entries = timeMap.entrySet();
        for (Map.Entry<String, Integer> time: entries ){
            int days = time.getValue() / (60 * 24);
            int hours = (time.getValue() % (60 * 24)) / 60;
            int minutes = time.getValue() % 60;

            System.out.print("для компании: " + time.getKey() + " составляет - ");
            if (days > 0)
            System.out.print(days + " дн. ");
            if (hours > 0)
            System.out.print( hours + " ч. ");
            System.out.println(minutes + " мин.");
        }
    }

    /**
     * Проверяет и выводит разницу между средней ценой и медианой цены.
     *
     * @param tiketsList список билетов
     */
    public static void checkPrices(List<Ticket> tiketsList){
        if (!tiketsList.isEmpty()){

            int summPrice = 0;
            List<Integer> prices = new ArrayList<>();

            for (Ticket ticket : tiketsList) {
                summPrice += ticket.getPrice();
                prices.add(ticket.getPrice());
            }
            int avgPrice = summPrice / tiketsList.size();

            Collections.sort(prices);
            int medianPrice = prices.get(prices.size() / 2);
            printDiffPrice(avgPrice, medianPrice);
        }
    }

    /**
     * Печатает разницу между средней ценой и медианой цены.
     *
     * @param avgPrice    средняя цена
     * @param medianPrice медиана цены
     */
    public static void printDiffPrice(int avgPrice, int medianPrice){
        System.out.println("Разница между средней ценой и медианой для полета между городами Владивосток и Тель-Авив: "
                + (avgPrice - medianPrice) + " руб.");
    }
}