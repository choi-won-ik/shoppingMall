$(document).ready(() => {
	const token = $("meta[name='_csrf']").attr("content");
	const header = $("meta[name='_csrf_header']").attr("content");
	// 아래 옵션 컨트롤
	const $item = document.getElementById('item');
	const $itemMenu = $item.getElementsByTagName('li');
	const $description = document.getElementById('description');
	const $itemOption = $description.getElementsByTagName('label');
	let count = 0;

	for (let i = 0; i < $itemMenu.length; i++) {
		$itemMenu[i].addEventListener('click', () => {
			$itemMenu[count].classList.remove('active');
			$itemOption[count].classList.remove('add');
			count = i;
			$itemMenu[count].classList.add('active');
			$itemOption[count].classList.add('add');
		});
	}


	// 좋아요 버튼
	const $likeyBTN = document.getElementById('likey-BTN');
	const $heart = document.getElementById('heart');

	$likeyBTN.addEventListener('click', (e) => {
		$heart.classList.toggle('plus');
	});

	// 소개글 추가 버튼
	const $contentEdit = document.getElementById('contentEdit');
	const $editInput = document.getElementById('editInput');
	// 소개글 확인 버튼
	const $editSucces = document.getElementById('editSucces');
	const $content = document.getElementById('content');


	// 소개글 추가 버튼 클릭 시
	$contentEdit.addEventListener('click', () => {
		let $contentEditInput = document.getElementById('contentEditInput');
		$contentEditInput.value = $content.innerHTML;
		$editInput.classList.remove('hidden');
		$contentEdit.classList.add('hidden');
	});

	// 소개글 확인 버튼 클릭 시
	$editSucces.addEventListener('click', () => {
		let $contentEditInput = document.getElementById('contentEditInput');
		$content.innerHTML = "";
		$content.innerHTML = $contentEditInput.value;

		// 비동기식으로 내용 db에 추가
		$.ajax({
			url: "/person/introduction",
			type: 'post',
			dataType: 'JSON',
			data: ({
				meID: meID,
				introduction: $contentEditInput.value
			}), beforeSend: function(xhr) {
				xhr.setRequestHeader(header, token);
			},
			success: () => {
				alert("성공적으로 수정 되었습니다.");
			}
		});

		$editInput.classList.add('hidden');
		$contentEdit.classList.remove('hidden');
	});
});


// 프로필 수정
document.addEventListener('DOMContentLoaded', () => {
	const $myImg = document.getElementById('myImg');
	const $op = document.getElementById('op');


	$myImg.addEventListener('mouseover', () => {
		$op.classList.add('show');
	});

	$myImg.addEventListener('mouseout', () => {
		$op.classList.remove('show');
	});
});


// 이미지 관리 함수
function previewImage(event) {
	const token = $("meta[name='_csrf']").attr("content");
	const header = $("meta[name='_csrf_header']").attr("content");
	const file = event.target.files[0]; // 선택된 파일

	var reader = new FileReader();
	reader.onload = function() {
		var output = document.getElementById('myProfile');
		output.innerHTML = ''; // 이미지 
		let str = "<img src='" + reader.result + "' alt=''>"
		$(output).append(str);

	};
	reader.readAsDataURL(file);


	const formData = new FormData();
	formData.append('file', file); // 파일을 추가
	formData.append('userID', userID); // 예시 userID



	// XMLHttpRequest를 사용하여 파일 업로드
	const xhr = new XMLHttpRequest();
	xhr.open('POST', '/person/uploadProfile', true);

	// CSRF 토큰을 헤더에 추가
	xhr.setRequestHeader(header, token);

	xhr.onload = function() {
		if (xhr.status === 200) {
			console.log('File uploaded successfully!');
		} else {
			console.error('File upload failed:', xhr.statusText);
		}
	};

	xhr.onerror = function() {
		console.error('Request failed.');
	};

	xhr.send(formData);
}
