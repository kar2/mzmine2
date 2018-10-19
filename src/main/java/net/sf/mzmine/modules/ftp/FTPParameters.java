package net.sf.mzmine.modules.ftp;

import net.sf.mzmine.parameters.Parameter;
import net.sf.mzmine.parameters.impl.SimpleParameterSet;
import net.sf.mzmine.parameters.parametertypes.PasswordParameter;
import net.sf.mzmine.parameters.parametertypes.StringParameter;
import net.sf.mzmine.parameters.parametertypes.filenames.FileNameParameter;


public class FTPParameters extends SimpleParameterSet {

    public static final FileNameParameter filename =
            new FileNameParameter("Filename", "File to upload to GNPS");
    public static final StringParameter loginID =
            new StringParameter("Login id", "GNPS login id");
    public static final PasswordParameter password =
            new PasswordParameter("Password", "GNPS login password");


    /**
     * Create a new parameterset
     */
    public FTPParameters() {
    /*
     * The order of the parameters is used to construct the parameter dialog automatically
     */
        super(new Parameter[] {filename, loginID, password});
    }
}
