// Lista de productos
const productos = [
  { id: 'FR001', nombre: 'Manzanas Fuji', categoria: 'frutas', precio: 1200, stock: 150, descripcion: 'Manzanas Fuji crujientes y dulces, cultivadas en el Valle del Maule.', imagen: './imagenes/manzanas.jpg'  },
  { id: 'FR002', nombre: 'Naranjas Valencia', categoria: 'frutas', precio: 1000, stock: 200, descripcion: 'Jugosas y ricas en vitamina C, ideales para zumos frescos.', imagen: './imagenes/Naranjas.png'  },
  { id: 'FR003', nombre: 'Plátanos Cavendish', categoria: 'frutas', precio: 800, stock: 250, descripcion: 'Plátanos maduros y dulces, perfectos para desayuno o snack.', imagen: './imagenes/Plátanos.png'  },
  { id: 'VR001', nombre: 'Zanahorias Orgánicas', categoria: 'verduras', precio: 900, stock: 100, descripcion: 'Zanahorias crujientes cultivadas sin pesticidas en O\'Higgins.', imagen: './imagenes/Zanahoria.jpg'  },
  { id: 'VR002', nombre: 'Espinacas Frescas', categoria: 'verduras', precio: 700, stock: 80, descripcion: 'Perfectas para ensaladas y batidos verdes.', imagen: './imagenes/Espinacas.png'  },
  { id: 'VR003', nombre: 'Pimientos Tricolores', categoria: 'verduras', precio: 1500, stock: 120, descripcion: 'Ricos en antioxidantes y vitaminas, ideales para salteados.', imagen: './imagenes/Pimientos.png'  },
  { id: 'PO001', nombre: 'Miel Orgánica', categoria: 'organicos', precio: 5000, stock: 50, descripcion: 'Miel pura y orgánica producida por apicultores locales.', imagen: './imagenes/Miel.webp'  },
  { id: 'PO003', nombre: 'Quinua Orgánica', categoria: 'organicos', precio: 3000, stock: 70, descripcion: 'Granos de quinua orgánica, perfectos para una dieta saludable.', imagen: './imagenes/Quinua.png'  },
  { id: 'PL001', nombre: 'Leche Entera', categoria: 'lacteos', precio: 1200, stock: 100, descripcion: 'Leche fresca de granjas locales, rica en calcio.', imagen: './imagenes/leches.webp'  }
];

// Referencias al DOM
const productList = document.querySelector('.product-list');
const filterButtons = document.querySelectorAll('.filters button');

// Función para mostrar productos
function mostrarProductos(lista) {
  productList.innerHTML = ''; 
  lista.forEach(producto => {
    const card = document.createElement('div');
    card.classList.add('product-card');
    card.innerHTML = `
      <img src="${producto.imagen}" alt="${producto.nombre}">
      <h3>${producto.nombre}</h3>
      <p>${producto.descripcion}</p>
      <p><strong>Precio:</strong> $${producto.precio.toLocaleString()} CLP</p>
      <p><strong>Stock:</strong> ${producto.stock}</p>
      <button class="add-cart" data-id="${producto.id}">Agregar al carrito</button>
    `;
    productList.appendChild(card);
  });
}

// Filtrado por categoría
filterButtons.forEach(btn => {
  btn.addEventListener('click', () => {
    filterButtons.forEach(b => b.classList.remove('active'));
    btn.classList.add('active');

    const categoria = btn.getAttribute('data-filter');
    if(categoria === 'all') {
      mostrarProductos(productos);
    } else {
      const filtrados = productos.filter(p => p.categoria === categoria);
      mostrarProductos(filtrados);
    }
  });
});

// Inicializar mostrando todos los productos
mostrarProductos(productos);

// Funciones para el carrito
function obtenerCarrito() {
  return JSON.parse(localStorage.getItem("carrito")) || [];
}

function guardarCarrito(carrito) {
  localStorage.setItem("carrito", JSON.stringify(carrito));
}

function actualizarContadorCarrito() {
  const carrito = obtenerCarrito();
  const total = carrito.reduce((acc, item) => acc + item.cantidad, 0);
  document.getElementById('cart-count').textContent = total;
}

// Función para mostrar un toast
function mostrarToast(mensaje) {
  const toastContainer = document.getElementById('toast-container');
  const toast = document.createElement('div');
  toast.classList.add('toast');
  toast.textContent = mensaje;
  toastContainer.appendChild(toast);

  // Eliminar el toast después de 3 segundos
  setTimeout(() => {
    toast.remove();
  }, 3000);
}

// Función para agregar al carrito
function agregarAlCarrito(idProducto) {
  let carrito = obtenerCarrito();
  const producto = productos.find(p => p.id === idProducto);

  if (!producto) return;

  const existe = carrito.find(item => item.id === producto.id);
  if (existe) {
    existe.cantidad++;
  } else {
    carrito.push({
      id: producto.id,
      nombre: producto.nombre,
      precio: producto.precio,
      imagen: producto.imagen,
      cantidad: 1
    });
  }

  guardarCarrito(carrito);
  actualizarContadorCarrito();
  mostrarToast(`${producto.nombre} agregado al carrito 🛒`);
}

// Listener para botones "Agregar al carrito"
productList.addEventListener('click', (e) => {
  if(e.target.classList.contains('add-cart')) {
    const idProducto = e.target.getAttribute('data-id');
    agregarAlCarrito(idProducto);
  }
});

actualizarContadorCarrito();
