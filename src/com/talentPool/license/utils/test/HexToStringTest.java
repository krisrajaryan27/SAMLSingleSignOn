/**
 * 
 */
package com.talentPool.license.utils.test;

import com.talentPool.license.utils.HexToString;

import junit.framework.TestCase;

/**
 * @author pallavi
 * @date Jan 11, 2007
 */
public class HexToStringTest extends TestCase {
	public void testNull() {
		String hexText = null;
		String plainText = HexToString.convert(hexText);
		assertNotNull("Wrong Result", plainText);
		assertEquals("Wrong Result", "", plainText);
	}
	
	public void testEmpty() {
		String hexText = "";
		String plainText = HexToString.convert(hexText);
		assertNotNull("Wrong Result", plainText);
		assertEquals("Wrong Result", "", plainText);
	}
	
	public void testValid() {
		String hexText = "31312d31312d30302d3131";
		String plainText = HexToString.convert(hexText);
		assertNotNull("Wrong Result", plainText);
		assertEquals("Wrong Result", "11-11-00-11", plainText);
	}
	
	public void test_I_Valid() {		
		String hexText = "30302d31362d37362d35452d37442d46367c54616c656e7469636120536f6674776172657c4265746120312e30";
		String plainText = HexToString.convert(hexText);
		assertNotNull("Wrong Result", plainText);
		assertEquals("Wrong Result", "00-16-76-5E-7D-F6|Talentica Software|Beta 1.0", plainText);
	}
	
	public void test_II_Valid() {		
		String hexText = "30302d31362d37362d35452d37442d46367c54616c656e746963617c4265746120312e307c31382f312f32303037";
		String plainText = HexToString.convert(hexText);
		assertNotNull("Wrong Result", plainText);
		assertEquals("Wrong Result", "00-16-76-5E-7D-F6|Talentica|Beta 1.0|18/1/2007", plainText);
	}
	
	public void test_III_Valid() {		
		String hexText = "30302d31362d37362d35452d37442d46367c54616c656e74506f6f6c7c54616c656e74506f6f6c7c4265746120312e307c32302f312f32303037";
		String plainText = HexToString.convert(hexText);
		assertNotNull("Wrong Result", plainText);
		assertEquals("Wrong Result", "00-16-76-5E-7D-F6|TalentPool|TalentPool|Beta 1.0|20/1/2007", plainText);
	}
	
	public void test_IV_Valid() {		
		String hexText = "30302d31362d37362d35452d37442d46367c54616c656e7469636120536f6674776172657c54616c656e74506f6f6c7c4265746120312e307c32302f312f323030377c32317c4576616c756174696f6e20436f7079";
		String plainText = HexToString.convert(hexText);
		assertNotNull("Wrong Result", plainText);
		assertEquals("Wrong Result", "00-16-76-5E-7D-F6|Talentica Software|TalentPool|Beta 1.0|20/1/2007|21|Evaluation Copy", plainText);
		String[] parts = "00-16-76-5E-7D-F6|Talentica Software|TalentPool|Beta 1.0|20/1/2007|21|Evaluation Copy".split("[|]");
		System.out.println(parts[0]);
		System.out.println(parts[1]);
		System.out.println(parts[2]);
		System.out.println(parts[3]);
		System.out.println(parts[4]);
		System.out.println(parts[5]);
	}	
}
