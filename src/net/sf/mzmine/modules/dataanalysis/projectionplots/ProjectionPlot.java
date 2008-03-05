/*
 * Copyright 2006-2008 The MZmine Development Team
 * 
 * This file is part of MZmine.
 * 
 * MZmine is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * MZmine is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * MZmine; if not, write to the Free Software Foundation, Inc., 51 Franklin St,
 * Fifth Floor, Boston, MA 02110-1301 USA
 */

package net.sf.mzmine.modules.dataanalysis.projectionplots;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.logging.Logger;

import net.sf.mzmine.data.ParameterSet;
import net.sf.mzmine.data.PeakList;
import net.sf.mzmine.main.MZmineCore;
import net.sf.mzmine.main.MZmineModule;
import net.sf.mzmine.userinterface.Desktop;
import net.sf.mzmine.userinterface.Desktop.MZmineMenu;
import net.sf.mzmine.userinterface.dialogs.ExitCode;

public class ProjectionPlot implements MZmineModule, ActionListener {

	private Logger logger = Logger.getLogger(this.getClass().getName());

	private Desktop desktop;

	private ProjectionPlotParameters parameters;

	/**
	 * @see net.sf.mzmine.main.MZmineModule#initModule(net.sf.mzmine.main.MZmineCore)
	 */
	public void initModule() {

		this.desktop = MZmineCore.getDesktop();

		desktop.addMenuItem(MZmineMenu.ANALYSIS,
				"Principal component analysis (PCA)", this, "PCA_PLOT",
				KeyEvent.VK_C, false, true);

		desktop.addMenuItem(MZmineMenu.ANALYSIS,
				"Curvilinear distance analysis (CDA)", this, "CDA_PLOT",
				KeyEvent.VK_C, false, true);

		desktop.addMenuItem(MZmineMenu.ANALYSIS, "Sammon's projection", this,
				"SAMMON_PLOT", KeyEvent.VK_S, false, true);

	}

	public String toString() {
		return "Projection plot analyzer";
	}

	public void setParameters(ParameterSet parameters) {
		this.parameters = (ProjectionPlotParameters) parameters;
	}

	public ProjectionPlotParameters getParameterSet() {
		return parameters;
	}

	public void actionPerformed(ActionEvent event) {

		PeakList selectedAlignedPeakLists[] = desktop.getSelectedPeakLists();
		if (selectedAlignedPeakLists.length != 1) {
			desktop
					.displayErrorMessage("Please select a single aligned peaklist");
			return;
		}

		if (selectedAlignedPeakLists[0].getNumberOfRows() == 0) {
			desktop.displayErrorMessage("Selected alignment result is empty");
			return;
		}

		logger.finest("Showing projection plot setup dialog");

		if ((parameters == null)
				|| (selectedAlignedPeakLists[0] != parameters
						.getSourcePeakList())) {
			parameters = new ProjectionPlotParameters(
					selectedAlignedPeakLists[0]);
		}

		ProjectionPlotSetupDialog setupDialog = new ProjectionPlotSetupDialog(
				selectedAlignedPeakLists[0], parameters);
		setupDialog.setVisible(true);

		if (setupDialog.getExitCode() == ExitCode.OK) {
			logger.info("Opening new projection plot");

			ProjectionPlotDataset dataset = null;

			String command = event.getActionCommand();
			if (command.equals("PCA_PLOT"))
				dataset = new PCADataset(parameters, 1, 2);

			if (command.equals("CDA_PLOT"))
				dataset = new CDADataset(parameters, 1, 2);
			
			if (command.equals("SAMMON_PLOT"))
				dataset = new SammonDataset(parameters, 1, 2);

			ProjectionPlotWindow newFrame = new ProjectionPlotWindow(desktop,
					dataset, parameters);
			desktop.addInternalFrame(newFrame);

		}

	}

}
