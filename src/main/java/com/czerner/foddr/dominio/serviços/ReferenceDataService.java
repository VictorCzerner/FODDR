package com.czerner.foddr.dominio.serviços;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.czerner.foddr.dominio.dados.ReferenceDataRepository;

@Service
public class ReferenceDataService {

    private final ReferenceDataRepository repository;
    private final Map<Integer, Optional<String>> nationCache = new ConcurrentHashMap<>();
    private final Map<Integer, Optional<String>> leagueCache = new ConcurrentHashMap<>();
    private final Map<Integer, Optional<String>> clubCache = new ConcurrentHashMap<>();

    public ReferenceDataService(ReferenceDataRepository repository) {
        this.repository = repository;
    }

    public String getNationName(Integer nationId) {
        return resolveCached(nationId, nationCache, repository::findNationName);
    }

    public String getLeagueName(Integer leagueId) {
        return resolveCached(leagueId, leagueCache, repository::findLeagueName);
    }

    public String getClubName(Integer clubId) {
        return resolveCached(clubId, clubCache, repository::findClubName);
    }

    private String resolveCached(
            Integer id,
            Map<Integer, Optional<String>> cache,
            java.util.function.Function<Integer, String> lookup
    ) {
        if (id == null) {
            return null;
        }
        Optional<String> cached = cache.computeIfAbsent(id, (key) -> Optional.ofNullable(lookup.apply(key)));
        return cached.orElse(null);
    }
}
