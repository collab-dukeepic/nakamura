/**
 * Licensed to the Sakai Foundation (SF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The SF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.sakaiproject.nakamura.api.ldap;


public class LdapUtil {
  /**
   * Escapes input intended for filter using a DN.<br/>
   * http://www.owasp.org/index.php/Preventing_LDAP_Injection_in_Java<br/>
   * <table>
   * <tr>
   * <th>Input</th>
   * <th>Output</th>
   * </tr>
   * <tr>
   * <td>\</td>
   * <td>\\</td>
   * </tr>
   * <tr>
   * <td>,</td>
   * <td>\,</td>
   * </tr>
   * <tr>
   * <td>+</td>
   * <td>\+</td>
   * </tr>
   * <tr>
   * <td>&quot;</td>
   * <td>\&quot;</td>
   * </tr>
   * <tr>
   * <td>&lt;</td>
   * <td>\&lt;</td>
   * </tr>
   * <tr>
   * <td>&gt;</td>
   * <td>\&gt;</td>
   * </tr>
   * <tr>
   * <td>;</td>
   * <td>\;</td>
   * </tr>
   * </table>
   *
   * @param name
   * @return
   */
  public static String escapeDN(String name) {
    StringBuilder sb = new StringBuilder();
    if ((name.length() > 0) && ((name.charAt(0) == ' ') || (name.charAt(0) == '#'))) {
      sb.append('\\'); // add the leading backslash if needed
    }
    for (int i = 0; i < name.length(); i++) {
      char curChar = name.charAt(i);
      switch (curChar) {
      case '\\':
        sb.append("\\\\");
        break;
      case ',':
        sb.append("\\,");
        break;
      case '+':
        sb.append("\\+");
        break;
      case '"':
        sb.append("\\\"");
        break;
      case '<':
        sb.append("\\<");
        break;
      case '>':
        sb.append("\\>");
        break;
      case ';':
        sb.append("\\;");
        break;
      default:
        sb.append(curChar);
      }
    }
    if ((name.length() > 1) && (name.charAt(name.length() - 1) == ' ')) {
      sb.insert(sb.length() - 1, '\\'); // add the trailing backslash if needed
    }
    return sb.toString();
  }

  /**
   * Escapes input intended for filter using a search.<br/>
   * http://www.owasp.org/index.php/Preventing_LDAP_Injection_in_Java<br/>
   * <table>
   * <tr>
   * <th>Input</th>
   * <th>Output</th>
   * </tr>
   * <tr>
   * <td>\</td>
   * <td>\5c</td>
   * </tr>
   * <tr>
   * <td>*</td>
   * <td>\2a</td>
   * </tr>
   * <tr>
   * <td>(</td>
   * <td>\28</td>
   * </tr>
   * <tr>
   * <td>)</td>
   * <td>\29</td>
   * </tr>
   * <tr>
   * <td>\u0000</td>
   * <td>\00</td>
   * </tr>
   * </table>
   *
   * @param filter
   * @return
   */
  public static final String escapeLDAPSearchFilter(String filter) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < filter.length(); i++) {
      char curChar = filter.charAt(i);
      switch (curChar) {
      case '\\':
        sb.append("\\5c");
        break;
      case '*':
        sb.append("\\2a");
        break;
      case '(':
        sb.append("\\28");
        break;
      case ')':
        sb.append("\\29");
        break;
      case '\u0000':
        sb.append("\\00");
        break;
      default:
        sb.append(curChar);
      }
    }
    return sb.toString();
  }
}
