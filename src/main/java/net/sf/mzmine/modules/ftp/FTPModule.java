package net.sf.mzmine.modules.ftp;

import net.sf.mzmine.datamodel.MZmineProject;
import net.sf.mzmine.modules.MZmineModuleCategory;
import net.sf.mzmine.modules.MZmineProcessingModule;
import net.sf.mzmine.parameters.ParameterSet;
import net.sf.mzmine.taskcontrol.Task;
import net.sf.mzmine.util.ExitCode;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;

import javax.annotation.Nonnull;
import java.util.Collection;

public class FTPModule implements MZmineProcessingModule {

    private static final String MODULE_NAME = "Upload via FTP";
    private static final String MODULE_DESCRIPTION = "This module is for FTP upload to GNPS";

    @Override
    @Nonnull
    public ExitCode runModule(@Nonnull MZmineProject project, @Nonnull ParameterSet parameters,
        @Nonnull Collection<Task> tasks) {

        System.out.println("This is just a test.");
        connectViaFTP();
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

    public void connectViaFTP() {
        FTPClient ftp = new FTPClient();
        FTPClientConfig config = new FTPClientConfig();
        //ftp.configure(config);
        boolean error = false;
        String loginID = FTPParameters.loginID.getValue();
        String password = FTPParameters.password.getValue(); // TODO: Add try block to see if this is null
        // TODO: Log in with login id and pass for FTP
        try {
            int reply;
            String server = "ftp.example.com"; // Testing url
            ftp.connect(server);
            //ftp.login();
            System.out.println("Connected to " + server + ".");
            System.out.print(ftp.getReplyString());

            // After connection attempt, you should check the reply code to verify
            // success.
            reply = ftp.getReplyCode();

            if(!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                System.err.println("FTP server refused connection.");
                System.exit(1);
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
            System.exit(error ? 1 : 0);
        }
    }
}
