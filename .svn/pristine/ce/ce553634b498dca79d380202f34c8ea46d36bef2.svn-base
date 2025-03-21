package nnsp.vo;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
public class NcUserDetails implements UserDetails {
	
	private List<GrantedAuthority> grantedAuthoritise;
	private NcUser ncUser;
	
	@SuppressWarnings("unlikely-arg-type")
	public NcUserDetails(NcUser ncUser){
		this.ncUser=ncUser;
		
		if("1".equals(ncUser.getNsu_secu_level())){ // 관리자
			grantedAuthoritise = AuthorityUtils.createAuthorityList("ROLE_ADMIN");
		}else if("2".equals(ncUser.getNsu_secu_level())){ // 모니터링
			grantedAuthoritise = AuthorityUtils.createAuthorityList("ROLE_USER");
		}
	}
	
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return grantedAuthoritise;
	}

	public String getPassword() {
		return ncUser.getNsu_pw();
	}

	public String getUsername() {
		return ncUser.getNsu_id();
	}

	public NcUser getNcUser() {
		return ncUser;
	}

	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return ncUser.getNsu_lock_yn() == 0;
	}

	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return ncUser.getNsu_use_yn() == 1;
	}
	
	public String getSalt(){
		return ncUser.getSalt();
	}
	
}