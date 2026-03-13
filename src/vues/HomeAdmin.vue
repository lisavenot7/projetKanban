<style src="../assets/css/style.css"></style>
<script setup>
import { useRouter } from "vue-router"
import { onMounted } from "vue"

import Navbar from "../components/NavbarAdmin.vue"
import Bandeau from "../components/BandeauAdmin.vue"

import {ref,computed} from 'vue'

const router = useRouter()

onMounted(() => {
  const token = localStorage.getItem("jwtToken")
  if (!token) {
    router.push("/connexion")
  }
})
</script>

<template>
<Navbar />
  <div class="home">
    <Bandeau />
  </div>
</template>
