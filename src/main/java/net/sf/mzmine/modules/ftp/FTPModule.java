package net.sf.mzmine.modules.ftp;

import net.sf.mzmine.datamodel.MZmineProject;
import net.sf.mzmine.modules.MZmineModuleCategory;
import net.sf.mzmine.modules.MZmineProcessingModule;
import net.sf.mzmine.parameters.ParameterSet;
import net.sf.mzmine.taskcontrol.Task;
import net.sf.mzmine.util.ExitCode;

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
}
