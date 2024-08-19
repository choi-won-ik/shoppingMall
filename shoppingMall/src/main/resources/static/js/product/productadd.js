// 이미지 관리 함수
function previewImage(event, index) {
	var reader = new FileReader();
	reader.onload = function() {
		var output = document.getElementsByClassName('image-preview')[index];
		output.innerHTML = ''; // 이미지 
		let str = "<img src='" + reader.result + "' alt=''>"
		$(output).append(str);

	};
	reader.readAsDataURL(event.target.files[0]);
}