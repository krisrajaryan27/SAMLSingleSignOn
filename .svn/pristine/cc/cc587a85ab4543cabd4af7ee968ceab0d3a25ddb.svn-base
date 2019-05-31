/*
 * Created on Aug 30, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.talentPool.parser.converter.test;

import com.talentPool.parser.converter.HTMLToPlainTextConverter;

import junit.framework.TestCase;

/**
 * @author pallavi
 * @date Aug 30, 2006
 */
public class HTMLToPlainTextConverterTest extends TestCase {
	public void testNullInput() {
		String plainText = new HTMLToPlainTextConverter().convertText(null);
		assertNull("Wrong Result", plainText);
	}
	
	public void testEmptyInput() {
		String plainText = new HTMLToPlainTextConverter().convertText("");
		assertEquals("Wrong Result", "", plainText);
	}
	
	public void test_I_ValidInput() {
		String html = new String("Talentica Software");
		String plainText = new HTMLToPlainTextConverter().convertText(html);
		assertNotNull("Wrong Result", plainText);
		assertEquals("Wrong Result", "Talentica Software", plainText);
	}
	
	public void test_II_ValidInput() {
		String html = new String("<html><head><title></title></head><body></body></html>");
		String plainText = new HTMLToPlainTextConverter().convertText(html);
		assertNotNull("Wrong Result", plainText);
		assertEquals("Wrong Result", "", plainText);
	}
	
	public void test_I_InvalidInput() {
		String html = new String("<html></title></head><body></body></html>");
		String plainText = new HTMLToPlainTextConverter().convertText(html);
		assertNotNull("Wrong Result", plainText);
		assertEquals("Wrong Result", "", plainText);
	}
	
	public void test_II_InvalidInput() {
		String html = new String("<html></title></head><body><br/></body></html>");
		String plainText = new HTMLToPlainTextConverter().convertText(html);
		assertNotNull("Wrong Result", plainText);
		assertEquals("Wrong Result", "", plainText);
	}
	
	public void test_III_ValidInput() {
		String html = new String("<html><head><title>Resume</title></head><body>Name: Blahhh</body></html>");
		String plainText = new HTMLToPlainTextConverter().convertText(html);
		assertNotNull("Wrong Result", plainText);
		assertEquals("Wrong Result", "Resume   Name: Blahhh", plainText);
	}
	
