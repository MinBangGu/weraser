<%@ page  contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>AJAX Content-Type Example</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
    	function isValidUrl(url) {
            var pattern = /^(http|https):\/\/[^ "]+$/;
            return pattern.test(url);
        }
        
        $(document).ready(function() {
            
            $("#btnSubmit").click(function() {
                var formData = {
                    type: $("#type").val(),
                    size: $("#size").val(),
                    url : $("#url").val()
                };
            	if (!isValidUrl(formData.url)) {
                    alert("유효한 URL을 입력하세요.");
                    $("#url").focus();
                    return;
                }
                if (size < 1) {
                    alert("출력 묶음 단위를 확인해주세요.");
                    $("#size").focus();
                    return;
                }
                $(this).prop('disabled', true);
                

                $.ajax({
                    type: "POST",
                    url: "/htmlAnalyzer",
                    data: JSON.stringify(formData),
                    contentType: "application/json",
                    success: function(data) {
                        $("#result").html(data);
                    },
                    error: function() {
                        alert("요청 실패!");
                    },
                    complete: function() {
                        $("#btnSubmit").prop('disabled', false);
                    }
                });
            });
        });
    </script>
    <style>
        .form-container {
            width: 50%;
            margin: auto;
        }
        .form-group {
            margin-bottom: 20px;
            width: 100%;
            display: inline-block;
        }
        .form-group label {
            display: block;
            margin: 10px;
            float: left;
        }
        .form-group input[type=text], .form-group input[type=number], .form-group select {
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
            float: left;
        }
        .form-group input[type=submit] {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .form-group input[type=submit]:hover {
            background-color: #45a049;
        }
        .form-group input[type=submit]:disabled {
		    background-color: #cccccc;
		}
    </style>
</head>
<body>
    
 
    <div class="form-group">
        <label for="url">URL</label>
        <input type="text" id="url" name="url" placeholder="site">
    </div>
    <div class="form-group">
        <label for="type">Type</label>
        <select id="type" name="type">
	        <option value="html">HTM 태그 제거</option>
	        <option value="text">Text 전체</option>
	    </select>
    </div>
    <div class="form-group">
        <label for="size">출력 묶음 단위</label>
        <input type="number" id="size" name="size" placeholder="number" min="1"">
    </div>
    <div class="form-group">
        <input type="submit" id="btnSubmit" value="출력"></input>
    </div>
    <div id="result"></div>
</body>
</html>