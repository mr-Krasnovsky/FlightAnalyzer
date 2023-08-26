import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Ticket {

    private Map<String, String> origin;
    private Map<String, String> destination;

    private LocalDate depDate;

    private LocalDate arrDate;
    private LocalTime depTime;
    private LocalTime arrTime;
    private String carrier;
    private int stops;
    private int price;

    public Ticket(Map<String, String> origin, Map<String, String> destination,
                  LocalDate depDate, LocalTime depTime,LocalDate arrDate,
                  LocalTime arrTime, String carrier, int stops, int price) {

        this.origin = origin;
        this.destination = destination;
        this.depDate = depDate;
        this.arrDate = arrDate;
        this.depTime = depTime;
        this.arrTime = arrTime;
        this.carrier = carrier;
        this.stops = stops;
        this.price = price;
    }

    public void setOrigin(Map<String, String> origin) {
        this.origin = origin;
    }

    public void setDestination(Map<String, String> destination) {
        this.destination = destination;
    }

    public void setDepDate(LocalDate depDate) {
        this.depDate = depDate;
    }

    public void setArrDate(LocalDate arrDate) {
        this.arrDate = arrDate;
    }

    public void setDepTime(LocalTime depTime) {
        this.depTime = depTime;
    }

    public void setArrTime(LocalTime arrTime) {
        this.arrTime = arrTime;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public void setStops(int stops) {
        this.stops = stops;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Map<String, String> getOrigin() {
        return origin;
    }

    public Map<String, String> getDestination() {
        return destination;
    }

    public LocalDate getDepDate() {
        return depDate;
    }

    public LocalDate getArrDate() {
        return arrDate;
    }

    public LocalTime getDepTime() {
        return depTime;
    }

    public LocalTime getArrTime() {
        return arrTime;
    }

    public String getCarrier() {
        return carrier;
    }

    public int getStops() {
        return stops;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "origin=" + origin +
                ", destination=" + destination +
                ", depDate=" + depDate +
                ", arrDate=" + arrDate +
                ", depTime=" + depTime +
                ", arrTime=" + arrTime +
                ", carrier='" + carrier + '\'' +
                ", stops=" + stops +
                ", price=" + price +
                '}';
    }
}