package com.talentPool.common.xmlutils;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.NamespaceSupport;
import org.xml.sax.helpers.XMLFilterImpl;

public class XMLWriter extends XMLFilterImpl {
	private final Attributes EMPTY_ATTS;
	private Map<String, String> prefixTable;
	private Map<String, Boolean> forcedDeclTable;
	private Map<String, String> doneDeclTable;
	private int elementLevel;
	private Writer output;
	private NamespaceSupport nsSupport;
	private int prefixCounter;

	public XMLWriter() {
		EMPTY_ATTS = new AttributesImpl();
		elementLevel = 0;
		prefixCounter = 0;
		init(null);
	}

	public XMLWriter(Writer writer) {
		EMPTY_ATTS = new AttributesImpl();
		elementLevel = 0;
		prefixCounter = 0;
		init(writer);
	}

	public XMLWriter(XMLReader xmlreader) {
		super(xmlreader);
		EMPTY_ATTS = new AttributesImpl();
		elementLevel = 0;
		prefixCounter = 0;
		init(null);
	}

	public XMLWriter(XMLReader xmlreader, Writer writer) {
		super(xmlreader);
		EMPTY_ATTS = new AttributesImpl();
		elementLevel = 0;
		prefixCounter = 0;
		init(writer);
	}

	private void init(Writer writer) {
		setOutput(writer);
		nsSupport = new NamespaceSupport();
		prefixTable = new HashMap<String, String>();
		forcedDeclTable = new HashMap<String, Boolean>();
		doneDeclTable = new Hashtable<String, String>();
	}

	public void reset() {
		elementLevel = 0;
		prefixCounter = 0;
		nsSupport.reset();
	}

	public void flush() throws IOException {
		output.flush();
	}

	public void setOutput(Writer writer) {
		if (writer == null) {
			output = new OutputStreamWriter(System.out);
		} else {
			output = writer;
		}
	}

	public void setPrefix(String s, String s1) {
		prefixTable.put(s, s1);
	}

	public String getPrefix(String s) {
		return prefixTable.get(s);
	}

	public void forceNSDecl(String s) {
		forcedDeclTable.put(s, Boolean.TRUE);
	}

	public void forceNSDecl(String s, String s1) {
		setPrefix(s, s1);
		forceNSDecl(s);
	}

	public void startDocument() throws SAXException {
		reset();
		write("<?xml version=\"1.0\" standalone=\"yes\"?>\n\n");
		super.startDocument();
	}

	public void endDocument() throws SAXException {
		write('\n');
		super.endDocument();
		try {
			flush();
		} catch (IOException ioexception) {
			throw new SAXException(ioexception);
		}
	}

	public void startElement(String s, String s1, String s2, Attributes attributes) throws SAXException {
		elementLevel++;
		nsSupport.pushContext();
		write('<');
		writeName(s, s1, s2, true);
		writeAttributes(attributes);
		if (elementLevel == 1) {
			forceNSDecls();
		}
		writeNSDecls();
		write('>');
		super.startElement(s, s1, s2, attributes);
	}

	public void endElement(String s, String s1, String s2) throws SAXException {
		write("</");
		writeName(s, s1, s2, true);
		write('>');
		if (elementLevel == 1) {
			write('\n');
		}
		super.endElement(s, s1, s2);
		nsSupport.popContext();
		elementLevel--;
	}

	public void characters(char ac[], int i, int j) throws SAXException {
		writeEsc(ac, i, j, false);
		super.characters(ac, i, j);
	}

	public void ignorableWhitespace(char ac[], int i, int j) throws SAXException {
		writeEsc(ac, i, j, false);
		super.ignorableWhitespace(ac, i, j);
	}

	public void processingInstruction(String s, String s1) throws SAXException {
		write("<?");
		write(s);
		write(' ');
		write(s1);
		write("?>");
		if (elementLevel < 1) {
			write('\n');
		}
		super.processingInstruction(s, s1);
	}

	public String doubleEscape(String s) {
		try {
			return s.replaceAll(">", "&gt;").replaceAll("<", "&lt;");
		} catch (NullPointerException e) {
			return "";
		}
	}

	public void emptyElement(String s, String s1, String s2, Attributes attributes) throws SAXException {
		nsSupport.pushContext();
		write('<');
		writeName(s, s1, s2, true);
		writeAttributes(attributes);
		if (elementLevel == 1) {
			forceNSDecls();
		}
		writeNSDecls();
		write("/>");
		super.startElement(s, s1, s2, attributes);
		super.endElement(s, s1, s2);
	}

	public void startElement(String s, String s1) throws SAXException {
		startElement(s, s1, "", EMPTY_ATTS);
	}

	public void startElement(String s) throws SAXException {
		startElement("", s, "", EMPTY_ATTS);
	}

	public void endElement(String s, String s1) throws SAXException {
		endElement(s, s1, "");
	}

	public void endElement(String s) throws SAXException {
		endElement("", s, "");
	}

