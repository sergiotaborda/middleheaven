/**
 * 
 */
package org.middleheaven.storage.types;

import java.util.Date;

import org.middleheaven.persistance.DataRow;
import org.middleheaven.persistance.model.DataColumnModel;
import org.middleheaven.quantity.time.CalendarDate;
import org.middleheaven.quantity.time.TimeUtils;

/**
 * 
 */
public class DateTypeMapper implements TypeMapper {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getMappedClassName() {
		return Date.class.getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object read(DataRow row, Object aggregateParent, DataColumnModel... columns) {
		return (Date) row.getColumn(columns[0].getName()).getValue();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(Object parent, Object object, DataRow row, DataColumnModel... columns) {

		row.getColumn(columns[0].getName()).setValue(TimeUtils.toDate((CalendarDate)object));

	}

}
