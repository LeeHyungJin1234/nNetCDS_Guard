## 2024-11-11 이형진
 1. 트래픽통계 > 그룹 선택 시 보안정책 페이지에서 해당그룹만 펼쳐지게 이동.
   - tx_proc 및 rx_proc에 hit 기능 추가 및 sendGroupList(ngl_group_name) 기능 추가 
   - rx_proc.do에서 model에 ngl_group_name과 ngl_id 추가 
   - group_list_grid.do에 @RequestParam ngl_group_name 추가
   - 수정 파일 : /views/stat/stat_traffic.jsp, /views/analyze/group_list_grid.jsp, 
   			   nnsp.controllers.NcStatController.java, nnsp.controllers.NcWhController.java
 
 2. 트래픽통계 > 내부 또는 외부 통신현황의 전체 보기 클릭시 해당되는 구분값으로 필터링되어 통신현황 페이지로 이동.
   - view_all_logs(ps_inout)에 ps_inout 파라미터 추가 및 id에 추가
   - ics_anomaly_list_grid.do에 @RequestParam stat_ps_inout 추가
   - ics_anomaly_list_grid.jsp에서 stat_ps_inout을 받아서 필터링 적용
   - 수정 파일 : /views/stat/stat_traffic.jsp, /views/analyze/ics_anomaly_list_grid.jsp,
			   nnsp.controllers.NcWhController.java
			   
			   
## 2025-02-04 박상현
1. csrf.jsp 파일 뒤로가기 방지 부분 수정 (뒤로가기는 작동하나 다시 되돌아 가기는 초기화 됨)
    history.pushState(null, null, document.URL);
	window.addEventListener('popstate', function() {
	    history.pushState(null, null, document.URL);
	    //alert('뒤로가기 버튼이 눌렸습니다!');
	});
	
2. 접근제어 정책 등록 폼 수정 
   - 현재 컨펌된 내용으로 등록 폼 수정
   - 정책명, 프로토콜, 보안영역(IP/IP대역, 단일PORT/ANY), 비보안영역(IP/IP대역, 단일PORT)
   - 그룹 및 구분 삭제 -> 그룹 대신 프로토콜 추가 HTTP, Oracle(TNS), MSSQL(ODBC)
   
   
 ## 2025-02-06 이형진
 1. 접근제어정책 등록 수정 
   - 보안영역과 비-보안영역을 좌우로 배치, IP 대역 선택 삭제
   - 그룹 등록 버튼 삭제
   - 접근제어 정책 등록, 수정, 삭제 기능 개발
   - 정책 사용여부 스위치 개발
 2. 접근제어정책 리스트 수정 
   - Grouping 기능 삭제
   - ID, 서비스 -> 정책명, 프로토콜로 변경   
 3. 접근통제현황 페이지 수정
   - 정책등록 버튼 삭제
   - 1시간, 6시간, 12시간 선택 후 검색 시 선택한 시간으로 활성화 되게 수정
   
## 2025-02-07 이형진
 1. 트래픽 통계 페이지 수정
   - 기존 그룹명이 표시되던 부분을 정책명으로 수정
   - 정책의 사용여부에 따라 Sankey Chart의 Node 색상 적용
   - 기존 그룹ID와 그룹명으로 맵핑하던 프로시저를 정책ID와 정책명으로 수정
   
## 2025-02-28 이형진
 1. 보안 정책 페이지 수정
   - 정책 등록 팝업과 정책 리스트에 정책 등급 추가
   - DB에 nnc_policy_level 테이블 추가 및 1 / 일반 등급 추가
 2. 환경설정
   - 관리자 등록 시 연속된 문자나 숫자를 3회 이상 입력시 예외처리 적용
   
## 2025-03-19 이형진
 1. 보안요구사항 미비 항목 개선
   - 관리자 등록 및 수정 패스워드 규칙 개선(연속된 3자리 이상 입력 시, 수정은 전부 다)
   - 메일 서버 설정 개선
   - 초기설정 부분에 로고 및 저작권이 잘못 표시 되어 있어서 수정
   - 계정잠금 팝업 및 로그 출력 수정
   - 추후 모듈 개발이 완료된 시점에서 추가로 테스트 진행해야 함.