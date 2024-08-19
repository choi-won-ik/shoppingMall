// pay버튼 설정
const $payBtn = document.getElementById('payBtn');
const $pay = document.getElementById('pay');
const $payBody = document.querySelector('.pay-body');
let $quantity = document.getElementById('quantity');

// 페이 스크린 작동
$payBtn.addEventListener('click', () => {
	if($quantity.value<totalQuantity&&$quantity.value!==""){
		
		console.log($quantity.value);
		
		$pay.classList.toggle('show');

		// 스크롤 정지
		document.body.classList.add('noscroll');
	
		// ESC 키를 누를 때 pay 창 닫기
		window.addEventListener("keydown", (e) => {
			if (e.keyCode === 27) {
				$pay.classList.remove('show');
	
				// 스크롤 가동
				document.body.classList.remove('noscroll');
			}
		});
	
		window.addEventListener('click', (e) => {
			if (e.target === $pay) {
				$pay.classList.remove('show');
	
				// 스크롤 가동
				document.body.classList.remove('noscroll');
			}
		});
	
		// .pay-body를 위로 슬라이드 애니메이션
		if ($pay.classList.contains('show')) {
			$payBody.animate([
				{ bottom: '-300px' },
				{ bottom: '0' }
			], {
				duration: 300,
				easing: 'ease-out',
				fill: 'forwards'
			});
		} else {
			// 열려 있는 상태에서 다시 닫을 때는 애니메이션 없이 바로 숨김
			$payBody.style.bottom = '-300px';
		}
	}
	else{
		alert("수량이 부족합니다.");
	}	

});





// 이미지 슬라이드 효과
const $left = document.getElementById('left');
const $right = document.getElementById('right');
const $imagesEX = document.getElementById('product-images-EX');
const $ready = document.querySelectorAll('.product-image-EX');

count = 0;

$left.addEventListener('click', () => {
	if (count !== 0) {
		count--;
		$imagesEX.style.transform = `translateX(${count * -100}%`;
	}
});

$right.addEventListener('click', () => {
	if (count !== $ready.length - 1) {
		count++;
		$imagesEX.style.transform = `translateX(${count * -100}%`;
	}
});
