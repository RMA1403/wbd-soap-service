package soapserver;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class SubscriptionService {
  @WebMethod
  public String hello() {
    return "TEKS INI DIKIRIM MELALUI SABUN :o";
  }
}
