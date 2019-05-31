/**
 * 
 */
package com.talentPool.domino;

/**
 * @author Ajeet
 *
 */
/*import lotus.domino.NotesException;
*/
/**
 * Send an email message using the Domino mail server. Can be used in non-Domino
 * Java applications, and can also be run from the command line.
 */
public class DominoMailBox {/*

	*//**
	 * One person or group name. For example, Brian Green/Acme@AcmeMAIL. Group
	 * names must appear in the Domino sever's address book.
	 *//*
	public String sendTo;

	*//**
	 * One person or group name. For example, Brian Green/Acme@AcmeMAIL. Group
	 * names must appear in the Domino sever's address book.
	 *//*
	public String copyTo;

	*//**
	 * The email's subject line.
	 *//*
	public String subject;

	*//**
	 * The email's message text.
	 *//*
	public String message;

	*//**
	 * Host name of the Domino server. A connection will be opened to this
	 * server.
	 *//*
	private String dominoServer =  "ajeet"; //"mail.acme.com";

	*//**
	 * Mailbox available to the dominoUsername.
	 *//*
	private String dominoMailbox = "C:\\Program Files\\IBM\\Lotus\\Domino\\data\\mail\\user1.nsf";

	*//**
	 * Username of the Domino user. Must be allowed to access the target
	 * database (dominoMailbox), and must be allowed to open DIIOP connections
	 * to the dominoServer. (The DIIOP task must be running on the Domino
	 * server.)
	 *//*
	private String dominoUsername = "user1";

	*//**
	 * Internet password for the dominoUsername. Defined in the Domino server's
	 * address book.
	 *//*
	private String dominoPassword = "123123";

	*//**
	 * Constructs a new email message. Call the "send" method when you're ready
	 * to send it.
	 * 
	 * @param sendTo
	 * @param copyTo
	 * @param subject
	 * @param message
	 *//*
	public DominoMailBox(String sendTo, String copyTo, String subject,
			String message) {
		this.sendTo = sendTo;
		this.copyTo = copyTo;
		this.subject = subject;
		this.message = message;
	}

	*//**
	 * Optional startup - Send a message from the command line.
	 * 
	 * @param args
	 *            To run from the command line, specify 4 parameters. [1]
	 *            SendTo. One person or group name. For example, Brian
	 *            Green/Acme@AcmeMAIL" [2] CopyTo. One person or group name. [3]
	 *            Subject. [4] Message.
	 *//*
	public static void main(String[] args) {
		if (args.length != 4) {
			System.out.println("\n\n"
							+ "To start from the command line, you must supply these arguments: \n"
							+ "[1] SendTo. One person or group name. For example, Brian Green/Acme@AcmeMAIL \n"
							+ "[2] CopyTo. One person or group name. \n"
							+ "[3] Subject. \n" + "[4] Message. \n\n");
		} else {
			DominoMailBox mail = new DominoMailBox(args[0], args[1], args[2], args[3]);
			mail.send();
		}
		
		DominoMailBox mail = new DominoMailBox("ajeet.jain@gmail.com", "ajeet.jain@gmail.com", "Test123", "Just a mail");
		System.out.println("before send");
		mail.send();
		System.out.println("after send");
	}

	*//**
	 * Connects to the Domino application server, and sends an email.
	 *//*
	public void send() {
		try {
			System.out.println("1");
			lotus.domino.Session dominoSession = lotus.domino.NotesFactory
					.createSession(dominoServer, dominoUsername, dominoPassword);
			System.out.println("2");
			lotus.domino.Database dominoDb = dominoSession.getDatabase(
					dominoServer, dominoMailbox);
			System.out.println("3");
			// System.out.println( "Connected as: " +
			// dominoSession.getUserName() );

			lotus.domino.Document memo = dominoDb.createDocument();
			memo.appendItemValue("Form", "Memo");
			memo.appendItemValue("Importance", "1");
			memo.appendItemValue("CopyTo", copyTo);
			memo.appendItemValue("Subject", subject);
			memo.appendItemValue("Body", message);
			memo.send(false, sendTo);

			// Recycle
			dominoDb.recycle();
			dominoSession.recycle();
		} catch (NotesException e) {
			System.out.println("Error - " + e.id + " " + e.text);
			// e.printStackTrace( System.out );
		} catch (Exception e) {
			System.out.println("Error - " + e.toString());
			// e.printStackTrace( System.out );
		}

	}

*/}
