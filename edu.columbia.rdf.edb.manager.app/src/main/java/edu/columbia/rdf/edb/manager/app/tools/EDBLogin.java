package edu.columbia.rdf.edb.manager.app.tools;

import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import org.slf4j.LoggerFactory;

import edu.columbia.rdf.edb.EDBWLogin;

/**
 * Logins entries for a connection to a caArray server.
 *
 * @author Antony Holmes Holmes
 */
public class EDBLogin extends EDBWLogin {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String mDb = null;
	private String mPassword = null;
	
	private static final org.slf4j.Logger LOG = 
			LoggerFactory.getLogger(EDBLogin.class);

	public EDBLogin(String server, 
			String db,
			String user,
			String password) throws UnsupportedEncodingException {
		super(server, user, null, null, -1, -1);
		
		mDb = db;
		mPassword = password;
	}
	
	public final String getDb() {
		return mDb;
	}
	
	public String getPassword() {
		return mPassword;
	}
}
