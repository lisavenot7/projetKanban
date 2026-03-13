<style src="../assets/css/style.css"></style>
<script setup>
import Navbar from "../components/NavbarAdmin.vue"
import Bandeau from "../components/BandeauAdmin.vue"

import StatsChart from '../components/StatsUtilisateur.vue'

import usersData from '../bdd/users.json'

import {ref,computed} from 'vue'
import {useRoute} from "vue-router"

const route = useRoute()

const pseudoParam = route.params.id
const user = usersData.find(u => u.pseudo === pseudoParam)

const nbComptes = computed(() => usersData.length)
const nbAdmin = computed(() => usersData.filter(user => user.isAdmin === 1).length)
const nbUtil = computed(() => usersData.filter(user => user.isAdmin === 0).length)
const nbAct = computed(() => usersData.filter(user => user.actif === 1).length)
const nbDes = computed(() => usersData.filter(user => user.actif === 0).length)

</script>

<template>
  <Navbar />
  <div v-if="user" class="home">
    <br/><br/>
    <h2 style="margin-left:5%;">
      Statistiques de l'utilisateur {{ user.pseudo }}
    </h2>
    <div class="stats-container">
      <div class="chart-wrapper">
        <StatsChart />
      </div>
      
      <div class="cards-wrapper">
       
        <div class="admin-card">
          <div class="icon-circle"><h1>{{nbAct}}</h1></div>
          <span>Tableaux créés</span>
        </div>
        <div class="admin-card">
          <div class="icon-circle"><h1>{{nbComptes}}</h1></div>
          <span>moyenne de commentaire par tâche</span>
        </div>
        <div class="admin-card">
          <div class="icon-circle"><h1>{{nbDes}}</h1></div>
          <span>Tableaux où il participe</span>
        </div>
        
      </div>
    </div>
  </div>
  <div v-else class="home">
  <br/><br/>
    <h2 style="margin-left:5%;">
      Aucun utilisateur trouvé
    </h2>
  </div>
</template>
