import ch.qos.logback.classic.Level
import ch.qos.logback.classic.encoder.PatternLayoutEncoder

import java.nio.charset.Charset

def appenderList = ["CONSOLE"]
appenderList.add("FILE")
def appName = System.getenv("SERVICE_NAME") ?: "hazel"
def appHost = System.getenv("HOST")
def profile = System.getenv("SPRING_PROFILES_ACTIVE")
Level logLevel = valueOf(System.getenv("LOG_LEVEL") ?: "INFO")

println "=" * 80
println """
    APP NAME          : $appName
    APP HOST          : $appHost
    APP PROFILE       : $profile
    LOGGING LEVEL     : $logLevel
"""
println "=" * 80

appender("CONSOLE", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        charset = Charset.forName("UTF-8")
        pattern = '%-4relative %d %-5level [ %t ] [%mdc{X-Forwarded-For}] [%mdc{X-B3-TraceId},%mdc{X-B3-SpanId}] %-55logger{13} | %m %n'
    }
}

appender("FILE", FileAppender) {
  file = "hazel.log"
  append = true
  encoder(PatternLayoutEncoder) {
      pattern = '%-4relative %d %-5level [ %t ] [%mdc{X-Forwarded-For}] [%mdc{X-B3-TraceId},%mdc{X-B3-SpanId}] %-55logger{13} | %m %n'
  }
}

logger('org.springframework', WARN)
logger('com.avaya.jtapi', WARN)
root(logLevel, appenderList)
