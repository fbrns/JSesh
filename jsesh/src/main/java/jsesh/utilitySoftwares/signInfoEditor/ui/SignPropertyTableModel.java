package jsesh.utilitySoftwares.signInfoEditor.ui;

import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import jsesh.utilitySoftwares.signInfoEditor.model.EditableSignInfo;
import jsesh.utilitySoftwares.signInfoEditor.model.XMLInfoProperty;

/**
 * Base class for table model linked to a specific sign property.
 * <p>
 * Should deal more or less directly with any type of property which is only
 * made of Strings. For other cases, it might be necessary to overwrite
 * setValueAt and getValueAt, maybe using the methods from this class in cases
 * where a String does the job.
 * 
 * @author rosmord
 * 
 */
public abstract class SignPropertyTableModel extends AbstractTableModel
		implements GrowableModel {
	protected EditableSignInfo editableSignInfo;

	protected String[] columnNames;
	/**
	 * A list of SignInfoProperty.
	 */
	protected List signProperties;

	protected List attributeNames;

	/**
	 * Create a table model for a certain property of this sign
	 * 
	 * @param editableSignInfo
	 * @param propertyTag
	 *            the XML tag name for the property.
	 * @param attributeNames
	 *            the attributes corresponding to each column.
	 */
	public SignPropertyTableModel(EditableSignInfo editableSignInfo,
			String propertyTag, String[] columnNames, String[] attributeNames) {
		this.editableSignInfo = editableSignInfo;
		this.attributeNames = Arrays.asList(attributeNames);
		signProperties = editableSignInfo.getPropertyList(propertyTag);
		this.columnNames = columnNames;
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return signProperties.size();
	}

	public String getColumnName(int column) {
		return columnNames[column];
	}

	protected String getAttributeName(int column) {
		return (String) attributeNames.get(column);
	}

	protected int getColumnForAttribute(String attributeName) {
		return attributeName.indexOf(attributeName);
	}

	protected XMLInfoProperty getRow(int rowIndex) {
		return (XMLInfoProperty) signProperties.get(rowIndex);
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return getRow(rowIndex).isUserDefinition();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jsesh.utilitySoftwares.signInfoEditor.GrowableModel#removeRow(int)
	 */
	public boolean removeRow(int row) {
		XMLInfoProperty property = getRow(row);
		if (property.isUserDefinition()) {
			signProperties.remove(row);
			editableSignInfo.remove(property);
			
			fireTableRowsDeleted(row, row);
			return true;
		} else
			return false;
	}

	
	public boolean canRemove(int row) {
		if (row == -1)
			return false;
		else {
			XMLInfoProperty property = getRow(row);
			return property.isUserDefinition();
		}
	}

	/**
	 * Return the value of the cell content, as a String.
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		XMLInfoProperty p = getRow(rowIndex);
		String attributeName = getAttributeName(columnIndex);
		return p.get(attributeName);
	}

	/**
	 * Sets the value of the cell content, as a String.
	 */
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		XMLInfoProperty p = getRow(rowIndex);
		if (!p.isUserDefinition())
			return;
		String attributeName = getAttributeName(columnIndex);
		p.setAttribute(attributeName, (String) value);
		fireTableCellUpdated(rowIndex, columnIndex);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jsesh.utilitySoftwares.signInfoEditor.GrowableModel#addRow(java.lang.String)
	 */
	public final void addRow(String code) {
		XMLInfoProperty property = buildDefaultSignProperty(code);
		editableSignInfo.add(property);
		signProperties.add(property);
		fireTableRowsInserted(getRowCount() - 1, getColumnCount() - 1);
	}

	/**
	 * Add a property to the current sign.
	 * 
	 * @param value
	 * @return
	 */
	abstract protected XMLInfoProperty buildDefaultSignProperty(String value);

	public Class getColumnClass(int columnIndex) {
		return String.class;
	}
}
