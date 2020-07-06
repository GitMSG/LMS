
# RESTful Endpoints # 

## ADMIN SPECIFIC ##   

```java
 @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register(@Valid @RequestBody User user, BindingResult result) throws UserCreationException {
        if (result.hasErrors()) {
            String errorMessages = "";
            for (ObjectError error : result.getAllErrors()) {
                errorMessages += error.getDefaultMessage() + "\n";
            }
            throw new UserCreationException(errorMessages);
        }
        auth.register(user.getEmail(), user.getPassword(), user.getPermission());
        return "{\"success\":true}";
    } 
``` 

```java
@RequestMapping(path = "/changeUserPermission", method = RequestMethod.POST)
    public void changeUserPermission(@RequestBody User user) {                     
    	userDao.changePermission(user.getEmail(), user.getPermission());
    } 
``` 
 
```java
@RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> getAllUsers(){
    	List<User> allUsers = userDao.getAllUsers();
    	return allUsers;
    }
```
`@RequestMapping(path = "/userProfile/{id}", method = RequestMethod.GET)`  *Serches userProfiles for a specific user*

`@RequestMapping(path = "/trainingByDate/{date}", method = RequestMethod.GET)`  *Serches all training by specified date*

`@RequestMapping(path = "/trainingByUser/{id}", method = RequestMethod.GET)`  *Serches all training by specified user*

`@RequestMapping(path = "/makeUserInactive/{id}", method = RequestMethod.PUT)`  *Update users profile with end date*

`@RequestMapping(path = "/deleteUser/{id}", method = RequestMethod.DELETE)`  *Delete a user*

----

## user ##

```java
@RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(@RequestBody User user, RedirectAttributes flash) throws UnauthorizedException {
        if (auth.signIn(user.getEmail(), user.getPassword())) {
            User currentUser = auth.getCurrentUser();
            return tokenHandler.createToken(user.getEmail(), currentUser.getPermission());
        } else {
            throw new UnauthorizedException();
        }
    }
```
----

## userProfile ##

```java
@RequestMapping(path = "/profile", method = RequestMethod.GET)
    public UserProfile getProfileById() {
    	  int id = (int) authProvider.getCurrentUser().getId();
    	  UserProfile aProfile = userProfileDao.getProfileById(id);
    	  return aProfile;
      }
```      

```java
@RequestMapping(path = "/createProfile", method = RequestMethod.POST) 
    public void addUserProfile(@RequestBody UserProfile aUserProfile, String email) {
        UserProfile newProfile = new UserProfile( 
                    aUserProfile.getFirstName(),aUserProfile.getLastName()
                    ,aUserProfile.getRole(),aUserProfile.getStartDate()
                    ,aUserProfile.getEndDate(),aUserProfile.getProfilePic());
                     
        userProfileDao.createUserProfile( newProfile , email );   
	  }
```  

`@RequestMapping(path = "/updateProfile/{id}", method = RequestMethod.PUT)` *Updates a userProfile*

----

## training ##

`@RequestMapping(path = "/training/{id}", method = RequestMethod.GET)`  *Get all trainings for user*

`@RequestMapping(path = "/createTraining/{id}", method = RequestMethod.POST)` *Create a training object*

`@RequestMapping(path = "/updateTraining/{id}", method = RequestMethod.PUT)` *Updates a training*




