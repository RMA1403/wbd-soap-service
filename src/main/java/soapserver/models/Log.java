package soapserver.models;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "logs")
@Getter
@Setter
public class Log implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Basic
  private Timestamp time;

  private String origin;

  private String method;

  private String description;
}
