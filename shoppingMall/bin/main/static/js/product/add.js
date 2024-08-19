$(document).ready(()=>{
    // 카테고리 블록
    const first = document.getElementById('first-menu');
    let second = document.getElementById('second-menu');
    let third = document.getElementById('third-menu');
    // 카테고리 매뉴들
    const firstOP = first.getElementsByTagName('div');
//    let secondOP = second.getElementsByTagName('div');
//    let thirdOP = third.getElementsByTagName('div');
    // 메뉴 선택 시
    let option1=document.getElementById('option first');
    let option2=document.getElementById('option second');
    let option3=document.getElementById('option third');
	// 선택한 카테고리
	const $choose1 = document.getElementById('choose1');
	const $choose2 = document.getElementById('choose2');
	const $choose3 = document.getElementById('choose3');
	const $arrow1 = document.getElementById('arrow1');
	const $arrow2 = document.getElementById('arrow2');

    // 마우스 오버 이벤트 카운터
    let count1 = 0;
    let count2 =0;
    let count3 =0;
    // 마우스 클리 이벤트 카운터
    let clickCount1 = 0;
    let clickCount2 = 0;
    let clickCount3 = 0;
    

    // 1번째 블럭
    for (let i = 0; i < firstOP.length; i++) {

        firstOP[i].addEventListener('mouseover',()=>{
            count1=i;
            firstOP[count1].classList.add('on');
        });

        firstOP[i].addEventListener('mouseout',()=>{
            firstOP[count1].classList.remove('on');
        });

        firstOP[i].addEventListener('click',()=>{
            if(i!==0){
				option1.innerText=firstOP[i].innerText;
			
				// 카테고리 선택 변경 시 아래 표시 초기화
				if($choose1.innerText!==""){
					$choose1.innerText="";
					$choose2.innerText="";
					$choose3.innerText="";
					$arrow1.style.display="none";
					$arrow2.style.display="none";
				}
				// 카테고리 선택 시 아래 표시
				$choose1.style.display="block";
				$choose1.innerText=firstOP[i].innerText;

				// 옵션 선택 시 선택 옵션 가장 위로 이동
				firstOP[clickCount1].style.display="flex";
				clickCount1=i;
				firstOP[clickCount1].style.display="none";
				option1.style.display="flex";
				first.scrollTop = 0;

				// 두번째 카테고리 옵션 삭제
				second.innerHTML="<div class='option2' id='option second'></div>";
				// 세번째 카테고리 옵션 삭제
				third.innerHTML="<div class='option3' id='option third'>test</div><div class='option3' id='option third'>옵션3</div>";

				// 두번째 카테고리 옵션 박스 입력
				let str ="a"+i;
				let str1 =(eval(str));
            	$('#second-menu').append(str1);

				// 두번째 옵션 박스 정의
            	let secondOP = second.getElementsByTagName('div');
				clickCount2=0;

				// 두번째 옵션 이벤트 함수 실행
            	secondfnc(secondOP,clickCount2,str);
            	
			}

        });
    }



    const secondfnc=(secondOP,clickCount2,str)=>{
        // 2번째 블럭
        for (let i = 0; i < secondOP.length; i++) {
            secondOP[i].addEventListener('mouseover',()=>{
                count2=i;
                secondOP[count2].classList.add('on');
				
            })
            secondOP[i].addEventListener('mouseout',()=>{
                secondOP[count2].classList.remove('on');
            });

            secondOP[i].addEventListener('click',()=>{
				
				if(i!==0){
					// 두번째 카테고리 옵션 박스 선택 옵션 정의
					const option2=document.getElementById('option second');
					// 옵션 내용 정의
					let option2STR = secondOP[i].innerText;
					option2.innerText=option2STR;

					// 카테고리 선택 변경 시 아래 표시 초기화
					if($choose2.innerText!==""){
						$choose2.innerText=""
						$choose3.innerText="";
						$arrow2.style.display="none";
					}
					// 카테고리 선택 시 아래 표시
					$choose2.innerText=option2STR;
					$choose2.style.display="block";
					$arrow1.style.display="block";
					
					// 옵션 선택 시 옵션 가장 위로 올림
					secondOP[clickCount2].style.display="flex";
					clickCount2=i;
					secondOP[clickCount2].style.display="none";
					option2.style.display="flex";
					second.scrollTop = 0;

					// 3번째 옵션 박스 입력
					third.innerHTML="<div class='option3' id='option third'></div>";
					let val =str+"b"+i;
					categoryNUM=val;
					categoryNUM+="c";
					
            		let val1 =(eval(val));
					$('#third-menu').append(val1);

					// 세번째 옵션 박스 정의
					clickCount3=0;
					let thirdOP = third.getElementsByTagName('div');

					thirdfnc(thirdOP,clickCount3,categoryNUM);
					
					
					
					
				}
            });
        }
    };


	const thirdfnc=(thirdOP,clickCount3,categoryNUM)=>{
		for (let i = 0; i < thirdOP.length; i++) {
			thirdOP[i].addEventListener('mouseover',()=>{
				count3=i;
				
				thirdOP[count3].classList.add('on');
			})
	
			thirdOP[i].addEventListener('mouseout',()=>{
				thirdOP[count3].classList.remove('on');
			});

			thirdOP[i].addEventListener('click',()=>{
				if(i!==0){
					// 세번쨰 카테고리 옵션 박스 선택 옵션 정의
					const option3=document.getElementById('option third');
					// 옵션 내용 정의
					let option3STR = thirdOP[i].innerText;
					option3.innerText=option3STR;

					// 옵션 선택 시 옵션 가장 위로 올림
					thirdOP[clickCount3].style.display="flex";
					clickCount3=i;
					thirdOP[clickCount3].style.display="none";
					option3.style.display="flex";
					thirdOP.scrollTop = 0;

					// 카테고리 선택 시 아래 표시
					$choose3.innerText=option3STR;
					$choose3.style.display="block";
					$arrow2.style.display="block";
					
					let $categoryId = document.getElementById('categoryId');
					$categoryId.value="";
					$categoryId.value=categoryNUM+i;
				}
			});
		}
	};
});