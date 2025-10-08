// Elementos del DOM
const authRegisterForm = document.getElementById("registerForm");
const authLoginForm = document.getElementById("loginForm");
const authRegisterContainer = document.getElementById("register-container");
const authLoginContainer = document.getElementById("login-container");
const authLandingPage = document.getElementById("landingPage");
const authLogoutBtn = document.getElementById("logoutBtn");
const authErrorRegistro = document.getElementById("errorRegistro");
const authErrorLogin = document.getElementById("errorLogin");
const authGoLogin = document.getElementById("goLogin");
const authGoRegister = document.getElementById("goRegister");

// Cambiar entre Login y Registro
authGoLogin.addEventListener("click", () => {
    authRegisterContainer.classList.add("hidden");
    authLoginContainer.classList.remove("hidden");
    authErrorRegistro.innerText = "";
});
authGoRegister.addEventListener("click", () => {
    authLoginContainer.classList.add("hidden");
    authRegisterContainer.classList.remove("hidden");
    authErrorLogin.innerText = "";
});

// Registro
authRegisterForm.addEventListener("submit", (e) => {
    e.preventDefault();
    const nombre = document.getElementById("nombre").value.trim();
    const apellido = document.getElementById("apellido").value.trim();
    const correo = document.getElementById("correo").value.trim();
    const clave1 = document.getElementById("clave1").value.trim();
    const clave2 = document.getElementById("clave2").value.trim();

    if (nombre.length < 3) {
        authErrorRegistro.innerText = "El nombre debe tener al menos 3 letras";
        return;
    }

    if (apellido.length < 3) {
        authErrorRegistro.innerText = "El apellido debe tener al menos 3 letras";
        return;
    }

    const gmailRegex = /^[a-zA-Z0-9._%+-]{6,}@gmail\.com$/;
    if (!gmailRegex.test(correo)) {
        authErrorRegistro.innerText = "El correo debe ser Gmail y tener al menos 6 caracteres antes del @ (ejemplo@gmail.com)";
        return;
    }

    if (clave1.length < 8) {
        authErrorRegistro.innerText = "La contraseña debe tener al menos 8 caracteres";
        return;
    }

    if (clave1 !== clave2) {
        authErrorRegistro.innerText = "Las contraseñas no coinciden";
        return;
    }

    localStorage.setItem("nombre", nombre);
    localStorage.setItem("apellido", apellido);
    localStorage.setItem("usuario", correo);
    localStorage.setItem("clave", clave1);

    authErrorRegistro.innerText = "Registro exitoso! Ahora inicia sesión.";
    authRegisterContainer.classList.add("hidden");
    authLoginContainer.classList.remove("hidden");
});

// Login
authLoginForm.addEventListener("submit", (e) => {
    e.preventDefault();
    const correo = document.getElementById("loginCorreo").value.trim();
    const clave = document.getElementById("loginClave").value.trim();

    if (correo === "" || clave === "") {
        authErrorLogin.innerText = "Debes completar todos los campos";
        return; 
    }

    const usuarioGuardado = localStorage.getItem("usuario");
    const claveGuardada = localStorage.getItem("clave");

    if (correo === usuarioGuardado && clave === claveGuardada) {
        localStorage.setItem("logueado", "true");
        authLoginContainer.classList.add("hidden");
        authRegisterContainer.classList.add("hidden");
        authLandingPage.classList.remove("hidden");
        authErrorLogin.innerText = "";

        const nombreGuardado = localStorage.getItem("nombre");
        const apellidoGuardado = localStorage.getItem("apellido");
        const welcomeMessage = document.getElementById("welcomeMessage");
        welcomeMessage.innerText = "Bienvenido/a " + nombreGuardado + " " + apellidoGuardado + "!";
    } else {
        authErrorLogin.innerText = "Correo o contraseña incorrectos";
    }
});


// Mantener sesión
window.onload = () => {
    const logueado = localStorage.getItem("logueado");
    if (logueado === "true") {
        authLandingPage.classList.remove("hidden");
        authRegisterContainer.classList.add("hidden");
        authLoginContainer.classList.add("hidden");

        const nombreGuardado = localStorage.getItem("nombre");
        const apellidoGuardado = localStorage.getItem("apellido");
        const personalWelcome = document.getElementById("personalWelcome");
        personalWelcome.innerText = "Bienvenido, " + nombreGuardado + " " + apellidoGuardado + "!";
    }
};


// Logout
authLogoutBtn.addEventListener("click", () => {
    localStorage.removeItem("logueado");
    authLandingPage.classList.add("hidden");
    authLoginContainer.classList.remove("hidden");

    // Limpiar inputs y errores
    authLoginForm.reset();
    authRegisterForm.reset(); 
    authErrorLogin.innerText = "";
    authErrorRegistro.innerText = "";
});

