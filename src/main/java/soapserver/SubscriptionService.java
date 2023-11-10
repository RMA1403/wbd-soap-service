package soapserver;

import javax.jws.WebMethod;
// import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import soapserver.repositories.SubscriptionRepository;

@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public class SubscriptionService {
  private SubscriptionRepository subscriptionRepo = new SubscriptionRepository();

  @WebMethod
  public String hello() {
    System.out.println("WOJWEKEJWFJEIF");
    subscriptionRepo.getMessages();
    return "TEKS INI DIKIRIM MELALUI SABUN :o";
  }

  @WebMethod
  public String checkSubscription(int id_user) {
    try {
      System.out.println(id_user);
      System.out.println(subscriptionRepo.isSubscribed(id_user));

      return "wowowowo";
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return e.getMessage();
    }
  }

  @WebMethod
  public String seedSubscription() {
    try {
      boolean success = subscriptionRepo.seedSubscription();

      return success ? "success" : "seeding failed";
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return "error";
    }
  }
}
