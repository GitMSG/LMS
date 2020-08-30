<template>
  <div class="search" >
      <div class="search-card" >
        <h1 v-if="employee === false && training === false" >Search</h1>
        <h1 v-if="employee === true" >Employee Search</h1>
        <h1 v-if="training === true" >Training Search</h1>
        <span class="label" v-if="employee === false && training === false"> Employee or Training </span>
                <select v-model="type" class="dropdown" @change="typeCheck($event.target.value)">
                <option value="employee" >Employee</option>
                <option value="training" >Training</option>
                </select><br/>
                <div v-if="employee === true" >
                  
                </div>
                 <div v-if="training === true" >
                    <span class="label">From </span><input type="date" class="short-input" v-model="fromDate">
                    <span class="label">To </span><input type="date" class="short-input" v-model="toDate"><br/>

                </div>
        <button class="submit-button" @click="search">Submit</button>
     </div>
      <div class="search-results" >
          <ul class="training-list">
          <h1 class="notraining" v-if="noTrainings == true">No Trainings need approved</h1>
          <li v-for="aTrain in trainingArr" v-bind:key="aTrain.trainingId" class="training-item">
            <div
              id="proof-image-div"
              v-if="image === true && aTrain.proof.includes('cloudinary')"
              :v-bind="aTrain.proof"
            >
              <img :src="aTrain.proof" class="proofPic" height="500px" />
              <button @click="closeProof" class="cancel-button" :v-bind="aTrain.proof">Cancel</button>
            </div>
            <div class="train-details">
               <div class="compliance-div">
                  <h2 class="compliance" v-if="aTrain.complianceTime != 0">Compliance</h2>
                  <h2 class="elective" v-if="aTrain.electiveTime != 0">Elective</h2>
                </div>
                <div>
                    <h2>
                      <span class="label">User:</span>
                      <span class="var">{{getUserEmail(users)}}</span>
                    </h2>
                    <h2>
                      <span class="label">Training Name:</span>
                      <span class="var">{{aTrain.name}}</span>
                    </h2>
                    <h2>
                      <span class="label">Training Provider:</span>
                      <span class="var">{{aTrain.provider}}</span>
                    </h2>
                    <h2>
                      <span class="label">Training Date:</span>
                    <span class="var">{{aTrain.date}}</span>
                    </h2>

                    <h2>
                      <div v-if="aTrain.complianceTime != 0">
                        <span class="label">Compliance Time:</span>
                        <span class="var">{{aTrain.complianceTime/60}} hrs</span>
                      </div>
                      <div v-if="aTrain.electiveTime != 0">
                        <span class="label">Elective Time:</span>
                        <span class="var">{{aTrain.electiveTime/60}} hrs</span>
                      </div>
                    </h2>
                    <h3>
                      <span class="label">Training Description:</span>
                      <span class="var">{{aTrain.topic}}</span>
                    </h3>
                    <h3
                      class="proof-link"
                      @click="showProof(aTrain.proof)"
                      v-if=" aTrain.proof.includes('cloudinary')"
                    >Image</h3>
                    <h3 class="proof-link" v-else>
                      <a v-bind:href="aTrain.proof" target="blank">Link</a>
                    </h3>
                  </div>
                <button v-on:click="approveTraining(aTrain.trainingId)"
                        v-if="aTrain.isApproved === false" 
                        class="form-button">Approve
                </button>
            </div>
            <div class="warn"> <h2 v-if="aTrain.isApproved === false" >Waiting on Approval</h2></div>
          
          </li>
        </ul>
      </div>
  </div>
  
</template>

<script>
import auth from "@/auth.js"

export default {
  name: 'search',
  data() {
    return {
     trainingArr:[],
     email: auth.getUser().sub,
     permission: auth.getUser().rol,
     type:null,
     employee: false,
     training: false,
     fromDate: Date,
     toDate:Date,
     noTrainings:false,
     image: false,
     link: "",
    }
  },
  created(){
   if(this.permission != 'admin' ){
     this.$router.push({ path: "/profile", params: this.email });
   }
  },
  methods: {
     getUserEmail(u) {
      for (let i = 0; i < u.length; i++) {
        let el = u.shift();
        return el;
      }
    },
    typeCheck(t){
      if(t === 'employee'){
        this.employee = true;
        this.training = false;
      }
      else{
        this.training = true;
        this.employee = false;
      }
    },
     showProof(p) {
      if (p.includes("cloudinary")) {
        this.image = true;
      }
    },
    closeProof() {
      if (this.image) {
        this.image = false;
         this.search();
        
      }
    },
    search(){
      fetch(
        `${process.env.VUE_APP_REMOTE_API}/api/searchTraining?fromDate=${this.fromDate}&toDate=${this.toDate}`,
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
           if (Object.keys(this.trainingArr).length === 0) {
          this.noTrainings = true;
        } else {
          this.users = Object.keys(this.trainingArr);
        }
         
        })
        .catch((err) => {
          console.log(err);
        });
    },
    approveTraining(id) {
      fetch(`${process.env.VUE_APP_REMOTE_API}/api/updateApproval/${id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + auth.getToken(),
        },
      })
        .then((response) => {
          if (response.ok) {
             this.search();
          }
        })
        .catch((err) => console.error(err));
    },
  }
}
</script>

<style scoped>
.search{
  min-height:100vh;
}
.search-card{
  color:white;
  display: block;
  max-width: 500px;
  padding:10px;
  margin: auto;
  background-color: rgba(32, 33, 36, 0.7);
  box-shadow: 0 2px 4px -1px rgba(0, 0, 0, 0.2), 0 4px 5px 0 rgba(0, 0, 0, 0.14),
    0 1px 10px 0 rgba(0, 0, 0, 0.12);
}
.submit-button{
  background-color: rgba(36, 104, 143, 1);
  color: white;
  padding: 5px 20px;
  max-width: 25%;
  text-decoration: none;
  font-size: 16px;
  border-radius: 4px;
  border: none;
  margin: 10px;
  cursor: pointer;
}
.dropdown{
    padding:5px;
    margin:10px;
    border:none;
    border-radius:2px;   
}
.short-input{
  padding: 5px;
  margin: 10px;
  width: 125px;
  border: none;
  border-radius: 2px;
}
.warn {
  color: rgb(231, 202, 34);
  z-index: -100;
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
.tab{
  background-color: rgba(32, 33, 36, 0.9);
  box-shadow: 10px 10px 5px 0px rgba(0,0,0,0.75);
  border: 1px solid #1c9a2f;
  border-radius: 3px;
  color: white;
  margin:30px auto;
  width: 400px;
}
.tab:hover{
  cursor: pointer;
  color:#1c9a2f;
  transform: scale(1.2);
  transition-duration: 500ms;
}
h1{
  font-weight: lighter;
}

</style>