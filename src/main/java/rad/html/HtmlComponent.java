package rad.html;

import java.util.regex.Matcher;

import rad.RadComponent;

public abstract class HtmlComponent implements RadComponent {
	protected String name;
	protected String className = null;
	protected boolean visible = true;
	protected boolean readOnly = false;
	protected boolean defaultValue = false;
	protected boolean defaultVisible = false;
	protected boolean defaultReadOnly = false;
	protected boolean useDataValue = false;
	protected boolean useValue = false;
	protected String radInit;

	private WebService webService = null;

	public HtmlComponent(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getRadInit() {
		return radInit;
	}

	public void setRadInit(String radInit) {
		this.radInit = radInit;
	}

	public WebService getWebService() {
		return webService;
	}

	public void setWebService(WebService webService) {
		this.webService = webService;
	}

	public void setValue(String value) {
		defaultValue = false;
	}

	public String getValue() {
		return null;
	}

	public void setDefault() {
		defaultValue = true;
		defaultVisible = true;
		defaultReadOnly = true;
	}

	public void setReadOnly(boolean readonly) {
		defaultReadOnly = false;
		this.readOnly = readonly;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setVisible(boolean visible) {
		defaultVisible = false;
		this.visible = visible;
	}

	/*
	public String getRequestField() {
		if (getWebService() != null)
			return getWebService().getRequestField();
		return "[],[]";
	}
*/
	public String getRADEvent(String eventName) {
		if (webService==null) return "";
		return "RADEvent('" + getName() + "','" + getWebService().getApiUrl() + "','" + webService.getName()
				+ "','"+eventName+"')" ;					
	}
	
	public String getResponseFunctions() {
		return null;
	}

	public String getResponseProperties() {
		if (defaultValue || !useValue)
			return null;
		return "[\"id" + getName() + "\",\"value\"," + JSONString(getValue()) + "]";
	}

	public String getResponseDataValues() {
		if (defaultValue || !useDataValue)
			return null;
		return "[\"id" + getName() + "\"," + JSONString(getValue()) + "]";
	}

	public String getResponseClasses() {
		if (defaultVisible)
			return null;
		return "[\"id" + getName() + "\",\"hidden\"," + !visible + "]";
	}

	public void triggerEvent(String eventName) {
	}

	public abstract String getHtml();

	static public String HTMLString(String value) {
		if (value == null) {
			return "";
		}
		return value.replaceAll("<", "&lt;");
	}

	static public String JSONString(String value) {
		if (value == null) {
			return "\"\"";
		}
		return "\"" + value.replaceAll("\"", Matcher.quoteReplacement("\\\"")).replaceAll("\n",
				Matcher.quoteReplacement("\\n")) + "\"";
	}

	protected String HtmlReadOnly() {
		if (readOnly) {
			return "readonly ";
		}
		return "";
	}

	protected String htmlInit() {		
		if (radInit != null) {
			return htmlClass() + "RADinit=\"" + radInit + "\" ";
		}
		return "";
	}
	
	protected String htmlExtra() {
		return htmlInit()+htmlClass()+htmlServiceValue()+htmlServiceDataValue();
	}
	protected String htmlClass() {
		String htmlClass;
		if (className == null) {
			htmlClass = "";
		} else {
			htmlClass = className;
		}
		if (!visible) {
			htmlClass += " hidden";
		}
		if (htmlClass.equals("")) {
			return "";
		}
		return "class = \"" + htmlClass + "\" ";
	}

	protected String htmlServiceValue() {
		if (useValue && getWebService() != null)
			return "RADWebServiceValue"+ getWebService().getName() + " ";
		return "";
	}

	protected String htmlServiceDataValue() {
		if (useDataValue && getWebService() != null)
			return "RADWebServiceDataValue" + getWebService().getName() + " ";
		return "";
	}

}
