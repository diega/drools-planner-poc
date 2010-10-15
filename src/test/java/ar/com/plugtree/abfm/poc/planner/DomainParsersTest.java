package ar.com.plugtree.abfm.poc.planner;

import java.io.IOException;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ar.com.plugtree.abfm.poc.domain.Location;
import ar.com.plugtree.abfm.poc.domain.ServiceDelivery;
import ar.com.plugtree.abfm.poc.domain.Skill;
import ar.com.plugtree.abfm.poc.domain.Technician;
import ar.com.plugtree.abfm.poc.domain.TrainingLevel;
import ar.com.plugtree.abfm.poc.parser.ServiceDeliveryParser;
import ar.com.plugtree.abfm.poc.parser.TechnicianParser;

public class DomainParsersTest {

    @Test
    public void parseTechnicians() throws IOException {
        TechnicianParser parser = new TechnicianParser( getClass().getResourceAsStream( "/technicians.dat" ) );
        List<Technician> technicians = parser.parse();
        Assert.assertArrayEquals( new Technician[]{
                                                   new Technician( Location.BRISBANE,
                                                                   TrainingLevel.SEMISENIOR,
                                                                   false,
                                                                   EnumSet.of( Skill.ENGINE ) ),
                                                   new Technician( Location.PERTH,
                                                                   TrainingLevel.TRAINEE,
                                                                   true,
                                                                   EnumSet.of( Skill.VEHICLES,
                                                                               Skill.COMPUTER ) ),
                                                   new Technician( Location.ADELAIDA,
                                                                   TrainingLevel.SENIOR,
                                                                   false,
                                                                   EnumSet.of( Skill.COMPUTER ) ),
                                                   new Technician( Location.BRISBANE,
                                                                   TrainingLevel.JUNIOR,
                                                                   false,
                                                                   Collections.<Skill> emptySet() ),
                                                   new Technician( Location.MELBOURNE,
                                                                   TrainingLevel.SENIOR,
                                                                   false,
                                                                   EnumSet.of( Skill.LANGUAGE ) ),
            },
                                  technicians.toArray() );
    }

    @Test
    public void parseServiceDeliveries() throws IOException {
        ServiceDeliveryParser parser = new ServiceDeliveryParser( getClass().getResourceAsStream( "/services.dat" ) );
        List<ServiceDelivery> services = parser.parse();
        Assert.assertArrayEquals( new ServiceDelivery[]{
                                                        new ServiceDelivery( Location.MELBOURNE,
                                                                             EnumSet.of( Skill.VEHICLES,
                                                                                         Skill.COMPUTER )
                                                                              )
                                                },
                                  services.toArray() );

    }

}
