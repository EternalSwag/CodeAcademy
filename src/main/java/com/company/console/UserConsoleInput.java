package com.company.console;

import java.math.BigDecimal;
import java.time.LocalDate;
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
        return sc.nextLine();
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

    public LocalDateTime enterDateTime(String message) {
        System.out.println(message);
        //  System.out.println("Enter date and time (format - yyyy-MM-dd HH:mm");
        //  System.out.println("Enter \"Now\" to use current date and time");

        LocalDateTime resultDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        do {
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
        } while (true);
    }
}
