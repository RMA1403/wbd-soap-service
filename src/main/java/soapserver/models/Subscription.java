package soapserver.models;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Basic;
// import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "subscriptions")
@Getter
@Setter
public class Subscription implements Serializable {
  @Id
  private Integer id_user;

  @Basic
  private Timestamp expiration_date;
}
