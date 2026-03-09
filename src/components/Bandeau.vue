<script setup></script>

<template>
  <div class="hero">
    <div class="content">
      <h1>Le cinéma, à la demande, sans limites.</h1>
      <p>Découvrez des centaines de films à louer en streaming, des grands classiques aux dernières nouveautés.
      Profitez d’une qualité HD, d’un accès instantané et regardez vos films préférés où vous voulez, quand vous voulez.
      Installez-vous confortablement… le spectacle commence.</p>
      <a><router-link to="/movies"><button>NOS FILMS À LA DEMANDE </button></router-link></a>
    </div>
  </div>
</template>

<style scoped>
.hero {
  width: 100%;
  height: 60vh;
  background-image: url('https://img.freepik.com/photos-gratuite/collage-fond-film_23-2149876010.jpg?semt=ais_user_personalization&w=740&q=80');
  background-size: 100% 100%;
  background-position: center;
  background-repeat: no-repeat;
  display: flex;
  align-items: center;
  padding-left: 60px;
  color: white;
}

.content {
  max-width: 50%;
}

button {
  margin-top: 20px;
  padding: 20px 40px;
  background-color: #81D4FA;
  border: none;
  color: 0F171E;
  cursor: pointer;
  border-radius: 4px;
  font-size:18px;
  font-weight: bold;
  height:80px;
}

button:hover {
  opacity: 0.8;
}
</style>