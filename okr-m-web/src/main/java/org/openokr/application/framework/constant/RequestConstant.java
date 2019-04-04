package org.openokr.application.framework.constant;

/**
 * http/https request常量
 * 
 * @author xiezm
 */
public class RequestConstant extends ProtocolConstant {

	/**
	 * 请求header的key
	 * 
	 * @author xiezm
	 */
	public enum HeaderKey {
		X_Requested_With("X-Requested-With"), Accept_Encoding("Accept-Encoding"), Accept_Language("Accept-Language"), Connection(
				"Connection"), Content_Length("Content-Length"), Content_Type("Content-Type"), User_Agent("User-Agent");

		private final String value;

		public String getValue() {
			return value;
		}

		HeaderKey(String value) {
			this.value = value;
		}
	}
	/**
	 * 请求header的value
	 * 
	 * @author xiezm
	 */
	public enum HeaderValue {
		XMLHttpRequest("XMLHttpRequest");

		private final String value;

		public String getValue() {
			return value;
		}

		HeaderValue(String value) {
			this.value = value;
		}
	}
	/**
	 * 请求方法
	 * 
	 * @author xiezm
	 */
	public enum MethodType {
		GET("GET"), POST("POST"), DEFAULT("POST");

		private final String value;

		public String getValue() {
			return value;
		}

		MethodType(String value) {
			this.value = value;
		}
	}

	/**
	 * 字符集
	 * 
	 * @author xiezm
	 */
	public enum Charset {
		UTF_8("UTF-8"), ISO_8859_1("ISO-8859-1"), GBK("GBK");

		private final String value;

		public String getValue() {
			return value;
		}

		Charset(String value) {
			this.value = value;
		}
	}
}
