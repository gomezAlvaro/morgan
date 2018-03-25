package com.company;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static java.time.DayOfWeek.*;

public class Instruction {
    private String entity;
    private LocalDate instructionDate;
    private LocalDate settlementDate;
    private BSFlag bsflag;
    private Double agreedFx;
    private Integer units;
    private Double unitPrice;
    private String currency;

    public Instruction(){
    }

    public Instruction(String entity, LocalDate instructionDate, LocalDate settlementDate, BSFlag bsFlag, Double agreedFx, Integer units, Double unitPrice, String currency) {
        this.entity = entity;
        this.instructionDate = instructionDate;
        this.bsflag = bsFlag;
        this.agreedFx = agreedFx;
        this.units = units;
        this.unitPrice = unitPrice;
        this.currency = currency;
        calculateAndSetSettlementDate(settlementDate, currency);
    }

    /**
     * Recalculates and sets the settlementDate, has to be a working day (Mon-Fri)
     * OR Sun-Thu if the  currency is AED or SAR
     * @param settlementDate the original settlementDate
     *
     */
    private void calculateAndSetSettlementDate(LocalDate settlementDate, String currency) {
        if(Constants.AER.equals(currency) || Constants.SAR.equals(currency)) {
            this.settlementDate = getNextWorkingDay(settlementDate, FRIDAY, SATURDAY);
        }
        else {
            this.settlementDate = getNextWorkingDay(settlementDate, SATURDAY, SUNDAY);
        }
    }

    private LocalDate getNextWorkingDay(LocalDate settlementDate, DayOfWeek firstWeekendDay, DayOfWeek secondWeekendDay){
        DayOfWeek originaldDayOfWeek = settlementDate.getDayOfWeek();
        if (firstWeekendDay == originaldDayOfWeek) return settlementDate.plusDays(2);
        else if (secondWeekendDay == originaldDayOfWeek) return settlementDate.plusDays(1);
        return settlementDate;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public LocalDate getInstructionDate() {
        return instructionDate;
    }

    public void setInstructionDate(LocalDate instructionDate) {
        this.instructionDate = instructionDate;
    }

    public LocalDate getSettlementDate() {
        return settlementDate;
    }

    public BSFlag getBsflag() {
        return bsflag;
    }

    public void setBsflag(BSFlag bsflag) {
        this.bsflag = bsflag;
    }

    public Double getAgreedFx() {
        return agreedFx;
    }

    public void setAgreedFx(Double agreedFx) {
        this.agreedFx = agreedFx;
    }


    public Integer getUnits() {
        return units;
    }

    public void setUnits(Integer units) {
        this.units = units;
    }

    public Double getTotalPrice(){
        return units*agreedFx*unitPrice;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Entity: ").append(entity).append(" AgreedFx: ").append(agreedFx).append(" Units: ").append(units);
        return sb.toString();
    }
}
