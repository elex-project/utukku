
/*
 * Project Utukku
 *
 * Copyright (c) 2019-2021. Elex. All Rights Reserved.
 * https://www.elex-project.com/
 */

package com.elex_project.utukku;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import java.io.File;

public final class BodyPartBuilder {
	private MimeBodyPart bodyPart;

	public BodyPartBuilder() {
		bodyPart = new MimeBodyPart();
	}

	public MimeBodyPart build() {
		return bodyPart;
	}

	public BodyPartBuilder text(String text) throws MessagingException {
		bodyPart.setText(text);
		return this;
	}

	public BodyPartBuilder html(String html) throws MessagingException {
		bodyPart.setText(html, Mail.UTF_8, "html");
		return this;
	}

	public BodyPartBuilder content(Object content, String type) throws MessagingException {
		bodyPart.setContent(content, type);
		return this;
	}

	public BodyPartBuilder content(File file) throws MessagingException {
		DataSource source = new FileDataSource(file);
		bodyPart.setDataHandler(new DataHandler(source));
		bodyPart.setFileName(file.getName());
		return this;
	}

	public BodyPartBuilder content(File file, String contentId) throws MessagingException {
		DataSource source = new FileDataSource(file);
		bodyPart.setDataHandler(new DataHandler(source));
		bodyPart.setFileName(file.getName());
		bodyPart.setHeader("Content-ID", "<" + contentId + ">");
		return this;
	}

	public BodyPartBuilder header(String name, String value) throws MessagingException {
		bodyPart.addHeader(name, value);
		return this;
	}
}
