/*
 * Created on 4 nov. 2004
 *
 * This file is distributed under the LGPL.
 */
package jsesh.graphics.export;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.geom.Dimension2D;
import java.io.IOException;

import javax.swing.JOptionPane;

import jsesh.i18n.I18n;

import org.qenherkhopeshef.graphics.wmf.WMFGraphics2D;

/**
 * Expert able to export the selection to an WMF file.
 *
 * @author S. Rosmorduc
 *
 */
public class WMFExporter extends AbstractGraphicalExporter {

    private Component frame;

    private Dimension2D scaledDimension;

    public WMFExporter() {
        super("wmf", I18n.getString("WMFExporter.description"));
        frame = null;
    }

    public void export(ExportData exportData) {
        try {
            SelectionExporter selectionExporter = new SelectionExporter(
                    exportData, this);
            selectionExporter.exportSelection();
        } catch (HeadlessException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(frame, "Can't open file", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


    /*
     * (non-Javadoc)
     * 
     * @see jsesh.editorSoftware.PagedExporter#getOptionsTitle()
     */
    protected String getOptionsTitle() {
        return "type".toUpperCase() + " options";
    }

    public void setDimension(Dimension2D scaledDimensions) {
        this.scaledDimension = scaledDimensions;
    }

    public Graphics2D buildGraphics()
            throws IOException {
        return new WMFGraphics2D(getExportFile(), scaledDimension);
    }

    public void writeGraphics() throws IOException {
        // NO-OP. the file is written as it is created.
    }

    @Override
    public void newPage() throws IOException {
        // NO-OP.
    }

}
