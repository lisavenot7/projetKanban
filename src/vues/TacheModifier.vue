<style src="../assets/css/style.css"></style>
<script setup>
import Navbar from "../components/NavbarUtilisateur.vue"
import { useRouter, useRoute } from "vue-router"
import { ref, computed } from 'vue'

import tabsData from '../bdd/tableaux.json'
import usersData from '../bdd/users.json'
import tachesData from '../bdd/taches.json'

const router = useRouter()
const route = useRoute()

const idTableau = Number(route.params.id)
const idColonne = Number(route.params.idcolonne)
const idTache = Number(route.params.idtache)

const tache = tachesData.find(t => t.id === idTache) 

const nom = ref(tache.nom)
const dateLimite = ref(tache? tache.dateLimite : null)
const assignéA = ref(tache.utilisateur || "")
const description = ref(tache.description)
const prioritaire = ref(Boolean(tache.prioritaire))
const etat = ref(tache.etat || "")
const error = ref("")

const tab = tabsData.find(t => t.id === idTableau) || { participants: [] }

const participants = computed(() => {
  const pseudos = [tab.createur, ...tab.participants]
  return usersData.filter(u => pseudos.includes(u.pseudo))
})

const annuler = async () => {
  router.push(`/private/tableaux/${idTableau}/colonnes/${idColonne}/taches/${idTache}`)
}
const valider = async () => {
  router.push(`/private/tableaux/${idTableau}/colonnes/${idColonne}/taches/${idTache}`)
}

const admin = ref(false)
</script>

<template>
<Navbar />
<div class="container">
  <div class="box">
    <h1>Modifier une tâche</h1>

    <input v-model="nom" placeholder="Nom" />

    <h3 :style="{ marginTop: '5px' }">Assigné à :</h3>
    <select v-model="assignéA">
      <option value="">Choisir un participant</option>
      <option 
        v-for="user in participants" 
        :key="user.pseudo" 
        :value="user.pseudo"
      >
        {{ user.prenom }} {{ user.nom }} ({{ user.pseudo }})
      </option>
    </select>

    <h3 :style="{ marginTop: '5px' }">Date limite (facultatif)</h3>
    <input v-model="dateLimite" type="date" placeholder="Date limite (facultatif)" />

    <textarea 
      v-model="description" 
      placeholder="Description" 
      rows="4" style="resize: none;">
    </textarea>
    <h3 :style="{ marginTop: '5px' }">Etat :</h3>
    <select v-model="etat">
      <option value="">Choisir un état</option>
      <option value="A faire"> A faire</option>
      <option value="En cours">En cours</option>
      <option value="Terminé">Terminé</option>
    </select>

    <div class="checkbox-container">
      <label class="checkbox-label">
        <input type="checkbox" v-model="prioritaire" />
        <h3 :style="{ marginTop: '5px' }">Prioritaire</h3>
      </label>
    </div>

    <div class="nav">
      <button class="boutonsNav" @click="valider">Valider</button> 
      <button class="boutonsNav" @click="annuler">Annuler</button>
    </div>

    <h2 v-if="error" class="error">&nbsp{{ error }}</h2>
  </div>
</div>
</template>