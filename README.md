# Dogs-App

App for people who like dogs, and who want to see different photos of different breeds of dogs, within it they can add the photos that you like to your favorites,
it also has an option to download the images and save them in your android device



you can download it at the following link [Click here](https://play.google.com/store/apps/details?id=com.edbinns.dogsapp "Click here")


Splash Screen
 -------------
 
 When the app is opened, the user will be able to see the following animation while the other modules load correctly.
 
 This animation was downloaded directly from [Lottie Files](https://lottiefiles.com/ "Lottie Files")
 
 ![](https://github.com/EdBinns/Dogs-App/blob/main/app/imagenes%20app/splash.jpg)
 
 
 
Main View
 -------------
 
 In this section the user can see images of different breeds of dogs, in the same way, he has the option to search for the breeds that he wants, 
 for this he will get a list with the breeds that at that moment there are images available
 
 
  ![](https://github.com/EdBinns/Dogs-App/blob/main/app/imagenes%20app/dogslist.jpg)
   ![](https://github.com/EdBinns/Dogs-App/blob/main/app/imagenes%20app/buscador.jpg)
   
 
 Breeds View
 -------------
 Clicking on any image in the main view or in the favorites view will open this view, which allows you to view the selected image larger, 
 as well as displaying more images relative to the breed of the selected image,when clicking the plus (+) button will open a menu with two options, 
 the first allows you to download the image and the second option, adds it to the favorites section 
 
 
  ![](https://github.com/EdBinns/Dogs-App/blob/main/app/imagenes%20app/itemdog.jpg)   

 Favorites View
 -------------
 
 In the favorites section the user can see all the images that he has marked as favorites, these are stored persistently inside the phone
 
   ![](https://github.com/EdBinns/Dogs-App/blob/main/app/imagenes%20app/favoritos.jpg)   
   

Apis
---------
All the images that appear within the application are loaded directly from the [Dog Api](https://dog.ceo/dog-api/ "Dog Api"), 
and in order to obtain these images, the Retrofit library was used, which allows making REST queries

  
Tools and technologies
 -------------
  - [Kotlin](https://kotlinlang.org/ "Kotlin") as a programing language 
  - [Room](https://developer.android.com/jetpack/androidx/releases/room "Room") used for data persistence 
  - [Retrofit](https://github.com/square/retrofit "Retrofit") as a REST Client
  - [Dagger Hilt](https://dagger.dev/hilt/ "Dagger hilt") user for dependency injection
  - [Glide](https://github.com/bumptech/glide "Glide") used to load the images
  - [Lottie Files](https://lottiefiles.com/ "Lottie Files") used to load animations 
 

Contact
-------------

- [![LinkedIn](https://img.shields.io/badge/LinkedIn-Eduardo_Binns-0077B5?style=for-the-badge&logo=linkedin&logoColor=white&labelColor=101010)](https://www.linkedin.com/in/eduar-binns)
 
