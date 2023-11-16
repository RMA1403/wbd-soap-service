package soapserver.services;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import soapserver.repositories.SubscriptionRepository;

@WebService
@HandlerChain(file = "handler-chain.xml")
public class SubscriptionService {
  private SubscriptionRepository subscriptionRepo = new SubscriptionRepository();

  // ! Delete this method later
  @WebMethod
  public String hello() {
    System.out.println("WOJWEKEJWFJEIF");
    subscriptionRepo.getMessages();
    return "TEKS INI DIKIRIM MELALUI SABUN :o";
  }

  @WebMethod
  public String checkSubscription(
      @WebParam(name = "idUser") int idUser) {
    try {
      boolean subscribed = subscriptionRepo.isSubscribed(idUser);

      return subscribed ? "subscribed" : "not subscribed";
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

  @WebMethod
  public String addSubscription(
    @WebParam(name = "idUser") int idUser) {
      try {
        boolean success = subscriptionRepo.addSubscription(idUser);

        return success? "success" : "failed";
      } catch (Exception e) {
        System.out.println(e.getMessage());
        return "error";
      }
  }
}
