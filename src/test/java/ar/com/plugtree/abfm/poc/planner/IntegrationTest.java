package ar.com.plugtree.abfm.poc.planner;

import org.junit.Ignore;
import org.junit.Test;

import ar.com.plugtree.abfm.poc.parser.ServiceDeliveryParser;
import ar.com.plugtree.abfm.poc.parser.TechnicianParser;

public class IntegrationTest {

    @Test
    @Ignore
    public void readFromFileAndSolve() {
        TechnicianParser technicianParser = new TechnicianParser( getClass().getResourceAsStream( "/technicians.dat" ) );
        ServiceDeliveryParser serviceDeliveryParser = new ServiceDeliveryParser( getClass().getResourceAsStream( "/services.dat" ) );

    }

}
