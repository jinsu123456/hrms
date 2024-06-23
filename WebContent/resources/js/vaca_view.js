function withdrawalFn(){
	let select = confirm("연차 신청을 철회하시겠습니까?");
	if(select===true){
		let f = document.createElement('form');
    
		let vacaNoInput;
		vacaNoInput = document.createElement('input');
		vacaNoInput.setAttribute('type', 'hidden');
		vacaNoInput.setAttribute('name', 'vacaNo');
		vacaNoInput.setAttribute('value', value);
		f.appendChild(vacaNoInput);
		
		f.setAttribute('method', 'post');
		f.setAttribute('action', 'withdrawal.do');
		document.body.appendChild(f);
		f.submit();
	}
} 