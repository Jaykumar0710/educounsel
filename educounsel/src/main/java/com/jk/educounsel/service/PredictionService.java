package com.jk.educounsel.service;

import com.jk.educounsel.model.CollegeResultDto;
import com.jk.educounsel.model.Cutoff;
import com.jk.educounsel.model.StudentInput;
import com.jk.educounsel.repository.CutoffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PredictionService {

    @Autowired
    private CutoffRepository cutoffRepository;

    public List<CollegeResultDto> predictColleges(StudentInput input) {
        double percentile = input.getPercentile();
        double min = percentile - 5;
        double max = percentile + 5;

        List<Cutoff> cutoffs = cutoffRepository.findLikelyCutoffsFlexible(
                input.getDepartment(),
                input.getCategory(),
                min,
                max,
                input.getRegion(),
                input.getCapRound()
        );

        return cutoffs.stream()
                .sorted(Comparator.comparing(c -> Math.abs(c.getClosingPercentile() - percentile)))
                .limit(10)
                .map(c -> new CollegeResultDto(
                        c.getInstituteBranch().getInstitute().getName(),
                        c.getInstituteBranch().getInstitute().getLocation(),
                        c.getInstituteBranch().getInstitute().getRegion(),
                        c.getInstituteBranch().getBranch().getBranchName(),
                        c.getClosingPercentile(),
                        c.getCapRound(),
                        c.getRemarks()
                ))
                .collect(Collectors.toList());
    }
}