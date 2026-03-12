<style src="../assets/css/style.css"></style>

<script setup>
import Navbar from "../components/NavbarUtilisateur.vue"
import Commentaire from "../components/Commentaire.vue"
import { useRouter, useRoute } from "vue-router"
import { ref } from 'vue'

import tabsData from '../bdd/tableaux.json'
import tachesData from '../bdd/taches.json'
import commentairesData from '../bdd/commentaires.json'

const router = useRouter()
const route = useRoute()

const idTableau = Number(route.params.id)
const idColonne = Number(route.params.idcolonne)
const idTache = Number(route.params.idtache)

const tache = tachesData.find(t => t.id === idTache) 
const commentaires = tache ? commentairesData.filter(c => tache.commentaires.includes(c.id)) : []

const menuOpen = ref(false)
function toggleMenu() {
  menuOpen.value = !menuOpen.value
}
function goToModifier() {
    router.push(`/private/tableaux/${idTableau}/colonnes/${idColonne}/taches/${idTache}/modifier`)
}
function goToNewCom() {
    router.push(`/private/tableaux/${idTableau}/colonnes/${idColonne}/taches/${idTache}/commentaires/ajouter`)
}


const today = new Date();
today.setHours(0, 0, 0, 0); 
const dateLimiteDate = new Date(tache.dateLimite);
dateLimiteDate.setHours(0, 0, 0, 0);

</script>

<template>
<Navbar />
<div class="container">
  <div class="tache-box">
    <div v-if="tache!=null">
      <div  class="tableau-header">
        <h1>
          <span v-if="tache.prioritaire === 1">
            <svg width="30" height="30" viewBox="0 0 24 24">
              <path d="M12 3L1 21h22L12 3z" fill="#e53935"/>
              <rect x="11" y="9" width="2" height="6" fill="white"/>
              <rect x="11" y="17" width="2" height="2" fill="white"/>
            </svg>
          </span>
          {{ tache.nom }}
          <span v-if="tache.prioritaire === 1" >
            -
            <span v-if="tache.prioritaire === 1" style="color:red;">
              Prioritaire
            </span>
          </span>
        </h1>
        <div class="menu-container">
          <button class="menu-button3" @click.stop="toggleMenu">⋮</button>
          <div v-if="menuOpen" class="menu-dropdown">
            <p @click="goToModifier">Modifier la tâche</p>
            <p @click="goToNewCom">Ajouter un commentaire</p>
            <p>Supprimer la tâche</p>
          </div>
        </div>
      </div>
      <div v-if="tache.dateLimite!=null">
        <h2>A finir avant le {{tache.dateLimite}}</h2>
      </div>
      <div>
        <h2>Assigné à {{tache.utilisateur}}</h2>
        <p>{{tache.description}}</p>
        <h2 v-if="tache.etat==='Terminé'" 
          style="background-color:green; 
            width: 15%;text-align:center; 
            border-radius:10px;
            color:white;">
          {{ tache.etat }}
        </h2>
        <h2 v-else-if="tache.etat==='En cours'" 
          style="background-color:orange; 
            width: 15%;text-align:center; 
            border-radius:10px;
            color:white;">
          {{ tache.etat }}
        </h2>
        <h2 v-else 
          style="background-color:black; 
          width: 15%;text-align:center; 
          border-radius:10px;
          color:white;">
          {{ tache.etat }}
        </h2>
        <h2 v-if="today>dateLimiteDate && tache.etat!=='Terminé'" 
          style="background-color:red; 
            width: 15%;text-align:center; 
            border-radius:10px;
            color:white;">
          Retard
        </h2>
      </div>
      <div class="com-box">
        <div style="margin-left:5%;">
          <h2>Commentaires ({{ tache.commentaires.length}})</h2>
          <Commentaire v-if="commentaires.length > 0" :commentaires="commentaires"/>
            
          <p v-else style="margin-left:5%;">Aucun commentaire pour cette tâche</p>
        </div>
      </div>
    </div>
    <div v-else>
        <h1>Tâche introuvable</h1>
    </div>
  </div>
</div>
</template>