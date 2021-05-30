package kodlamaio.hrms.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kodlamaio.hrms.business.abstracts.JobPositionsService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.JobPositions;

@RestController
@RequestMapping("api/job_positions")
public class JobPositionsController {

    private JobPositionsService jobPositionsService;

    @Autowired
    public JobPositionsController(JobPositionsService jobPositionsService) {
        this.jobPositionsService = jobPositionsService;
    }

    @GetMapping("/getall")
    public DataResult<List<JobPositions>> getAll(){
        return this.jobPositionsService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody JobPositions jobPosition) {

        return this.jobPositionsService.add(jobPosition);

    }
}
