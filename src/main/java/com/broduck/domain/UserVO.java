package com.broduck.domain;

public class UserVO {

  private Integer uno;
  private String id;
  private String password;
  private String name;
  private String birth;

  public Integer getUno() {
    return uno;
  }

  public void setUno(Integer uno) {
    this.uno = uno;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBirth() {
    return birth;
  }

  public void setBirth(String birth) {
    this.birth = birth;
  }

  @Override
  public String toString() {
    return "UserVO{" +
            "uno=" + uno +
            ", id='" + id + '\'' +
            ", password='" + password + '\'' +
            ", name='" + name + '\'' +
            ", birth='" + birth + '\'' +
            '}';
  }
}
