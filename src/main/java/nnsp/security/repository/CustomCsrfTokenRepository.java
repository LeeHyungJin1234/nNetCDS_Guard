/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nnsp.security.repository;

import static nnsp.security.Constants.SECURE_RANDOM_STRING_LENGTH;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nnsp.security.NcSecurityUtil;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.util.Assert;

/**
 * A {@link CsrfTokenRepository} that stores the {@link CsrfToken} in the {@link HttpSession}.
 *
 * @author Rob Winch
 * @since 3.2
 */
public final class CustomCsrfTokenRepository implements CsrfTokenRepository {
  private static final String DEFAULT_CSRF_PARAMETER_NAME = "_csrf";

  private static final String DEFAULT_CSRF_HEADER_NAME = "X-CSRF-TOKEN";

  private static final String DEFAULT_CSRF_TOKEN_ATTR_NAME =
    CustomCsrfTokenRepository.class.getName().concat(".CSRF_TOKEN");

  private String parameterName = DEFAULT_CSRF_PARAMETER_NAME;

  private String headerName = DEFAULT_CSRF_HEADER_NAME;

  private String sessionAttributeName = DEFAULT_CSRF_TOKEN_ATTR_NAME;

  /*
   * (non-Javadoc)
   * @see org.springframework.security.web.csrf.CsrfTokenRepository#saveToken(org.springframework.security.web.csrf.CsrfToken, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  public void saveToken(CsrfToken token, HttpServletRequest request,
    HttpServletResponse response) {
    if (token == null) {
      HttpSession session = request.getSession(false);
      if (session != null) {
        session.removeAttribute(sessionAttributeName);
      }
    } else {
      HttpSession session = request.getSession();
      session.setAttribute(sessionAttributeName, token);
    }
  }

  /* (non-Javadoc)
   * @see org.springframework.security.web.csrf.CsrfTokenRepository#loadToken(javax.servlet.http.HttpServletRequest)
   */
  public CsrfToken loadToken(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session == null) {
      return null;
    }
    return (CsrfToken) session.getAttribute(sessionAttributeName);
  }

  /*
   * (non-Javadoc)
   * @see org.springframework.security.web.csrf.CsrfTokenRepository#generateToken(javax.servlet.http.HttpServletRequest)
   */
  public CsrfToken generateToken(HttpServletRequest request) {
    return new DefaultCsrfToken(headerName, parameterName, createNewToken());
  }

  /**
   * Sets the {@link HttpServletRequest} parameter name that the {@link CsrfToken} is expected to appear on
   * @param parameterName the new parameter name to use
   */
  public void setParameterName(String parameterName) {
    Assert.hasLength(parameterName, "parameterName cannot be null or empty");
    this.parameterName = parameterName;
  }

  /**
   * Sets the header name that the {@link CsrfToken} is expected to appear on
   * and the header that the response will contain the {@link CsrfToken}.
   *
   * @param headerName
   *            the new header name to use
   */
  public void setHeaderName(String headerName) {
    Assert.hasLength(headerName, "headerName cannot be null or empty");
    this.headerName = headerName;
  }


  /**
   * Sets the {@link HttpSession} attribute name that the {@link CsrfToken} is stored in
   * @param sessionAttributeName the new attribute name to use
   */
  public void setSessionAttributeName(String sessionAttributeName) {
    Assert.hasLength(sessionAttributeName, "sessionAttributename cannot be null or empty");
    this.sessionAttributeName = sessionAttributeName;
  }

  private String createNewToken() {
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    return NcSecurityUtil.getSecureRandomString(SECURE_RANDOM_STRING_LENGTH) +
      timestamp.toString();
  }
}
