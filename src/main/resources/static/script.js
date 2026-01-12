const RATING_START = 79;
const RATING_END = 93;
const DEFAULT_FORRAGEM = {
  79: 10,
  80: 10,
  81: 10,
  82: 10,
  83: 10,
  84: 10,
  85: 10,
  86: 10,
  87: 10,
  88: 10,
  89: 10,
  90: 10,
  91: 10,
  92: 10,
  93: 10
};

const TOTAL_RATINGS = RATING_END - RATING_START + 1;
const DEFAULT_TOTW = {
  79: 0,
  80: 0,
  81: 0,
  82: 0,
  83: 0,
  84: 0,
  85: 0,
  86: 0,
  87: 0,
  88: 0,
  89: 0,
  90: 0,
  91: 0,
  92: 0,
  93: 0
};
const forragemValues = Array(TOTAL_RATINGS).fill(0);
const forragemTotwValues = Array(TOTAL_RATINGS).fill(0);

Object.entries(DEFAULT_FORRAGEM).forEach(([rating, value]) => {
  const idx = Number(rating) - RATING_START;
  if (idx >= 0 && idx < forragemValues.length) {
    forragemValues[idx] = value;
  }
});

Object.entries(DEFAULT_TOTW).forEach(([rating, value]) => {
  const idx = Number(rating) - RATING_START;
  if (idx >= 0 && idx < forragemTotwValues.length) {
    forragemTotwValues[idx] = value;
  }
});

const form = document.getElementById("sbcForm");
const resultadoBox = document.getElementById("resultado");
const hiddenForragem = document.getElementById("forragem");
const ratingGrid = document.getElementById("ratingGrid");
const totwGrid = document.getElementById("totwGrid");
const ovrContainer = document.getElementById("ovrContainer");
const addOvrBtn = document.getElementById("addOvr");
const numElencosInput = document.getElementById("numElencos");
const resultTrack = document.getElementById("resultTrack");
const resultPrev = document.getElementById("resultPrev");
const resultNext = document.getElementById("resultNext");
const resultPagination = document.getElementById("resultPagination");
const resultTotals = document.getElementById("resultTotals");
const resultInforms = document.getElementById("resultInforms");
const partialAlert = document.getElementById("partialAlert");
const faltantesWrapper = document.getElementById("faltantesWrapper");
const faltantesLista = document.getElementById("faltantesLista");
const faltantesPositivo = document.getElementById("faltantesPositivo");
const faltantesNegativo = document.getElementById("faltantesNegativo");

const resultState = {
  currentIndex: 0,
  cards: []
};

const normalizeCounts = (counts) => {
  const normalized = Array(TOTAL_RATINGS).fill(0);
  if (!Array.isArray(counts)) return normalized;
  counts.forEach((value, index) => {
    if (index < normalized.length) {
      normalized[index] = Number(value) || 0;
    }
  });
  return normalized;
};

const setPartialAlertVisible = (visible) => {
  if (!partialAlert) return;
  partialAlert.classList.toggle("is-hidden", !visible);
};

const showResultPlaceholder = (message) => {
  resultTrack.innerHTML = "";
  const placeholder = document.createElement("div");
  placeholder.className = "composition-empty";
  placeholder.textContent = message;
  resultTrack.appendChild(placeholder);
  resultPagination.innerHTML = "";
  resultState.cards = [];
  resultState.currentIndex = 0;
  resultPrev.disabled = true;
  resultNext.disabled = true;
  resultTrack.style.transform = "translateX(0)";
  renderTotals();
  renderInformsSummary();
  setPartialAlertVisible(false);
  renderFaltantesSection(null, false);
};

const updateNavigation = () => {
  const total = resultState.cards.length;
  resultPrev.disabled = total <= 1 || resultState.currentIndex === 0;
  resultNext.disabled = total <= 1 || resultState.currentIndex === total - 1;
  resultTrack.style.transform = `translateX(-${resultState.currentIndex * 100}%)`;
};

const renderPagination = () => {
  resultPagination.innerHTML = "";

  if (resultState.cards.length <= 1) return;

  resultState.cards.forEach((_, index) => {
    const dot = document.createElement("button");
    dot.type = "button";
    dot.className = `result-dot${index === resultState.currentIndex ? " is-active" : ""}`;
    dot.addEventListener("click", () => {
      resultState.currentIndex = index;
      updateNavigation();
      renderPagination();
    });
    resultPagination.appendChild(dot);
  });
};

