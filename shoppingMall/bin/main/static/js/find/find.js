$(document).ready(()=>{
	// 로그인 모달 열기
	const $findLogin = document.getElementById('find-login');
	const $modal = document.getElementById('modal');

	$findLogin.addEventListener('click', () => {
		$modal.classList.remove('hidden');
	});

});