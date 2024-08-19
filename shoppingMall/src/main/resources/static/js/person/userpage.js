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


$likeyBTN.addEventListener('click', () => {
	$heart.classList.toggle('plus');
});

