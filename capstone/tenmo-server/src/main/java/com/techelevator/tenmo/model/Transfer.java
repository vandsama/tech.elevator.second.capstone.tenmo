package com.techelevator.tenmo.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.security.cert.LDAPCertStoreParameters;

public class Transfer {
    private Account account;

    private long transferId;
    private long fromAccountId; // (pull from Account class because that's where we store the account numbers)
    private long toAccountId;
    private BigDecimal transferAmount = new BigDecimal(0.00);

    // generate transactionDetails (object[?]) and move to log (create log) -- refer to Capstone 1 Vending Machine log
    // Transaction Details will be pulled from transaction log when user requests Account History (create class [?])


    final String logPath = "C:\\Users\\Student\\workspace\\capstone-2-team-5\\capstone\\tenmo-server\\src\\main\\java" +
            "\\transactionDetailsLog";

    public void run() {
        File logFile = new File(logPath);

        try (final FileOutputStream fos = new FileOutputStream(logFile, true);
             final PrintWriter writer = new PrintWriter(fos)) {
//            writer.printf("%s %s %s %s $%s $%s\n", nowDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")),
//                    nowTime.format(DateTimeFormatter.ofPattern("KK:mm:ss a")),x.getName(),x.getSlot(), x.getCost(),
//                    balance);
        } catch (FileNotFoundException fnfe) {
            System.out.println("could not create the file");
        } catch (Exception e) {
            System.out.println("An error occurred");
        }
    }

}



