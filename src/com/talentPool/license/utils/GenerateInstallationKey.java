/**
 * 
 */
package com.talentPool.license.utils;

import java.io.FileOutputStream;

import com.talentPool.license.manager.NetworkInfo;

/**
 * @author pallavi
 * @date Jan 11, 2007
 */
public class GenerateInstallationKey {
	private static final String FILE = "installationKey.dat";
	
	public final void generateInstallationKey(String[] args) {
		try {
			
			StringBuffer key = new StringBuffer();
			key.append(args[0]);			// MAC Address
			key.append("|");
			key.append(args[1]);			// Company Name
			key.append("|");
			key.append(args[2]);			// Product Name
			key.append("|");
			key.append(args[3]);			// Product Version
			key.append("|");
			key.append(args[4]);			// Installation Date
			String installationKey = StringToHex.convert(key.toString());
			FileOutputStream stream = new FileOutputStream(FILE);
			stream.write(installationKey.getBytes());
			stream.close();
		} catch (Exception e) {
			
		}		
	}
	
	
	public static void main(String args[]) {	
		try {
			String[] temp = new String[5];
			System.out.println("Generate Installation Key");
			
			 args = new String[5];
			args[0] = "64-00-6A-6B-42-85";
			args[1]="TalentPool";
			args[2]="TalentPool";
			args[3]="v15.2.0";
			args[4]="29/10/2018";
			int indx = 0;
			if (args.length == 4) {
				NetworkInfo networkInfo = new NetworkInfo();
				temp[0] = networkInfo.getMacAddress();
			} else {
				temp[0] = args[indx++];			
			}
			
			for (int i = 1; indx < args.length; i++) {
				temp[i] = args[indx++];
			}
			GenerateInstallationKey generateInstallationKey = new GenerateInstallationKey();
			generateInstallationKey.generateInstallationKey(temp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
