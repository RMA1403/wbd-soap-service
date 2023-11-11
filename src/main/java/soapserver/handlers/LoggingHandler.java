package soapserver.handlers;

import java.util.Set;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import soapserver.repositories.LoggingRepostitory;

public class LoggingHandler implements SOAPHandler<SOAPMessageContext> {
  private LoggingRepostitory loggingRepostitory = new LoggingRepostitory();

  public Set<QName> getHeaders() {
    return Collections.emptySet();
  }

  public boolean handleMessage(SOAPMessageContext messageContext) {
    boolean isOutbound = (boolean) messageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

    // Only intercepts inbound message
    if (!isOutbound) {
      @SuppressWarnings("unchecked")
      Map<String, List<String>> requestHeaders = (Map<String, List<String>>) messageContext
          .get(MessageContext.HTTP_REQUEST_HEADERS);
      System.out.println(requestHeaders);
      String host = requestHeaders.get("host").get(0);

      loggingRepostitory.addNewLog(host);
    }
    return true;
  }

  public boolean handleFault(SOAPMessageContext messageContext) {
    return true;
  }

  public void close(MessageContext messageContext) {

  }
}
