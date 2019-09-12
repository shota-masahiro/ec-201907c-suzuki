package com.example.common;

import java.nio.charset.StandardCharsets;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * メール送信機能を管理するサービス.
 * 
 * @author shota.suzuki
 *
 */
@Service
public class SendMailService {

	@Autowired
	private JavaMailSender javaMailSender;


	/**
	 * ログインユーザのメールアドレス登録などを行います.
	 * 
	 * @param context
	 * @param mailAddress
	 */
	public void sendMail(Context context, String mailAddress) {
		javaMailSender.send(new MimeMessagePreparator() {

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());
				helper.setFrom("aaa@test.com");
				helper.setTo(mailAddress);
				helper.setSubject("商品一覧");
				helper.setText(getMailBody("samplemail", context), true);
			}
		});
	}


	/**
	 * メール本文を作ります.
	 * 
	 * @param templateName
	 * @param context
	 * @return
	 */
	private String getMailBody(String templateName, Context context) {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(mailTemplateResolver());
		return templateEngine.process(templateName, context);
	}


	/**
	 * メールの形式を規定します.
	 * 
	 * @return
	 */
	private ClassLoaderTemplateResolver mailTemplateResolver() {
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setPrefix("mailtemplate/");
		templateResolver.setSuffix(".html");
		templateResolver.setCharacterEncoding("UTF-8");
		templateResolver.setCacheable(true);
		return templateResolver;
	}

}