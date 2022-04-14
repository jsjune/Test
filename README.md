### 와이어프레임 및 기능 설계

# 미니 프로젝트- 애견운동장 리뷰

### 프로젝트 소개

애견 운동장/카페 등을 사진, 별점, 좋아요 기능들로 리뷰하는 사이트 입니다.

<br><br><br>

로그인

![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/f8329117-0d50-4247-916c-a5508e534ad5/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220414%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220414T082718Z&X-Amz-Expires=86400&X-Amz-Signature=c045afb08bc9f49f14cb3b8cbc5728decc9222b16879b44bdaab232b2be10223&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)

회원가입

![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/594f5d93-a1c8-40e2-87c3-b7cdb963162e/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220414%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220414T082742Z&X-Amz-Expires=86400&X-Amz-Signature=dd4dd25c593b59973d5ba705ec7bdd4651b3cc12579860f9ac0ffbd0365d66e7&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)


메인 페이지

![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/f2607c5d-9d66-42d8-b6fa-a3aeaec9d268/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220414%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220414T082814Z&X-Amz-Expires=86400&X-Amz-Signature=f521315ee3cfe97ba1613f9abcfaca5258f794e22539080e770fe9f21566d176&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)

글작성

![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/f49eb391-f150-41f3-8dfd-00d812e545a2/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220414%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220414T082826Z&X-Amz-Expires=86400&X-Amz-Signature=4309755431b43309ce5b960d8d585edb31c4197daafa235d0d9946fc1421e564&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)

상세 페이지

![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/07c94357-d1bb-49f8-8c81-037073d210cf/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220414%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220414T082843Z&X-Amz-Expires=86400&X-Amz-Signature=9ed327de136433067dda675e0c942dd66d1a4343dd715740c037b3b3dc55e285&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)


<br><br><br>

### API 작성

기능|Method|URL|request|response
---|---|---|---|---
로그인 요청|POST|/user/login|{<br>username : "iamuser",<br>password : "1234"<br>}|{<br>result : true/fasle<br>}  
회원가입|POST|/user/signup|{<br>username: "iamuser",<br>password: "1234",<br>nickname : “abc”<br>}|{<br>result : true/fasle,<br>err_msg : string<br>}
닉네임 중복 검사|POST|/user/nicknameCheck|{<br>nickname:"iamuser",<br>username:” “<br>}|{<br>result : true/false,<br>nickname : “ “<br>}<br> ———————————————<br>아이디가 중복되지 않으면(사용 가능하면) true, 중복이면 false 반환. 회원가입시 한번에 검사하기로하여 제외
전체 게시글 조회|GET|/api/posts/|{<br>page : 1,<br>username:”user”<br>}|result : [{<br>postId: 1,<br>title: "제목입니다",<br>content: "반가워요",<br>imageSrc:"src",<br>createdAt: LocalDateTime,<br>good :  10,<br>star :  (1~5)},{...}....,<br>Heart: True/False<br>]}<br>—— 시간순서대로 정렬 (최신글이 제일 상단에 위치)
게시글 작성|POST|/api/posts|{<br>title: "제목입니다",<br>content: "반가워요",<br>imageSrc:"/images/cancle.png",<br>star :  (1~5),<br>username : 이름<br>}|{<br>result : true/fasle,<br>err_msg : string<br>}
게시글 수정|PUT|/api/posts/|{<br>postid : 1,<br>title: "제목입니다",<br>content: "반가워요",<br>star :  (1~5),<br>username : 이름<br>}|
게시글 삭제|DELETE|/api/posts/{postid}|{<br>postid : 1<br>}|{<br>result : true/fasle,<br>err_msg : string<br>}
상세페이지 조회|GET|/api/posts/detail|{<br>postid : 1,<br>username : name<br>}|result : [{<br>postId: 1,<br>title: "제목입니다",<br>content: "반가워요",<br>imageSrc:"src",<br>createdAt: LocalDateTime,<br>good :  10,<br>heart : true/false,<br>star :  (1~5)},{...}....<br>]}
댓글 조회|GET|/api/posts/comment/{postid}|{<br>postid : 1<br>}|{<br>commentid : 1,<br>username : name,<br>content : 내용 ,<br>createAt : 시간 <br>}<br>——— 시간순서대로 정렬
댓글 작성|POST|api/posts/comment|{<br>postid : 1,<br>username : name,<br>content : 내용 <br>}|{<br>result : true/fasle,<br>err_msg : string<br>}
좋아요 기능|POST|/api/posts/like|{<br>postid : 1,<br>username : 1<br>}|{<br>result : true/false,<br>err_msg : string<br>}






