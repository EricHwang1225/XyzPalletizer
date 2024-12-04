/*
 * Copyright (C) 2012 LG CNS Inc.
 * All right reserved
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포 사용하는 행위를 금지합니다.
 *
 * @version $id$
 */
package com.lgcns.smartwcs.common.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * <PRE>
 * TokenCache를 위한 설정.
 * </PRE>
 *
 * @author $Author$
 * @version $Id$
 */
@EnableCaching
@Configuration()
public class TokenCacheConfig {
}
