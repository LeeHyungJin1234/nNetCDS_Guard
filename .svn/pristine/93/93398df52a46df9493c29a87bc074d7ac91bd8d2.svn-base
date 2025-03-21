package nnsp.common;

import nnsp.security.Constants;
import nnsp.services.NcAuditService;
import nnsp.util.MessageUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;

public class SessionDestoryListener implements ApplicationListener<SessionDestroyedEvent> {

	@Autowired
	NcAuditService ncAuditService;

	@Qualifier("sessionRegistry")
	@Autowired
	SessionRegistry sessionRegistry;

	@Override
	public void onApplicationEvent(SessionDestroyedEvent event) {
		HttpSession targetSession = ((HttpSessionDestroyedEvent) event).getSession();

 		if(targetSession.getAttribute("loginUser") != null){
			String user_id = (String) (targetSession.getAttribute("loginUserId"));
			String client_ip = (String) (targetSession.getAttribute("clientIp"));
			String audit_param = MessageUtil.getMessage("log.logout.userrequest");

			List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
			List<SessionInformation> allSessions = new ArrayList<>();

			// 현재 `ROLE_ADMIN`만 사용 중이긴 하지만...
			allPrincipals.forEach(p -> allSessions.addAll(
				sessionRegistry.getAllSessions(p, false)
			));
			// user logout, session timeout
			if (!allSessions.stream().filter(info ->
				!info.getPrincipal().equals("anonymousUser")).findAny().isPresent()) {
				TimeUnit tu = TimeUnit.MILLISECONDS;
				long lastAccessedTime = (long) targetSession.getAttribute(Constants.SESSION_CHECK_TIME);

				if (lastAccessedTime>0) {
					long expireTimeInMillis =
						lastAccessedTime + tu.convert(targetSession.getMaxInactiveInterval(), TimeUnit.SECONDS);
					long currTimeInMillis = System.currentTimeMillis();

					// session timeout, 추후 대안 찾아보기
					// 현재는 getCreationTime()기준으로 하지않음(세션 연장시 대응 불가)
					if (expireTimeInMillis - currTimeInMillis - 999 <= 0) {
						audit_param = MessageUtil.getMessage("log.logout.sessiontimeout");
					}
				}
			}
			// concurrent connection(Same ID, Same authority)
			else {
				// 현재 동일ID/동일권한 구분 힘들어서 일단 동일 권한으로 통일
				audit_param = MessageUtil.getMessage("log.logout.sameauth");
			}

			String strInvalidReason = (String) targetSession.getAttribute("InvalidSession");
			//System.out.println("###### strInvalidReason = "+strInvalidReason);
			if (!audit_param.equals(MessageUtil.getMessage("log.logout.sessiontimeout")) && Objects.nonNull(strInvalidReason) && !strInvalidReason.isEmpty()
					&& (strInvalidReason.equals("CSRF") || strInvalidReason.equals("ID")
							|| strInvalidReason.equals("IP") || strInvalidReason.equals("UNAUTH")
							 || strInvalidReason.equals("TIMEOUT") || strInvalidReason.equals("RELOAD"))) {
			// 	audit_param = "비인가 요청으로 인한 로그아웃: " + strInvalidReason;
			}
			else {
				ncAuditService.mng_log_insert(user_id, client_ip,
						MessageUtil.getMessage("log.logout"),
						audit_param,
						"S", "I"); // 감사로그 저장
			}
			targetSession.invalidate(); // 세션 삭제
		}
	}
}