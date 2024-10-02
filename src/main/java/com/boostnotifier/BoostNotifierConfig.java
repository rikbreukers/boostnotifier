package com.boostnotifier;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.api.Skill;

@ConfigGroup("strengthboostnotifier")
public interface BoostNotifierConfig extends Config
{
    // Skill to monitor
    @ConfigItem(
            keyName = "skillToMonitor",
            name = "Skill to Monitor",
            description = "Choose the skill for which you want boost notifications",
            position = 0
    )
    default Skill skillToMonitor()
    {
        return Skill.STRENGTH;
    }

    // Threshold 1
    @ConfigItem(
            keyName = "boostThreshold1",
            name = "Boost Threshold 1",
            description = "Notify when boost level drops to this value",
            position = 1
    )
    default int boostThreshold1()
    {
        return 5;
    }

    @ConfigItem(
            keyName = "enableBoostThreshold1",
            name = "Enable Threshold 1",
            description = "Enable notification for boost threshold 1",
            position = 2
    )
    default boolean enableBoostThreshold1()
    {
        return true;
    }

    // Threshold 2
    @ConfigItem(
            keyName = "boostThreshold2",
            name = "Boost Threshold 2",
            description = "Notify when boost level drops to this value",
            position = 3
    )
    default int boostThreshold2()
    {
        return 3;
    }

    @ConfigItem(
            keyName = "enableBoostThreshold2",
            name = "Enable Threshold 2",
            description = "Enable notification for boost threshold 2",
            position = 4
    )
    default boolean enableBoostThreshold2()
    {
        return true;
    }

    // Threshold 3
    @ConfigItem(
            keyName = "boostThreshold3",
            name = "Boost Threshold 3",
            description = "Notify when boost level drops to this value",
            position = 5
    )
    default int boostThreshold3()
    {
        return 1;
    }

    @ConfigItem(
            keyName = "enableBoostThreshold3",
            name = "Enable Threshold 3",
            description = "Enable notification for boost threshold 3",
            position = 6
    )
    default boolean enableBoostThreshold3()
    {
        return true;
    }

    // Threshold 4
    @ConfigItem(
            keyName = "boostThreshold4",
            name = "Boost Threshold 4",
            description = "Notify when boost level drops to this value",
            position = 7
    )
    default int boostThreshold4()
    {
        return 0;
    }

    @ConfigItem(
            keyName = "enableBoostThreshold4",
            name = "Enable Threshold 4",
            description = "Enable notification for boost threshold 4",
            position = 8
    )
    default boolean enableBoostThreshold4()
    {
        return false;
    }

    // Threshold 5
    @ConfigItem(
            keyName = "boostThreshold5",
            name = "Boost Threshold 5",
            description = "Notify when boost level drops to this value",
            position = 9
    )
    default int boostThreshold5()
    {
        return 0;
    }

    @ConfigItem(
            keyName = "enableBoostThreshold5",
            name = "Enable Threshold 5",
            description = "Enable notification for boost threshold 5",
            position = 10
    )
    default boolean enableBoostThreshold5()
    {
        return false;
    }

    // Email Notifications Section
    @ConfigItem(
            keyName = "enableEmailNotifications",
            name = "Enable Email Notifications",
            description = "Send an email when a boost notification is triggered",
            position = 11
    )
    default boolean enableEmailNotifications()
    {
        return false;
    }

    // Custom Email Subject
    @ConfigItem(
            keyName = "enableCustomSubject",
            name = "Enable Custom Subject",
            description = "Enable the use of a custom email subject",
            position = 12
    )
    default boolean enableCustomSubject()
    {
        return false;
    }

    @ConfigItem(
            keyName = "customEmailSubject",
            name = "Custom Email Subject",
            description = "Enter a custom subject for email notifications",
            position = 13
    )
    default String customEmailSubject()
    {
        return "RuneLite Boost Notification";
    }

    // Email Settings
    @ConfigItem(
            keyName = "recipientEmailAddress",
            name = "Recipient Email Address",
            description = "The email address to send notifications to",
            position = 14
    )
    default String recipientEmailAddress()
    {
        return "";
    }

    @ConfigItem(
            keyName = "senderEmailAddress",
            name = "Sender Email Address",
            description = "The email address to send notifications from",
            position = 15
    )
    default String senderEmailAddress()
    {
        return "";
    }

    @ConfigItem(
            keyName = "senderEmailPassword",
            name = "Sender Email Password",
            description = "The password for the sender email address (use app password for Gmail with 2FA)",
            position = 16
    )
    default String senderEmailPassword()
    {
        return "";
    }

    @ConfigItem(
            keyName = "smtpPort",
            name = "SMTP Port",
            description = "Port to use for the SMTP server (e.g., 465 for SSL/TLS)",
            position = 17
    )
    default int smtpPort()
    {
        return 465;
    }

    @ConfigItem(
            keyName = "smtpServer",
            name = "SMTP Server",
            description = "SMTP server to use for sending emails (e.g., smtp.gmail.com)",
            position = 18
    )
    default String smtpServer()
    {
        return "smtp.gmail.com";
    }
}
