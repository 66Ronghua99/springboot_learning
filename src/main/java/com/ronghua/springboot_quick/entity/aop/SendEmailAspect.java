package com.ronghua.springboot_quick.entity.aop;

import com.ronghua.springboot_quick.entity.auth.UserIdentity;
import com.ronghua.springboot_quick.service.MailService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
@Aspect
public class SendEmailAspect {

    @Autowired
    private UserIdentity userIdentity;

    @Autowired
    private MailService mailService;

    private static final Map<ActionType, String> SUBJECT_TEMPLATE_MAP;
    private static final Map<ActionType, String> MESSAGE_TEMPLATE_MAP;

    static {
        SUBJECT_TEMPLATE_MAP = new EnumMap<>(ActionType.class);
        SUBJECT_TEMPLATE_MAP.put(ActionType.CREATE, "New %s");
        SUBJECT_TEMPLATE_MAP.put(ActionType.UPDATE, "Update %s");
        SUBJECT_TEMPLATE_MAP.put(ActionType.DELETE, "Delete %s");

        MESSAGE_TEMPLATE_MAP = new EnumMap<>(ActionType.class);
        MESSAGE_TEMPLATE_MAP.put(ActionType.CREATE, "Hi, %s. There's a new %s (%s) created.");
        MESSAGE_TEMPLATE_MAP.put(ActionType.UPDATE, "Hi, %s. There's a %s (%s) updated.");
        MESSAGE_TEMPLATE_MAP.put(ActionType.DELETE, "Hi, %s. A %s (%s) is just deleted.");
    }

    @Pointcut("@annotation(com.ronghua.springboot_quick.entity.aop.SendMail)")
    public void pointcut() {
    }

    @AfterReturning(pointcut = "pointcut()", returning = "result")
    public void sendEmail(JoinPoint joinPoint, Object result) {

    }
}