const renderTotals = (totalCounts) => {
  if (!resultTotals) return;
  resultTotals.innerHTML = "";

  if (!Array.isArray(totalCounts) || totalCounts.length === 0) {
    const empty = document.createElement("p");
    empty.className = "totals-empty";
    empty.textContent = "Nenhum total calculado ainda.";
    resultTotals.appendChild(empty);
    return;
  }

  const list = document.createElement("ul");
  list.className = "composition-list totals-list";
  let hasContent = false;

  totalCounts.forEach((count = 0, index) => {
    if (index >= TOTAL_RATINGS) return;
    if (!count) return;
    hasContent = true;
    const rating = RATING_START + index;
    const item = document.createElement("li");
    item.className = "composition-item totals-item";

    const ratingSpan = document.createElement("span");
    ratingSpan.textContent = `OVR ${rating}`;

    const countSpan = document.createElement("span");
    countSpan.textContent = formatPlayerCount(count);

    item.append(ratingSpan, countSpan);
    list.appendChild(item);
  });

  if (!hasContent) {
    const empty = document.createElement("p");
    empty.className = "totals-empty";
    empty.textContent = "Nenhum total calculado ainda.";
    resultTotals.appendChild(empty);
    return;
  }

  resultTotals.appendChild(list);
};

const renderInformsSummary = (informsList) => {
  if (!resultInforms) return;
  resultInforms.innerHTML = "";

  if (!Array.isArray(informsList) || informsList.length === 0) {
    const empty = document.createElement("p");
    empty.className = "totals-empty";
    empty.textContent = "Nenhum Inform utilizado.";
    resultInforms.appendChild(empty);
    return;
  }

  const totals = Array(TOTAL_RATINGS).fill(0);
  let hasData = false;

  informsList.forEach((counts = []) => {
    if (!Array.isArray(counts)) return;
    counts.forEach((value, index) => {
      if (index >= TOTAL_RATINGS) return;
      const numeric = Number(value) || 0;
      if (numeric > 0) hasData = true;
      totals[index] += numeric;
    });
  });

  if (!hasData) {
    const empty = document.createElement("p");
    empty.className = "totals-empty";
    empty.textContent = "Nenhum Inform utilizado.";
    resultInforms.appendChild(empty);
    return;
  }

  const list = document.createElement("ul");
  list.className = "composition-list totals-list";

  totals.forEach((count, index) => {
    if (!count) return;
    const rating = RATING_START + index;
    const item = document.createElement("li");
    item.className = "composition-item totals-item";

    const ratingSpan = document.createElement("span");
    ratingSpan.textContent = `OVR ${rating}`;

    const countSpan = document.createElement("span");
    countSpan.textContent = count === 1 ? "1 Inform" : `${count} Informs`;

    item.append(ratingSpan, countSpan);
    list.appendChild(item);
  });

  resultInforms.appendChild(list);
};

const goToPage = (index) => {
  if (index < 0 || index >= resultState.cards.length) return;
  resultState.currentIndex = index;
  updateNavigation();
  renderPagination();
};

resultPrev.addEventListener("click", () => {
  goToPage(resultState.currentIndex - 1);
});

resultNext.addEventListener("click", () => {
  goToPage(resultState.currentIndex + 1);
});

const formatPlayerCount = (value) => (value === 1 ? "1 jogador" : `${value} jogadores`);

const buildCompositionList = (counts = []) => {
  const normalized = normalizeCounts(counts);
  const list = document.createElement("ul");
  list.className = "composition-list";
  let hasContent = false;

  normalized.forEach((count, index) => {
    if (!count) return;
    hasContent = true;
    const rating = RATING_START + index;
    const item = document.createElement("li");
    item.className = "composition-item";

    const ratingSpan = document.createElement("span");
    ratingSpan.textContent = `OVR ${rating}`;

    const countSpan = document.createElement("span");
    countSpan.textContent = formatPlayerCount(count);

    item.append(ratingSpan, countSpan);
    list.appendChild(item);
  });

  if (!hasContent) {
    const empty = document.createElement("div");
    empty.className = "composition-empty";
    empty.textContent = "Nenhum jogador informado para este elenco.";
    return empty;
  }

  return list;
};

