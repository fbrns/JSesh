/*
 * Created on 28 nov. 2004
 */
package jsesh.swing.groupEditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import jsesh.mdc.model.AbsoluteGroup;

/**
 * Dialog panel for editing groups.
 * 
 * @author S. Rosmorduc
 */
public class GroupEditorDialog extends JPanel {

    private JButton next, previous, reset;

    
    private JToggleButton rotate;

    private JToggleButton resize;
    
    private JToggleButton move;

    private GroupEditor editor;

    public GroupEditorDialog() {
        setBackground(Color.WHITE);
        editor = new GroupEditor();
        GroupEditorControl control = new GroupEditorControl(editor);

        ActionListener l = new GroupEditorDialogListener();
        next= new JButton("next");
        previous= new JButton("previous");
        reset= new JButton("reset");
        ButtonGroup buttonGroup= new ButtonGroup();
        rotate= new JToggleButton("rotate");
        resize= new JToggleButton("resize");
        move= new JToggleButton("move");
        buttonGroup.add(rotate);
        buttonGroup.add(resize);
        buttonGroup.add(move);
        JToolBar sub= new JToolBar(SwingConstants.HORIZONTAL);
        //sub.setLayout(new BoxLayout(sub, BoxLayout.X_AXIS));
        sub.add(previous);
        previous.addActionListener(l);
        sub.add(next);
        next.addActionListener(l);
        sub.add(reset);
        reset.addActionListener(l);
        sub.add(rotate);
        rotate.addActionListener(l);
        sub.add(resize);
        resize.addActionListener(l);
        sub.add(move);
        move.addActionListener(l);
        
        setLayout(new BorderLayout());

        add(new JScrollPane(editor), BorderLayout.CENTER);
        add(sub, BorderLayout.SOUTH);
        setPreferredSize(new Dimension(640, 480));
        
    }

    public void setGroup(AbsoluteGroup group) {
        editor.setGroup(group);
    }

    public AbsoluteGroup getGroup() {
        return editor.getGroup();
    }

    private class GroupEditorDialogListener implements ActionListener {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == next) {
                editor.next();
            }
            else if (e.getSource() == previous)
                editor.previous();
            else if (e.getSource() == rotate)
                editor.setMode(GroupEditor.ROTATION);
            else if (e.getSource()== resize)
                editor.setMode(GroupEditor.RESIZE);
            else if (e.getSource() == reset)
                editor.resetSign();
            else if (e.getSource() == move)
            	editor.setMode(GroupEditor.MOVE);
        }

    }

}