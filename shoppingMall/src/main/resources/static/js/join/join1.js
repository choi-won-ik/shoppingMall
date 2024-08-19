window.onload=()=>{
    // 채크박스
    const $agree = document.getElementById('agree');
    const $nextStage = document.getElementById('next-stage');
    

    $nextStage.addEventListener('click',()=>{
        if(!$agree.checked){
            alert('위의 사항 및 이용동의에 동의하셔야 합니다.');
        }else{
            window.location.href = 'http://localhost:8080/join/joinPage2';
        }
    });
};