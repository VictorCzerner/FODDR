(() => {
  const DEFAULT_LANG = "pt-BR";
  const LANG_KEY = "foddr_lang";
  const SUPPORTED_LANGS = ["pt-BR", "en-US"];

  const translations = {
    "pt-BR": {
      "title.index": "FODDR | SBCs Solutions",
      "title.sbc": "FODDR | Catálogo de SBCs",
      "title.sbc_set": "FODDR | Detalhes do Set",
      "nav.calculator": "Calculadora",
      "nav.sbcs": "SBCs",
      "lang.label": "Idioma",
      "lang.pt": "Português",
      "lang.en": "English",
      "index.heading": "Soluções SBCs",
      "index.squads_label": "ELENCOS:",
      "index.number_label": "N°:",
      "index.base_fodder_title": "Forragem base",
      "index.base_fodder_desc": "Atualize a quantidade de cartas disponíveis em cada OVR.",
      "index.totw_fodder_title": "Forragem TOTW",
      "index.totw_fodder_desc": "Opcionalmente informe cartas TOTW disponíveis.",
      "index.optional_badge": "OPCIONAL",
      "index.submit": "Enviar",
      "index.results_title": "Resultados",
      "index.results_prompt": "Informe os dados e clique em Enviar para ver os elencos.",
      "index.prev_squad": "Elenco anterior",
      "index.next_squad": "Próximo elenco",
      "index.totals_title": "Totais por OVR",
      "index.totals_empty": "Nenhum total calculado ainda.",
      "index.informs_title": "Informs usados",
      "index.informs_empty": "Nenhum Inform utilizado.",
      "index.partial_title": "Resultado parcial",
      "index.partial_desc": "Não conseguimos completar todos os elencos. Veja abaixo as sugestões para montar o restante.",
      "index.next_steps_title": "Próximos passos",
      "index.next_steps_desc": "Utilize as combinações sugeridas para concluir os elencos pendentes.",
      "index.suggestions_title": "Sugestões completas",
      "index.have_title": "Tenho",
      "index.missing_title": "Falta",
      "index.pending_empty": "Nenhum elenco pendente.",
      "index.no_info": "Nenhuma informação disponível.",
      "sbc.heading": "Catálogo de SBCs",
      "sbc.subtitle": "Explore as categorias disponíveis e encontre os desafios ativos.",
      "sbc.refresh": "Atualizar",
      "sbc.refresh_loading": "Atualizando...",
      "sbc.loading": "Carregando SBCs…",
      "sbc.tabs_label": "Categorias de SBC",
      "sbc.no_categories": "Nenhuma categoria encontrada no momento.",
      "sbc.load_error": "Não foi possível carregar os SBCs agora. Tente novamente mais tarde.",
      "sbc.card_open": "Abrir detalhes do set",
      "sbc.card_open_named": "Abrir detalhes do set {name}",
      "sbc.card_untitled": "Set sem nome",
      "sbc.card_set_id": "Set ID: ",
      "sbc.card_expires": "Expira em ",
      "sbc.card_no_date": "Sem data",
      "sbc.card_view_details": "Ver detalhes",
      "sbc.eyebrow": "Squad Building Challenges",
      "sbc.category_label": "Categoria {index}",
      "sbc.no_sets_in_category": "Nenhum set disponível nesta categoria.",
      "set.heading": "Detalhes do set",
      "set.subtitle_loading": "Carregando informações…",
      "set.back_catalog": "← Voltar ao catálogo",
      "set.loading_details": "Carregando detalhes do set…",
      "set.resolve_title": "Resolver este set",
      "set.resolve_desc": "Informe sua forragem disponível e resolva automaticamente os desafios deste set.",
      "set.resolve_button": "Resolver set",
      "set.status_default": "Informe a forragem e clique em \"Resolver set\".",
      "set.results_prompt": "Informe a forragem acima e clique em \"Resolver set\" para ver as soluções.",
      "set.meta_set_id": "Set ID",
      "set.meta_category": "Categoria: ",
      "set.meta_category_unknown": "Não informada",
      "set.meta_expires": "Expira em",
      "set.challenge": "Desafio {id}",
      "set.meta_formation": "Formação",
      "set.no_challenges": "Nenhum desafio encontrado para este set.",
      "set.not_informed": "Set não informado.",
      "set.not_informed_detail": "Set não informado. Volte ao catálogo e selecione um set.",
      "set.resolved": "Set resolvido! {count} elenco(s) encontrados.",
      "set.error_loading_squads": "Erro ao carregar elencos.",
      "set.error_loading_data": "Erro ao carregar dados.",
      "set.untitled": "Set sem nome",
      "common.optional": "OPCIONAL",
      "common.results_title": "Resultados",
      "common.totals_title": "Totais por OVR",
      "common.totals_empty": "Nenhum total calculado ainda.",
      "common.informs_title": "Informs usados",
      "common.informs_empty": "Nenhum Inform utilizado.",
      "common.partial_title": "Resultado parcial",
      "common.partial_desc": "Não conseguimos completar todos os elencos. Veja abaixo as sugestões para montar o restante.",
      "common.next_steps_title": "Próximos passos",
      "common.next_steps_desc": "Utilize as combinações sugeridas para concluir os elencos pendentes.",
      "common.suggestions_title": "Sugestões completas",
      "common.have_title": "Tenho",
      "common.missing_title": "Falta",
      "common.pending_empty": "Nenhum elenco pendente.",
      "common.no_info": "Nenhuma informação disponível.",
      "common.ovr_label": "OVR {rating}",
      "common.inform_single": "1 Inform",
      "common.inform_multi": "{count} Informs",
      "common.player_single": "1 jogador",
      "common.player_multi": "{count} jogadores",
      "common.no_player_for_squad": "Nenhum jogador informado para este elenco.",
      "common.no_data": "Nenhum dado disponível.",
      "common.squad_label": "Elenco {index}",
      "common.status_pending": "Pendente",
      "common.status_complete": "Completo",
      "common.target_ovr": "OVR alvo {ovr}",
      "common.avg_ovr": "OVR médio {ovr}",
      "common.no_complete_squads": "Nenhum elenco completo encontrado.",
      "common.no_squad_returned": "Nenhum elenco foi retornado para os dados informados.",
      "common.complete_squads_found": "Elencos completos encontrados: {count}",
      "common.no_squad_loaded": "Nenhum elenco carregado.",
      "common.loading": "Carregando...",
      "common.loading_squads": "Buscando elencos...",
      "common.error_ovr_required": "Erro: informe o OVR de cada squad.",
      "common.error_fill_ovr": "Preencha todos os OVRs antes de continuar.",
      "common.error_request": "Erro na requisição",
      "common.error_loading_squads": "Não foi possível carregar os elencos.",
      "common.informs_label": "Informs",
      "common.partial_summary": "Resultado parcial: {completed} de {total} elenco(s) completos.",
      "common.error_response": "Erro {status}",
      "common.error_message": "Erro: {message}",
      "common.suggestion_prefix": "Sugestão",
      "common.have_prefix": "Tenho",
      "common.missing_prefix": "Falta",
      "common.range_label": "Intervalo {start} - {end}",
      "set.resolving": "Resolvendo set…",
      "set.resolve_error_retry": "Não foi possível resolver o set agora. Tente novamente.",
      "set.resolve_error": "Erro ao resolver set.",
      "set.requirements_none": "Sem requisitos informados.",
      "set.requirement_label": "Requisito",
      "set.requirement_value": "Valor: {value}",
      "set.requirement_team_rating": "Team Rating",
      "set.requirement_nation_label": "Nation",
      "set.requirement_league_label": "League",
      "set.requirement_club_label": "Club",
      "set.requirement_named_count": "{name} ({count})",
      "set.requirement_count_only": "({count})",
      "set.requirement_nation": "Nação #{id}",
      "set.requirement_league": "Liga #{id}",
      "set.requirement_club": "Clube #{id}",
      "set.requirement_no_details": "Sem detalhes adicionais",
      "set.challenge_repeatable": "Repetível",
      "set.challenge_single": "Único",
      "set.category_label": "Categoria: {name}",
      "set.category_unknown_detail": "Categoria não informada.",
      "set.id_missing": "ID do set ausente.",
      "set.load_details_error": "Não foi possível carregar os detalhes agora. Tente novamente mais tarde."
    },
    "en-US": {
      "title.index": "FODDR | SBCs Solutions",
      "title.sbc": "FODDR | SBC Catalog",
      "title.sbc_set": "FODDR | Set Details",
      "nav.calculator": "Calculator",
      "nav.sbcs": "SBCs",
      "lang.label": "Language",
      "lang.pt": "Portuguese",
      "lang.en": "English",
      "index.heading": "SBCs Solutions",
      "index.squads_label": "SQUADS:",
      "index.number_label": "No.:",
      "index.base_fodder_title": "Base fodder",
      "index.base_fodder_desc": "Update the amount of cards available in each OVR.",
      "index.totw_fodder_title": "TOTW fodder",
      "index.totw_fodder_desc": "Optionally add available TOTW cards.",
      "index.optional_badge": "OPTIONAL",
      "index.submit": "Submit",
      "index.results_title": "Results",
      "index.results_prompt": "Enter the data and click Submit to see the squads.",
      "index.prev_squad": "Previous squad",
      "index.next_squad": "Next squad",
      "index.totals_title": "Totals by OVR",
      "index.totals_empty": "No totals calculated yet.",
      "index.informs_title": "Informs used",
      "index.informs_empty": "No Inform used.",
      "index.partial_title": "Partial result",
      "index.partial_desc": "We could not complete all squads. See the suggestions below to finish the rest.",
      "index.next_steps_title": "Next steps",
      "index.next_steps_desc": "Use the suggested combinations to finish the pending squads.",
      "index.suggestions_title": "Complete suggestions",
      "index.have_title": "Have",
      "index.missing_title": "Missing",
      "index.pending_empty": "No pending squad.",
      "index.no_info": "No information available.",
      "sbc.heading": "SBC Catalog",
      "sbc.subtitle": "Explore available categories and find active challenges.",
      "sbc.refresh": "Refresh",
      "sbc.refresh_loading": "Refreshing...",
      "sbc.loading": "Loading SBCs…",
      "sbc.tabs_label": "SBC categories",
      "sbc.no_categories": "No categories found right now.",
      "sbc.load_error": "Couldn't load SBCs right now. Please try again later.",
      "sbc.card_open": "Open set details",
      "sbc.card_open_named": "Open set details for {name}",
      "sbc.card_untitled": "Untitled set",
      "sbc.card_set_id": "Set ID",
      "sbc.card_expires": "Expires on",
      "sbc.card_no_date": "No date",
      "sbc.card_view_details": "View details",
      "sbc.eyebrow": "Squad Building Challenges",
      "sbc.category_label": "Category {index}",
      "sbc.no_sets_in_category": "No set available in this category.",
      "set.heading": "Set details",
      "set.subtitle_loading": "Loading information…",
      "set.back_catalog": "← Back to catalog",
      "set.loading_details": "Loading set details…",
      "set.resolve_title": "Solve this set",
      "set.resolve_desc": "Enter your available fodder and automatically solve this set's challenges.",
      "set.resolve_button": "Solve set",
      "set.status_default": "Enter fodder and click \"Solve set\".",
      "set.results_prompt": "Enter the fodder above and click \"Solve set\" to see the solutions.",
      "set.meta_set_id": "Set ID: ",
      "set.meta_category": "Category: ",
      "set.meta_category_unknown": "Not informed",
      "set.meta_expires": "Expires on ",
      "set.challenge": "Challenge {id}",
      "set.meta_formation": "Formation",
      "set.no_challenges": "No challenge found for this set.",
      "set.not_informed": "Set not provided.",
      "set.not_informed_detail": "Set not provided. Go back to the catalog and select a set.",
      "set.resolved": "Set solved! {count} squad(s) found.",
      "set.error_loading_squads": "Error loading squads.",
      "set.error_loading_data": "Error loading data.",
      "set.untitled": "Untitled set",
      "common.optional": "OPTIONAL",
      "common.results_title": "Results",
      "common.totals_title": "Totals by OVR",
      "common.totals_empty": "No totals calculated yet.",
      "common.informs_title": "Informs used",
      "common.informs_empty": "No Inform used.",
      "common.partial_title": "Partial result",
      "common.partial_desc": "We could not complete all squads. See the suggestions below to finish the rest.",
      "common.next_steps_title": "Next steps",
      "common.next_steps_desc": "Use the suggested combinations to finish the pending squads.",
      "common.suggestions_title": "Complete suggestions",
      "common.have_title": "Have",
      "common.missing_title": "Missing",
      "common.pending_empty": "No pending squad.",
      "common.no_info": "No information available.",
      "common.ovr_label": "OVR {rating}",
      "common.inform_single": "1 Inform",
      "common.inform_multi": "{count} Informs",
      "common.player_single": "1 player",
      "common.player_multi": "{count} players",
      "common.no_player_for_squad": "No player informed for this squad.",
      "common.no_data": "No data available.",
      "common.squad_label": "Squad {index}",
      "common.status_pending": "Pending",
      "common.status_complete": "Complete",
      "common.target_ovr": "Target OVR {ovr}",
      "common.avg_ovr": "Average OVR {ovr}",
      "common.no_complete_squads": "No complete squad found.",
      "common.no_squad_returned": "No squad returned for the provided data.",
      "common.complete_squads_found": "Complete squads found: {count}",
      "common.no_squad_loaded": "No squad loaded.",
      "common.loading": "Loading...",
      "common.loading_squads": "Loading squads...",
      "common.error_ovr_required": "Error: enter the OVR for each squad.",
      "common.error_fill_ovr": "Fill all OVRs before continuing.",
      "common.error_request": "Request error",
      "common.error_loading_squads": "Couldn't load the squads.",
      "common.informs_label": "Informs",
      "common.partial_summary": "Partial result: {completed} of {total} squad(s) complete.",
      "common.error_response": "Error {status}",
      "common.error_message": "Error: {message}",
      "common.suggestion_prefix": "Suggestion",
      "common.have_prefix": "Have",
      "common.missing_prefix": "Missing",
      "common.range_label": "Range {start} - {end}",
      "set.resolving": "Solving set…",
      "set.resolve_error_retry": "Couldn't solve the set right now. Please try again.",
      "set.resolve_error": "Error solving set.",
      "set.requirements_none": "No requirements provided.",
      "set.requirement_label": "Requirement",
      "set.requirement_value": "Value: {value}",
      "set.requirement_team_rating": "Team Rating",
      "set.requirement_nation_label": "Nation",
      "set.requirement_league_label": "League",
      "set.requirement_club_label": "Club",
      "set.requirement_named_count": "{name} ({count})",
      "set.requirement_count_only": "({count})",
      "set.requirement_nation": "Nation #{id}",
      "set.requirement_league": "League #{id}",
      "set.requirement_club": "Club #{id}",
      "set.requirement_no_details": "No additional details",
      "set.challenge_repeatable": "Repeatable",
      "set.challenge_single": "Single",
      "set.category_label": "Category: {name}",
      "set.category_unknown_detail": "Category not informed.",
      "set.id_missing": "Missing set ID.",
      "set.load_details_error": "Couldn't load the details right now. Try again later."
    }
  };

  let currentLang = DEFAULT_LANG;

  const interpolate = (template, vars) =>
    template.replace(/\{(\w+)\}/g, (match, key) => {
      if (vars && Object.prototype.hasOwnProperty.call(vars, key)) {
        return String(vars[key]);
      }
      return match;
    });

  const t = (key, vars) => {
    const table = translations[currentLang] || translations[DEFAULT_LANG];
    const fallback = translations[DEFAULT_LANG] || {};
    const template = table[key] || fallback[key] || key;
    return interpolate(template, vars);
  };

  const applyTranslations = (root = document) => {
    root.querySelectorAll("[data-i18n]").forEach((el) => {
      const key = el.getAttribute("data-i18n");
      if (!key) return;
      const useHtml = el.getAttribute("data-i18n-html") === "true";
      const text = t(key);
      if (useHtml) {
        el.innerHTML = text;
      } else {
        el.textContent = text;
      }
    });

    const attrMap = {
      placeholder: "data-i18n-placeholder",
      title: "data-i18n-title",
      "aria-label": "data-i18n-aria-label"
    };

    Object.entries(attrMap).forEach(([attr, dataAttr]) => {
      root.querySelectorAll(`[${dataAttr}]`).forEach((el) => {
        const key = el.getAttribute(dataAttr);
        if (!key) return;
        el.setAttribute(attr, t(key));
      });
    });
  };

  const setLanguage = (lang) => {
    if (!SUPPORTED_LANGS.includes(lang)) return;
    currentLang = lang;
    localStorage.setItem(LANG_KEY, lang);
    document.documentElement.lang = lang;
    applyTranslations();
    const select = document.getElementById("languageSelect");
    if (select && select.value !== lang) {
      select.value = lang;
    }
  };

  const getCurrentLocale = () => currentLang;

  const init = () => {
    const stored = localStorage.getItem(LANG_KEY);
    if (stored && SUPPORTED_LANGS.includes(stored)) {
      currentLang = stored;
    }
    document.documentElement.lang = currentLang;
    const select = document.getElementById("languageSelect");
    if (select) {
      select.value = currentLang;
      select.addEventListener("change", (event) => {
        setLanguage(event.target.value);
      });
    }
    applyTranslations();
  };

  document.addEventListener("DOMContentLoaded", init);

  window.t = t;
  window.applyTranslations = applyTranslations;
  window.setLanguage = setLanguage;
  window.getCurrentLocale = getCurrentLocale;
})();
