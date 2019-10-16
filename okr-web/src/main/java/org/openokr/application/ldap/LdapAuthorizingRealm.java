package org.openokr.application.ldap;

import org.apache.shiro.realm.ldap.DefaultLdapRealm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Desc:
 * @author: cww
 * @DateTime: 2019/10/15 16:54
 */

public class LdapAuthorizingRealm extends DefaultLdapRealm {
    private Logger logger = LoggerFactory.getLogger(LdapAuthorizingRealm.class);

}
