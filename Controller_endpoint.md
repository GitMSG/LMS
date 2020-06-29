
# RESTful Endpoints # 

## ADMIN SPECIFIC ##   

`@RequestMapping(path = "/register", method = RequestMethod.POST)` *Create a user*  

`@RequestMapping(path = "/changeUserRole", method = RequestMethod.POST)` *Change a users role*

`@RequestMapping(path = "/userProfiles", method = RequestMethod.GET)`  *Get all userProfiles*

`@RequestMapping(path = "/searchTrainingByCertPeriod", method = RequestMethod.GET)`  *Serches all training by specified date*

`@RequestMapping(path = "/searchTrainingByUser", method = RequestMethod.GET)`  *Serches all training by specified user*

`@RequestMapping(path = "/makeUserInactive/{id}", method = RequestMethod.PUT)`  *Update users profile with end date*

`@RequestMapping(path = "/deleteUser/{id}", method = RequestMethod.DELETE)`  *Delete a user*


## user ##

`@RequestMapping(path = "/login", method = RequestMethod.POST)`  *Allow user to login*


## userProfile ##

`@RequestMapping(path = "/userProfile/{id}", method = RequestMethod.GET)` *Get a userProfile by id*

`@RequestMapping(path = "/createProfile", method = RequestMethod.POST)` *Create a userProfile*  

`@RequestMapping(path = "/updateProfile/{id}", method = RequestMethod.PUT)` *Updates a userProfile*


## training ##

`@RequestMapping(path = "/training/{id}", method = RequestMethod.GET)`  *Get all trainings for user*

`@RequestMapping(path = "/createTraining/{id}", method = RequestMethod.POST)` *Create a training object*

`@RequestMapping(path = "/updateTraining/{id}", method = RequestMethod.PUT)` *Updates a training*




