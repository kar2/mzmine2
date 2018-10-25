package net.sf.mzmine.modules.ftp;

import net.sf.mzmine.datamodel.MZmineProject;
import net.sf.mzmine.datamodel.MassList;
import net.sf.mzmine.datamodel.PeakList;
import net.sf.mzmine.modules.MZmineModuleCategory;
import net.sf.mzmine.modules.MZmineProcessingModule;
import net.sf.mzmine.modules.peaklistmethods.io.gnpsexport.GNPSExportTask;
import net.sf.mzmine.parameters.ParameterSet;
import net.sf.mzmine.parameters.impl.SimpleParameterSet;
import net.sf.mzmine.parameters.parametertypes.filenames.FileNameParameter;
import net.sf.mzmine.parameters.parametertypes.selectors.PeakListsSelection;
import net.sf.mzmine.taskcontrol.Task;
import net.sf.mzmine.util.ExitCode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;

import javax.annotation.Nonnull;
import java.util.Collection;

public class FTPModule implements MZmineProcessingModule {

    private static final String MODULE_NAME = "Upload via FTP";
    private static final String MODULE_DESCRIPTION = "This module is for FTP upload to GNPS";

    // Parameters
    private static final String LOGIN_ID = FTPParameters.LOGIN_ID.getValue();
    private static final String PASSWORD = FTPParameters.PASSWORD.getValue();
    private static final PeakList[] PEAKLIST = FTPParameters.PEAKLIST.getValue().getMatchingPeakLists();
    private static final File FILE = FTPParameters.FILENAME.getValue();
    private static final String MASSLIST = FTPParameters.MASSLIST.getValue();

    private static final String FTP_URL = "massive.ucsd.edu"; // MassIVE ftp url
    private static final int FTP_PORT = 21;

    // TODO: Option to select peak list and analyze then upload
    // TODO: Upload form disk
    @Override
    @Nonnull
    public ExitCode runModule(@Nonnull MZmineProject project, @Nonnull ParameterSet parameters,
        @Nonnull Collection<Task> tasks) {

        // TODO: FIX THIS
        //GNPSExportTask exportTask = new GNPSExportTask(new SimpleParameterSet(PEAKLIST, FILE, MASSLIST));
        // TODO: USE GNPS EXPORT TO GET MGF FILE THEN UPLOAD MGF TO GNPS

        return ExitCode.OK;

    }

    @Override
    public @Nonnull MZmineModuleCategory getModuleCategory() {
        /**
         * Change category: will automatically be added to the linked menu
         */
        return MZmineModuleCategory.PEAKLIST;
    }

    @Override
    public @Nonnull Class<? extends ParameterSet> getParameterSetClass() {
        return FTPParameters.class;
    }

    @Override
    public @Nonnull String getName() {
        return MODULE_NAME;
    }

    @Override
    public @Nonnull String getDescription() {
        return MODULE_DESCRIPTION;
    }

//    public void connectViaFTP(String loginID, String password, File file) throws FileNotFoundException {
//        FTPClient ftp = new FTPClient();
//        FTPClientConfig config = new FTPClientConfig();
//        //ftp.configure(config);
//        boolean error = false;
//
//        FileInputStream fis = new FileInputStream(file);
//
//        try {
//            int reply;
//            ftp.connect(FTP_URL, FTP_PORT);
//            System.out.println("Connected to " + FTP_URL + ".");
//            System.out.print(ftp.getReplyString());
//
//            // After connection attempt, you should check the reply code to verify
//            // success.
//            reply = ftp.getReplyCode();
//
//            if(!FTPReply.isPositiveCompletion(reply)) {
//                ftp.disconnect();
//                System.err.println("FTP server refused connection.");
//                System.exit(1);
//            } else {
//                if (ftp.login(loginID, password)) {
//                    System.out.println("Successfully logged in to " + FTP_URL + ".");
//                }
//                System.out.println("Filename: " + file.getName());
//                if (ftp.storeFile(file.getName(), fis)) {
//                    System.out.println("Successfully uploaded file to GNPS.");
//                }
//            }
//            ftp.logout();
//        } catch(IOException e) {
//            error = true;
//            e.printStackTrace();
//        } finally {
//            if(ftp.isConnected()) {
//                try {
//                    ftp.disconnect();
//                } catch(IOException ioe) {
//                    // do nothing
//                }
//            }
//            if (error) {
//                System.exit(1);
//            }
//        }
//    }
}
