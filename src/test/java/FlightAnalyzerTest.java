import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class FlightAnalyzerTest {

    @Test
    public void testReadTicketsFromJson(){
        List<Ticket> tickets = FlightAnalyzer.readTicketsFromJson("src/test/resources/test_tickets.json");
        assertEquals(12, tickets.size());

        List<Ticket> tickets1 = FlightAnalyzer.readTicketsFromJson("src/test/resources/empty.json");
        assertEquals(0, tickets1.size());
    }

    @Test
    public void testMakeTickets() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = (objectMapper.readTree(new File("src/test/resources/test_tickets.json"))).get("tickets");
        List<Ticket> tickets = FlightAnalyzer.makeTickets(jsonNode);
        assertEquals(12, tickets.size());

        Ticket ticket1 = tickets.get(0);
        assertEquals(3, ticket1.getStops());

        Ticket ticket2 = tickets.get(1);
        assertEquals("VVO", ticket2.getOrigin().keySet().iterator().next());
    }

    @Test
    public void testGetDate() {
        LocalDate date = FlightAnalyzer.getDate("12.05.18");
        assertEquals(2018, date.getYear());
        assertEquals(5, date.getMonthValue());
        assertEquals(12, date.getDayOfMonth());
    }

    @Test
    public void testGetTime() {
        LocalTime time = FlightAnalyzer.getTime("16:20");
        assertEquals(16, time.getHour());
        assertEquals(20, time.getMinute());
    }
}
