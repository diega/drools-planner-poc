package ar.com.plugtree.abfm.poc.planner;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

import junit.framework.Assert;

import org.drools.planner.config.XmlSolverConfigurer;
import org.drools.planner.core.Solver;
import org.junit.Test;

import ar.com.plugtree.abfm.poc.domain.Location;
import ar.com.plugtree.abfm.poc.domain.ServiceDelivery;
import ar.com.plugtree.abfm.poc.domain.Skill;
import ar.com.plugtree.abfm.poc.domain.Technician;
import ar.com.plugtree.abfm.poc.domain.TrainingLevel;

public class BestTechnicianAvailableTest {

    private static final Location SERVICE_LOCATION = Location.MELBOURNE;
    private static final String   SOLVER_CONFIG    = "/ServiceDeliverySolverConfig.xml";

    @Test
    public void findBestTechnician(){
        List<Technician> technicians = getTechnicians();
        List<ServiceDelivery> deliveries = getServiceDeliveries();

        Solver solver = createSolver();
        solver.setStartingSolution( getInitialSolution( technicians,
                                                        deliveries ) );
        solver.solve();
        TechniciansSolution bestTechnicians = (TechniciansSolution) solver.getBestSolution();

        Assert.assertEquals( technicians.get( 4 ),
                             bestTechnicians.getServiceDeliveries().get( 0 ).getTechnician() );
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
                                               new Technician( Location.BRISBANE,
                                                               TrainingLevel.JUNIOR,
                                                               false,
                                                               Collections.<Skill> emptySet() ),
                                               new Technician( Location.MELBOURNE,
                                                               TrainingLevel.SENIOR,
                                                               false,
                                                               EnumSet.of( Skill.LANGUAGE ) ),
                                               new Technician( Location.ADELAIDA,
                                                               TrainingLevel.SENIOR,
                                                               false,
                                                               EnumSet.of( Skill.COMPUTER ) )
        } );
    }

}
