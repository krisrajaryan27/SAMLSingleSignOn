/**
 * 
 */
package com.talentPool.license.manager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.license.exception.LicenseException;
import com.talentPool.common.utils.Utils;
import com.talentPool.license.utils.HexToString;

/**
 * @author shivprasad Singleton license class
 * 
 *         The license object is generated only once and stores license info.
 */
public class LicenseObj {
	private static LicenseObj ref;
	private final String COMMA = ",";
	private final String PIPE = "[|]";
	private final String SLASH = "/";
	// private final String SLASH = "-";
	private final String LICENSED_COPY_NO_OF_DAYS = "-1";

	private String macAddress = null;
	private String licenseDate = null;
	private String noOfDays = null;
	private String licenseVersion = null;
	private BitSet modules = null;
	private BitSet reportExceptions = null;
	private boolean isValidLicence = false;

	private LicenseObj() {
		// do not write any code here
	}

	public static synchronized LicenseObj getLicenseObject() {
		if (ref == null) {
			ref = new LicenseObj();
			ref.loadLicense();
		}
		return ref;
	}

	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
		// clone not allowed
	}

	/**
	 * Added By : Shantanu Sikdar Method : loadLicense() Desc :fetching out the
	 * license string element
	 * 
	 * @return
	 * @param
	 */
	private void loadLicense() {
		try {
			String licenseText = getLicenseText(TPApplicationProperties
					.getProperty("license.file.path"));
			if (licenseText != null && licenseText.length() > 0) {
				String[] parts = licenseText.split(PIPE);
				macAddress = parts[0];
				licenseDate = parts[4];
				noOfDays = parts[5];
				licenseVersion = parts[7];
				String strModules = parts[8];
				modules = new BitSet(64);
				for (int i = 0; i < 64; i++) {
					int valModules = strModules.charAt(i);
					if (valModules == '1') {
						modules.set(i);
					}
				}

				String strReportExceptions = parts[9];
				reportExceptions = new BitSet(128);
				for (int j = 0; j < 128; j++) {
					int valReportException = strReportExceptions.charAt(j);
					if (valReportException == '1') {
						reportExceptions.set(j);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			TPLogger.getLogger().error(e);
			isValidLicence = false;
		}
	}

	/**
	 * Added By : Shantanu Sikdar Method : checkMachineAddress() Desc :Checking
	 * the Machine Address for Licensing Purpose
	 * 
	 * @param macAddress
	 * @throws LicenseException
	 */
	private final boolean checkMachineAddress(String macAddress) {
		boolean isAddressFound = false;
		try {
			NetworkInfo networkInfo = new NetworkInfo();
			String _address = networkInfo.getMacAddress();
			String[] address = _address.split(COMMA);
			if (address != null && address.length > 0) {
				String[] parts = macAddress.split(COMMA);
				for (int i = 0; i < parts.length && !isAddressFound; i++) {
					for (int j = 0; j < address.length && !isAddressFound; j++) {
						if (address[j].equalsIgnoreCase(parts[i])) {
							isAddressFound = true;
						}
					}
				}
			} else {
				isAddressFound = false;
			}
		} catch (IOException e) {
			isAddressFound = false;
		}
		if (!isAddressFound) {
			isAddressFound = false;
		}
		return isAddressFound;
	}

	/**
	 * Added By : Shantanu Sikdar Method : checkLicenseDate Desc :Checking the
	 * License Date for Licensing Purpose
	 * 
	 * @param licenseDate
	 * @throws LicenseException
	 */
	private final boolean checkLicenseDate(String licenseDate, String noOfDays) {
		boolean isValidDate = false;
		try {
			Date dateLicense = new Date();
			Date today = new Date();
			if (licenseDate == null || noOfDays == null) {
				isValidDate = false;
			}
			if (LICENSED_COPY_NO_OF_DAYS.equalsIgnoreCase(noOfDays)) {
				isValidDate = true;
			} else {
				dateLicense = Utils.convertToDate(licenseDate, "dd" + SLASH
						+ "MM" + SLASH + "yyyy");
				dateLicense = Utils.adjustDateBy(dateLicense, Calendar.DATE,
						Integer.parseInt(noOfDays));
				if (!today.before(dateLicense)) {
					isValidDate = false;
				} else {
					isValidDate = true;
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
			isValidDate = false;
		}
		return isValidDate;
	}

	/**
	 * Added By : Shantanu Sikdar Method : getLicenseText() Desc :Fetching the
	 * License Text from the License File Name sent to the method
	 * 
	 * @param fileName
	 * @return installationKeyString
	 * @throws LicenseException
	 */

	private String getLicenseText(String fileName) throws LicenseException {
		StringBuffer fileData = new StringBuffer(1000);
		String installationKeyString = null;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			char[] buf = new char[1024];
			int numRead = 0;
			while ((numRead = reader.read(buf)) != -1) {
				String readData = String.valueOf(buf, 0, numRead);
				fileData.append(readData);
				buf = new char[1024];
			}
			reader.close();
			installationKeyString = HexToString.convert(fileData.toString());
		} catch (FileNotFoundException e) {
			throw new LicenseException("The file " + fileName
					+ " is not found.", e);
		} catch (IOException e) {
			throw new LicenseException(e);
		}
		return installationKeyString;
	}

	public boolean isValidLicence() {
		isValidLicence = false;
		try {
			if (checkMachineAddress(macAddress)
					&& checkLicenseDate(licenseDate, noOfDays)) {
				isValidLicence = true;

			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return isValidLicence;
	}

	/**
	 * @return the licenseVersion
	 */
	public String getLicenseVersion() {
		return licenseVersion;
	}

	/**
	 * @return the modules
	 */
	public BitSet getModules() {
		return modules;
	}

	/**
	 * @return the reportExceptions
	 */
	public BitSet getReportExceptions() {
		return reportExceptions;
	}

	public String getLicenseValidity() {
		String validity = "Life time";
		try {
			Date dateLicense = new Date();
			Date today = new Date();
			if (licenseDate == null || noOfDays == null) {
				validity = "Expired";
			}
			if (LICENSED_COPY_NO_OF_DAYS.equalsIgnoreCase(noOfDays)) {
				validity = "Life time";
			} else {
				dateLicense = Utils.convertToDate(licenseDate, "dd" + SLASH + "MM" + SLASH + "yyyy");
				dateLicense = Utils.adjustDateBy(dateLicense, Calendar.DATE, Integer.parseInt(noOfDays));

				Calendar da1 = new GregorianCalendar();
				Calendar da2 = new GregorianCalendar();
				da1.setTime(today);
				da2.setTime(dateLicense);
				long d1 = da1.getTime().getTime();
				long d2 = da2.getTime().getTime();
				long difMil = d2 - d1;
				long milPerDay = 1000 * 60 * 60 * 24;
				long days = difMil / milPerDay;
				validity = (days+1) + " days";
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return validity;
	}

}
