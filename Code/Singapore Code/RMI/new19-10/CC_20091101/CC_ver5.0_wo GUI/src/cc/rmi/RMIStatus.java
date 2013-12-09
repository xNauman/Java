package cc.rmi;

import java.rmi.*;

public class RMIStatus {

    private boolean status[] = new boolean[6];

    // 0: Online, Currently Idling
    // 1: Pre-Start Initiated, Currently in Progress
    // 2: Pre-Start Completed, Ready to Start
    // 3: Started, Currently in Operation

    /* a series of methods to update the status of all other subsystems */
    private void updateStatus_CME(int state) throws RemoteException {

        switch(state)
        {
            // CME online, waiting for prestart
            case 0: System.out.println("CME Online");
                    break;
            // CME prestart initiated
            case 1: System.out.println("CME Initialising");
                    System.out.println("Please Wait...");
                    break;
            // CME prestart completed
            case 2: System.out.println("CME Ready");
                    break;
            // CME started, currently in operation
            case 3: System.out.println("CME Running");
                    break;
        }
    }

    private void updateStatus_DB(int state) throws RemoteException {

        switch(state)
        {
            // DB online, waiting for prestart
            case 0: System.out.println("DB Online");
                    break;
            // DB prestart initiated
            case 1: System.out.println("DB Initialising");
                    System.out.println("Please Wait...");
                    break;
            // DB prestart completed
            case 2: System.out.println("DB Ready");
                    break;
            // DB started, currently in operation
            case 3: System.out.println("DB Running");
                    break;
        }
    }

    private void updateStatus_NAV(int state) throws RemoteException {

        switch(state)
        {
            // NAV online, waiting for prestart
            case 0: System.out.println("NAV Online");
                    break;
            // NAV prestart initiated
            case 1: System.out.println("NAV Initialising");
                    System.out.println("Please Wait...");
                    break;
            // NAV prestart completed
            case 2: System.out.println("NAV Ready");
                    break;
            // NAV started, currently in operation
            case 3: System.out.println("NAV Running");
                    break;
        }
    }

    private void updateStatus_SCH(int state) throws RemoteException {

        switch(state)
        {
            // SCH online, waiting for prestart
            case 0: System.out.println("SCH Online");
                    break;
            // SCH prestart initiated
            case 1: System.out.println("SCH Initialising");
                    System.out.println("Please Wait...");
                    break;
            // SCH prestart completed
            case 2: System.out.println("SCH Ready");
                    break;
            // SCH started, currently in operation
            case 3: System.out.println("SCH Running");
                    break;
        }
    }

    private void updateStatus_SU(int state) throws RemoteException {

        switch(state)
        {
            // SU online, waiting for prestart
            case 0: System.out.println("SU Online");
                    break;
            // SU prestart initiated
            case 1: System.out.println("SU Initialising");
                    System.out.println("Please Wait...");
                    break;
            // SU prestart completed
            case 2: System.out.println("SU Ready");
                    break;
            // SU started, currently in operation
            case 3: System.out.println("SU Running");
                    break;
        }
    }

    private void updateStatus_TKR(int state) throws RemoteException {

        switch(state)
        {
            // TKR online, waiting for prestart
            case 0: System.out.println("TKR Online");
                    break;
            // TKR prestart initiated
            case 1: System.out.println("TKR Initialising");
                    System.out.println("Please Wait...");
                    break;
            // TKR prestart completed
            case 2: System.out.println("TKR Ready");
                    break;
            // TKR started, currently in operation
            case 3: System.out.println("TKR Running");
                    break;
        }
    }
}
