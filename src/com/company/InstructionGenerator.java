package com.company;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InstructionGenerator {
    public static final String[] entities = {"foo", "bar", "another", "entity"};
    public static final String[] currencies = {"ASD", "TAE", "AER", "SAR"};
    Random random = new Random();


    public List<Instruction> getInstructions(Long numberOfInstructions){
        List<Instruction> instructions = new ArrayList<>();
        for(int i = 0; i < numberOfInstructions; ++i) {
            instructions.add(getRandomInstruction());
        }
        return instructions;
    }

    private Instruction getRandomInstruction() {
        Integer units = random.nextInt(50)+1;
        Double unitPrice = (double) (random.nextInt(1000) / 10)+1;
        Double agreedFx = Math.round(random.nextDouble() * 100.0) / 100.0;
        String entity = entities[random.nextInt(4)];
        String currency = currencies[random.nextInt(4)];
        int dayOfMonth = random.nextInt(10) + 1;
        LocalDate instructionDate = LocalDate.of(2018, 2, dayOfMonth);
        LocalDate settlementDate = LocalDate.of(2018, 2, dayOfMonth++);
        BSFlag bsFlag = BSFlag.values()[new Random().nextInt(BSFlag.values().length)];
        return new Instruction(entity, instructionDate, settlementDate, bsFlag, agreedFx, units, unitPrice, currency);
    }
}
