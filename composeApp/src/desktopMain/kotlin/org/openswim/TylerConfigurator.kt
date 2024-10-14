package org.openswim

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.spi.Configurator
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.classic.tyler.TylerConfiguratorBase
import ch.qos.logback.core.Appender
import ch.qos.logback.core.FileAppender
import java.nio.file.Paths

class TylerConfigurator : TylerConfiguratorBase(), Configurator {
    /**
     * This method performs configuration per [Configurator] interface.
     *
     * If `TylerConfgiurator` is installed as a configurator service, this
     * method will be called by logback-classic during initialization.
     */
    override fun configure(loggerContext: LoggerContext): Configurator.ExecutionStatus {
        context = loggerContext;
        val appenderFILE = setupAppenderFILE();
        val logger_ROOT = setupLogger("ROOT", "DEBUG", null);
        logger_ROOT.addAppender(appenderFILE);
        return Configurator.ExecutionStatus.DO_NOT_INVOKE_NEXT_IF_ANY;
    }

    private fun setupAppenderFILE(): Appender<ILoggingEvent> {
        val appenderFILE = FileAppender<ILoggingEvent>()
        appenderFILE.setContext(context);
        appenderFILE.setName("FILE");
        val timestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            .toString().replace(":", "").replace("-", "").replace(".", "").replace("T", "-").substring(0, 15)
        val logFilePath = if (System.getProperty("os.name").lowercase().contains("win")) {
            val appDataPath = System.getenv("APPDATA")
            Paths.get(appDataPath, "OpenSwim", "logs", "${timestamp}.log").toString()
        } else {
            val homePath = System.getProperty("user.home")
            val openSwimPath = Paths.get(homePath, ".local", "openswim", "logs")
            Paths.get(openSwimPath.toString(), "${timestamp}.log").toString()
        }
        appenderFILE.file = logFilePath
        appenderFILE.setAppend(true);
        appenderFILE.setImmediateFlush(true);

        // Configure component of type PatternLayoutEncoder
        val patternLayoutEncoder = PatternLayoutEncoder();
        patternLayoutEncoder.setContext(context);
        patternLayoutEncoder.setPattern("%-30(%d{HH:mm:ss.SSS} [%thread]) %-5level -%logger [%file:%line] -%kvp- %msg%n");
        patternLayoutEncoder.setParent(appenderFILE);
        patternLayoutEncoder.start();
        // Inject component of type PatternLayoutEncoder into parent
        appenderFILE.setEncoder(patternLayoutEncoder);

        appenderFILE.start();
        return appenderFILE;
    }
}
