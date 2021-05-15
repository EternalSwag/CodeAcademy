package com.company.console.menu;

import com.company.core.enums.PaymentMethod;
import com.company.core.enums.TransactionCategory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Scanner;

public class UserConsoleInput {

    private Scanner sc;

    public UserConsoleInput(Scanner sc) {
        this.sc = sc;
    }

    public int enterInt(String message) {
        System.out.print(message);
        while (sc.hasNext()) {
            if (sc.hasNextInt()) {
                int i = Integer.parseInt(sc.next());
                return i;
            } else {
                System.out.println("Value must be integer number! Try again");
            }
            System.out.println("" + sc.next());
        }
        return 0;
    }

    public String enterString(String message) {
        System.out.print(message);
        sc.nextLine();
        String result = sc.nextLine();
        return result;
    }

    public BigDecimal enterBigDecimal(String message) {
        System.out.print(message);
        while (sc.hasNext()) {
            if (sc.hasNextBigDecimal()) {
                BigDecimal i = new BigDecimal(sc.next());
                return i;
            } else {
                System.out.println("Value must be numeric value! Try again");
            }
            System.out.println("" + sc.next());
        }
        return null;
    }

    public LocalDateTime enterDateTime() {
        System.out.println("Enter date and time (format - yyyy-MM-dd HH:mm)");
        System.out.println("Enter \"Now\" to use current date and time");

        LocalDateTime resultDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        sc.nextLine();
        while(true) {
            String dateTimeInput = sc.nextLine();
            if (dateTimeInput.toLowerCase(Locale.ROOT).equals("now")) return resultDateTime;
            try {
                resultDateTime = LocalDateTime.parse(dateTimeInput, dateTimeFormatter);
                System.out.println(resultDateTime.toString());

                if (resultDateTime.isBefore(LocalDateTime.now())) {
                    return resultDateTime;
                } else {
                    System.out.println("You entered future time, try again");
                }

            } catch (DateTimeParseException e) {
                System.out.println("Wrong date time input. Format is yyyy-MM-dd HH:mm");
            }
        }
    }

    public PaymentMethod enterPaymentMethod()
    {
        System.out.println("Enter payment method: ");
        System.out.println("1. Bank transaction");
        System.out.println("2. Cash");

        while (sc.hasNext()) {
            if (sc.hasNextInt()) {
                int i = Integer.parseInt(sc.next());
                switch(i){
                    case 1:
                        return PaymentMethod.BANK_TRANSACTION;
                    case 2:
                        return PaymentMethod.CASH;
                    default:
                        System.out.println("Wrong input, pick number out of possible options");
                }
            } else {
                System.out.println("Value must be numeric value! Try again");
            }
            System.out.println("" + sc.next());
        }
        return null;
    }


    public TransactionCategory enterCategory() {
        System.out.print("Enter category: ");
        System.out.println("1. ASSET");
        System.out.println("2. LIABILITY");
        System.out.println("3. EQUITY");
        System.out.println("4. REVENUE");
        System.out.println("5. EXPENSES");
        while (sc.hasNext()) {
            if (sc.hasNextInt()) {
                int i = Integer.parseInt(sc.next());
                switch(i){
                    case 1:
                        return TransactionCategory.ASSET;
                    case 2:
                        return TransactionCategory.LIABILITY;
                    case 3:
                        return TransactionCategory.EQUITY;
                    case 4:
                        return TransactionCategory.REVENUE;
                    case 5:
                        return TransactionCategory.EXPENSES;
                    default:
                        System.out.println("Wrong input, pick number out of possible options");
                }
            } else {
                System.out.println("Value must be numeric value! Try again");
            }
            System.out.println("" + sc.next());
        }
        return null;
    }
}
