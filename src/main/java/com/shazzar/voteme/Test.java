package com.shazzar.voteme;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Test {

    public static void main(String[] args) {
            String str = "1986-04-08 12:30";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
            String dateTime1 = LocalDateTime.now().format(formatter);
            
            LocalDateTime dateTime2 = LocalDateTime.now();
            
//            dateTime2.isAfter(dateTime);

            System.out.println(dateTime);
            System.out.println(dateTime1);

        System.out.println(dateTime2.isBefore(dateTime));
        
    }
    
}
