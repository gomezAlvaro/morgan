package com.company;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        InstructionGenerator ig = new InstructionGenerator();
        List<Instruction> instructions = ig.getInstructions(15L);

        InstructionServiceImpl service = new InstructionServiceImpl();

        System.out.println("Incoming Total per day:");
        Map<LocalDate, Double> totalIncomingPerDay = service.getTotalIncomingPerDay(instructions);
        totalIncomingPerDay.forEach((key, value) -> System.out.println("Day:" + key + " Total: " + Math.round(value * 100.0) / 100.0));

        System.out.println("Outgoing Total per day:");
        Map<LocalDate, Double> totalOutgoingPerDay = service.getTotalOutgoingPerDay(instructions);
        totalOutgoingPerDay.forEach((key, value) -> System.out.println("Day:" + key + " Total: " + Math.round(value * 100.0) / 100.0));

        System.out.println("Incoming Ranking:");
        Map<String, Double> totalIncoming = service.getOrderedTotalIncomingPerEntity(instructions);
        totalIncoming.forEach((key, value) -> System.out.println("Entity:" + key + " Total: " + Math.round(value * 100.0) / 100.0));

        System.out.println("Outgoing Ranking:");
        Map<String, Double> totalOutgoing = service.getOrderedTotalOutgoingPerEntity(instructions);
        totalOutgoing.forEach((key, value) -> System.out.println("Entity:" + key + " Total: " + Math.round(value * 100.0) / 100.0));

    }
}
