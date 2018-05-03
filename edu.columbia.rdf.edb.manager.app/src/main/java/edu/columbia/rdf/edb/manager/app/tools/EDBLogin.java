package edu.columbia.rdf.edb.manager.app.tools;

import org.slf4j.LoggerFactory;

import edu.columbia.rdf.edb.EDBWLogin;

/**
 * Logins entries for a connection to a caArray server.
 *
 * @author Antony Holmes Holmes
 */
public class EDBLogin {
  private String mDb = null;
  private String mPassword = null;

  private String mServer;

  private EDBWLogin mLogin;
  private String mUser;

  private static final org.slf4j.Logger LOG = LoggerFactory
      .getLogger(EDBLogin.class);

  public EDBLogin(EDBWLogin l, String server, String db, String user, String password) {
    mLogin = l;

    mDb = db;
    mServer = server;
    mUser = user;
    mPassword = password;
  }

  public final String getDb() {
    return mDb;
  }

  public String getPassword() {
    return mPassword;
  }
  
  public String getServer() {
    return mServer;
  }
  
  public String getUser() {
    return mUser;
  }
  
  public EDBWLogin getLogin() {
    return mLogin;
  }
}
