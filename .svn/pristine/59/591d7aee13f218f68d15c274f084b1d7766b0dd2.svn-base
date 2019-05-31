/**
 * 
 */
package com.talentPool.positions.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.positions.PositionParserConstants;
import com.talentPool.positions.utils.PositionParser;

import junit.framework.TestCase;

/**
 * @author Sanjay
 *
 */
public class PositionParserTest extends TestCase {	
	PositionParser parser = null;
	PositionParserTestHelper helper = null;
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		parser = new PositionParser();
		helper = new PositionParserTestHelper();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
		parser = null;
	}

	public void testNull_MatchKey() {
		String line = null;
		String key = parser.matchKey(line);
		assertNotNull(key);
		assertEquals("", key);
	}
	
	public void testEmpty_MatchKey() {
		String line = "";		
		String key = parser.matchKey(line);
		assertNotNull(key);
		assertEquals("", key);
	}
	
	public void testInvalid_I_MatchKey() {
		String line = "POSITION NAME";		
		String key = parser.matchKey(line);	
		assertNotNull(key);
		assertEquals("", key);
	}
	
	public void testInvalid_II_MatchKey() {
		String line = "POSITION           NAME";		
		String key = parser.matchKey(line);
		assertNotNull(key);
		assertEquals("", key);
	}
	
	public void testValid_I_MatchKey() {
		String line = "Position Name:";
		String key = parser.matchKey(line);
		assertNotNull(key);
		assertEquals("Position Name", key);
	}
	
	public void testValid_II_MatchKey() {
		String line = "      Position        Name     :   ";
		String key = parser.matchKey(line);
		assertNotNull(key);		
		assertEquals("Position Name", key);
	}
	
	public void testValid_III_MatchKey() {
		String line = "\n      \t\tPosition   \t\t\n     Name     \n\n:   ";
		String key = parser.matchKey(line);
		assertNotNull(key);		
		assertEquals("Position Name", key);
	}
	
	public void testValid_IV_MatchKey() {
		String line = "\n      \t\tposition   \t\t\n     name     \n\n:   ";
		String key = parser.matchKey(line);
		assertNotNull(key);		
		assertEquals("Position Name", key);
	}
	
	public void testNull_GetWords() {
		String line = null;
		List<String> words = parser.getWords(line);
		assertNull(words);
	}
	
	public void testEmpty_GetWords() {
		String line = "";
		List<String> words = parser.getWords(line);
		assertNull(words);
	}
	
	public void testValid_I_GetWords() {
		String line = "word1";
		List<String> words = parser.getWords(line);
		assertNotNull(words);
		assertEquals(1, words.size());
		assertEquals("Word1", words.get(0));
	}
	
	public void testValid_II_GetWords() {
		String line = "\nword1\n\tword1\t\tword1";;
		List<String> words = parser.getWords(line);
		assertNotNull(words);
		assertEquals(3, words.size());
		assertEquals("Word1", words.get(0));
		assertEquals("Word1", words.get(1));
		assertEquals("Word1", words.get(2));
	}
	
	public void testValid_III_GetWords() {
		String line = "\n\n\n\t\t\tword1\n\n\n\n";
		List<String> words = parser.getWords(line);
		assertNotNull(words);
		assertEquals(1, words.size());
		assertEquals("Word1", words.get(0));
	}
	
	public void testNull_GetPositionDefinitions() {
		String contents = null;
		List<String> positions = parser.getPositionDefinitions(contents);
		assertNull(positions);
	}
	
	public void testEmpty_GetPositionDefinitions() {
		String contents = "";
		List<String> positions = parser.getPositionDefinitions(contents);
		assertNull(positions);
	}
	
	public void testValid_I_GetPositionDefinitions() {
		String contents = helper.getPosition();
		List<String> positions = parser.getPositionDefinitions(contents);
		assertNotNull(positions);
		assertEquals(1, positions.size());
		assertEquals(helper.getPosition().trim(), positions.get(0));
	}
	
	public void testValid_II_GetPositionDefinitions() {
		String contents = helper.getMultiplePositions(4);
		List<String> positions = parser.getPositionDefinitions(contents);
		assertNotNull(positions);
		assertEquals(4, positions.size());
		String expected = helper.getPosition();
		assertEquals(expected.trim(), positions.get(0).trim());
		assertEquals(expected.trim(), positions.get(1).trim());
		assertEquals(expected.trim(), positions.get(2).trim());
		assertEquals(expected.trim(), positions.get(3).trim());
	}
	
	public void testValid_III_GetPositionDefinitions() {
		String contents = helper.getPosition();
		String temp = helper.getPosition();
		contents += "\t" + "#end" + System.getProperty("line.separator") + temp;
		List<String> positions = parser.getPositionDefinitions(contents);
		assertNotNull(positions);
		assertEquals(2, positions.size());
		String expected = helper.getPosition();
		assertEquals(expected.trim(), positions.get(0).trim());
		assertEquals(expected.trim(), positions.get(1).trim());
	}
	
	public void testNull_ParsePosition() {
		try {
			String contents = null;
			Map<String, String> position = parser.parsePosition(contents, null);
			assertNotNull(position);
			assertEquals(25, position.size());
			Iterator<String> itr = position.keySet().iterator();
			while(itr.hasNext()) {
				String key = itr.next();
				assertEquals(key + " should be blank","", position.get(key));
			}
		} catch (Exception e) {
			fail();
		}
	}
	
	public void testEmpty_ParsePosition() {
		try {
			String contents = "";
			Map<String, String> position = parser.parsePosition(contents, null);
			assertNotNull(position);
			assertEquals(25, position.size());
			Iterator<String> itr = position.keySet().iterator();
			while(itr.hasNext()) {
				String key = itr.next();
				assertEquals(key + " should be blank","", position.get(key));
			}
		} catch (Exception e) {
			fail();
		}
	}
	
	public void testInvalid_ParsePosition() {
		try {
			String contents = helper.getPosition();
			contents += "\nMyTest:\n\tMytest";
			Map<String, String> position = parser.parsePosition(contents, null);
			assertNotNull(position.get(PositionParserConstants.PARSING_ERRORS));
			assertEquals("<br/>Unknown attribute&nbsp;MyTest&nbsp;on line number&nbsp;65", position.get(PositionParserConstants.PARSING_ERRORS));
		} catch (Exception e) {
			fail();			
		}
	}
	
	public void testValid_I_ParsePosition() {
		try {
			String contents = helper.getPosition();
			Map<String, String> position = parser.parsePosition(contents, null);
			assertNotNull(position);
			assertEquals(25, position.size());
			Iterator<String> itr = position.keySet().iterator();
			while(itr.hasNext()) {
				String key = itr.next();				
				assertNotSame("", position.get(key));
			}
		} catch (Exception e) {
			fail();
		}
	}
	
	public void testValid_II_ParsePosition() {
		try {
			String contents = helper.getPosition();
			contents += "\nMyTest:\n\tMytest";
			
			List<CustomFieldData> customFields = new ArrayList<CustomFieldData>();
			CustomFieldData customFieldData = new CustomFieldData();
			customFieldData.setFieldDisplayName("MyTest");
			customFields.add(customFieldData);
			
			Map<String, String> position = parser.parsePosition(contents, customFields);
			assertNotNull(position);
			assertEquals(26, position.size());
			Iterator<String> itr = position.keySet().iterator();
			while(itr.hasNext()) {
				String key = itr.next();				
				assertNotSame("", position.get(key));
			}
		} catch (Exception e) {
			fail();
		}
	}
	
	public void testNull_IsComment() {
		String line = null;
		boolean isComment = parser.isComment(line);
		assertFalse(isComment);
	}
	
	public void testEmpty_IsComment() {
		String line = "";
		boolean isComment = parser.isComment(line);
		assertFalse(isComment);
	}
	
	public void testInvalid_I_IsComment() {
		String line = "Hello world";
		boolean isComment = parser.isComment(line);
		assertFalse(isComment);
	}
	
	public void testInvalid_II_IsComment() {
		String line = "Hello world #COMMENT";
		boolean isComment = parser.isComment(line);
		assertFalse(isComment);
	}
	
	public void testValid_I_IsComment() {
		String line = "#COMMENT";
		boolean isComment = parser.isComment(line);
		assertTrue(isComment);
	}
	
	public void testValid_II_IsComment() {
		String line = "#comment";
		boolean isComment = parser.isComment(line);
		assertTrue(isComment);
	}
}
