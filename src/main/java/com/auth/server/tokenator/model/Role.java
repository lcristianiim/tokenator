package com.auth.server.tokenator.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
  ROLE_GUEST,
  ROLE_ADMIN;

  public String getAuthority() {
    return name();
  }

}
