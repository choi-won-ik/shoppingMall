const $heart = document.getElementById('heart-body');
let $likey = document.getElementById('likey');
let $checkbox = document.getElementById('checkbox');

let count=0;
count = likeyNUM;
$likey.innerHTML = count;

document.addEventListener('DOMContentLoaded',()=>{
	if(meLikey===true){
		console.log("좋아요 누룸");
		$checkbox.checked  = true;
	}else{
		console.log("좋아요 안 누룸");
		$checkbox.checked  = false;
	}
	
	
});


$heart.addEventListener('change', () => {
	$.ajax({
		url: '/likey',
		type: 'post',
		beforeSend: function(xhr) {
			xhr.setRequestHeader(header, token);
		},
		data: {
			productCode: productCode
		},
		dataType: 'text',
		success: (data) => {
			console.log(data);
			if (data === 'true') {
				$likey.innerHTML="";
				count++;
				$likey.innerHTML = count;
			} else if(data==='false'){
				$likey.innerHTML="";
				count--;
				$likey.innerHTML = count;
			}
		},
		error: (xhr, status, error) => {
			console.log("상태: " + status);
			console.log("에러 메시지: " + error);
			console.log("응답 내용: " + xhr.responseText);
		}
	})
});