const buildInformsList = (counts = []) => {
  const normalized = normalizeCounts(counts);
  const list = document.createElement("ul");
  list.className = "composition-list";
  let hasContent = false;

  normalized.forEach((count, index) => {
    if (!count) return;
    hasContent = true;
    const rating = RATING_START + index;
    const item = document.createElement("li");
    item.className = "composition-item";

    const ratingSpan = document.createElement("span");
    ratingSpan.textContent = `OVR ${rating}`;

    const countLabel = count === 1 ? "1 Inform" : `${count} Informs`;
    const countSpan = document.createElement("span");
    countSpan.textContent = countLabel;

    item.append(ratingSpan, countSpan);
    list.appendChild(item);
  });

  if (!hasContent) {
    const empty = document.createElement("p");
    empty.className = "inform-empty";
    empty.textContent = "Nenhum Inform utilizado.";
    return empty;
  }

  return list;
};

const hasInformUsage = (counts = []) => {
  if (!Array.isArray(counts)) return false;
  return counts.some((value) => Number(value) > 0);
};

const createInformBlock = (counts) => {
  const block = document.createElement("div");
  block.className = "inform-block";

  const title = document.createElement("h4");
  title.textContent = "Informs usados";
  block.appendChild(title);

  if (!hasInformUsage(counts)) {
    const empty = document.createElement("p");
    empty.className = "inform-empty";
    empty.textContent = "Nenhum Inform utilizado.";
    block.appendChild(empty);
    return block;
  }

  block.appendChild(buildInformsList(counts));
  return block;
};

function renderFaltantesColumn(container, combos, labelPrefix) {
  if (!container) return;
  container.innerHTML = "";

  if (!Array.isArray(combos) || combos.length === 0) {
    const empty = document.createElement("p");
    empty.className = "totals-empty";
    empty.textContent = "Nenhum dado disponível.";
    container.appendChild(empty);
    return;
  }

  combos.forEach((counts, index) => {
    const card = document.createElement("article");
    card.className = "faltante-card";

    const title = document.createElement("h5");
    title.textContent = `${labelPrefix} ${index + 1}`;
    card.appendChild(title);

    card.appendChild(buildCompositionList(counts));
    container.appendChild(card);
  });
}

function renderFaltantesSection(payload, shouldShow) {
  if (!faltantesWrapper) return;

  const faltantes = payload && Array.isArray(payload.elencosFaltantes) ? payload.elencosFaltantes : [];
  const positivos = payload && Array.isArray(payload.elencosFaltantesPositivo) ? payload.elencosFaltantesPositivo : [];
  const negativos = payload && Array.isArray(payload.elencosFaltantesNegativo) ? payload.elencosFaltantesNegativo : [];

  const hasData = shouldShow && (faltantes.length || positivos.length || negativos.length);
  faltantesWrapper.classList.toggle("is-hidden", !hasData);

  if (!hasData) {
    [faltantesLista, faltantesPositivo, faltantesNegativo].forEach((container) => {
      if (container) container.innerHTML = "";
    });
    return;
  }

  renderFaltantesColumn(faltantesLista, faltantes, "Sugestão");
  renderFaltantesColumn(faltantesPositivo, positivos, "Tenho");
  renderFaltantesColumn(faltantesNegativo, negativos, "Falta");
}

const createResultCard = (counts, index, targetOvr, options = {}) => {
  const normalizedCounts = normalizeCounts(counts);
  const isPending = Boolean(options.isPending);
  const informsCounts = options.informs;
  const card = document.createElement("article");
  card.className = `result-card${isPending ? " result-card--pending" : ""}`;

  const title = document.createElement("h3");
  title.textContent = `Elenco ${index + 1}`;
  card.appendChild(title);

  const statusBadge = document.createElement("span");
  statusBadge.className = `result-status-badge ${isPending ? "is-warning" : "is-success"}`;
  statusBadge.textContent = isPending ? "Pendente" : "Completo";
  card.appendChild(statusBadge);

  const metaContainer = document.createElement("div");
  metaContainer.className = "result-meta";

  const totalPlayers = normalizedCounts.reduce((acc, value) => acc + value, 0);
  const weightedSum = normalizedCounts.reduce((acc, value, pos) => acc + value * (RATING_START + pos), 0);
  const average = totalPlayers ? (weightedSum / totalPlayers).toFixed(1) : "0.0";

  const firstRating = (() => {
    for (let i = 0; i < normalizedCounts.length; i++) {
      if (normalizedCounts[i]) return RATING_START + i;
    }
    return "--";
  })();

  const lastRating = (() => {
    for (let i = normalizedCounts.length - 1; i >= 0; i--) {
      if (normalizedCounts[i]) return RATING_START + i;
    }
    return "--";
  })();

  const ovrLabel = Number.isFinite(targetOvr) ? `OVR alvo ${targetOvr}` : `OVR médio ${average}`;
  const metaItems = [
    `${totalPlayers} jogadores`,
    ovrLabel,
    `Intervalo ${firstRating} - ${lastRating}`
  ];

  metaItems.forEach((label) => {
    const badge = document.createElement("span");
    badge.className = "result-meta__item";
    badge.textContent = label;
    metaContainer.appendChild(badge);
  });

  card.appendChild(metaContainer);

  const composition = buildCompositionList(normalizedCounts);
  card.appendChild(composition);

  if (hasInformUsage(informsCounts)) {
    card.appendChild(createInformBlock(informsCounts));
  }

  return card;
};

