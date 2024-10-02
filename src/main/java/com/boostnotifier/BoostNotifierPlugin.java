package com.boostnotifier;

import com.google.inject.Provides;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Skill;
import net.runelite.api.events.StatChanged;
import net.runelite.client.Notifier;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
        name = "Boost Notifier",
        description = "Notifies you when your selected skill boost drops to certain levels",
        tags = {"boost", "notification"}
)
public class BoostNotifierPlugin extends Plugin
{
    @Inject
    private Client client;

    @Inject
    private Notifier notifier;

    @Inject
    private BoostNotifierConfig config;

    // Variables to track notification states
    private boolean[] notifiedForBoosts = new boolean[5];

    @Override
    protected void startUp() throws Exception
    {
        log.info("Boost Notifier started!");
        resetNotifications();
    }

    @Override
    protected void shutDown() throws Exception
    {
        log.info("Boost Notifier stopped!");
        resetNotifications();
    }

    @Subscribe
    public void onStatChanged(StatChanged statChanged)
    {
        Skill skillToMonitor = config.skillToMonitor();

        // Check if the stat change is for the selected skill
        if (statChanged.getSkill() != skillToMonitor)
        {
            return;
        }

        int boostedLevel = client.getBoostedSkillLevel(skillToMonitor);
        int realLevel = client.getRealSkillLevel(skillToMonitor);
        int boostDifference = boostedLevel - realLevel;

        // Loop through thresholds (up to 5)
        for (int i = 1; i <= 5; i++)
        {
            if (isBoostThresholdEnabled(i) && boostDifference == getThresholdValue(i) && !notifiedForBoosts[i - 1])
            {
                sendNotification("Your " + skillToMonitor.getName() + " boost has dropped to " + getThresholdValue(i) + "!");
                notifiedForBoosts[i - 1] = true;
            }
        }

        // Reset notifications if the boost is above the highest enabled threshold
        if (boostDifference > getHighestEnabledThreshold())
        {
            resetNotifications();
        }
    }

    // Method to send email and visual notifications
    private void sendNotification(String message)
    {
        // Send RuneLite notification
        notifier.notify(message);

        // If email notifications are enabled and settings are filled, send an email
        if (config.enableEmailNotifications() && !config.recipientEmailAddress().isEmpty())
        {
            String subject = config.enableCustomSubject() ? config.customEmailSubject() : "RuneLite Boost Notification";

            sendEmail(
                    config.smtpServer(),
                    config.smtpPort(),
                    config.senderEmailAddress(),
                    config.senderEmailPassword(),
                    config.recipientEmailAddress(),
                    subject,
                    message
            );
        }
    }

    // Helper to get the threshold value based on the index
    private int getThresholdValue(int index)
    {
        switch (index)
        {
            case 1: return config.boostThreshold1();
            case 2: return config.boostThreshold2();
            case 3: return config.boostThreshold3();
            case 4: return config.boostThreshold4();
            case 5: return config.boostThreshold5();
            default: return 0;
        }
    }

    // Check if the threshold is enabled
    private boolean isBoostThresholdEnabled(int index)
    {
        switch (index)
        {
            case 1: return config.enableBoostThreshold1();
            case 2: return config.enableBoostThreshold2();
            case 3: return config.enableBoostThreshold3();
            case 4: return config.enableBoostThreshold4();
            case 5: return config.enableBoostThreshold5();
            default: return false;
        }
    }

    // Get the highest enabled threshold
    private int getHighestEnabledThreshold()
    {
        for (int i = 5; i >= 1; i--)
        {
            if (isBoostThresholdEnabled(i))
            {
                return getThresholdValue(i);
            }
        }
        return 0;
    }

    // Reset notification states for all thresholds
    private void resetNotifications()
    {
        for (int i = 0; i < notifiedForBoosts.length; i++)
        {
            notifiedForBoosts[i] = false;
        }
    }

    // Send an email using JavaMail
    private void sendEmail(String smtpHost, int smtpPort, String username, String password, String to, String subject, String body)
    {
        // Set up email server properties
        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", String.valueOf(smtpPort));
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", String.valueOf(smtpPort));
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(username));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            msg.setSubject(subject);
            msg.setText(body);

            Transport.send(msg);

            log.info("Email sent successfully to " + to);

        } catch (MessagingException e) {
            log.error("Failed to send email", e);
        }
    }

    @Provides
    BoostNotifierConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(BoostNotifierConfig.class);
    }
}
