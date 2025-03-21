package nnsp.common;

import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

import nnsp.util.StringUtil;

/**
 * <pre>
 * Config 파일 Load Class
 * </pre>
 */
public class Config {
	private static Config config;

	/**
	 * Config constructor
	 */
	private Config() {
		try {
			this.divCode = Integer.parseInt(StringUtil.NVL(getProperty("nncCfg.divCode"), "1"));
			this.model = StringUtil.NVL(getProperty("nncCfg.product.model"), "single");
			
			this.sndPath = getProperty("nncCfg.policy.sndPath");
			this.rcvPath = getProperty("nncCfg.policy.rcvPath");
			this.transPath = getProperty("nncCfg.policy.transPath");
			
			this.perPage = Integer.parseInt(StringUtil.NVL(getProperty("nncCfg.common.pageRecord"), "10"));
			this.pageBlockSize = Integer.parseInt(StringUtil.NVL(getProperty("nncCfg.common.pageBlockSize"), "10"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Config(Locale locale){
		this.locale = locale;
	}

	/**
	 * 
	 * <pre>
	 * Config Instance
	 * </pre>
	 * 
	 * @return instance
	 * @return Config
	 */
	public static Config getInstance() {
		if (Config.config == null) {
			config = new Config();
		}
		return config;
	}

	private int divCode;
	private String model;
	
	private String sndPath;
	private String rcvPath;
	private String transPath;

	private int perPage;
	private int pageBlockSize;
	
	private Locale locale;

	public Locale getLocale() {
		return locale;
	}
	
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getTransPath() {
		return transPath;
	}

	public void setTransPath(String transPath) {
		this.transPath = transPath;
	}

	public String getSndPath() {
		return sndPath;
	}

	public void setSndPath(String sndPath) {
		this.sndPath = sndPath;
	}

	public String getRcvPath() {
		return rcvPath;
	}

	public void setRcvPath(String rcvPath) {
		this.rcvPath = rcvPath;
	}

	public int getDivCode() {
		return divCode;
	}

	public void setDivCode(int divCode) {
		this.divCode = divCode;
	}

	public int getPerPage() {
		return perPage;
	}

	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}

	public int getPageBlockSize() {
		return pageBlockSize;
	}
	
	public void setPageBlockSize(int pageBlockSize) {
		this.pageBlockSize = pageBlockSize;
	}

	/**
	 * <pre>
	 * keyName에 해당하는 Config properties 조회
	 * </pre>
	 * 
	 * @param keyName
	 * @return Config property
	 * @return String
	 */
	public String getProperty(String keyName) {
		InputStream is = getClass().getResourceAsStream("/config.properties");
		Properties props = new Properties();

		try {
			props.load(is);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return props.getProperty(keyName);
	}
}