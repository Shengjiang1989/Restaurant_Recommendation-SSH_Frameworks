<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<title>Restaurant Recommendation</title>

<link href='https://fonts.googleapis.com/css?family=Open+Sans:400,300,700' 
			rel='stylesheet' type='text/css'>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<link type="text/css"
			rel="stylesheet"
			href="${pageContext.request.contextPath}/resources/css/main.css" />
</head>
<body>
  <header class="top-header">
    <nav class="top-nav">
      <a href="#">Home</a>
      <a href="#">Contact</a>
      <a href="#">About</a>
    </nav>
    <span id="welcome-msg"></span>
    <a id="logout-link" href="LogoutServlet">Logout</a>
    <i id="avatar" class="avatar fa fa-user fa-2x"></i>
  </header>
  
  <div class="container">
    <header>
      <p>
        <span>Restaurant</span>
        <br /> Recommendation
      </p>
    </header>

    <section class="main-section">
      <div id="login-form">
	    <label for="username">Username:</label>
	    <input id="username" name="username" type="text">
	    <label for="password">Password:</label>
	    <input id="password" name="password" type="password">
	    <button id="login-btn">Login</button>
	    <p id="login-error"></p>
	  </div>

      <aside id="restaurant-nav">
        <div class="nav-icon">
          <i class="fa fa-cutlery fa-2x"></i>
        </div>
        <nav class="main-nav">
          <a href="#" id="nearby-btn" class="main-nav-btn active">
            <i class="fa fa-map-marker"></i> Nearby
          </a>
          <a href="#" id="fav-btn" class="main-nav-btn">
            <i class="fa fa-heart"></i> My Favorites
          </a>
          <a href="#" id="recommend-btn" class="main-nav-btn">
            <i class="fa fa-thumbs-up"></i> Recommendation
          </a>
        </nav>
      </aside>

      <ul id="restaurant-list">
        <li class="restaurant">
          <img alt="restaurant image" src="https://s3-media3.fl.yelpcdn.com/bphoto/EmBj4qlyQaGd9Q4oXEhEeQ/ms.jpg" />
          <div>
            <a class="restaurant-name" href="#">Restaurant</a>
            <p class="restaurant-category">Vegetarian</p>
            <div class="stars">
              <i class="fa fa-star"></i>
              <i class="fa fa-star"></i>
              <i class="fa fa-star"></i>
            </div>
          </div>
          <p class="restaurant-address">699 Calderon Ave<br/>Mountain View<br/> CA</p>
          <div class="fav-link">
            <i class="fa fa-heart"></i>
          </div>
        </li>
      </ul>
    </section>
  </div>
  
  <footer>
    <p class="title">What We Do</p>
    <p>"Help you find the best restaurant around."</p>
    <ul>
      <li>
        <p><i class="fa fa-map-o fa-2x"></i></p>
        <p>Boston, MA</p>
      </li>
      <li>
        <p><i class="fa fa-envelope-o fa-2x"></i></p>
        <p>shengjiang89us@gmail.com</p>
      </li>
      <li>
        <p><i class="fa fa-phone fa-2x"></i></p>
        <p>+1 857 272 8183</p>
      </li>
    </ul>
  </footer>
  
  <script src="https://rawgit.com/emn178/js-md5/master/build/md5.min.js"></script>
  <script src="resources/scripts/main.js"></script>
</body>
</html>