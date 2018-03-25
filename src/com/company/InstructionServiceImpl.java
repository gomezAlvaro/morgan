package com.company;

import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.company.BSFlag.B;
import static com.company.BSFlag.S;

public class InstructionServiceImpl implements InstructionService{


    public Map<LocalDate, Double> getTotalIncomingPerDay(List<Instruction> instructions) {
        return getTotalsPerDay(instructions, B);
    }

    public Map<LocalDate, Double> getTotalOutgoingPerDay(List<Instruction> instructions) {
        return getTotalsPerDay(instructions, S);
    }
    private Map<LocalDate, Double> getTotalsPerDay(List<Instruction> instructions, BSFlag flag) {
        Map<LocalDate, List<Instruction>> instructionsPerDay = instructions.stream().filter(inst -> inst.getBsflag() == flag).collect(Collectors.groupingBy(Instruction::getSettlementDate));
        return instructionsPerDay.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> getTotalForAction(e.getValue(), flag)
        ));
    }

    private Double getTotalForAction(List<Instruction> instructions, BSFlag flag) {
        return instructions.stream().filter(instruction -> flag.equals(instruction.getBsflag())).mapToDouble(Instruction::getTotalPrice).sum();
    }

    public Map<String, Double> getOrderedTotalIncomingPerEntity(List<Instruction> instructions) {
        Map<String, Double> totalPerEntity = getTotalPerEntityForAction(instructions, B);
        return getSortedTotalPerEntity(totalPerEntity);
    }

    public Map<String, Double> getOrderedTotalOutgoingPerEntity(List<Instruction> instructions) {
        Map<String, Double> totalPerEntity = getTotalPerEntityForAction(instructions, S);
        return getSortedTotalPerEntity(totalPerEntity);
    }

    private Map<String, Double> getSortedTotalPerEntity(Map<String, Double> totalPerEntity) {
        return totalPerEntity.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).collect(Collectors.toMap(
                Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    private Map<String, Double> getTotalPerEntityForAction(List<Instruction> instructions, BSFlag flag) {
        Map<String, List<Instruction>> instructionsPerEntity = instructions.stream().filter(inst -> inst.getBsflag() == flag).collect(Collectors.groupingBy(Instruction::getEntity));
        return instructionsPerEntity.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> getTotalForAction(e.getValue(), flag)
        ));
    }



}
