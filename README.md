Week 1 Project: Flicks

![](http://learning.coderschool.vn/course_images/_intro_to_android/movie_db1.gif)

![](http://learning.coderschool.vn/course_images/_intro_to_android/movie_db2.gif)





Overview: Build a read-only movie listing app using the Movie Database API.

- The Movie Database [API documentation](https://developers.themoviedb.org/3/getting-started/introduction)
- Download the [JSONView extension](https://chrome.google.com/webstore/detail/jsonview/chklaanhfefbnpoihckbnefhakgolnmc?hl=en) to view JSON better in your browser.
- Sample API Request: https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed
- Trailers API response: https://api.themoviedb.org/3/movie/209112/trailers?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed

Libraries: Completing this project, requires the use of the retrofit and the Glide libraries. Add dependencies for these libraries to the app/build.gradle file.

- Be sure to include a README containing a GIF walkthrough of your app.
- Use this README template in order to have a complete README.

User Stories:

The following user stories must be completed:

- User can view a list of movies (title, poster image, and overview) currently playing in theaters from the Movie Database API.
- Lists should be fully optimized for performance with Recyclerview.
- Views should be responsive for both landscape/portrait mode.
	- In portrait mode, the poster image, title, and movie overview is shown.
	  
	- In landscape mode, the rotated layout should use the backdrop image instead and show the title and movie overview to the right of it.
	  

The following advanced user stories are optional but recommended:

- Add pull-to-refresh for popular stream with SwipeRefreshLayout (1 point)
- Display a nice default placeholder graphic for each image during loading (read more about Glide) (1 point)
- Improve the user interface through styling and coloring (1 to 5 points depending on the difficulty of UI improvements)
- For popular movies (i.e. a movie voted for more than 5 stars), the full backdrop image is displayed. Otherwise, a poster image, the movie title, and overview is listed. Use Heterogenous RecyclerViews and use different ViewHolder layout files for popular movies and less popular ones. (2 points)
- Stretch: Expose details of movie (ratings using RatingBar, popularity, and synopsis) in a separate activity. (3 points)
- Stretch: Allow video posts to be played in full-screen using the YouTubePlayerView (2 points)
	- When clicking on a popular movie (i.e. a movie voted for more than 5 stars) the video should be played immediately.
	- Less popular videos rely on the detailed page should show an image preview that can initiate playing a YouTube video.
	- See the trailers API for video information. Here's a sample request.
- Stretch: Add a play icon overlay to popular movies to indicate that the movie can be played (1 point).
- Stretch: Leverage the data binding support module to bind data into one or more activity layout templates. (1 point)
- Stretch: Apply the popular ButterKnife annotation library to reduce view boilerplate. (2 point)
- Stretch: Add a rounded corners for the images using the Picasso transformations or Glide transformations. (1 point)
