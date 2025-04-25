/*
 * This class defines the behavior for a manager password, including storage security measures that
 * salt the password each time it is typed.
 */
package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class ManagerPassword {
	private String hashedPassword;
	private String password;
	private String salt;
	
	public ManagerPassword() {
		getManagerPassword("/data/staff.txt");
	}
	
	// retrieves and reads the salted password from the file 
	public void getManagerPassword(String filename) {
		filename = System.getProperty("user.dir") + filename;
    	
    	try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            
    		String line;
    		int retrieve = 0;
            
            while ((line = reader.readLine()) != null) {
            	
            	// skip empty lines and comments
                if (line.trim().isEmpty() || line.trim().startsWith("//")) {
                	if (line.trim().equals("//MANAGER")){
                		retrieve = 1;
                	}
                	continue;
                }
                
                if (retrieve == 1) {
                	salt = line.strip();
                	retrieve++;
                	
                }
                else if (retrieve == 2) {
                	hashedPassword = line.strip();
                	retrieve++;
                }
                else {
                }               
            }
          }catch (IOException e) {
             e.printStackTrace();
         }
	}
	
	// rewrites the salt for the password in the data file
	public void rewriteToFile(String filename) {
	    filename = System.getProperty("user.dir") + filename;
	    List<String> lines = new ArrayList<>();

	    try {
	        BufferedReader reader = new BufferedReader(new FileReader(filename));
	        String line;

	        while ((line = reader.readLine()) != null) {
	        	
	            // Look for the manager section
	            if (line.trim().equals("//MANAGER")) {
	                lines.add(line); // Keep the //MANAGER line

	                // Generate and add new salt + hashed password
	                salt = generateSalt();
	                hashedPassword = hashPassword(password, salt);

	                lines.add(salt);
	                lines.add(hashedPassword);

	                // Skip the next two lines (old salt + old hashed password)
	                reader.readLine(); // skip old salt
	                reader.readLine(); // skip old hash
	            } else {
	                lines.add(line);
	            }
	        }
	        reader.close();
	        password = null; // clear password after storing

	        // Write updated content to file
	        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
	        for (String outputLine : lines) {
	            writer.write(outputLine);
	            writer.newLine();
	        }
	        writer.close();

	    } catch (IOException | NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setHashedPassword(String password) {
		this.hashedPassword = password;
	}
	
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	public String getSalt() {
		return salt;
	}
	
	public String getHashedPassword() {
		return hashedPassword;
	}
	
	public String getPassword() {
		return password;
	}
	
	public static String generateSalt() {
    	/*
    	 * This method generates a random salt for a password to 
    	 * enhance password security.
    	 */
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String hashPassword(String password, String salt) throws NoSuchAlgorithmException {
    	/*
    	 * This method fully hashes a password given a password String and a salt
    	 * String.
    	 */
    	MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(Base64.getDecoder().decode(salt));
        byte[] hashedPassword = md.digest(password.getBytes());
        
        return Base64.getEncoder().encodeToString(hashedPassword);
    }

}
