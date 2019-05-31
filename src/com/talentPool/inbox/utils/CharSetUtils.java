/**
 * 
 */
package com.talentPool.inbox.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import javax.mail.Part;
import javax.mail.internet.ContentType;

import net.freeutils.charset.CCGSMCharset;
import net.freeutils.charset.CCPackedGSMCharset;
import net.freeutils.charset.HPRoman8Charset;
import net.freeutils.charset.ISO88596Charset;
import net.freeutils.charset.ISO88598Charset;
import net.freeutils.charset.KOI8UCharset;
import net.freeutils.charset.SCGSMCharset;
import net.freeutils.charset.SCPackedGSMCharset;
import net.freeutils.charset.UTF7Charset;
import net.freeutils.charset.UTF7OptionalCharset;

/**
 * @author shivprasad
 * 
 */
public class CharSetUtils {
	private static ArrayList<String> ccgsm;
	private static ArrayList<String> ccpgsm;
	private static ArrayList<String> hproman;
	private static ArrayList<String> iso88596;
	private static ArrayList<String> iso88598;
	private static ArrayList<String> koi;
	private static ArrayList<String> scgsm;
	private static ArrayList<String> scpgsm;
	private static ArrayList<String> utf7;
	private static ArrayList<String> utf7opt;
	private static ArrayList<String> userdefined;
	
	static {
		ccgsm = new ArrayList<String>();
		ccgsm.add("ccgsm");
		
		ccpgsm = new ArrayList<String>();
		ccpgsm.add("ccpgsm");

		hproman = new ArrayList<String>();
		hproman.add("hp-roman8");
		hproman.add("roman8");
		hproman.add("r8");
		hproman.add("cshproman8");
		hproman.add("x-roman8");

		iso88596 = new ArrayList<String>();
		iso88596.add("iso-8859-6-bidi");
		iso88596.add("csiso88596i");
		iso88596.add("iso-8859-6-i");
		iso88596.add("iso_8859-6-i");
		iso88596.add("csiso88596e");
		iso88596.add("iso-8859-6-e");
		iso88596.add("iso_8859-6-e");

		iso88598 = new ArrayList<String>();
		iso88598.add("iso-8859-8-bidi");
		iso88598.add("csiso88598i");
		iso88598.add("iso-8859-8-i");
		iso88598.add("iso_8859-8-i");
		iso88598.add("csiso88598e");
		iso88598.add("iso-8859-8-e");
		iso88598.add("iso_8859-8-e");

		koi = new ArrayList<String>();
		koi.add("koi8-u");
		koi.add("koi8-ru");

		scgsm = new ArrayList<String>();
		scgsm.add("scgsm");
		scgsm.add("gsm-default-alphabet");
		scgsm.add("gsm_0338");
		scgsm.add("gsm_default");
		scgsm.add("gsm7");
		scgsm.add("gsm-7bit");

		scpgsm = new ArrayList<String>();
		scpgsm.add("scpgsm");

		utf7 = new ArrayList<String>();
		utf7.add("utf-7");
		utf7.add("utf7");
		utf7.add("unicode-1-1-utf-7");
		utf7.add("csunicode11utf7");
		utf7.add("unicode-2-0-utf-7");
		

		utf7opt = new ArrayList<String>();
		utf7opt.add("utf-7-optional");
		utf7opt.add("utf-7o");
		utf7opt.add("utf7o");
		utf7opt.add("utf-7-o");
		
		userdefined =  new ArrayList<String>();
		userdefined.add("x-user-defined");
	}

	private static int checkIfNotSupported(String charset) {
		int typ = 0;
		if (charset != null) {
			charset = charset.toLowerCase();
			if (ccgsm.contains(charset)) {
				typ = 1;
			} else if (ccpgsm.contains(charset)) {
				typ = 2;
			} else if (hproman.contains(charset)) {
				typ = 3;
			} else if (iso88596.contains(charset)) {
				typ = 4;
			} else if (iso88598.contains(charset)) {
				typ = 5;
			} else if (koi.contains(charset)) {
				typ = 6;
			} else if (scgsm.contains(charset)) {
				typ = 7;
			} else if (scpgsm.contains(charset)) {
				typ = 8;
			} else if (utf7.contains(charset)) {
				typ = 9;
			} else if (utf7opt.contains(charset)) {
				typ = 10;
			} else if( userdefined.contains(charset)) {
				typ = 11;
			}
		}
		return typ;

	}

	public static String getContentIfNonSupportedCharset(Part p) throws Exception {
		String o = null;
		ContentType ct = new ContentType(p.getContentType());
		String charset = ct.getParameter("charset");
		int typ = checkIfNotSupported(charset);
		if (typ != 0) {
			InputStream in = p.getInputStream();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int c;
			while ((c = in.read()) != -1) {
				out.write(c);
			}
			byte[] bytes = out.toByteArray();
			switch (typ) {
			case 1:
				o = new String(bytes, new CCGSMCharset());
				break;
			case 2:
				o = new String(bytes, new CCPackedGSMCharset());
				break;
			case 3:
				o = new String(bytes, new HPRoman8Charset());
				break;
			case 4:
				o = new String(bytes, new ISO88596Charset());
				break;
			case 5:
				o = new String(bytes, new ISO88598Charset());
				break;
			case 6:
				o = new String(bytes, new KOI8UCharset());
				break;
			case 7:
				o = new String(bytes, new SCGSMCharset());
				break;
			case 8:
				o = new String(bytes, new SCPackedGSMCharset());
				break;
			case 9:
				o = new String(bytes, new UTF7Charset());
				break;
			case 10:
				o = new String(bytes, new UTF7OptionalCharset());
				break;
			case 11:
				o = new String(bytes, new UserDefinedCharset().charsetForName(charset));
				break;
				
			default:
				break;
			}
		}
		return o;
	}
}
