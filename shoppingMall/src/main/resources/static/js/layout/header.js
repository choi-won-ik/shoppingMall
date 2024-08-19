window.onload = () => {
	const $myname = document.getElementById('myname');
	const $myMarket = document.getElementById('myMarket');

	const token = document.querySelector("meta[name='_csrf']").getAttribute("content");
	const header = document.querySelector("meta[name='_csrf_header']").getAttribute("content");

	// XMLHttpRequest 객체 생성
	let xhr = new XMLHttpRequest();

	// 요청 설정
	xhr.open('POST', '/myname', true);

	xhr.setRequestHeader(header, token);
	// 요청 헤더 설정
	xhr.setRequestHeader('Content-Type', 'application/json');

	// 요청 완료 시 실행될 함수
	xhr.onreadystatechange = function() {
		if (xhr.readyState === XMLHttpRequest.DONE) {
			if (xhr.status === 200) {
				let user = JSON.parse(xhr.responseText);
				
				if(user!==null){
					$myname.innerText = user.name;
					$myMarket.href += user.userid;
				}
			}
		}
	};
	
	// 요청 보내기
	xhr.send();
};