package com.company;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface InstructionService {
    Map<LocalDate, Double> getTotalIncomingPerDay(List<Instruction> instructions);

    Map<LocalDate, Double> getTotalOutgoingPerDay(List<Instruction> instructions);

    Map<String, Double> getOrderedTotalIncomingPerEntity(List<Instruction> instructions);

    Map<String, Double> getOrderedTotalOutgoingPerEntity(List<Instruction> instructions);

}
