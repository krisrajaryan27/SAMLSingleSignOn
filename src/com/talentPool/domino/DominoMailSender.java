/**
 * 
 */
package com.talentPool.domino;

/*import lotus.domino.*;*/

/**
 * @author Ajeet
 *
 */
public class DominoMailSender {/*

	*//**
	 * @param args
	 *//*

	public void send(Document doc, String to, String subject, String msg)
			throws NotesException {
		doc.appendItemValue("Form", "Memo");
		doc.appendItemValue("Subject", subject);
		doc.appendItemValue("Body", msg);
		doc.send(to);
	}

	public static void main(String args[]) {
		if (args.length < 3) {
			System.out.println("Need to supply to/subject/message");
			//return;
		}

		DominoMailSender mailer = new DominoMailSender();

		try {
			NotesThread.sinitThread();			
			Session s = NotesFactory.createSession();
			
			System.out.println("3");
			//NotesMain();
			
			// the following two lines can't be commented out if a notes client is already running
//			Registration reg = s.createRegistration();
//			reg.switchToID("C:\\Program Files\\IBM\\Lotus\\Domino\\data\\server.id", "123123");
			
			DbDirectory dir = s.getDbDirectory(null);	
			//Database db = dir.openMailDatabase();
			Database db = dir.openDatabase("C:\\Program Files\\IBM\\Lotus\\Domino\\data\\mail\\user1.nsf");
			isitopen(db);
			mailer.send(db.createDocument(), "ajeet.jain@gmail.com", "test123", "just a mail test");
			System.out.println("sent");
		} catch (NotesException ne) {
			ne.printStackTrace();
		} finally {
			NotesThread.stermThread();
		}
	}
	
	public static void isitopen(Database db) throws NotesException {
		if (db.isOpen())
			System.out.println("\"" + db.getTitle() + "\" is open");
		else
			System.out.println("\"" + db.getTitle() + "\" is not open");
	}
	
	*//*** 
	 * This agent opens the current user's mail database and prints its title, size, and number of documents.
	 * *//*
	 public static void NotesMain2() {
		   try {
		     Session session = NotesFactory.createSession();
		     AgentContext agentContext = session.getAgentContext();
		     // (Your code goes here) 
		     DbDirectory dir = session.getDbDirectory(null);
		     Database db = dir.openMailDatabase();
		     DocumentCollection dc = db.getAllDocuments();
		     System.out.println("Mail database : " +
		     db.getTitle() + " is " +
		     ((int)(db.getSize()/1024)) + "KB long and has " +
		     dc.getCount() + " documents");
		   } catch(Exception e) {
		     e.printStackTrace();
		   }
		 }
*//***
 * This agent prints the file name and title of each database and template in the local directory
 * *//*
	 public static void NotesMain() {
		   try {
		     Session session = NotesFactory.createSession();
		     AgentContext agentContext = session.getAgentContext();
		     // (Your code goes here) 
		     DbDirectory dir = session.getDbDirectory(null);
		     String server = dir.getName();
		     if (server.equals("")) server = "Local";
		     System.out.println ("Database directory list on server"+
		     server + "\n");
		     System.out.println("***DATABASES***\n");
		     Database db = dir.getFirstDatabase(DbDirectory.DATABASE);
		     while (db != null) {
		       String fn = db.getFileName();
		       String title = db.getTitle();
		       System.out.println(fn.toUpperCase() + " - " + title);
		       db = dir.getNextDatabase(); 
		      }
		     System.out.println("\n***TEMPLATES***\n");
		     db = dir.getFirstDatabase(DbDirectory.TEMPLATE);
		     while (db != null) {
		       String fn = db.getFileName();
		       String title = db.getTitle();
		       System.out.println(fn.toUpperCase() + " - " + title);
		       db = dir.getNextDatabase(); }
		   } catch(Exception e) {
		     e.printStackTrace();
		   }
		 }
	 
*/}
