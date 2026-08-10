package com.crimsonlogic.ecommerce;

import com.crimsonlogic.ecommerce.config.*;
import com.crimsonlogic.ecommerce.handler.MenuHandler;

public class Main {
    public static void main(String[] args) {
//        AdminDataLoader.loadAdmins();

        MenuHandler mh = new MenuHandler();
        mh.mainMenu();
    }
}