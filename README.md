##  CodeFellowShip
- a Community website aim to gather developers in one place.
- CodeFellowship that allows people learning to code to connect with each other and support each other on their coding journeys.

## run application
* from ide: press run button.
* terminal `./gradlew bootrun`
## Routes:
- outer homepage: `/`
- Login Page: `/login`
- Signup Page: `/signup`
- error Page: `/error`
- current user profile Page: `/profile`
- specific user profile Page: `/profile/{id}`
- all users in community : `/loggedInUser`
- followed users feed : `/feed`


## project structure:

```
.
├── java
│   └── com
│       └── codefellowship
│           └── codefellowship
│               ├── CodefellowshipApplication.java
│               ├── auth
│               │   ├── UserDetailsServiceImpl.java
│               │   └── WebSecurityConfig.java
│               ├── controller
│               │   ├── Controller.java
│               │   ├── ControllerApplicationUser.java
│               │   └── PostController.java
│               ├── model
│               │   ├── ApplicationUser.java
│               │   └── Post.java
│               └── repository
│                   ├── ApplicationUserRepository.java
│                   └── PostRepository.java
└── resources
    ├── application.properties
    ├── static
    │   └── style.css
    └── templates
        ├── error.html
        ├── feed.html
        ├── fragments
        │   └── general.html
        ├── homepage.html
        ├── loggedInHome.html
        ├── login.html
        ├── profile.html
        ├── signup.html
        └── viewOneProfile.html

```
