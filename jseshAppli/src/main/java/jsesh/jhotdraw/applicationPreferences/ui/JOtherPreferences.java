/*
Copyright Serge Rosmorduc
contributor(s) : Serge J. P. Thomas for the fonts
serge.rosmorduc@qenherkhopeshef.org

This software is a computer program whose purpose is to edit ancient egyptian hieroglyphic texts.

This software is governed by the CeCILL license under French law and
abiding by the rules of distribution of free software.  You can  use, 
modify and/ or redistribute the software under the terms of the CeCILL
license as circulated by CEA, CNRS and INRIA at the following URL
"http://www.cecill.info". 

As a counterpart to the access to the source code and  rights to copy,
modify and redistribute granted by the license, users are provided only
with a limited warranty  and the software's author,  the holder of the
economic rights,  and the successive licensors  have only  limited
liability. 

In this respect, the user's attention is drawn to the risks associated
with loading,  using,  modifying and/or developing or reproducing the
software by the user in light of its specific status of free software,
that may mean  that it is complicated to manipulate,  and  that  also
therefore means  that it is reserved for developers  and  experienced
professionals having in-depth computer knowledge. Users are therefore
encouraged to load and test the software's suitability as regards their
requirements in conditions enabling the security of their systems and/or 
data to be ensured and,  more generally, to use and operate it in the 
same conditions as regards security. 

The fact that you are presently reading this means that you have had
knowledge of the CeCILL license and that you accept its terms.
 */
package jsesh.jhotdraw.applicationPreferences.ui;

import java.util.Arrays;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jsesh.graphics.export.RTFExportPreferences;
import jsesh.graphics.export.RTFExportPreferences.RTFExportGranularity;
import jsesh.graphics.export.RTFExportPreferences.RTFExportGraphicFormat;
import jsesh.jhotdraw.JSeshApplicationModel;
import jsesh.jhotdraw.Messages;
import jsesh.jhotdraw.applicationPreferences.model.ApplicationPreferences;
import jsesh.jhotdraw.applicationPreferences.model.ExportPreferences;
import jsesh.jhotdraw.utils.PanelBuilder;
import jsesh.mdcDisplayer.swing.units.LengthUnit;
import jsesh.mdcDisplayer.swing.units.UnitMediator;
import net.miginfocom.swing.MigLayout;

/**
 * Panel for various (ui linked) preferences.
 * @author Serge Rosmorduc (serge.rosmorduc@qenherkhopeshef.org)
 */
public class JOtherPreferences {


    private JPanel panel;
    private JFormattedTextField iconHeight;


    public JOtherPreferences() {
        init();
        layout();
    }


    private void init() {
        panel = new JPanel();
        iconHeight= new JFormattedTextField();
        iconHeight.setValue(30);

    }

    private void layout() {
        panel.setLayout(new MigLayout());
        PanelBuilder helper = new PanelBuilder(panel);
        helper.addLabel("otherPrefs.iconHeight.label");
        panel.add(iconHeight, "span, grow, wrap");
        JLabel comment= new JLabel(Messages.getString("otherPrefs.iconHeight.comment"));
        panel.add(comment, "span, grow, wrap");
    }

    public JPanel getPanel() {
        return panel;
    }


    public void loadPreferences() {
        ApplicationPreferences prefs = ApplicationPreferences.getFromPreferences();
        iconHeight.setValue(prefs.getIconHeight());
    }

    void savePreferences() {
        ApplicationPreferences prefs = new ApplicationPreferences();
        prefs.setIconHeight((Integer)iconHeight.getValue());
        prefs.savetoPreferences();
    }
}
