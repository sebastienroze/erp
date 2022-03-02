package rad.html;


public class HtmlImage extends HtmlComponent {
private String src;

	public HtmlImage(String name, String src) {
		super(name);
		this.src = src;
	}
	
	@Override
	public String getHtml(){

		return
				"<img "
				+ "name=\""+getName()+"\" "
				+ "id=\"id"+getName()+"\" "
				+ "src=\""+src+"\" "
				+htmlExtra()				
				+ ">";
	}
	@Override
	public String getResponseProperties() {
		return null;
	}	
}
