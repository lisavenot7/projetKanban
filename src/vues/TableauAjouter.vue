<style src="../assets/css/style.css"></style>
<script setup>
import Navbar from "../components/NavbarUtilisateur.vue"

import { useRouter} from "vue-router"
import { ref,onMounted } from 'vue'

const router = useRouter()

const nom = ref("")
const error = ref("")


const annuler = async () => {
  router.push("/private/tableaux")
}
const valider = async () => {

  const today = new Date();
  const dateOnly = today.toISOString().split("T")[0];
  
  const tableau = {
    tabNom: nom.value,
    tabDateCreation: dateOnly,
    createurId:idUser
  }
  try {
    const response = await fetch("http://localhost:10056/tableaux", {
      method: "POST",
      headers: {
        "Authorization": `Bearer ${token}`,
        "Content-Type": "application/json"
      },
      body: JSON.stringify(tableau)
    })
    if (!response.ok) {
      const data = await response.json()
      error.value = data.message || "Erreur lors de la création du tableau"
      return
    }
    const data = await response.json()
    console.log("Tableau créé:", data)
    router.push("/private/tableaux") 
  } catch (err) {
    console.error(err)
    error.value = "Impossible de contacter le serveur"
  }
}

const token = localStorage.getItem("jwtToken")
const idUser = localStorage.getItem("cptId")
onMounted(() => {
  const admin = localStorage.getItem("isAdmin")
  if (!token) {
    router.push("/connexion")
  }
  if (admin === "1") {
    router.push("/admin")
  }
})


</script>

<template>
<Navbar />
  <div class="container">
    <div class="box">
      <h1>Ajouter un tableau</h1>
      <input v-model="nom" placeholder="Nom" />

        
      
      <div class="nav">
        <button class="boutonsNav" @click="valider">Valider</button> 
        <button class="boutonsNav" @click="annuler">Annuler</button>
      </div>
      <p v-if="error" class="error">&nbsp{{ error }}</p>
    </div>
  </div>
</template>

