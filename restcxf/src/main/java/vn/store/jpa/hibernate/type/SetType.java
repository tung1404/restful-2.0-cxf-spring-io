package vn.store.jpa.hibernate.type;
 
import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
/**
 * A type that maps between {@link java.sql.Types#VARCHAR CHAR(1) or CHAR(2)} and {@link Boolean} (using "J " and "N ").
 * <p>
 * Optionally, a parameter "length" can be set that will result in right-padding with spaces up to the
 * specified length.
 */
public class SetType implements UserType, ParameterizedType {
 
    public static final String NAME = "ja_nee";
 
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
 
    private int length = 1;
 
    @Override
    public int[] sqlTypes() {
        return new int[] { Types.VARCHAR };
    }
 
    @SuppressWarnings("rawtypes")
    @Override
    public Class returnedClass() {
        return Set.class;
    }
 
    @Override
    public boolean equals(final Object x, final Object y) throws HibernateException {
        if (x == null || y == null) {
            return false;
        } else {
            return x.equals(y);
        }
    }
 
    @Override
    public int hashCode(final Object x) throws HibernateException {
        assert (x != null);
        return x.hashCode();
    }
 /*
    @Override
    public Object nullSafeGet(final ResultSet rs, final String[] names, final SessionImplementor session, final Object owner) throws HibernateException, SQLException {
        final String s = rs.getString(names[0]);
        if (StringUtils.isBlank(s)) {
            return false;
        }
        if ("J".equalsIgnoreCase(s.trim())) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
 
    @Override
    public void nullSafeSet(final PreparedStatement st, final Object value, final int index, final SessionImplementor session) throws HibernateException, SQLException {
        String s = Boolean.TRUE.equals(value) ? "J" : "N";
        if (this.length > 1) {
            s = StringUtils.rightPad(s, this.length);
        }
        st.setString(index, s);
    }
 */
    @Override
    public Object deepCopy(final Object value) throws HibernateException {
        return value;
    }
 
    @Override
    public boolean isMutable() {
        return true;
    }
 
    @Override
    public Serializable disassemble(final Object value) throws HibernateException {
        return (Serializable) value;
    }
 
    @Override
    public Object assemble(final Serializable cached, final Object owner) throws HibernateException {
        return cached;
    }
 
    @Override
    public Object replace(final Object original, final Object target, final Object owner) throws HibernateException {
        return original;
    }
 
    @Override
    public void setParameterValues(final Properties parameters) {
        if (parameters != null && !parameters.isEmpty()) {
            final String lengthString = parameters.getProperty("length");
            try {
                if (StringUtils.isNotBlank(lengthString)) {
                    this.setLength(Integer.parseInt(lengthString));
                }
            } catch (final NumberFormatException e) {
                LOGGER.error("Error parsing int " + lengthString, e);
            }
        }
    }

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor arg2, Object owner) throws HibernateException, SQLException {
		final String s = rs.getString(names[0]);
        if (StringUtils.isBlank(s)) {
            return null;
        }
        
        Set<String> set = new HashSet<String>();
        
        String[] array = s.split(",");
        for (String str : array) {
        	set.add(str);
        }
        
        return set;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor arg3) throws HibernateException, SQLException {
		StringBuilder s = new StringBuilder();
		if (value != null) {
			Set<String> set = (Set<String>) value;
			for (String str : set) {
				if (s.length() > 0) {
					s.append(",");
				}
	        	s.append(str);
	        }
		}
		
        st.setString(index, s.toString());
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

}