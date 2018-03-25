package com.company;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.company.BSFlag.B;
import static org.junit.jupiter.api.Assertions.assertEquals;

class InstructionServiceTest {
    InstructionService instructionService = new InstructionServiceImpl();

    @Test
    void test_totalIncomingAmount(){
        List<Instruction> instructions = new ArrayList<>();
        instructions.add(getFooInstruction(B));
        instructions.add(getFooInstruction(B));
        Map<LocalDate, Double> totalMap = instructionService.getTotalIncomingPerDay(instructions);
        assertEquals(200D, (double) totalMap.entrySet().stream().findFirst().get().getValue());
    }

    @Test
    void test_totalOutgoingAmount(){
        List<Instruction> instructions = new ArrayList<>();
        instructions.add(getFooInstruction(B));
        instructions.add(getFooInstruction(BSFlag.S));
        Map<LocalDate, Double> totalMap = instructionService.getTotalOutgoingPerDay(instructions);
        assertEquals(100D, (double) totalMap.entrySet().stream().findFirst().get().getValue());
    }

    @Test
    void test_entityWithMaxIncome(){
        List<Instruction> instructions = new ArrayList<>();
        instructions.add(getFooInstruction(B));
        instructions.add(getBarInstruction(B));
        instructions.add(getBarInstruction(B));
        Map<String, Double> totalMap = instructionService.getOrderedTotalIncomingPerEntity(instructions);
        assertEquals("bar", totalMap.entrySet().stream().findFirst().get().getKey());
    }

    @Test
    void test_entityWithMaxOutcome(){
        List<Instruction> instructions = new ArrayList<>();
        instructions.add(getFooInstruction(BSFlag.S));
        instructions.add(getFooInstruction(BSFlag.S));
        instructions.add(getBarInstruction(BSFlag.S));
        Map<String, Double> totalMap = instructionService.getOrderedTotalOutgoingPerEntity(instructions);
        assertEquals("foo", totalMap.entrySet().stream().findFirst().get().getKey());
    }

    @Test
    void test_settlementInWorkWeek(){
        Instruction instruction = getInstructionThursday(B, "foo");
        assertEquals(LocalDate.of(2018, 2, 2), instruction.getSettlementDate());
    }

    @Test
    void test_settlementOutOfWorkWeek(){
        Instruction instruction = getFooInstruction(BSFlag.S);
        assertEquals(LocalDate.of(2018, 2, 5), instruction.getSettlementDate());
    }

    @Test
    void test_settlementAedSarInWorkWeek(){
        Instruction instruction = getInstructionSundaySAR(B, "foo", "SAR");
        assertEquals(LocalDate.of(2018, 2, 4), instruction.getSettlementDate());
    }

    @Test
    void test_settlementAedSarOutOfWorkWeek(){
        Instruction instruction = getInstructionThursdaySAR(B, "foo");
        assertEquals(LocalDate.of(2018, 2, 4), instruction.getSettlementDate());
    }

    @Test
    void test_amountOfInstruction(){
        Instruction instruction = getFooInstruction(B);
        assertEquals(100, instruction.getUnits()* instruction.getUnitPrice() * instruction.getAgreedFx());
    }


    private Instruction getFooInstruction(BSFlag flag) {
        return getInstruction(flag, "foo");
    }

    private Instruction getInstruction(BSFlag flag, String foo) {
        Integer units = 10;
        Double unitPrice = 20D;
        Double agreedFx = 0.5D;
        String entity = foo;
        String currency = "TAR";
        int dayOfMonth = 3;
        LocalDate instructionDate = LocalDate.of(2018, 2, dayOfMonth);
        LocalDate settlementDate = LocalDate.of(2018, 2, dayOfMonth++);
        BSFlag bsFlag = flag;
        return new Instruction(entity, instructionDate, settlementDate, bsFlag, agreedFx, units, unitPrice, currency);
    }

    private Instruction getInstructionThursday(BSFlag flag, String foo) {
        return getInstructionCurrency(flag, foo, "TAR");
    }

    private Instruction getInstructionCurrency(BSFlag flag, String foo, String tar) {
        return getInstruction(flag, foo, tar, 1);
    }

    private Instruction getInstruction(BSFlag flag, String foo, String tar, int day) {
        Integer units = 10;
        Double unitPrice = 20D;
        Double agreedFx = 0.5D;
        String entity = foo;
        String currency = tar;
        int dayOfMonth = day;
        LocalDate instructionDate = LocalDate.of(2018, 2, dayOfMonth);
        LocalDate settlementDate = LocalDate.of(2018, 2, dayOfMonth+1);
        BSFlag bsFlag = flag;
        return new Instruction(entity, instructionDate, settlementDate, bsFlag, agreedFx, units, unitPrice, currency);
    }

    private Instruction getInstructionSundaySAR(BSFlag flag, String foo, String tar) {
        return getInstruction(flag, foo, tar, 3);
    }

    private Instruction getInstructionThursdaySAR(BSFlag flag, String foo) {
        return getInstructionCurrency(flag, foo, "SAR");
    }

    private Instruction getBarInstruction(BSFlag flag) {
        return getInstruction(flag, "bar");
    }
}