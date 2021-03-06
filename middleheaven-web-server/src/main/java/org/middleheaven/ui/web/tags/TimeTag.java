package org.middleheaven.ui.web.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import org.middleheaven.global.text.TimepointFormatter;
import org.middleheaven.quantity.time.TimeUtils;

public class TimeTag extends AbstractTagSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5193504676600735939L;
	private Object value;

	public void setValue(Object value) {
		this.value = value;
	}
	
	public int doStartTag() throws JspException {
		try {

			pageContext.getOut().print(localize(TimeUtils.from(value), TimepointFormatter.Format.TIME_ONLY));

			return SKIP_BODY;
		} catch (Exception e) {
			throw new JspTagException(e.getMessage());
		}
	}

	public int doEndTag() {
		return EVAL_PAGE;
	}

}
