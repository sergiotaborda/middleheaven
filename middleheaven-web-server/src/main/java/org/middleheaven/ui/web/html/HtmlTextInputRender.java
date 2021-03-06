package org.middleheaven.ui.web.html;

import static org.middleheaven.util.SafeCastUtils.safeCast;

import java.io.IOException;
import java.io.Writer;

import org.middleheaven.ui.UIComponent;
import org.middleheaven.ui.UIReadState;
import org.middleheaven.ui.components.UIField;
import org.middleheaven.ui.rendering.RenderingContext;

/**
 * Text input render
 */
public class HtmlTextInputRender extends AbstractHtmlInputRender {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 * @param b
	 */
	public HtmlTextInputRender( ) {

	}


	
	@Override
	public void write(HtmlDocument document, RenderingContext context,UIComponent component) throws IOException {

		UIField comp =  safeCast(component, UIField.class).get();


		UIReadState state = comp.getReadStateProperty().get();

		if (state == null){
			state = UIReadState.INPUT_ENABLED;
			
		}
		Writer writer = document.getBodyWriter();
		
		
		String strValue = "";
		final Object value = comp.getValueProperty().get();
		if (value != null ){
			if (comp.getFormaterProperty().get() == null){
				strValue = value.toString();
			} else {
				strValue = comp.getFormaterProperty().get().format(value);
			}
		}
		
		String name = comp.getNameProperty().get();
		
		if (!state.isVisible()){
			writer.append("<input ")
			.append(" id=\"" + component.getGID() + "\"")
			.append(" name=\"" + name + "\"")
			.append(" value=\"" + strValue + "\"")
			.append(" type=\"hidden\"")
			.append(" class=\"mh-ui-input\"" )
			.append(" uiType=\"").append("input").append("\"")
			.append("/>");
		} else if (!state.isEditable()){
			writer.append("<input ");
			writer.append(" id=\"" + component.getGID() + "\"");
			writer.append(" name=\"" + name + "\"");
			writer.append(" value=\"" + strValue + "\"");
			writer.append(" type=\"hidden\"");
			writer.append("/>");
			writer.append("<span class=\"readOnlyField\"");
			writer.append(" id=\"" + component.getGID() + "\"")
			.append(" class=\"mh-ui-input\"" )
			.append(" uiType=\"").append("input").append("\"")
			.append(">")
			.append(strValue)
			.append("</span>");
		} else {
			writer.append("<input ");
			writer.append(" id=\"" + component.getGID() + "\"");
			writer.append(" name=\"" + name + "\"");
			writer.append(" value=\"" + strValue + "\"")
			.append(" class=\"mh-ui-input\"" )
			.append(" uiType=\"").append("input").append("\"");
			
			writer.append(" type=\"text\"");
			if (!state.isEnabled()){
				writer.append(" disabled=\"disabled\" ");
			}
			
			final Integer maxLength = comp.getMaxLengthProperty().get();
			if (maxLength  > 0 ){
				writer.append(" maxlength=\"" + maxLength + "\"");
			}
			
			writer.append("/>");
		}

		
	

		


		



	}

}
