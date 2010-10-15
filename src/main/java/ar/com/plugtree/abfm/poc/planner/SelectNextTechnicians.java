package ar.com.plugtree.abfm.poc.planner;

import java.util.ArrayList;
import java.util.List;

import org.drools.planner.core.move.Move;
import org.drools.planner.core.move.factory.CachedMoveFactory;
import org.drools.planner.core.solution.Solution;

import ar.com.plugtree.abfm.poc.domain.ServiceDelivery;
import ar.com.plugtree.abfm.poc.domain.Technician;

public class SelectNextTechnicians extends CachedMoveFactory {

    @Override
    public List<Move> createCachedMoveList(Solution solution) {
        TechniciansSolution techSolution = (TechniciansSolution) solution;
        List<Move> moves = new ArrayList<Move>();
        for ( ServiceDelivery serviceDelivery : techSolution.getServiceDeliveries() ) {
            for ( Technician technician : techSolution.getTechnicians() ) {
                moves.add( new SetNewTechnician( serviceDelivery,
                                                 technician ) );
            }
        }
        return moves;
    }

}
