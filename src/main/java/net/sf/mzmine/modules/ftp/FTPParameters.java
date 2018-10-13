package net.sf.mzmine.modules.ftp;

import net.sf.mzmine.parameters.Parameter;
import net.sf.mzmine.parameters.impl.SimpleParameterSet;
import net.sf.mzmine.parameters.parametertypes.filenames.FileNameParameter;

public class FTPParameters extends SimpleParameterSet {

    public static final FileNameParameter filename = new FileNameParameter("Test1", "Test2");


    /**
     * Create a new parameterset
     */
    public FTPParameters() {
    /*
     * The order of the parameters is used to construct the parameter dialog automatically
     */
        super(new Parameter[] {filename});
    }
}
