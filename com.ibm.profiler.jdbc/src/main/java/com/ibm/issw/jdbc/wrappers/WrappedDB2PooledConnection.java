/*
 * Copyright 2017 Steve McDuff
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ibm.issw.jdbc.wrappers;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.PooledConnection;

import com.ibm.db2.jcc.DB2Connection;

/**
 * 
 * WrappedDB2PooledConnection
 */
public class WrappedDB2PooledConnection extends WrappedPooledConnection {

	private static final Logger LOG = Logger
			.getLogger(WrappedDB2PooledConnection.class.getName());

	/**
	 * ctor
	 * @param pooledConn the connection to wrap
	 */
	public WrappedDB2PooledConnection(PooledConnection pooledConn) {
		super(pooledConn);
	}
	/*
	 * (non-Javadoc)
	 * @see com.ibm.issw.jdbc.wrappers.WrappedPooledConnection#getConnection()
	 */
	@Override
    public Connection getConnection() throws SQLException {
		PooledConnection pooledConnection = this.getPooledConnection();
        Connection conn = getConnectionFromPooledConnection(pooledConnection);

		if (LOG.isLoggable(Level.FINE)) {
			LOG.fine("Real connection " + conn.toString());
		}

		if ((conn instanceof WrappedConnection)) {
			return conn;
		}
		
		if ((conn instanceof DB2Connection)) {
			return new WrappedDB2Connection((DB2Connection) conn);
		}

		return new WrappedConnection(conn);
	}
	

}
