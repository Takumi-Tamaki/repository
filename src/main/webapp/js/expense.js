'use strict';

var rootUrl = "/java_s04/api/v1.1/expense";
findAll();

$('#savePost').click(function() {
	var title = $('[name = "title"').val();
	var date = $('[name = "date"]').val();
	var person = $('[name = "person"]').val();
	var price = $('[name = "price"]').val();
	var status = $('[name = "status"]').val();
	if (title === ''||date === '' ||person === '' ||price === ''|| status === '') {
		$('.error').text('必須入力事項が抜けています。');
		return false;
	} else {
		$('.error').text('');
	}

	var id = $('#postId').val()
	if (id == ''){
		console.log('idは自動でつけるよ',id);
		addPost();
	}
	else
		updatePost(id);
	return false;
})

$('#newPost').click(function() {
	renderDetails({});
});

function findAll(){
	console.log('findAll start.')
	$.ajax({
		type: "GET",
		url: rootUrl,
		dataType: "json",
		success: renderTable
	});
}

function findById(id) {
	console.log('findByID start - id:'+id);
	$.ajax({
		type: "GET",
		url: rootUrl+'/'+id,
		dataType: "json",
		success: function(data) {
			console.log('findById success: ' + data);
			renderDetails(data)
		}
	});
}

function addPost() {
	console.log('addPost start');
	console.log('送るデータの確認',formToJSON());
	$.ajax({
		type: "POST",
		contentType: "application/json",
		url: rootUrl,
		dataType: "json",
		data: formToJSON(),
		success: function(data, textStatus, jqXHR) {
			alert('経費データの追加に成功しました');
			$('#postId').val(data.appricationId);
			findAll();
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('経費データの追加に失敗しました');
		}
	})
}

function updatePost(id) {
	console.log('updatePost start');
	$.ajax({
		type: "PUT",
		contentType: "application/json",
		url: rootUrl+'/'+id,
		dataType: "json",
		data: formToJSON(),
		success: function(data, textStatus, jqXHR) {
			alert('経費データの更新に成功しました');
			findAll();
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('経費データの更新に失敗しました');
		}
	})
}


function deleteById(id) {
	console.log('delete start - id:'+id);
	$.ajax({
		type: "DELETE",
		url: rootUrl+'/'+id,
		success: function() {
			findAll();
			//入力フォームの初期化
			$('#postId').val('');
			$('[name="title"]').val('');
			$('[name = "date"]').val('');
			$('[name = "person"]').val('');
			$('[name = "price"]').val('');
			$('[name = "status"]').val('');
			alert('削除に成功しました')

		}
	});
}

function renderTable(data) {

	//確認用のコンソール
	console.log('返却地search', data);
	var headerRow = '<tr><th>ID</th><th>タイトル</th></th>';
	headerRow += '<th>申請日</th><th>申請者</th><th>金額</th><th>ステータス</th><th></th><th></th></tr>';

	$('#posts').children().remove();

//	$('#postId').val(post.appricatinId);
//	$('[name="title"]').val(post.expenseTitle);
//	$('[name = "date"]').val(post.appricationDate);
//	$('[name = "person"]').val(post.appricant);
//	$('[name = "price"]').val(post.price);
//	$('[name = "status"]').val(post.status);

	if (data.length === 0) {
		$('#posts').append('<p>現在データが存在していません。</p>')
	} else {
		var table = $('<table>').attr('border', 1);
		table.append(headerRow);
		$.each(data, function(index, post) {
			var row = $('<tr>');
			row.append($('<td>').text(post.appricationId));
			row.append($('<td>').text(post.expenseTitle));
			row.append($('<td>').text(post.appricationDate));
			row.append($('<td>').text(post.appricant));
			row.append($('<td>').text(post.price));
			row.append($('<td>').text(post.status));
			row.append($('<td>').append(
					$('<button>').text("編集").attr("type","button").attr("onclick", "findById("+post.appricationId+')')
				));
			row.append($('<td>').append(
					$('<button>').text("削除").attr("type","button").attr("onclick", "deleteById("+post.appricationId+')')
				));
			table.append(row);
		});

		$('#posts').append(table);
	}

}

function renderDetails(post) {
	$('.error').text('');
	$('#postId').val(post.appricationId);
	$('[name="title"]').val(post.expenseTitle);
	$('[name = "date"]').val(post.appricationDate);
	$('[name = "person"]').val(post.appricant);
	$('[name = "price"]').val(post.price);
	$('[name = "status"]').val(post.status);
}

function formToJSON() {
	var postId = $('#postId').val();
	return JSON.stringify({
		"appricationId": (postId == '' ? 0 : postId),
		"expenseTitle": $('[name = "title"]').val(),
		"appricationDate": $('[name = "date"]').val(),
		"appricant": $('[name = "person"]').val(),
		"price": $('[name = "price"]').val(),
		"status": $('[name = "status"]').val()
	});
}
