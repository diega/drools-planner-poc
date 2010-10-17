package ar.com.plugtree.abfm.poc.planner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.drools.planner.core.score.Score;
import org.drools.planner.core.score.SimpleScore;
import org.drools.planner.core.solution.Solution;

import ar.com.plugtree.abfm.poc.domain.ServiceDelivery;
import ar.com.plugtree.abfm.poc.domain.Technician;

public class TechniciansSolution
    implements
    Solution {

    private List<Technician> technicians;
    private List<ServiceDelivery> serviceDeliveries;
    private SimpleScore           score;

    public TechniciansSolution(List<Technician> technicians, List<ServiceDelivery> serviceDelivery) {
        this.technicians = technicians;
        this.serviceDeliveries = serviceDelivery;
    }

    private TechniciansSolution() { }

    @Override
    @SuppressWarnings("rawtypes")
    public Score getScore() {
        return score;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void setScore(Score score) {
        this.score = (SimpleScore) score;
    }

    @Override
    public Collection< ? extends Object> getFacts() {
        return serviceDeliveries;
    }

    @Override
    public Solution cloneSolution() {
        TechniciansSolution solution = new TechniciansSolution();
        solution.score = score;
        solution.technicians = technicians;
        List<ServiceDelivery> clonedServices = new ArrayList<ServiceDelivery>( serviceDeliveries.size() );
        for ( ServiceDelivery serviceDelivery : serviceDeliveries ) {
            clonedServices.add( new ServiceDelivery(serviceDelivery) );
        }
        solution.serviceDeliveries = clonedServices;
        return solution;
    }

    public List<Technician> getTechnicians() {
        return technicians;
    }

    public List<ServiceDelivery> getServiceDeliveries() {
        return serviceDeliveries;
    }
}
