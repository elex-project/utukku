/******************************************************************************
 * Project Utukku: E-Mail to Send                                             *
 *                                                                            *
 * Copyright (c) 2019. Elex. All Rights Reserved.                             *
 * https://www.elex-project.com/                                              *
 ******************************************************************************/

package com.elex_project.utukku;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;
import java.io.File;
import java.io.UnsupportedEncodingException;

public class MailTest {

	// @Test @Disabled
	public void send() throws MessagingException, UnsupportedEncodingException {
		String GMAIL_ACCOUNT = "";
		String GMAIL_PW = "";
		String RECEIVER = "";
		String SENDER = "";
		String SENDER_NAME = "";
		;
		Mail.newGmailInstance(GMAIL_ACCOUNT, GMAIL_PW)
				.subject("Test message")
				//.text("This is a test message")
				//.html("<h1>Test Message</h1><p>Hello, there</p>")
				.content(new BodyPartBuilder().html("<h1>Test Message</h1><p>Hello, there</p>").build())
				.content(new File("README.md"))
				.from(SENDER, SENDER_NAME)
				.to(RECEIVER)
				.send();
	}
}
