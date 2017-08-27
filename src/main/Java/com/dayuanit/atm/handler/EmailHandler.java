package com.dayuanit.atm.handler;

import com.dayuanit.atm.domain.Transfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentLinkedQueue;

@Component//在初始化bean的时候就将这个类初始化好
public class EmailHandler implements InitializingBean{

    private Logger log = LoggerFactory.getLogger(EmailHandler.class);

    private final static ConcurrentLinkedQueue<Transfer> queue = new ConcurrentLinkedQueue<Transfer>();

    //LinkedList是线程不安全的，ConcurrentLinkedQueue是单向队列，ConcurrentLinkedDQueue是双向队列

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SimpleMailMessage templateMeaasge;

    public void sendEmail() {

        while (true) {
            log.info("------begin send mail-------------");
            Transfer transfer = queue.poll();//从队列的头部取数据
            if (null == transfer) {
                log.info("---------send email sleep --------");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                }
                continue;
            }

            SimpleMailMessage msg = new SimpleMailMessage(this.templateMeaasge);
            msg.setTo("young9s@126.com");
            msg.setText("尊敬的用户" + transfer.getInCardNum() + "，您的转账已经到位,请注意查收");
            mailSender.send(msg);
            log.info("-------send email over---------");
        }
    }

    public void submit(Transfer transfer) {
        queue.offer(transfer);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                sendEmail();
            }
        }).start();//启动线程是start
    }
}
