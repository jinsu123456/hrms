function withdrawalFn(){
	let select = confirm("기안을 철회하시겠습니까?");
	if(select===true){
		let f = document.createElement('form');
		let docNoInput;
		docNoInput = document.createElement('input');
		docNoInput.setAttribute('type', 'hidden');
		docNoInput.setAttribute('name', 'docNo');
		docNoInput.setAttribute('value', value);
		f.appendChild(docNoInput);
		
		f.setAttribute('method', 'post');
		f.setAttribute('action', 'withdrawal.do');
		document.body.appendChild(f);
		f.submit();
	}
}

function downloadFn(fileNo, docNo, realNm, originNm){
	let f = document.createElement('form');
	let nameArr = ["fileNo", "docNo", "realNm", "originNm"];
	let valueArr = [fileNo, docNo, realNm, originNm];
	let inputArr = [];
	for(let i=0; i<valueArr.length; i++){
		let input = document.createElement('input');
		input.setAttribute('type', 'hidden');
		input.setAttribute('name', nameArr[i]);
		input.setAttribute('value', valueArr[i]);
		inputArr[i] = input;
		f.appendChild(inputArr[i]);
	}
	f.setAttribute('method', 'post');
	f.setAttribute('action', 'download.do');
	document.body.appendChild(f);
	f.submit();
}