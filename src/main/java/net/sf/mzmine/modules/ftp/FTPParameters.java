package net.sf.mzmine.modules.ftp;

import net.sf.mzmine.parameters.Parameter;
import net.sf.mzmine.parameters.impl.SimpleParameterSet;
import net.sf.mzmine.parameters.parametertypes.PasswordParameter;
import net.sf.mzmine.parameters.parametertypes.StringParameter;
import net.sf.mzmine.parameters.parametertypes.selectors.RawDataFilesParameter;

import java.io.File;


public class FTPParameters extends SimpleParameterSet {

    public static final StringParameter LOGIN_ID =
            new StringParameter("Login id", "GNPS login id");
    public static final PasswordParameter PASSWORD =
            new PasswordParameter("Password", "GNPS login password");
    public static final RawDataFilesParameter DATAFILES = new RawDataFilesParameter();




    /**
     * Create a new parameterset
     */
    public FTPParameters() {
    /*
     * The order of the parameters is used to construct the parameter dialog automatically
     */
        super(new Parameter[] {LOGIN_ID, PASSWORD, DATAFILES});
    }
}
