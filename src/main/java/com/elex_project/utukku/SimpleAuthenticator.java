
/*
 * Project Utukku
 *
 * Copyright (c) 2019-2021. Elex. All Rights Reserved.
 * https://www.elex-project.com/
 */

package com.elex_project.utukku;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public final class SimpleAuthenticator extends Authenticator {
	private String user, pw;

	public SimpleAuthenticator(String user, String pw) {
		this.user = user;
		this.pw = pw;
	}

	@NotNull
	@Contract(value = " -> new", pure = true)
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(user, pw);
	}
}
