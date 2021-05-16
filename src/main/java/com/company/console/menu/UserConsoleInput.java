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
        ConsolePrinter.printMessage(message);
        while (sc.hasNext()) {
            if (sc.hasNextInt()) {
                int i = Integer.parseInt(sc.next());
                return i;
            } else {
                ConsolePrinter.printMessageLine(Messages.VALUE_INTEGER);
            }
            ConsolePrinter.printMessageLine("" + sc.next());
        }
        return 0;
    }

    public String enterString(String message) {
        ConsolePrinter.printMessage(message);
        sc.nextLine();
        String result = sc.nextLine();
        return result;
    }

    public BigDecimal enterBigDecimal(String message) {
        ConsolePrinter.printMessage(message);
        while (sc.hasNext()) {
            if (sc.hasNextBigDecimal()) {
                BigDecimal i = new BigDecimal(sc.next());
                return i;
            } else {
                ConsolePrinter.printMessageLine(Messages.VALUE_NUMERIC);
            }
            ConsolePrinter.printMessageLine("" + sc.next());
        }
        return null;
    }

    public LocalDateTime enterDateTime() {
        ConsolePrinter.printMessageLine(Messages.DATE_FORMAT);
        ConsolePrinter.printMessageLine(Messages.ENTER_NOW_DATE);

        LocalDateTime resultDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        sc.nextLine();
        while (true) {
            String dateTimeInput = sc.nextLine();
            if (dateTimeInput.toLowerCase(Locale.ROOT).equals("now")) return resultDateTime;
            try {
                resultDateTime = LocalDateTime.parse(dateTimeInput, dateTimeFormatter);
                ConsolePrinter.printMessageLine(resultDateTime.toString());

                if (resultDateTime.isBefore(LocalDateTime.now())) {
                    return resultDateTime;
                } else {
                    ConsolePrinter.printMessageLine(Messages.FUTURE_TIME);
                }

            } catch (DateTimeParseException e) {
                ConsolePrinter.printMessageLine(Messages.WRONG_TIME_FORMAT);
            }
        }
    }

    public PaymentMethod enterPaymentMethod() {
        ConsolePrinter.printMessage(Messages.ENTER_PAYMENT_METHOD);

        while (sc.hasNext()) {
            if (sc.hasNextInt()) {
                int i = Integer.parseInt(sc.next());
                switch (i) {
                    case 1:
                        return PaymentMethod.BANK_TRANSACTION;
                    case 2:
                        return PaymentMethod.CASH;
                    default:
                        ConsolePrinter.printMessageLine(Messages.MENU_WRONG_INPUT);
                }
            } else {
                ConsolePrinter.printMessageLine(Messages.VALUE_NUMERIC);
            }
            ConsolePrinter.printMessageLine("" + sc.next());
        }
        return null;
    }


    public TransactionCategory enterCategory() {
        int input = enterInt(Messages.ENTER_CATEGORY);

        while (input < 1 || input > TransactionCategory.values().length - 1) {
            //ConsolePrinter.printMessageLine(Messages.MENU_WRONG_INPUT);
            input = enterInt(Messages.MENU_WRONG_INPUT);
        }
        return TransactionCategory.values()[input - 1];
    }
}