const renderSolutions = (payload, ovrs = []) => {
  const elencos = payload && Array.isArray(payload.elencosCompletos) ? payload.elencosCompletos : [];
  const informs = payload && Array.isArray(payload.informsUsado) ? payload.informsUsado : [];
  const targetOvrs = (() => {
    const elencosResp = payload && payload.sbc && Array.isArray(payload.sbc.elencos) ? payload.sbc.elencos : [];
    if (!elencosResp.length) return ovrs;

    // Restaura a ordem original usando originalIndex vindo do backend
    return elencosResp
      .slice()
      .sort((a, b) => Number(a.originalIndex ?? 0) - Number(b.originalIndex ?? 0))
      .map((elenco) => {
        const valor = elenco.ovr ?? elenco.OVR;
        return Number.isFinite(Number(valor)) ? Number(valor) : undefined;
      });
  })();

  if (elencos.length === 0) {
    resultadoBox.textContent = "Nenhum elenco completo encontrado.";
    showResultPlaceholder("Nenhum elenco foi retornado para os dados informados.");
    return;
  }

  resultTrack.innerHTML = "";
  resultState.cards = elencos.map((counts, index) =>
    createResultCard(counts, index, targetOvrs[index], {
      isPending: !Array.isArray(counts),
      informs: informs[index]
    })
  );
  resultState.cards.forEach((card) => resultTrack.appendChild(card));
  resultState.currentIndex = 0;

  const completedCount = elencos.filter((counts) => Array.isArray(counts)).length;
  const hasPending = completedCount !== elencos.length;
  resultadoBox.textContent = hasPending
    ? `Resultado parcial: ${completedCount} de ${elencos.length} elenco(s) completos.`
    : `Elencos completos encontrados: ${resultState.cards.length}`;

  updateNavigation();
  renderPagination();

  const totals = (() => {
    if (payload && Array.isArray(payload.total) && payload.total.length) {
      return normalizeCounts(payload.total);
    }
    return elencos.reduce((acc, counts) => {
      if (!Array.isArray(counts)) return acc;
      counts.forEach((value, index) => {
        if (index < acc.length) acc[index] += Number(value) || 0;
      });
      return acc;
    }, Array(TOTAL_RATINGS).fill(0));
  })();

  renderTotals(totals);
  renderInformsSummary(informs);
  setPartialAlertVisible(hasPending);
  renderFaltantesSection(payload, hasPending);
};

showResultPlaceholder("Nenhum elenco carregado.");

const syncForragem = () => {
  if (hiddenForragem) {
    hiddenForragem.value = forragemValues.join(",");
  }
};

const setupGridControls = (gridElement, values, onChange) => {
  if (!gridElement) return;
  const cols = gridElement.querySelectorAll(".rating-col");

  cols.forEach((col) => {
    const rating = Number(col.dataset.rating);
    const index = rating - RATING_START;
    if (Number.isNaN(index) || index < 0 || index >= values.length) return;

    const valueEl = col.querySelector(".cell-value");
    const plusBtn = col.querySelector(".cell-plus");
    const minusBtn = col.querySelector(".cell-minus");
    if (!valueEl) return;

    const refresh = () => {
      valueEl.textContent = values[index];
    };

    plusBtn?.addEventListener("click", () => {
      values[index] += 1;
      refresh();
      if (typeof onChange === "function") onChange();
    });

    minusBtn?.addEventListener("click", () => {
      if (values[index] === 0) return;
      values[index] -= 1;
      refresh();
      if (typeof onChange === "function") onChange();
    });

    refresh();
  });

  if (typeof onChange === "function") onChange();
};

