package pl.novabanana;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Wczytuje config/novabanana.json. Jak nie ma pliku, tworzy go z przykladami - edytuj do woli. */
public class EventConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("novabanana.json");

    public static List<FakeEvent> load() {
        try {
            if (Files.exists(FILE)) {
                List<FakeEvent> list = GSON.fromJson(Files.readString(FILE), new TypeToken<List<FakeEvent>>() {}.getType());
                if (list != null && !list.isEmpty()) return list;
            }
        } catch (Exception e) {
            System.err.println("[NovaBANANA] Blad configu, uzywam domyslnych: " + e);
        }
        List<FakeEvent> defaults = defaults();
        try { Files.writeString(FILE, GSON.toJson(defaults)); } catch (Exception ignored) {}
        return defaults;
    }

    private static List<FakeEvent> defaults() {
        List<FakeEvent> l = new ArrayList<>();
        l.add(new FakeEvent("Wyscig", "§e§lWYSCIG", "§fStart za 10 sekund!", List.of(
                "§8[§6EVENT§8] §fRozpoczyna sie §eWyscig§f!",
                "§8[§6EVENT§8] §fStart za §e10 §fsekund. Ustaw sie na lini startu!",
                "§8[§6EVENT§8] §a§lSTART!")));
        l.add(new FakeEvent("Loteria", "§b§lLOTERIA", "§fLosowanie nagrod", List.of(
                "§8[§6EVENT§8] §fLoteria rozpoczeta! Wpisz §e/loteria §fzeby dolaczyc.",
                "§8[§6EVENT§8] §fLosowanie za §e30 §fsekund.")));
        l.add(new FakeEvent("Kto pierwszy", "§d§lKTO PIERWSZY", "§fPrzepisz slowo z czatu", List.of(
                "§8[§6EVENT§8] §fKto pierwszy napisze §eBANAN §fwygrywa nagrode!")));
        l.add(new FakeEvent("Boss", "§c§lBOSS EVENT", "§fBoss pojawil sie na spawnie!", List.of(
                "§8[§6EVENT§8] §cBoss §fpojawil sie na spawnie!",
                "§8[§6EVENT§8] §fZbierzcie druzyne i pokonajcie go!")));
        return l;
    }
}
