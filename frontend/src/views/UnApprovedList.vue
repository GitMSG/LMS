<template>
  <div id="unapproved-page">
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
        <div class="compliance-div">
          <h2 class="compliance" v-if="aTrain.complianceTime != 0">Compliance</h2>
          <h2 class="elective" v-if="aTrain.electiveTime != 0">Elective</h2>
        </div>
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
        <h2 v-if="aTrain.isApproved === false" class="warn">Waiting on Approval</h2>
        <button v-on:click="approveTraining(aTrain.trainingId)" class="form-button">Approve</button>
      </li>
    </ul>
  </div>
</template>

<script>
import auth from "@/auth.js";
export default {
  name: "unApprovedList",

  data() {
    return {
      trainingArr: [],
      noTrainings: false,
      image: false,
      link: "",
    };
  },
  methods: {
    getUserEmail(u) {
      for (let i = 0; i < u.length; i++) {
        let el = u.shift();
        return el;
      }
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
            this.$router.go();
          }
        })
        .catch((err) => console.error(err));
    },
    showProof(p) {
      if (p.includes("cloudinary")) {
        this.image = true;
      }
    },
    closeProof() {
      if (this.image) {
        this.image = false;
      }
    },
  },
  mounted() {
    fetch(`${process.env.VUE_APP_REMOTE_API}/api/needApproval`, {
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
};
</script>

<style>
#unapproved-page {
  min-height: 100vh;
}
.training-item {
  background-color: rgba(32, 33, 36, 0.8);
  border-radius: 4px;
  box-shadow: 0 2px 4px -1px rgba(0, 0, 0, 0.2), 0 4px 5px 0 rgba(0, 0, 0, 0.14),
    0 1px 10px 0 rgba(0, 0, 0, 0.12);
  padding: 5px;
  margin: 10px;
  list-style: none;
  text-align: left;
}
.warn {
  float: right;
  color: rgb(231, 202, 34);
}
.label {
  font-size: 16px;
  font-weight: lighter;
  padding: 10px;
  color: rgb(211, 228, 243);
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

.var {
  color: silver;
  font-weight: lighter;
  text-align: center;
}
.notraining {
  color: #1c9a2f;
}
.elective {
  color: rgba(36, 104, 143, 1);
  font-size: 20px;
}
.total {
  color: #914522;
  font-size: 20px;
}
.compliance-div {
  text-align: center;
  float: right;
  margin: 10px;
  padding: 10px;
}
ul {
  padding: 0;
}
h2 {
  margin: 10px;
}
h3 {
  margin: 10px;
}
</style>