setupGridControls(ratingGrid, forragemValues, syncForragem);
setupGridControls(totwGrid, forragemTotwValues);

const updateSquadMetadata = () => {
  const rows = Array.from(ovrContainer.querySelectorAll(".squad-row"));
  rows.forEach((row) => {
    const removeBtn = row.querySelector(".squad-row__remove");
    if (removeBtn) {
      removeBtn.disabled = rows.length === 1;
    }
  });
  if (numElencosInput) {
    numElencosInput.value = rows.length || 1;
  }
};

const createSquadRow = (ovr = 84, totw = 0) => {
  const row = document.createElement("div");
  row.className = "squad-row";

  const inputGroup = document.createElement("div");
  inputGroup.className = "squad-row__inputs";

  const ovrInput = document.createElement("input");
  ovrInput.type = "number";
  ovrInput.className = "ovr-input";
  ovrInput.min = "60";
  ovrInput.max = "99";
  ovrInput.value = ovr;
  inputGroup.appendChild(ovrInput);

  const totwWrapper = document.createElement("div");
  totwWrapper.className = "squad-row__totw";

  const totwLabel = document.createElement("span");
  totwLabel.textContent = "Informs";
  totwWrapper.appendChild(totwLabel);

  const totwInput = document.createElement("input");
  totwInput.type = "number";
  totwInput.className = "totw-input";
  totwInput.min = "0";
  totwInput.max = "11";
  totwInput.value = Math.max(0, Math.floor(Number(totw) || 0));
  totwWrapper.appendChild(totwInput);

  inputGroup.appendChild(totwWrapper);
  row.appendChild(inputGroup);

  const removeBtn = document.createElement("button");
  removeBtn.type = "button";
  removeBtn.className = "squad-row__remove";
  removeBtn.innerHTML = "&times;";
  removeBtn.addEventListener("click", () => {
    if (ovrContainer.querySelectorAll(".squad-row").length === 1) return;
    row.remove();
    updateSquadMetadata();
  });
  row.appendChild(removeBtn);

  return row;
};

const addSquadRow = (ovr = 84, totw = 0) => {
  ovrContainer.appendChild(createSquadRow(ovr, totw));
  updateSquadMetadata();
};

addOvrBtn.addEventListener("click", () => {
  addSquadRow();
});

addSquadRow();

form.addEventListener("submit", async (e) => {
  e.preventDefault();

  const squadRows = Array.from(ovrContainer.querySelectorAll(".squad-row"));
  const ovrs = [];
  const elencosPayload = [];
  let hasInvalid = false;

  squadRows.forEach((row) => {
    const ovrInput = row.querySelector(".ovr-input");
    const totwInput = row.querySelector(".totw-input");
    const ovrValor = Math.floor(Number(ovrInput?.value));

    if (Number.isNaN(ovrValor) || ovrValor <= 0) {
      hasInvalid = true;
      return;
    }

    const totwValor = Math.max(0, Math.floor(Number(totwInput?.value) || 0));
    ovrs.push(ovrValor);
    elencosPayload.push({ ovr: ovrValor, informs: totwValor });
  });

  if (hasInvalid || elencosPayload.length === 0) {
    resultadoBox.textContent = "Erro: informe o OVR de cada squad.";
    showResultPlaceholder("Preencha todos os OVRs antes de continuar.");
    return;
  }

  const data = {
    sbc: { elencos: elencosPayload },
    forragem: [...forragemValues],
    forragemTotw: [...forragemTotwValues]
  };

  resultadoBox.textContent = "Carregando...";
  showResultPlaceholder("Buscando elencos...");

  try {
    const response = await fetch("https://foddr.onrender.com/sbc/encontrar", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data)
    });

    if (!response.ok) throw new Error("Erro na requisição");
    const resultado = await response.json();
    renderSolutions(resultado, ovrs);
  } catch (err) {
    resultadoBox.textContent = "Erro: " + err.message;
    showResultPlaceholder("Não foi possível carregar os elencos.");
  }
});
