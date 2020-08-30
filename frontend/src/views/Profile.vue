<template>
  <div id="profile">
    <div class="loading" v-if="isLoading">
      <img class="loading-image" src="@/assets/TE_bur2.gif" />
    </div>
    <div id="detail-container">
      <div id="profile-detail">
        <div id="image-div">
          <img :src="profile.profilePic" class="profilePic" height="200px" />
        </div>
        <div id="profile-text">
          <h1>{{profile.firstname+" "+profile.lastname}}</h1>
          <h2>
            <span class="label">Location</span>
            {{profile.campusShortCode}}
          </h2>
          <h2>
            <span class="label">Position</span>
            {{profile.role}}
          </h2>
          <h2>
            <span class="label">Start Date</span>
            {{profile.startDate}}
          </h2>
        </div>
        <div class="time-div">
          <p>
            Compliance Total:
            <span class="compliance">{{profile.complianceTime/60}}</span>
          </p>
          <p>
            Elective Total:
            <span class="elective">{{profile.electiveTime/60}}</span>
          </p>
          <p>
            Total:
            <span class="total">{{profile.electiveTime/60 + profile.complianceTime/60}}</span>
          </p>
        </div>
      </div>
      <div class="sub-detail" >
        <button v-if="!this.formMode" v-on:click="toggleFormMode" class="form-button">Add Training</button>
        <h3 class="ctp" >{{curTrainPeriod}}</h3>
      </div>
    </div>
    <div id="training">
      <training
        v-if="!this.formMode"
        :profileId="profile.profileId"
        :training="trainingArr"
        :firstName="profile.firstName"
      />
      <training-form v-if="this.formMode" :profileId="profile.profileId" />
    </div>
  </div>
</template>

<script>
import auth from "@/auth.js";
import Training from "@/components/Training.vue";
import TrainingForm from "@/components/TrainingForm.vue";
export default {
  name: "profile",
  components: {
    Training,
    TrainingForm,
  },
  data() {
    return {
      isLoading: true,
      formMode: false,
      profile: {
        profileId: "",
        firstname: "",
        lastname: "",
        campusShortCode: "",
        role: "",
        startDate: "",
        profilePic: "",
        complianceTime: 0,
        electiveTime: 0,
      },
      curTrainPeriod:'',
      trainingArr: [],
    };
  },
  methods: {
    toggleFormMode() {
      if (!this.formMode) {
        this.formMode = true;
      }
    },
    setCurPeriod(tcp){
      const endDate = new Date(tcp.currentPeriod)
      endDate.setMonth(endDate.getMonth()+tcp.certLength)
      const endYe = new Intl.DateTimeFormat('en', { year: 'numeric' }).format(endDate)
      const endMo = new Intl.DateTimeFormat('en', { month: 'short' }).format(endDate)
      const startDate = new Date(tcp.currentPeriod)
      //const startDate = new Date(date.getDate(date.setUTCDate()))
      const starYe = new Intl.DateTimeFormat('en', { year: 'numeric' }).format(startDate)
      const starMo = new Intl.DateTimeFormat('en', { month: 'short' }).format(startDate)
      
       this.curTrainPeriod = `${starMo},${starYe}`+"  -  "+`${endMo},${endYe}`
      return this.curTrainPeriod
    },
    showTraining() {
      fetch(
        `${process.env.VUE_APP_REMOTE_API}/api/training/${this.profile.profileId}`,
        {
          method: "GET",
          headers: {
            Authorization: "Bearer " + auth.getToken(),
          },
          credentials: "same-origin",
        }
      )
        .then((response) => {
          return response.json();
        })
        .then((theData) => {
          this.trainingArr = theData;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getCurtcp(){
      fetch(
        `${process.env.VUE_APP_REMOTE_API}/api/certperiod/${this.profile.campusShortCode}`,
        {
          method: "GET",
          headers: {
            Authorization: "Bearer " + auth.getToken(),
          },
          credentials: "same-origin",
        }
      )
        .then((response) => {
          return response.json();
        })
        .then((theData) => {
          this.tcp = theData;
          this.setCurPeriod(this.tcp)
        })
        .catch((err) => {
          console.log(err);
        });
    }
  },
  created() {
    
    fetch(`${process.env.VUE_APP_REMOTE_API}/api/profile`, {
      method: "GET",
      headers: {
        Authorization: "Bearer " + auth.getToken(),
      },
      credentials: "same-origin",
    })
      .then((response) => {
        return response.json();
      })
      .then((theData) => {
        this.profile = theData;
        this.showTraining();
        this.getCurtcp();
        if (this.profile.firstname === "TE Firstname") {
          this.$router.push("/profileForm");
          this.$router.go();
        }
      })
      .catch((err) => {
        console.log(err);
      })
      .finally(() => {
        setTimeout(() => {
          this.isLoading = false;
        }, 1000);
      });
  },
};
</script>

<style scoped>
#profile {
  min-height: 100vh;
  border-radius: 3px;
  color: silver;
}
#detail-container {
  background-color: rgba(233, 235, 241, 0.8);
  box-shadow: 0 2px 4px -1px rgba(0, 0, 0, 0.2), 0 4px 5px 0 rgba(0, 0, 0, 0.14),
    0 1px 10px 0 rgba(0, 0, 0, 0.12);
}
.sub-detail{
  display:flex;
  justify-content: space-around;
}
#profile-detail {
  display: flex;
  justify-content: space-around;
  padding: 5px 0px;
}
.profilePic {
  float: left;
  border-radius: 50%;
  border: 1px solid #1c9a2f;
}
#profile-text {
  background-color: rgba(32, 33, 36, 0.8);
  box-shadow: 0 2px 4px -1px rgba(0, 0, 0, 0.2), 0 4px 5px 0 rgba(0, 0, 0, 0.14),
    0 1px 10px 0 rgba(0, 0, 0, 0.12);
  align-self: center;
}
.time-div {
  background-color: rgba(32, 33, 36, 0.8);
  box-shadow: 0 2px 4px -1px rgba(0, 0, 0, 0.2), 0 4px 5px 0 rgba(0, 0, 0, 0.14),
    0 1px 10px 0 rgba(0, 0, 0, 0.12);
  align-self: center;
  text-align: right;
  padding: 10px;
}
p {
  color: silver;
}
.loading {
  background-color: rgba(32, 33, 36, 1);
  margin: 0px;
  width: 100vw;
  height: 100vh;
  position: fixed;
  z-index: 1000;
}
.loading-image {
  margin-top: 20%;
}
.form-button {
  background-color: rgba(36, 104, 143, 1);
  color: white;
  padding: 5px 20px;
  text-decoration: none;
  font-size: 16px;
  border-radius: 4px;
  border: none;
  margin: 10px;
  cursor: pointer;
}
.label {
  font-weight: lighter;
}
.ctp{
  color:rgba(32, 33, 36, 0.8);
  font-weight: lighter;
  font-style: italic;
  
}
</style>