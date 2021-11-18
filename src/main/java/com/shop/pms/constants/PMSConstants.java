/**
 * 
 */
package com.shop.pms.constants;

/**
 * @author muthu
 *
 */
public enum PMSConstants {
	
	RESTORE("restore"),
	YES("Yes"),
	NO("No");
	
	private String value ;
	
	PMSConstants(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
