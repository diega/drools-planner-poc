package ar.com.plugtree.abfm.poc;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

import org.drools.planner.config.XmlSolverConfigurer;
import org.drools.planner.core.Solver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.com.plugtree.abfm.poc.domain.Location;
import ar.com.plugtree.abfm.poc.domain.ServiceDelivery;
import ar.com.plugtree.abfm.poc.domain.Skill;
import ar.com.plugtree.abfm.poc.domain.Technician;
import ar.com.plugtree.abfm.poc.domain.TrainingLevel;
import ar.com.plugtree.abfm.poc.parser.ServiceDeliveryParser;
import ar.com.plugtree.abfm.poc.parser.TechnicianParser;
import ar.com.plugtree.abfm.poc.planner.TechniciansSolution;

public class DroolsPlannerPoCApp {

    private static final Location SERVICE_LOCATION = Location.MELBOURNE;
    private static final String   SOLVER_CONFIG    = "/ServiceDeliverySolverConfig.xml";
    private static final Logger   logger           = LoggerFactory.getLogger( DroolsPlannerPoCApp.class );

    public static void main(String[] args) throws MalformedURLException,
                                          IOException {
        String serviceDeliveriesFileName = args[0];
        String techniciansFileName = args[1];
        InputStream techniciansStream = new URL( "file:" + techniciansFileName ).openStream();
        InputStream serviceDeliveriesStream = new URL( "file:" + serviceDeliveriesFileName ).openStream();

        TechnicianParser technicianParser = new TechnicianParser( techniciansStream );
        ServiceDeliveryParser serviceDeliveryParser = new ServiceDeliveryParser( serviceDeliveriesStream );

        List<Technician> technicians = technicianParser.parse();
        List<ServiceDelivery> deliveries = serviceDeliveryParser.parse();

        logger.info( "* planned deliveries:" );
        printTabbedList( deliveries );
        logger.info( "* technician candidates: " );
        printTabbedList( technicians );

        DroolsPlannerPoCApp plannerApp = new DroolsPlannerPoCApp();
        deliveries = plannerApp.findOptimalServiceDeliveries( technicians,
                                                              deliveries );
        logger.info( "* best technicians :" );
        printTabbedList( deliveries );
    }

    private static void printTabbedList(List< ? extends Object> objects) {
        for ( int i = 0; i < objects.size() ; i++ ) {
            Object element = objects.get( i );
            logger.info( "\t {}) {}", i+1 , element );
        }
    }

    public List<ServiceDelivery> findOptimalServiceDeliveries(List<Technician> technicians,
                                         List<ServiceDelivery> deliveries) {
        Solver solver = createSolver();
        solver.setStartingSolution( getInitialSolution( technicians,
                                                        deliveries ) );
        solver.solve();
        TechniciansSolution bestTechnicians = (TechniciansSolution) solver.getBestSolution();
        return bestTechnicians.getServiceDeliveries();
    }

    private TechniciansSolution getInitialSolution(List<Technician> technicians,
                                                   List<ServiceDelivery> deliveries) {
        for ( ServiceDelivery serviceDelivery : deliveries ) {
            serviceDelivery.setTechnician( technicians.get( 0 ) );
        }
        TechniciansSolution initialSolution = new TechniciansSolution( technicians,
                                                                deliveries );
        return initialSolution;
    }

    private Solver createSolver() {
        XmlSolverConfigurer configurer = new XmlSolverConfigurer();
        configurer.configure( SOLVER_CONFIG );
        return configurer.buildSolver();
    }

    private static List<ServiceDelivery> getServiceDeliveries() {
        return Arrays.asList( new ServiceDelivery[]{
                new ServiceDelivery( SERVICE_LOCATION,
                                     EnumSet.of( Skill.VEHICLES,
                                                 Skill.COMPUTER )
                                      )
        } );
    }

    private static List<Technician> getTechnicians() {
        return Arrays.asList( new Technician[]{
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
        } );
    }

}