	public void test_IV_ValidInput() {
		String html = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\"><HTML><HEAD><META http-equiv=Content-Type content=\"text/html; charset=iso-8859-1\"><META content=\"MSHTML 6.00.2800.1106\" name=GENERATOR></HEAD><BODY><B>From:</B> saroj somala bhukya[sarojsaru123@rediffmail.com]<BR><B>Sent:</B> Wednesday, July 28, 2004 1:09PM<BR><B>To:</B> go4it@talentica.com<BR><B>Subject:</B> Fresher Resume<BR><P>&nbsp; Respected Sir/ Madam, <BR>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; MyselfSaroj Somala Bhukya. I have completed my gradution in Electronics Engineeringfrom Visvesvaraya National Instiute of Technnology(formerly VRCE), Nagpur from2004 batch.<BR>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; I am a fresher and I aminterested in doing work with you people. If you find me eligible kindly acceptmy resume and allow me to work with you people.<BR>&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; Thanking You.<BR>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; Yours Sincerely<BR>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; Saroj S. Bhukya<BR><BR><BR><BR>Resume&nbsp; &nbsp;&nbsp;<BR>ELECTRONICS ENGINEER(REC, NAGPUR)<BR>Ms. Saroj SomalaBhukya<BR><BR>PRESENT ADDRESS:&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <BR>C/0.Y.M.Sapkal,<BR>12/49/120,Yojangandha Soc, <BR>Shivtirth Nagar,<BR>Paud Road,Kothrud,<BR>Pune-38<BR><BR>Date of Birth:&nbsp; 1st November1981<BR><BR>Phone&nbsp; &nbsp; &nbsp; &nbsp; :9890799688/9850107194<BR><BR>Email&nbsp; &nbsp; &nbsp; &nbsp; :&nbsp;saroj_b1@yahoo.co.in/<BR>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;sarojsaru123@rediffmail.com<BR><BR><BR>Career Objective:<BR>&nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; To acquire thechallenging position where my creativity, energy, skill&nbsp; can beapplied&nbsp; &nbsp; with new learning experience.<BR><BR>ProjectUndertaken:<BR>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<BR>PROJECT TITLE : \" RESOURCE OPTIMIZATION FOR BRAKING OF A TRAIN USING&nbsp;<BR>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; FUZZY&nbsp;MEMBERSIP. \"<BR><BR>PROJECT GUIDE:&nbsp; Dr. A.G. KESKAR<BR><BR>BRIEFDESCRIPTION -&nbsp; <BR>&nbsp; &nbsp; <BR>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; While braking a train manually, it consumesmuch more time and power in crawling to the destination or target,&nbsp; henceto avoid this consumption of time and power, a computerized method is used usinga MATLAB tool.<BR>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; In the project, an ATP (Automatic Train Protection) curve is designed inFuzzy Inference System (FIS) which is a MATLAB tool. The train model is designedin such a way that it should track this ATP curve and save the time and reachthe destination without crawling. Three train models are designed using aSimulink Software which is also a MATLAB tool. Out of these three models, thirdmodel is selected because it follows ATP curve more accurately as compared toother two.&nbsp; <BR><BR>Skill sets:<BR><BR>Operating Systems&nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;: DOS, Windows&nbsp;<BR>Programming Languages&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;:C<BR><BR><BR>Educational Qualifications:<BR><BR>Qualification SpecializationInstitute Board/University&nbsp; &nbsp; &nbsp; %&nbsp; PassingYr.<BR>&nbsp;&nbsp; B.E.&nbsp; &nbsp; &nbsp; Electronics <BR>&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; Engineering&nbsp; Visvesvaraya<BR>&nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; NationalInstitute<BR>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; of Technology (VRCE),<BR>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;Nagpur&nbsp; &nbsp; <BR>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;National Institute<BR>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; of Technology <BR>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;(formerly VRCE), <BR>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; Nagpur&nbsp; &nbsp; &nbsp;<BR><BR>&nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; 66.67%&nbsp; &nbsp; &nbsp;2004<BR>&nbsp; &nbsp; HSC&nbsp; &nbsp;&nbsp; &nbsp; Science&nbsp; &nbsp; &nbsp;&nbsp; Thapar Jr. College<BR>&nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; Ballarpur&nbsp; &nbsp; &nbsp;<BR>&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; Board of Higher <BR>&nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; Secondary Education,<BR>&nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; Maharashtra<BR>&nbsp; &nbsp;&nbsp;<BR>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 74.33%&nbsp; &nbsp;&nbsp;2000<BR>&nbsp; &nbsp; SSC&nbsp; &nbsp; Semi English Thapar HighSchool<BR>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; Ballarpur&nbsp; &nbsp; &nbsp;<BR>&nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; Board of Secondary<BR>&nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;Education,<BR>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; Maharashtra<BR>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 72.40%&nbsp; &nbsp;&nbsp;1998<BR><BR>Educational Achievement:<BR><BR>· Merit in school inFourth-class scholarship.<BR>· Was a member of IEEE and ODYSSEY during 2001-02and also organised Ganeshotsav and Janmashtami in college.<BR>· Was a CSM(Senior) in NCC in 9th std. and also attended NIC (National IntegrationCamp).<BR><BR>Interest &amp; Hobbies<BR><BR>· Listening music.<BR>· Singing.<BR><BR>Personal Details:<BR>* Date of Birth /Age&nbsp; :-&nbsp; 1st November1981 ( 22 Yrs)<BR>* Languages&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; :-&nbsp; Banjara(Mother tongue), English, Hindi,&nbsp; Marathi.<BR>* Permanent Address&nbsp; :-&nbsp; D/o. Mr. Somala Bhukya,<BR>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; Behind PaddhariaHotel,<BR>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; Opp. BPM cycle stand,<BR>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; Fulsingh NaikWard,<BR>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; Ballarpur,<BR>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; Distt. Chandrapur,<BR>&nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; Pin. 442901. (M.S. India)<BR><BR><BR>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; Ihere by declare that the information furnished above istrue.<BR><BR><BR>Place:&nbsp; &nbsp; &nbsp;Pune&nbsp; &nbsp; &nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp; &nbsp;<BR><BR>Date:28/07/04&nbsp; &nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;Saroj S.Bhukya<BR></P><BR><BR><Ahref=\"http://clients.rediff.com/signature/track_sig.asp\" target=_blank><IMGhspace=0src=\"http://ads.rediff.com/RealMedia/ads/adstream_nx.cgi/www.rediffmail.com/inbox.htm@Bottom\" border=0></A> </BODY></HTML>";
		String plainText = new HTMLToPlainTextConverter().convertText(html);
		assertNotNull("Wrong Result", plainText);
		//assertEquals("Wrong Result", "From:  saroj somala bhukya[sarojsaru123@rediffmail.com]\n Sent:  Wednesday, July 28, 2004 1:09PM\n To:  go4it@talentica.com\n Subject:  Fresher Resume\n  Respected Sir/ Madam,\n\n     MyselfSaroj Somala Bhukya. I have completed my gradution in Electronics Engineeringfrom Visvesvaraya National Instiute of Technnology(formerly VRCE), Nagpur from2004 batch.\n     I am a fresher and I aminterested in doing work with you people. If you find me eligible kindly acceptmy resume and allow me to work with you people.\n    Thanking You.\n                   Yours Sincerely\n                   Saroj S. Bhukya\n\n\nResume\n\nELECTRONICS ENGINEER(REC, NAGPUR)\nMs. Saroj SomalaBhukya\n\nPRESENT ADDRESS:\n                        \nC/0.Y.M.Sapkal,\n12/49/120,Yojangandha Soc,\n\nShivtirth Nagar,\nPaud Road,Kothrud,\nPune-38\n\nDate of Birth: 1st November1981\n\nPhone    :9890799688/9850107194\n\nEmail    :saroj_b1@yahoo.co.in/\n       sarojsaru123@rediffmail.com\n\n\nCareer Objective:\n           To acquire thechallenging position where my creativity, energy, skill can beapplied  with new learning experience.\n\nProjectUndertaken:\n                  \nPROJECT TITLE : \" RESOURCE OPTIMIZATION FOR BRAKING OF A TRAIN USING\n         FUZZYMEMBERSIP. \"\n\nPROJECT GUIDE: Dr. A.G. KESKAR\n\nBRIEFDESCRIPTION -\n\n  \n         While braking a train manually, it consumesmuch more time and power in crawling to the destination or target, henceto avoid this consumption of time and power, a computerized method is used usinga MATLAB tool.\n         In the project, an ATP (Automatic Train Protection) curve is designed inFuzzy Inference System (FIS) which is a MATLAB tool. The train model is designedin such a way that it should track this ATP curve and save the time and reachthe destination without crawling. Three train models are designed using aSimulink Software which is also a MATLAB tool. Out of these three models, thirdmodel is selected because it follows ATP curve more accurately as compared toother two.\n\n\nSkill sets:\n\nOperating Systems       : DOS, Windows\nProgramming Languages    :C\n\n\nEducational Qualifications:\n\nQualification SpecializationInstitute Board/University   % PassingYr.\n B.E.   Electronics\n\n      Engineering Visvesvaraya\n            NationalInstitute\n           of Technology (VRCE),\n             Nagpur\n \n                 National Institute\n                  of Technology\n\n                 (formerly VRCE),\n\n                    Nagpur\n \n\n                        66.67%  2004\n  HSC   Science   Thapar Jr. College\n            Ballarpur\n \n                  Board of Higher\n\n                 Secondary Education,\n                   Maharashtra\n \n                         74.33% 2000\n  SSC  Semi English Thapar HighSchool\n             Ballarpur\n \n                  Board of Secondary\n                  Education,\n                   Maharashtra\n                         72.40% 1998\n\nEducational Achievement:\n\n· Merit in school inFourth-class scholarship.\n· Was a member of IEEE and ODYSSEY during 2001-02and also organised Ganeshotsav and Janmashtami in college.\n· Was a CSM(Senior) in NCC in 9th std. and also attended NIC (National IntegrationCamp).\n\nInterest &amp; Hobbies\n\n· Listening music.\n· Singing.\n\nPersonal Details:\n* Date of Birth /Age :- 1st November1981 ( 22 Yrs)\n* Languages     :- Banjara(Mother tongue), English, Hindi, Marathi.\n* Permanent Address :- D/o. Mr. Somala Bhukya,\n            Behind PaddhariaHotel,\n            Opp. BPM cycle stand,\n            Fulsingh NaikWard,\n            Ballarpur,\n            Distt. Chandrapur,\n           Pin. 442901. (M.S. India)\n\n\n     Ihere by declare that the information furnished above istrue.\n\n\nPlace:  Pune\n           \n\nDate:28/07/04           Saroj S.Bhukya", plainText);
	}
	
