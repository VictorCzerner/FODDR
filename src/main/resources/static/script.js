const RATING_START = 79;
const RATING_END = 93;
const forragemValues = Array(RATING_END - RATING_START + 1).fill(0);

const form = document.getElementById("sbcForm");
const resultadoBox = document.getElementById("resultado");
const hiddenForragem = document.getElementById("forragem");
const ratingCols = document.querySelectorAll(".rating-col");
const ovrContainer = document.getElementById("ovrContainer");
const addOvrBtn = document.getElementById("addOvr");
const numElencosInput = document.getElementById("numElencos");

const syncForragem = () => {
  hiddenForragem.value = forragemValues.join(",");
};

ratingCols.forEach((col, index) => {
  const valueEl = col.querySelector(".cell-value");
  const plusBtn = col.querySelector(".cell-plus");
  const minusBtn = col.querySelector(".cell-minus");

  plusBtn.addEventListener("click", () => {
    forragemValues[index] += 1;
    valueEl.textContent = forragemValues[index];
    syncForragem();
  });

  minusBtn.addEventListener("click", () => {
    if (forragemValues[index] === 0) return;
    forragemValues[index] -= 1;
    valueEl.textContent = forragemValues[index];
    syncForragem();
  });
});

syncForragem();

const attachOvrInputHandlers = (input) => {
  input.addEventListener("keydown", (event) => {
    if (event.key === "Backspace" && input.value === "" && ovrContainer.children.length > 1) {
      event.preventDefault();
      ovrContainer.removeChild(input);
    }
  });
};

ovrContainer.querySelectorAll(".ovr-input").forEach(attachOvrInputHandlers);

const createOvrInput = (value = 84) => {
  const input = document.createElement("input");
  input.type = "number";
  input.className = "ovr-input";
  input.min = "60";
  input.max = "99";
  input.value = value;
  attachOvrInputHandlers(input);
  return input;
};

addOvrBtn.addEventListener("click", () => {
  ovrContainer.appendChild(createOvrInput());
});

form.addEventListener("submit", async (e) => {
  e.preventDefault();

  const ovrs = Array.from(ovrContainer.querySelectorAll(".ovr-input"))
    .map((input) => Number(input.value))
    .filter((value) => !Number.isNaN(value) && value > 0);

  if (ovrs.length === 0) {
    resultadoBox.textContent = "Erro: informe pelo menos um OVR.";
    return;
  }

  const numElencos = Number.parseInt(numElencosInput.value, 10) || ovrs.length;

  const data = {
    sbc: { numElencos, ovrs },
    forragem: [...forragemValues]
  };

  resultadoBox.textContent = "Carregando...";

  try {
    const response = await fetch("http://localhost:8080/sbc/encontrar", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data)
    });

    if (!response.ok) throw new Error("Erro na requisição");
    const resultado = await response.json();
    resultadoBox.textContent = JSON.stringify(resultado, null, 2);
  } catch (err) {
    resultadoBox.textContent = "Erro: " + err.message;
  }
});
