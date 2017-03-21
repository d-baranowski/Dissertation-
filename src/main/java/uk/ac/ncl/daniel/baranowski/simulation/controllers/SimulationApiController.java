package uk.ac.ncl.daniel.baranowski.simulation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ncl.daniel.baranowski.simulation.ExamSimulator;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("simulate")
public class SimulationApiController {

    private final ExamSimulator examSimulator;

    @Autowired
    public SimulationApiController(ExamSimulator examSimulator) {
        this.examSimulator = examSimulator;
    }

    @RequestMapping("/exam/{examId}")
    @PreAuthorize("hasAnyAuthority('ModuleLeader')")
    public ResponseEntity simulateExam(@PathVariable int examId, HttpSession moduleLeader) {
        examSimulator.simulateExam(examId, moduleLeader);
        return ResponseEntity.ok().build();
    }
}
