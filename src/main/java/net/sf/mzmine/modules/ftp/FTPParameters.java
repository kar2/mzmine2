package net.sf.mzmine.modules.ftp;

import net.sf.mzmine.parameters.Parameter;
import net.sf.mzmine.parameters.impl.SimpleParameterSet;
import net.sf.mzmine.parameters.parametertypes.MassListParameter;
import net.sf.mzmine.parameters.parametertypes.PasswordParameter;
import net.sf.mzmine.parameters.parametertypes.StringParameter;
import net.sf.mzmine.parameters.parametertypes.filenames.FileNameParameter;
import net.sf.mzmine.parameters.parametertypes.selectors.PeakListsParameter;

import java.io.File;


public class FTPParameters extends SimpleParameterSet {

    public static final StringParameter LOGIN_ID =
            new StringParameter("Login id", "GNPS login id");
    public static final PasswordParameter PASSWORD =
            new PasswordParameter("Password", "GNPS login password");
    public static final PeakListsParameter PEAKLIST =
            new PeakListsParameter();
    public static final FileNameParameter FILENAME =
            new FileNameParameter("Filename", "Name of the output MGF file.","mgf");
    public static final MassListParameter MASSLIST =
            new MassListParameter("Mass list", "Mass list used during peak list processing.");



    /**
     * Create a new parameterset
     */
    public FTPParameters() {
    /*
     * The order of the parameters is used to construct the parameter dialog automatically
     */
        super(new Parameter[] {LOGIN_ID, PASSWORD, PEAKLIST, FILENAME, MASSLIST});
    }
}
