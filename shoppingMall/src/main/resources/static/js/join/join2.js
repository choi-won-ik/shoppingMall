const token = $("meta[name='_csrf']").attr("content");
const header = $("meta[name='_csrf_header']").attr("content");

const $joinBTN = document.getElementById('joinBTN');
let $userid = document.getElementById('overlap');
// 정규 표현식을 사용하여 아이디 패턴 체크
const idPattern = /^(?=.*[a-z])(?=.*\d)[a-z\d]{6,15}$/;
let $overlap = false;
const $overlapBTN = document.getElementById('overlapBTN');
let $pw = document.getElementById('pw');
// 정규 표현식을 사용하여 비밀번호 패턴 체크
const pwPattern = /^(?=.*[a-z])(?=.*\d)(?=.*[@$!%*?&])[a-z\d@$!%*?&]{8,20}$/;
let $check = document.getElementById('pw-check');
let $name = document.getElementById('name');
let $phone = document.getElementById('phone');
let $birthday = document.getElementById('birthday');

// 회원가입
$overlapBTN.addEventListener('click', () => {
	if ($userid.value === "") {
		alert("아이디를 입력하여 주세요.");
	}
	else if (!idPattern.test($userid.value)) {
		alert("대,소문자 구분없이 숫자를 조합하여 6자 이상 15자 이하인 아이디만을 허용합니다.");
	} else {
		$.ajax({
			type: 'post',
			dataType: 'json',
			data: ({
				userid: $userid.value
			}),
			url: "./overlap",
			beforeSend: function(xhr) {
				xhr.setRequestHeader(header, token);
			},
			success: (result) => {
				$overlap = result;
				if (result === false) {
					alert("사용할 수 있는 아이디 입니다.");
				} else {
					alert("중복된 아이디 입니다.");
				}
			}, error: () => {
				alert("알 수 없는 에러. 다시 시도해 주세요.");
			}
		});
	}
});


const $loginForm = document.getElementById('loginForm');

$joinBTN.addEventListener('click', (e) => {
	e.preventDefault();

	console.log($overlap);
	// 중복확인
	if ($overlap === true) {
		alert("중복확인 바랍니다.");
	} else {
		// 이름 입력
		if ($name.value === "") {
			alert("이름을 입력하여 주십시오.");
		} else {
			// 비밀번호 패던
			if ($pw.value === "") {
				alert("비밀번호를 입력하여 주십시오");
			} else if (!pwPattern.test($pw.value)) {
				alert("대,소문자 구분없이 숫자, 그리고 특정 특수문자가 포함되어 있으며, 길이가 8자 이상 20자 이하인 비밀번호만을 허용합니다.")
			} else {
				// 비밀번호 확인
				if ($pw.value !== $check.value) {
					alert("비밀번호가 다릅니다.");
				} else {
					console.log($phone.value.length);
					// 핸드폰 번호
					if ($phone.value === "") {
						alert("'-'는 빼고 입력하여 주십시오.");
					} else if ($phone.value.length > 11) {
						alert("전화번호 자릿수를 확인하여 주십시오.");
					} else {
						if ($birthday.value.length > 6) {
							alert('생년월일 자릿수를 확인하여 주십시오.');
						} else {
							document.getElementById('loginForm').submit();
						}
					}
				}
			}
		}
	}
});

// 회원가입 취소
const $cancel = document.getElementById('join-cancel');

$cancel.addEventListener('click',()=>{
	alert('회원가입이 취소되었습니다. 홈으로 이동합니다.');
	window.location.href = "http://localhost:8080/index";
});