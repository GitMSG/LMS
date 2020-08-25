import Vue from 'vue'
import Router from 'vue-router'
import auth from './auth'
import AdmnHome from './views/AdmnHome.vue'
import Login from './views/Login.vue'
import Register from './views/Register.vue'
import EditUserRoles from './views/EditUserRoles.vue'
import ProfileForm from './views/ProfileForm.vue'
import Profile from './views/Profile.vue'
import AdmnProfile from './views/AdmnProfile.vue'
import AllProfiles from './views/AllProfiles.vue'
import UnApprovedList from './views/UnApprovedList.vue'

Vue.use(Router)

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/admnhome',
      name: 'admnhome',
      component: AdmnHome,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/login",
      name: "login",
      component: Login,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: '/profile',
      name: 'profile',
      component: Profile,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: '/admnprofile',
      name: 'admnprofile',
      component: AdmnProfile,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: '/unapprovedlist',
      name: 'unApprovedList',
      component: UnApprovedList,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/allProfiles',
      name: 'allProfiles',
      component: AllProfiles,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/profileForm",
      name: "profileForm",
      component: ProfileForm,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/register",
      name: "register",
      component: Register,
      meta: {
        requiresAuth: false
      }
    },
     {
       path: "/editUserRoles",
       name: "editUserRoles",
       component: EditUserRoles,
       meta: {
         requiresAuth: true
       }
     },
  ]
})

router.beforeEach((to, from, next) => {
  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);
  const user = auth.getUser();

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && !user) {
    next("/login");
  } else {
    // Else let them go to their next destination
    next();
  }
});

export default router;
