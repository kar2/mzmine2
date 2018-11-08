package net.sf.mzmine.modules.ftp;

import io.github.msdk.MSDKException;
import io.github.msdk.io.mzml.MzMLFileExportMethod;
import io.github.msdk.io.mzml.data.MzMLCompressionType;
import net.sf.mzmine.datamodel.MZmineProject;
import net.sf.mzmine.datamodel.RawDataFile;
import net.sf.mzmine.datamodel.impl.MZmineToMSDKRawDataFile;
import net.sf.mzmine.modules.MZmineModuleCategory;
import net.sf.mzmine.modules.MZmineProcessingModule;
import net.sf.mzmine.parameters.ParameterSet;
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

    private static final String MODULE_NAME = "Upload all raw data to GNPS via FTP";
    private static final String MODULE_DESCRIPTION = "This module is for FTP upload to GNPS";

    // Parameters
    private static final String FTP_URL = "massive.ucsd.edu"; // MassIVE ftp url
    private static final int FTP_PORT = 21;

    @Override
    @Nonnull
    public ExitCode runModule(@Nonnull MZmineProject project, @Nonnull ParameterSet parameters,
        @Nonnull Collection<Task> tasks) {

        // TODO: Distinguish between select all or if some raw data files are selected
        String loginId = FTPParameters.LOGIN_ID.getValue();
        String password = FTPParameters.PASSWORD.getValue();

        RawDataFile[] dataFileArray = FTPParameters.DATAFILES.getValue().getMatchingRawDataFiles();
        for (int i = 0; i < dataFileArray.length; i++) {
            if (dataFileArray[i] != null) {
                try {
                    File tempFile = null;
                    try {
                        tempFile = File.createTempFile(dataFileArray[i].getName().substring(0,
                                dataFileArray[i].getName().indexOf(".")), ".mzML");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (tempFile != null) {
                        tempFile = convertRawDataToFile(dataFileArray[i], tempFile);
                        try {
                            connectViaFTP(loginId, password, tempFile);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (MSDKException e) {
                    e.printStackTrace();
                }
            }
        }
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

    public void generateMGFFile() {
        
    }

    public void connectViaFTP(String loginID, String password, File file) throws FileNotFoundException {

        FTPClient ftp = new FTPClient();
        FTPClientConfig config = new FTPClientConfig();
        //ftp.configure(config);
        boolean error = false;

        FileInputStream fis = new FileInputStream(file);

        try {
            int reply;
            ftp.connect(FTP_URL, FTP_PORT);
            System.out.println("Connected to " + FTP_URL + ".");
            System.out.print(ftp.getReplyString());

            // After connection attempt, you should check the reply code to verify
            // success.
            reply = ftp.getReplyCode();

            if(!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                System.err.println("FTP server refused connection.");
                System.exit(1);
            } else {
                if (ftp.login(loginID, password)) {
                    System.out.println("Successfully logged in to " + FTP_URL + ".");
                }
                System.out.println("Filename: " + file.getName());
                if (ftp.storeFile(file.getName(), fis)) {
                    System.out.println("Successfully uploaded file to GNPS. \n");
                }
            }
            ftp.logout();
        } catch(IOException e) {
            error = true;
            e.printStackTrace();
        } finally {
            if(ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch(IOException ioe) {
                    // do nothing
                }
            }
            if (error) {
                System.exit(1);
            }
        }
    }

    public File convertRawDataToFile(RawDataFile dataFile, File file) throws MSDKException {
        MzMLFileExportMethod msdkMethod;
        if (file.getName().toLowerCase().endsWith("mzml")) {
             msdkMethod = new MzMLFileExportMethod(new MZmineToMSDKRawDataFile(dataFile), file,
                    MzMLCompressionType.ZLIB,
                    MzMLCompressionType.ZLIB);
             //msdkMethod.execute();
        }
        return file;
    }
}
