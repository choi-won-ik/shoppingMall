/* 검색창 드롭다운 */
let $input = document.getElementById('input');
let $dropdown = document.getElementById('dropdown-body');


let box1Width = window.getComputedStyle($input).width;
$dropdown.style.width = box1Width;

// 첫 번째 요소의 위치와 크기를 계산
let firstRect = $input.getBoundingClientRect();

// 두 번째 요소의 위치를 첫 번째 요소 바로 아래로 설정
$dropdown.style.top = `${firstRect.bottom}px`;
$dropdown.style.left = `${firstRect.left}px`;


window.addEventListener('resize', () => {
	firstRect = $input.getBoundingClientRect();

	$input = document.getElementById('input');
	$dropdown = document.getElementById('dropdown-body');
	box1Width = window.getComputedStyle($input).width;
	$dropdown.style.left = `${firstRect.left}px`;
	$dropdown.style.width = box1Width;
});

let Atag = document.getElementsByTagName('a');

const input = () => {
	// XMLHttpRequest 객체 생성
	let xhr = new XMLHttpRequest();

	// 요청 설정
	xhr.open('POST', '/productSearch', true);

	xhr.setRequestHeader(header, token);
	// 요청 헤더 설정
	xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

	// 요청 완료 시 실행될 함수
	xhr.onreadystatechange = function() {
		if (xhr.readyState === XMLHttpRequest.DONE) {
			if (xhr.status === 200) {
				let products = JSON.parse(xhr.responseText);


				if (products !== "") {
					let dropdownBody = document.getElementById('dropdown-body');
					
					dropdownBody.innerHTML = "";
					
					products.forEach((item) => {
						// 새로운 li 요소 생성
						let li = document.createElement('li');
						li.className = 'dropdown-op';
						li.id = 'dropdown-op';

						// a 요소 생성
						let a = document.createElement('a');
						a.className = 'userList';
						a.id = 'UserList';
						a.href = `http://localhost:8080/product/product/${item.productNum}`;

						// div 요소 생성
						let div = document.createElement('div');
						div.textContent = item.productName;

						// a 요소에 div 요소를 자식 요소로 추가
						a.appendChild(div);

						// li 요소에 a 요소를 자식 요소로 추가
						li.appendChild(a);

						// dropdownBody에 li 요소를 자식 요소로 추가
						dropdownBody.appendChild(li);
					});
				}
			}
		}
	};

	// 데이터 준비 (폼 데이터 형식으로 인코딩)
	let data = 'productName=' + encodeURIComponent($input.value);

	// 요청 보내기
	xhr.send(data);
};

const foucsin = () => {
	$dropdown.classList.add('show');


	foucsout = (e) => {
		if (!e.target.classList.contains('userList') && e.target !== $input) {
			$dropdown.classList.remove('show');
			window.removeEventListener('click', foucsout);

		}
	};

	window.addEventListener('click', foucsout);
};

$input.addEventListener('click', foucsin);
$input.addEventListener('input', input);

$dropdown = document.getElementById('dropdown-body');
$option = $dropdown.getElementsByTagName('li');