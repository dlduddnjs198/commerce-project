# 🗒 Commerce-Project

온라인 쇼핑몰 서비스입니다.

## 프로젝트 기능 및 설계
- 회원가입 기능
  - 사용자는 회원가입을 할 수 있다.
  - 회원가입시 아이디와 패스워드를 입력받으며, 아이디는 unique 해야한다. 

- 로그인 기능
  - 사용자는 로그인을 할 수 있다. 로그인시 회원가입때 사용한 아이디와 패스워드가 일치해야한다. 

- 상품명 검색 기능
   - 로그인하지 않은 사용자를 포함한 모든 사용자는 상품을 검색할 수 있다.
  - 상품은 최신순으로 기본 정렬된다.
   - 상품명, 가격, 평점, 리뷰수를 보여준다.

- 특정 상품 조회 기능
   - 로그인하지 않은 사용자를 포함한 모든 사용자는 상품을 조회할 수 있다.
  - 상품 조회시 응답에는 상품명, 가격, 설명, 평균리뷰평점, 리뷰수, 리뷰 정보가 필요하다.

- 상품 장바구니 기능
   - 로그인한 사용자는 장바구니 기능을 이용할 수 있다.
   - 상품 담기, 장바구니 조회, 장바구니 상품 삭제의 기능을 사용할 수 있다.

- 리뷰 목록 조회 기능
   - 특정 상품 조회시 리뷰목록도 함께 조회한다. 이 또한 로그인하지 않은 사용자를 포함한 모든 사용자가 리뷰를 조회할 수 있다.
   - 리뷰는 최신순으로만 정렬되며, paging 처리를 한다.
   - 리뷰 목록 조회시에는 리뷰 작성자와 리뷰 내용, 리뷰 작성일의 정보가 필요하다.

- 리뷰 작성 기능
   - 로그인한 사용자는 권한에 관계 없이 댓글을 작성할 수 있다. 
  - 사용자는 댓글 내용(텍스트)를 작성할 수 있다. 

## ERD 
![ERD](doc/img/erd.png)

## Trouble Shooting
[go to the trouble shooting section](doc/TROUBLE_SHOOTING.md)

### Tech Stack
<div align=center> 
  <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> 
  <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> 
  <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> 
  <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
</div>
