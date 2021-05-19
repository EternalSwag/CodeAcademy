package com.company.console;
import com.company.console.menu.Menu;
import com.company.core.Budget;

public class Main {

    public static void main(String[] args) {
        Menu m = new Menu(new Budget());
        m.entryMenu();
    }
}
