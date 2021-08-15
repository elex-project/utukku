# Project Utukku

E-Mail to Send

```java
new Mail()
    .subject("Test message")
    //.text("This is a test message")
    //.html("<h1>Test Message</h1><p>Hello, there</p>")
    .content(new BodyPartBuilder().html("<h1>Test Message</h1><p>Hello, there</p>").build())
    .content(new File("README.md"))
    .from("sender@example.com", "Elex")
    .to("receiver@example.com")
    .send();
```

```java
Properties props = new Properties();
props.put("mail.smtp.host", "smtp.gmail.com");
props.put("mail.smtp.port", 465);
props.put("mail.smtp.auth", "true");
props.put("mail.smtp.ssl.enable", "true");
props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
props.put("mail.debug", "false");
props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
props.put("mail.smtp.socketFactory.fallback", "false");
props.put("mail.smtp.socketFactory.port", 465);
```

---
developed by Elex

https://www.elex-project.com
