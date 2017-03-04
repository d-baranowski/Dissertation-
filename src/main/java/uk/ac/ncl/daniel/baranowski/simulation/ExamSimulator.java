package uk.ac.ncl.daniel.baranowski.simulation;

import org.springframework.beans.factory.annotation.Autowired;
import uk.ac.ncl.daniel.baranowski.simulation.annotations.Simulator;

@Simulator
public class ExamSimulator {

    @Autowired
    public ExamSimulator() {

    }
}