	public void test_V_ValidInput() {
		String html = new String("<html><head><title>Resume</title></head><body><table><tr><td>Name: Blahhh</td></tr></table></body></html>");
		String plainText = new HTMLToPlainTextConverter().convertText(html);
		assertNotNull("Wrong Result", plainText);
		assertEquals("Wrong Result", "Resume     \nName: Blahhh", plainText);
		
		String html2 = new String("<html><head><title>Resume</title></head><body><table><tr><TD>Name: Blahhh</td></tr></table></body></html>");
		String plainText2 = new HTMLToPlainTextConverter().convertText(html2);
		assertNotNull("Wrong Result", plainText2);
		assertEquals("Wrong Result", "Resume     \nName: Blahhh", plainText2);
		
		String html3 = new String("<html><head><title>Resume</title></head><body><table><tr><TD><BR/>Name: Blahhh</td></tr></table></body></html>");
		String plainText3 = new HTMLToPlainTextConverter().convertText(html3);
		assertNotNull("Wrong Result", plainText3);
		assertEquals("Wrong Result", "Resume     \n\nName: Blahhh", plainText3);
		
		String html4 = new String("<html><head><title>Resume</title></head><body><table><tr><TD><br/>Name: Blahhh</td></tr></table></body></html>");
		String plainText4 = new HTMLToPlainTextConverter().convertText(html4);
		assertNotNull("Wrong Result", plainText4);
		assertEquals("Wrong Result", "Resume     \n\nName: Blahhh", plainText4);
	}
}