	public void emptyElement(String s, String s1) throws SAXException {
		emptyElement(s, s1, "", EMPTY_ATTS);
	}

	public void emptyElement(String s) throws SAXException {
		emptyElement("", s, "", EMPTY_ATTS);
	}

	public void dataElement(String s, String s1, String s2, Attributes attributes, String s3) throws SAXException {
		startElement(s, s1, s2, attributes);
		characters(s3);
		endElement(s, s1, s2);
	}

	public void dataElement(String s, String s1, String s2) throws SAXException {
		dataElement(s, s1, "", EMPTY_ATTS, s2);
	}

	public void dataElement(String s, String s1) throws SAXException {
		dataElement("", s, "", EMPTY_ATTS, s1);
	}

	public void characters(String s) throws SAXException {
		char ac[] = s.toCharArray();
		characters(ac, 0, ac.length);
	}

	public void cdatacharacters(String s) throws SAXException {
		write("<![CDATA[");
		char ac[] = s.toCharArray();
		super.characters(ac, 0, ac.length);
		write("]]>");
	}

	private void forceNSDecls() {
		String s;
		for (Iterator<String> itr = forcedDeclTable.keySet().iterator(); itr.hasNext(); doPrefix(s, null, true)) {
			s = itr.next();
		}
	}

	private String doPrefix(String s, String s1, boolean flag) {
		String s2 = nsSupport.getURI("");
		if ("".equals(s)) {
			if (flag && s2 != null) {
				nsSupport.declarePrefix("", "");
			}
			return null;
		}
		String s3;
		if (flag && s2 != null && s.equals(s2)) {
			s3 = "";
		} else {
			s3 = nsSupport.getPrefix(s);
		}
		if (s3 != null) {
			return s3;
		}
		s3 = doneDeclTable.get(s);
		if (s3 != null && ((!flag || s2 != null) && "".equals(s3) || nsSupport.getURI(s3) != null)) {
			s3 = null;
		}
		if (s3 == null) {
			s3 = prefixTable.get(s);
			if (s3 != null && ((!flag || s2 != null) && "".equals(s3) || nsSupport.getURI(s3) != null)) {
				s3 = null;
			}
		}
		if (s3 == null && s1 != null && !"".equals(s1)) {
			int i = s1.indexOf(':');
			if (i == -1) {
				if (flag && s2 == null) {
					s3 = "";
				}
			} else {
				s3 = s1.substring(0, i);
			}
		}
		for (; s3 == null || nsSupport.getURI(s3) != null; s3 = "__NS" + ++prefixCounter) {
			;
		}
		nsSupport.declarePrefix(s3, s);
		doneDeclTable.put(s, s3);
		return s3;
	}

	private void write(char c) throws SAXException {
		try {
			output.write(c);
		} catch (IOException ioexception) {
			throw new SAXException(ioexception);
		}
	}

	private void write(String s) throws SAXException {
		try {
			output.write(s);
		} catch (IOException ioexception) {
			throw new SAXException(ioexception);
		}
	}

	private void writeAttributes(Attributes attributes) throws SAXException {
		int i = attributes.getLength();
		for (int j = 0; j < i; j++) {
			char ac[] = attributes.getValue(j).toCharArray();
			write(' ');
			writeName(attributes.getURI(j), attributes.getLocalName(j), attributes.getQName(j), false);
			write("=\"");
			writeEsc(ac, 0, ac.length, true);
			write('"');
		}

	}

	private void writeEsc(char ac[], int i, int j, boolean flag) throws SAXException {
		for (int k = i; k < i + j; k++) {
			switch (ac[k]) {
			case 38: // '&'
				write("&amp;");
				break;

			case 60: // '<'
				write("&lt;");
				break;

			case 62: // '>'
				write("&gt;");
				break;

			case 34: // '"'
				if (flag)
					write("&quot;");
				else
					write('"');
				break;

			default:
				if (ac[k] > '\177') {
					write("&#");
					write(Integer.toString(ac[k]));
					write(';');
				} else {
					write(ac[k]);
				}
				break;
			}
		}
	}

	private void writeNSDecls() throws SAXException {
		for (Enumeration enumeration = nsSupport.getDeclaredPrefixes(); enumeration.hasMoreElements(); write('"')) {
			String s = (String) enumeration.nextElement();
			String s1 = nsSupport.getURI(s);
			if (s1 == null) {
				s1 = "";
			}
			char ac[] = s1.toCharArray();
			write(' ');
			if ("".equals(s)) {
				write("xmlns=\"");
			} else {
				write("xmlns:");
				write(s);
				write("=\"");
			}
			writeEsc(ac, 0, ac.length, true);
		}
	}

	private void writeName(String s, String s1, String s2, boolean flag) throws SAXException {
		String s3 = doPrefix(s, s2, flag);
		if (s3 != null && !"".equals(s3)) {
			write(s3);
			write(':');
		}
		write(s1);
	}

}