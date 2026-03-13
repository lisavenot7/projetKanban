<style src="../assets/css/style.css"></style>
<script setup>
import Navbar from "../components/NavbarUtilisateur.vue"

import { useRouter, useRoute} from "vue-router"
import { ref} from 'vue'

import tabsData from '../bdd/tableaux.json'

const router = useRouter()
const route = useRoute()

const idParam = Number(route.params.id)
const tab = tabsData.find(t => t.id === idParam) || { participants: [] }

const nom = ref("")
const error = ref("")

const annuler = async () => {
  router.push(`/private/tableaux/${tab.id}`)
}
const valider = async () => {
  router.push(`/private/tableaux/${tab.id}`)
}

const admin = ref(false)
</script>

<template>
<Navbar />
  <div class="container">
    <div class="box">
      <h1>Ajouter une colonne </h1>
      <input v-model="nom" placeholder="Nom" />
      <div class="nav">
        <button class="boutonsNav" @click="valider">Valider</button> 
        <button class="boutonsNav" @click="annuler">Annuler</button>
      </div>
      <p v-if="error" class="error">&nbsp{{ error }}</p>
    </div>
  </div>
</template>

