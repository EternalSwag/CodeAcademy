package com.company.console;
import com.company.console.Menu;
import com.company.core.Budget;
import com.company.core.enums.PaymentMethod;
import com.company.core.enums.TransactionCategory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Menu m = new Menu(prepareSampleBudget());
        m.entryMenu();
    }


    private static Budget prepareSampleBudget()
    {
        Budget sampleBudget = new Budget();
        sampleBudget.addIncome(LocalDateTime.now(), new BigDecimal("2500"), TransactionCategory.ASSET, PaymentMethod.BANK_TRANSACTION, "Salary");
        sampleBudget.addIncome(LocalDateTime.now(), new BigDecimal("2800"), TransactionCategory.ASSET, PaymentMethod.BANK_TRANSACTION, "Salary");
        sampleBudget.addIncome(LocalDateTime.now(), new BigDecimal("2200"), TransactionCategory.ASSET, PaymentMethod.BANK_TRANSACTION, "Salary");
        sampleBudget.addExpenditure(LocalDateTime.now(), new BigDecimal("50"), TransactionCategory.EXPENSES, PaymentMethod.CASH, "bottle of whiskey");
        return sampleBudget;
    }

}
