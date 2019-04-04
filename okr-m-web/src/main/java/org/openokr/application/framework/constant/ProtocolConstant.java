package org.openokr.application.framework.constant;

/**
 * (http,https)协议常量
 * 
 * @author xiezm
 */
public class ProtocolConstant {

	/**
	 * 协议类型
	 * 
	 * @author xiezm
	 */
	public enum ProtocolType {
		HTTP("http"), HTTPS("https");

		private final String value;

		public String getValue() {
			return value;
		}

		ProtocolType(String value) {
			this.value = value;
		}
	}
	/**
	 * 数据类型
	 * 
	 * @author xiezm
	 */
	public enum DataType {
		STRING("string"), BYTES("bytes");

		private final String value;

		public String getValue() {
			return value;
		}

		DataType(String value) {
			this.value = value;
		}
	}
}
