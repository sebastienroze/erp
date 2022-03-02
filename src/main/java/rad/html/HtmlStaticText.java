package rad.html;

import rad.RadStaticText;

public class HtmlStaticText extends HtmlComponent implements RadStaticText {
	private String value;

	public HtmlStaticText(String nomChamp) {
		super(nomChamp);
	}

	@Override
	public String getHtml() {
		return "<span " + "name = \"" + getName() + "\"" + "id = \"id" + getName() + "\"" 
				+ htmlExtra() + ">"+getValue()+"</span>";
	}

	@Override
	public void setValue(String value) {
		super.setValue(value);
		this.value = value;
	}

	@Override
	public String getValue() {
		return value;
	}

	public int getIntegerValue() {
		int val = 0;
		try {
			val = Integer.parseInt(value);
		} catch (Exception e) {	}
		return val;

	}

	public long getLongValue() {
		long val = 0;
		try {
			val = Long.parseLong(value);
		} catch (Exception e) {}
		return val;
	}

	@Override
	public String getResponseProperties() {
		return "[\"id"+getName()+"\",\"textContent\","+ JSONString(getValue())+"]";		
	}
}
