
/*
 * Project Utukku
 *
 * Copyright (c) 2019-2021. Elex. All Rights Reserved.
 * https://www.elex-project.com/
 */

package com.elex_project.utukku;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public final class Mail {
	static final String UTF_8 = "UTF-8";
	private final MimeMessage message;
	private Multipart multipart;

	@Contract("_, _ -> new")
	public static @NotNull Mail newGmailInstance(@NotNull String user, @NotNull String pw) {
		return new Mail(getDefaultGmailSMTPProperties(), user, pw);
	}

	public Mail(Properties properties, Authenticator authenticator) {
		message = new MimeMessage(Session.getInstance(properties, authenticator));
	}

	public Mail(Properties properties, String user, String pw) {
		this(properties, new SimpleAuthenticator(user, pw));
	}

	@NotNull
	public static Properties getDefaultGmailSMTPProperties() {
		Properties props = new Properties();
		//props.put("mail.smtp.auth", "true");
		//props.put("mail.smtp.starttls.enable", "true");
		//props.put("mail.smtp.host", "smtp.gmail.com");
		//props.put("mail.smtp.port", "587");

		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", 465);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		props.put("mail.debug", "false");
		//GMAIL_SMTP.put("mail.smtp.starttls.required", "true");
		//GMAIL_SMTP.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		props.put("mail.smtp.socketFactory.port", 465);
		return props;
	}

	public static @NotNull Properties getDefaultLocalhostSMTPProperties() {
		Properties props = new Properties();
		props.put("mail.smtp.host", "localhost");
		props.put("mail.smtp.port", 25);
		return props;
	}


	/**
	 * @param from 보내는 사람
	 * @return self
	 * @throws MessagingException 내부 구현에서 지원되지 않는 경우
	 * @see Message#setFrom(Address)
	 */
	public Mail from(Address from) throws MessagingException {
		message.setFrom(from);
		return this;
	}

	/**
	 * @param emailAddr 보내는 사람 이메일
	 * @return self
	 * @throws MessagingException 내부 구현에서 지원되지 않는 경우
	 * @see Message#setFrom(Address)
	 */
	public Mail from(String emailAddr) throws MessagingException {
		message.setFrom(new InternetAddress(emailAddr));
		return this;
	}

	/**
	 * @param emailAddr 보내는 사람 이메일
	 * @param title     보내는 사람 이름
	 * @return self
	 * @throws MessagingException           내부 구현에서 지원되지 않는 경우
	 * @throws UnsupportedEncodingException utf-8 미지원
	 * @see Message#setFrom(Address)
	 */
	public Mail from(String emailAddr, String title) throws MessagingException, UnsupportedEncodingException {
		message.setFrom(new InternetAddress(emailAddr, title, UTF_8));
		return this;
	}

	/**
	 * 받는 사람이 여러 명인 경우, 여러 번 호출하시오.
	 *
	 * @param to 받는 사람
	 * @return self
	 * @throws MessagingException 내부 구현에서 지원되지 않는 경우
	 * @see MimeMessage#addRecipient(Message.RecipientType, Address)
	 */
	public Mail to(Address to) throws MessagingException {
		message.addRecipient(Message.RecipientType.TO, to);
		return this;
	}

	/**
	 * @param emailAddr 받는 사람
	 * @return self
	 * @throws MessagingException 내부 구현에서 지원되지 않는 경우
	 * @see MimeMessage#addRecipient(Message.RecipientType, Address)
	 */
	public Mail to(String emailAddr) throws MessagingException {
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddr));
		return this;
	}

	/**
	 * @param emailAddr 받는 사람
	 * @param title     받는 사람 이름
	 * @return self
	 * @throws MessagingException           내부 구현에서 지원되지 않는 경우
	 * @throws UnsupportedEncodingException utf-8 인코딩 미지원
	 * @see MimeMessage#addRecipient(Message.RecipientType, Address)
	 */

	public Mail to(String emailAddr, String title) throws MessagingException, UnsupportedEncodingException {
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddr, title, UTF_8));
		return this;
	}

	/**
	 * @param to 참조
	 * @return self
	 * @throws MessagingException 내부 구현에서 지원되지 않는 경우
	 * @see MimeMessage#addRecipient(Message.RecipientType, Address)
	 */
	public Mail cc(Address to) throws MessagingException {
		message.addRecipient(Message.RecipientType.CC, to);
		return this;
	}

	/**
	 * @param emailAddr 참조 이메일 주소
	 * @return self
	 * @throws MessagingException 내부 구현에서 지원되지 않는 경우
	 * @see MimeMessage#addRecipient(Message.RecipientType, Address)
	 */
	public Mail cc(String emailAddr) throws MessagingException {
		message.addRecipient(Message.RecipientType.CC, new InternetAddress(emailAddr));
		return this;
	}

	/**
	 * @param emailAddr 참조 이메일 주소
	 * @param title     참조 받는 사람 이름
	 * @return self
	 * @throws MessagingException           내부 구현에서 지원되지 않는 경우
	 * @throws UnsupportedEncodingException utf-8 인코딩 미지원
	 * @see MimeMessage#addRecipient(Message.RecipientType, Address)
	 */
	public Mail cc(String emailAddr, String title) throws MessagingException, UnsupportedEncodingException {
		message.addRecipient(Message.RecipientType.CC, new InternetAddress(emailAddr, title, UTF_8));
		return this;
	}

	/**
	 * @param to 숨은 참조
	 * @return self
	 * @throws MessagingException 내부 구현에서 지원되지 않는 경우
	 * @see MimeMessage#addRecipient(Message.RecipientType, Address)
	 */
	public Mail bcc(Address to) throws MessagingException {
		message.addRecipient(Message.RecipientType.BCC, to);
		return this;
	}

	/**
	 * @param emailAddr 숨은 참조 이메일 주소
	 * @return self
	 * @throws MessagingException 내부 구현에서 지원되지 않는 경우
	 * @see MimeMessage#addRecipient(Message.RecipientType, Address)
	 */
	public Mail bcc(String emailAddr) throws MessagingException {
		message.addRecipient(Message.RecipientType.BCC, new InternetAddress(emailAddr));
		return this;
	}

	/**
	 * @param emailAddr 숨은 참조 이메일 주소
	 * @param title     숨은 참조 이름
	 * @return self
	 * @throws MessagingException           내부 구현에서 지원되지 않는 경우
	 * @throws UnsupportedEncodingException utf-8 인코딩 미지원
	 * @see MimeMessage#addRecipient(Message.RecipientType, Address)
	 */
	public Mail bcc(String emailAddr, String title) throws MessagingException, UnsupportedEncodingException {
		message.addRecipient(Message.RecipientType.BCC, new InternetAddress(emailAddr, title, UTF_8));
		return this;
	}

	/**
	 * 메일 제목
	 *
	 * @param subject 이메일 제목
	 * @return self
	 * @throws MessagingException 내부 구현에서 지원되지 않는 경우
	 * @see Message#setSubject(String)
	 */
	public Mail subject(String subject) throws MessagingException {
		message.setSubject(subject, UTF_8);
		return this;
	}

	/**
	 * 텍스트 메시지
	 *
	 * @param text 이메일 메시지
	 * @return self
	 * @throws MessagingException 내부 구현에서 지원되지 않는 경우
	 * @see Message#setText(String)
	 */
	public Mail text(String text) throws MessagingException {
		message.setText(text, UTF_8);
		return this;
	}

	/**
	 * text/html 메시지
	 *
	 * @param html HTML 메시지
	 * @return self
	 * @throws MessagingException 내부 구현에서 지원되지 않는 경우
	 * @see MimeMessage#setContent(Object, String)
	 */
	public Mail html(String html) throws MessagingException {
		message.setText(html, UTF_8, "html");
		return this;
	}

	/**
	 * 멀티 파트 메시지에 바디 파트 추가
	 *
	 * @param message 멀티파트 메시지
	 * @return self
	 * @throws MessagingException 내부 구현에서 지원되지 않는 경우
	 * @see Multipart#addBodyPart(BodyPart)
	 */
	public Mail content(BodyPart message) throws MessagingException {
		if (null == multipart) {
			multipart = new MimeMultipart();
		}
		multipart.addBodyPart(message);
		return this;
	}

	/**
	 * 멀티 파트 메시지에 문자열을 첨부
	 *
	 * @param message 멀티파트 텍스트 메시지
	 * @return self
	 * @throws MessagingException 내부 구현에서 지원되지 않는 경우
	 * @see #content(BodyPart)
	 */
	public Mail content(String message) throws MessagingException {
		MimeBodyPart bodyPart = new MimeBodyPart();
		bodyPart.setText(message, UTF_8);
		return content(bodyPart);
	}

	/**
	 * 멀티 파트 메시지에 파일을 첨부
	 *
	 * @param file 첨부 파일
	 * @return self
	 * @throws MessagingException 내부 구현에서 지원되지 않는 경우
	 * @see #content(BodyPart)
	 */
	public Mail content(File file) throws MessagingException {
		MimeBodyPart bodyPart = new MimeBodyPart();
		DataSource source = new FileDataSource(file);
		bodyPart.setDataHandler(new DataHandler(source));
		bodyPart.setFileName(file.getName());
		return content(bodyPart);
	}

	/**
	 * 멀티 파트 메시지에 파일을 첨부
	 *
	 * @param file      첨부 파일
	 * @param contentId 인라인 이미지를 사용하려는 때에 참조 id로 사용한다.
	 *                     eg. String htmlText = \"&lt;h1&gt;Hello^lt;/h1&gt;&lt;img src=\"cid:image\"&gt;\";
	 * @return self
	 * @throws MessagingException 내부 구현에서 지원되지 않는 경우
	 * @see #content(BodyPart)
	 */
	public Mail content(File file, String contentId) throws MessagingException {
		MimeBodyPart bodyPart = new MimeBodyPart();
		DataSource source = new FileDataSource(file);
		bodyPart.setDataHandler(new DataHandler(source));
		bodyPart.setFileName(file.getName());
		bodyPart.setHeader("Content-ID", "<" + contentId + ">");
		return content(bodyPart);
	}

	/**
	 * 헤더를 추가한다. 헤더는 US-ASCII 인코딩을 사용하도록 되어 있다.
	 *
	 * @param name name
	 * @param value value
	 * @return self
	 * @throws MessagingException 내부 구현에서 지원되지 않는 경우
	 * @see Message#addHeader(String, String)
	 */
	public Mail header(String name, String value) throws MessagingException {
		message.addHeader(name, value);
		return this;
	}

	/**
	 * Sender 헤더 필드를 추가한다.
	 *
	 * @param sender sender. 널이면 헤더 값을 지운다.
	 * @return self
	 * @throws MessagingException 내부 구현에서 지원되지 않는 경우
	 * @see MimeMessage#setSender(Address)
	 */
	public Mail sender(String sender) throws MessagingException {
		if (null == sender) {
			message.setSender(null);
		} else {
			message.setSender(new InternetAddress(sender));
		}

		return this;
	}

	/**
	 * Reply-To 헤더를 추가한다.
	 *
	 * @param replyTo reply-to
	 * @return self
	 * @throws MessagingException 내부 구현에서 지원되지 않는 경우
	 * @see Message#setReplyTo(Address[])
	 */
	public Mail replyTo(String... replyTo) throws MessagingException {
		if (null == replyTo) {
			message.setReplyTo(null);
		} else {
			InternetAddress[] addresses = new InternetAddress[replyTo.length];
			for (int i = 0; i < replyTo.length; i++) {
				addresses[i] = new InternetAddress(replyTo[i]);
			}
			message.setReplyTo(addresses);
		}

		return this;
	}

	/**
	 * 메일 전송
	 *
	 * @throws MessagingException 내부 구현에서 지원되지 않는 경우
	 * @see Transport#send(Message)
	 */
	public void send() throws MessagingException {
		if (null != multipart) {
			message.setContent(multipart);
		}

		Transport.send(message);
	}

	/**
	 * "1″ This is the most common way of setting the priority of an email.
	 * "3″ is normal, and "5″ is the lowest. "2″ and "4″ are in-betweens,
	 * and frankly I’ve never seen anything but "1″ or "3″ used.
	 *
	 * @param priority 우선 순위. 1은 높음. 3은 보통. 5는 낮음.
	 * @return self
	 * @throws MessagingException 내부 구현에서 지원되지 않는 경우
	 */
	public Mail priority(int priority) throws MessagingException {
		message.addHeader("X-Priority", String.valueOf(priority));
		return this;
	}
}
