package com.student.rating.encoder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;

@SuppressWarnings("deprecation")
public class ShaPasswordEncoder extends MessageDigestPasswordEncoder {

	private static final String EMPTY_SALT = "";
	private static final Logger LOG = LoggerFactory.getLogger(ShaPasswordEncoder.class);

	public ShaPasswordEncoder() {
		this(1);
	}

	public ShaPasswordEncoder(int strength) {
		super("SHA-" + strength);
	}

	public String encode(String rawPassword) {
		try {
			Method method = getClass().getSuperclass().getDeclaredMethod("digest", String.class, CharSequence.class);
			method.setAccessible(true);
			return (String) method.invoke(this, EMPTY_SALT, rawPassword);
		} catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			LOG.error("Error during encoding user password. \n", e);
		}
		return rawPassword;
	}
}
